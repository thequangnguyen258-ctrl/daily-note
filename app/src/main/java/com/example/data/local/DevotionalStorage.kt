package com.example.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.data.local.room.*
import com.example.data.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class DevotionalStorage(context: Context) {

    private val db = AppDatabase.getInstance(context)
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val prefs: SharedPreferences =
        context.getSharedPreferences("caysusong_sanctuary_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_CURRENT_USER = "current_user"
        private const val KEY_ACCOUNTS = "accounts"
        private const val KEY_SETTINGS = "settings"
    }

    init {
        initializeDefaultAccountsIfEmpty()
        syncToRoomDatabase()
    }

    private fun initializeDefaultAccountsIfEmpty() {
        if (!prefs.contains(KEY_ACCOUNTS)) {
            val defaultAccounts = mutableListOf<UserAccount>()
            defaultAccounts.add(
                UserAccount(
                    username = "admin",
                    password = "123",
                    fullName = "Quản Trị Viên",
                    isAdmin = true
                )
            )
            for (i in 1..20) {
                defaultAccounts.add(
                    UserAccount(
                        username = "nguoidung$i",
                        password = "12345678",
                        fullName = "Người Dùng $i",
                        isAdmin = false
                    )
                )
            }
            saveAccounts(defaultAccounts)
        }
    }

    private fun syncToRoomDatabase() {
        scope.launch {
            try {
                val accounts = getAllAccounts()
                val entities = accounts.map {
                    UserAccountEntity(
                        username = it.username,
                        password = it.password,
                        fullName = it.fullName,
                        isAdmin = it.isAdmin
                    )
                }
                db.userAccountDao().insertAllAccounts(entities)

                // Sync initial admin data to Room
                val adminJournals = loadJournals("admin")
                adminJournals.forEach { j ->
                    db.journalDao().insertJournal(
                        JournalEntity(
                            id = j.id,
                            username = "admin",
                            title = j.title,
                            content = j.content,
                            moodsCsv = j.moods.joinToString(","),
                            category = j.category,
                            associatedFruit = j.associatedFruit,
                            createdAt = j.createdAt
                        )
                    )
                }

                val adminChats = loadPrayerChats("admin")
                adminChats.forEach { c ->
                    db.prayerChatDao().insertChat(
                        PrayerChatEntity(
                            id = c.id,
                            username = "admin",
                            sender = c.sender,
                            text = c.text,
                            verse1925 = c.verse1925,
                            reference = c.reference,
                            associatedFruit = c.associatedFruit,
                            createdAt = c.createdAt
                        )
                    )
                }

                val adminTree = loadTreeState("admin")
                db.treeStateDao().saveTreeState(
                    TreeStateEntity(
                        username = "admin",
                        waterDrops = adminTree.waterDrops,
                        rootsCount = adminTree.rootsCount,
                        leavesCount = adminTree.leavesCount,
                        branchesCount = adminTree.branchesCount,
                        unlockedFruitsCsv = adminTree.unlockedFruits.joinToString(","),
                        level = adminTree.level,
                        isWilted = adminTree.isWilted,
                        lastWatered = adminTree.lastWatered
                    )
                )

                val adminHeart = loadHeartFieldState("admin")
                db.heartFieldDao().saveHeartField(
                    HeartFieldEntity(
                        username = "admin",
                        isNegative = adminHeart.isNegative,
                        negativityType = adminHeart.negativityType,
                        thornsLevel = adminHeart.thornsLevel,
                        amenLeavesCsv = adminHeart.amenLeaves.joinToString(","),
                        amenFruitsCsv = adminHeart.amenFruits.joinToString(","),
                        amenRootsCsv = adminHeart.amenRoots.joinToString(",")
                    )
                )

                val adminHome = loadHomeMessage("admin")
                db.homeMessageDao().saveHomeMessage(
                    HomeMessageEntity(
                        username = "admin",
                        recentProblem = adminHome.recentProblem,
                        verse1925 = adminHome.verse1925,
                        reference = adminHome.reference,
                        blessing = adminHome.blessing,
                        comfortText = adminHome.comfortText,
                        updatedAt = adminHome.updatedAt
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // --- Reactive Flow Accessors ---

    fun getJournalsFlow(username: String): Flow<List<JournalEntry>> {
        return db.journalDao().getJournalsForUser(username).map { list ->
            if (list.isEmpty()) {
                loadJournals(username)
            } else {
                list.map {
                    JournalEntry(
                        id = it.id,
                        title = it.title,
                        content = it.content,
                        moods = it.moodsCsv.split(",").filter { m -> m.isNotBlank() },
                        category = it.category,
                        associatedFruit = it.associatedFruit,
                        createdAt = it.createdAt
                    )
                }
            }
        }
    }

    fun getPrayerChatsFlow(username: String): Flow<List<PrayerMessage>> {
        return db.prayerChatDao().getChatsForUser(username).map { list ->
            if (list.isEmpty()) {
                loadPrayerChats(username)
            } else {
                list.map {
                    PrayerMessage(
                        id = it.id,
                        sender = it.sender,
                        text = it.text,
                        verse1925 = it.verse1925,
                        reference = it.reference,
                        associatedFruit = it.associatedFruit,
                        createdAt = it.createdAt
                    )
                }
            }
        }
    }

    // --- Accounts ---

    fun getAllAccounts(): List<UserAccount> {
        val jsonStr = prefs.getString(KEY_ACCOUNTS, null) ?: return emptyList()
        val list = mutableListOf<UserAccount>()
        try {
            val jsonArray = JSONArray(jsonStr)
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                list.add(
                    UserAccount(
                        username = obj.getString("username"),
                        password = obj.getString("password"),
                        fullName = obj.optString("fullName", obj.getString("username")),
                        isAdmin = obj.optBoolean("isAdmin", false)
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    fun saveAccounts(accounts: List<UserAccount>) {
        val jsonArray = JSONArray()
        for (acc in accounts) {
            val obj = JSONObject()
            obj.put("username", acc.username)
            obj.put("password", acc.password)
            obj.put("fullName", acc.fullName)
            obj.put("isAdmin", acc.isAdmin)
            jsonArray.put(obj)
        }
        prefs.edit().putString(KEY_ACCOUNTS, jsonArray.toString()).apply()

        // Sync to Room Database
        scope.launch {
            val entities = accounts.map {
                UserAccountEntity(it.username, it.password, it.fullName, it.isAdmin)
            }
            db.userAccountDao().insertAllAccounts(entities)
        }
    }

    fun updateAccount(account: UserAccount) {
        val accounts = getAllAccounts().toMutableList()
        val index = accounts.indexOfFirst { it.username.equals(account.username, ignoreCase = true) }
        if (index != -1) {
            accounts[index] = account
        } else {
            accounts.add(account)
        }
        saveAccounts(accounts)

        scope.launch {
            db.userAccountDao().insertAccount(
                UserAccountEntity(account.username, account.password, account.fullName, account.isAdmin)
            )
        }
    }

    fun addWhitelistedUser(username: String, initialPassword: String = "12345678"): Boolean {
        val accounts = getAllAccounts().toMutableList()
        if (accounts.any { it.username.equals(username, ignoreCase = true) }) {
            return false
        }
        val newAcc = UserAccount(
            username = username.lowercase().trim(),
            password = initialPassword,
            fullName = username,
            isAdmin = false
        )
        accounts.add(newAcc)
        saveAccounts(accounts)
        return true
    }

    fun removeUser(username: String): Boolean {
        if (username.equals("admin", ignoreCase = true)) return false
        val accounts = getAllAccounts().toMutableList()
        val removed = accounts.removeAll { it.username.equals(username, ignoreCase = true) }
        if (removed) {
            saveAccounts(accounts)
            scope.launch {
                db.userAccountDao().deleteAccountByUsername(username)
            }
        }
        return removed
    }

    fun getCurrentUsername(): String? {
        return prefs.getString(KEY_CURRENT_USER, null)
    }

    fun setCurrentUsername(username: String?) {
        prefs.edit().putString(KEY_CURRENT_USER, username).apply()
    }

    // --- Per-User Storage ---

    private fun userKey(key: String, username: String): String {
        return "user_${username.lowercase()}_$key"
    }

    fun loadTreeState(username: String): TreeState {
        val jsonStr = prefs.getString(userKey("tree_state", username), null) ?: return TreeState()
        return try {
            val obj = JSONObject(jsonStr)
            val fruitsArray = obj.optJSONArray("unlockedFruits")
            val fruits = mutableListOf<String>()
            if (fruitsArray != null) {
                for (i in 0 until fruitsArray.length()) {
                    fruits.add(fruitsArray.getString(i))
                }
            } else {
                fruits.addAll(listOf("love", "peace", "joy"))
            }
            TreeState(
                waterDrops = obj.optInt("waterDrops", 250),
                rootsCount = obj.optInt("rootsCount", 3),
                leavesCount = obj.optInt("leavesCount", 8),
                branchesCount = obj.optInt("branchesCount", 2),
                unlockedFruits = fruits,
                level = obj.optInt("level", 1),
                isWilted = obj.optBoolean("isWilted", false),
                lastWatered = obj.optLong("lastWatered", System.currentTimeMillis())
            )
        } catch (e: Exception) {
            TreeState()
        }
    }

    fun saveTreeState(username: String, state: TreeState) {
        val obj = JSONObject()
        obj.put("waterDrops", state.waterDrops)
        obj.put("rootsCount", state.rootsCount)
        obj.put("leavesCount", state.leavesCount)
        obj.put("branchesCount", state.branchesCount)
        val arr = JSONArray()
        state.unlockedFruits.forEach { arr.put(it) }
        obj.put("unlockedFruits", arr)
        obj.put("level", state.level)
        obj.put("isWilted", state.isWilted)
        obj.put("lastWatered", state.lastWatered)
        prefs.edit().putString(userKey("tree_state", username), obj.toString()).apply()

        scope.launch {
            db.treeStateDao().saveTreeState(
                TreeStateEntity(
                    username = username,
                    waterDrops = state.waterDrops,
                    rootsCount = state.rootsCount,
                    leavesCount = state.leavesCount,
                    branchesCount = state.branchesCount,
                    unlockedFruitsCsv = state.unlockedFruits.joinToString(","),
                    level = state.level,
                    isWilted = state.isWilted,
                    lastWatered = state.lastWatered
                )
            )
        }
    }

    fun loadHeartFieldState(username: String): HeartFieldState {
        val jsonStr = prefs.getString(userKey("heart_field", username), null) ?: return HeartFieldState()
        return try {
            val obj = JSONObject(jsonStr)
            val leaves = mutableListOf<String>()
            obj.optJSONArray("amenLeaves")?.let { for (i in 0 until it.length()) leaves.add(it.getString(i)) }
            val fruits = mutableListOf<String>()
            obj.optJSONArray("amenFruits")?.let { for (i in 0 until it.length()) fruits.add(it.getString(i)) }
            val roots = mutableListOf<String>()
            obj.optJSONArray("amenRoots")?.let { for (i in 0 until it.length()) roots.add(it.getString(i)) }

            HeartFieldState(
                isNegative = obj.optBoolean("isNegative", false),
                negativityType = obj.optString("negativityType", ""),
                thornsLevel = obj.optInt("thornsLevel", 0),
                amenLeaves = leaves,
                amenFruits = fruits,
                amenRoots = roots,
                lastHealedAt = if (obj.has("lastHealedAt")) obj.optLong("lastHealedAt") else null
            )
        } catch (e: Exception) {
            HeartFieldState()
        }
    }

    fun saveHeartFieldState(username: String, state: HeartFieldState) {
        val obj = JSONObject()
        obj.put("isNegative", state.isNegative)
        obj.put("negativityType", state.negativityType)
        obj.put("thornsLevel", state.thornsLevel)
        val lArr = JSONArray()
        state.amenLeaves.forEach { lArr.put(it) }
        obj.put("amenLeaves", lArr)
        val fArr = JSONArray()
        state.amenFruits.forEach { fArr.put(it) }
        obj.put("amenFruits", fArr)
        val rArr = JSONArray()
        state.amenRoots.forEach { rArr.put(it) }
        obj.put("amenRoots", rArr)
        state.lastHealedAt?.let { obj.put("lastHealedAt", it) }
        prefs.edit().putString(userKey("heart_field", username), obj.toString()).apply()

        scope.launch {
            db.heartFieldDao().saveHeartField(
                HeartFieldEntity(
                    username = username,
                    isNegative = state.isNegative,
                    negativityType = state.negativityType,
                    thornsLevel = state.thornsLevel,
                    amenLeavesCsv = state.amenLeaves.joinToString(","),
                    amenFruitsCsv = state.amenFruits.joinToString(","),
                    amenRootsCsv = state.amenRoots.joinToString(","),
                    lastHealedAt = state.lastHealedAt
                )
            )
        }
    }

    fun loadJournals(username: String): List<JournalEntry> {
        val jsonStr = prefs.getString(userKey("journals", username), null) ?: return getInitialJournals()
        val list = mutableListOf<JournalEntry>()
        try {
            val arr = JSONArray(jsonStr)
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                val moods = mutableListOf<String>()
                obj.optJSONArray("moods")?.let {
                    for (m in 0 until it.length()) moods.add(it.getString(m))
                } ?: run {
                    val mood = obj.optString("mood", "cam_ta")
                    moods.add(mood)
                }

                list.add(
                    JournalEntry(
                        id = obj.getString("id"),
                        title = obj.optString("title", "Nhật ký"),
                        content = obj.getString("content"),
                        moods = moods,
                        category = obj.optString("category", "Cuộc sống"),
                        associatedFruit = obj.optString("associatedFruit", "peace"),
                        createdAt = obj.optLong("createdAt", System.currentTimeMillis())
                    )
                )
            }
        } catch (e: Exception) {
            return getInitialJournals()
        }
        return list
    }

    private fun getInitialJournals(): List<JournalEntry> {
        return listOf(
            JournalEntry(
                id = "journal_init_1",
                title = "Khởi đầu ngày mới bình an",
                content = "Hôm nay tôi bắt đầu viết nhật ký và gieo mầm cho Cây Sự Sống. Ước mong mỗi ngày đều tìm thấy sự tĩnh lặng và niềm vui trong từng việc nhỏ.",
                moods = listOf("binh_an", "cam_ta"),
                category = "Cuộc sống",
                associatedFruit = "peace",
                createdAt = System.currentTimeMillis() - 86400000L
            )
        )
    }

    fun saveJournals(username: String, journals: List<JournalEntry>) {
        val arr = JSONArray()
        for (j in journals) {
            val obj = JSONObject()
            obj.put("id", j.id)
            obj.put("title", j.title)
            obj.put("content", j.content)
            val mArr = JSONArray()
            j.moods.forEach { mArr.put(it) }
            obj.put("moods", mArr)
            obj.put("category", j.category)
            obj.put("associatedFruit", j.associatedFruit)
            obj.put("createdAt", j.createdAt)
            arr.put(obj)
        }
        prefs.edit().putString(userKey("journals", username), arr.toString()).apply()

        scope.launch {
            journals.forEach { j ->
                db.journalDao().insertJournal(
                    JournalEntity(
                        id = j.id,
                        username = username,
                        title = j.title,
                        content = j.content,
                        moodsCsv = j.moods.joinToString(","),
                        category = j.category,
                        associatedFruit = j.associatedFruit,
                        createdAt = j.createdAt
                    )
                )
            }
        }
    }

    fun loadPrayerChats(username: String): List<PrayerMessage> {
        val jsonStr = prefs.getString(userKey("prayer_chats", username), null) ?: return getInitialPrayers()
        val list = mutableListOf<PrayerMessage>()
        try {
            val arr = JSONArray(jsonStr)
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                list.add(
                    PrayerMessage(
                        id = obj.getString("id"),
                        sender = obj.getString("sender"),
                        text = obj.getString("text"),
                        verse1925 = if (obj.has("verse1925")) obj.getString("verse1925") else null,
                        reference = if (obj.has("reference")) obj.getString("reference") else null,
                        associatedFruit = if (obj.has("associatedFruit")) obj.getString("associatedFruit") else null,
                        createdAt = obj.optLong("createdAt", System.currentTimeMillis())
                    )
                )
            }
        } catch (e: Exception) {
            return getInitialPrayers()
        }
        return list
    }

    private fun getInitialPrayers(): List<PrayerMessage> {
        return listOf(
            PrayerMessage(
                id = "chat_init_1",
                sender = "user",
                text = "Lạy Chúa, con cảm ơn Ngài vì ban cho con một ngày mới và ban cho con sự sống.",
                associatedFruit = "joy",
                createdAt = System.currentTimeMillis() - 86400000L
            ),
            PrayerMessage(
                id = "chat_init_2",
                sender = "god_ai",
                text = "Hỡi con yêu dấu, Ta nghe tiếng con. Hãy luôn giữ sự vui mừng và bình an trong lòng. Ta hằng ở cùng con trong mọi nẻo đường.",
                verse1925 = "Hãy vui mừng trong Chúa luôn luôn. Tôi lại còn nói nữa: hãy vui mừng đi!",
                reference = "Phi-líp 4:4",
                createdAt = System.currentTimeMillis() - 86390000L
            )
        )
    }

    fun savePrayerChats(username: String, chats: List<PrayerMessage>) {
        val arr = JSONArray()
        for (c in chats) {
            val obj = JSONObject()
            obj.put("id", c.id)
            obj.put("sender", c.sender)
            obj.put("text", c.text)
            c.verse1925?.let { obj.put("verse1925", it) }
            c.reference?.let { obj.put("reference", it) }
            c.associatedFruit?.let { obj.put("associatedFruit", it) }
            obj.put("createdAt", c.createdAt)
            arr.put(obj)
        }
        prefs.edit().putString(userKey("prayer_chats", username), arr.toString()).apply()

        scope.launch {
            chats.forEach { c ->
                db.prayerChatDao().insertChat(
                    PrayerChatEntity(
                        id = c.id,
                        username = username,
                        sender = c.sender,
                        text = c.text,
                        verse1925 = c.verse1925,
                        reference = c.reference,
                        associatedFruit = c.associatedFruit,
                        createdAt = c.createdAt
                    )
                )
            }
        }
    }

    fun loadHomeMessage(username: String): HomeMessage {
        val jsonStr = prefs.getString(userKey("home_message", username), null) ?: return getInitialHomeMessage()
        return try {
            val obj = JSONObject(jsonStr)
            HomeMessage(
                recentProblem = obj.optString("recentProblem", "Hôm nay tôi bắt đầu viết nhật ký và gieo mầm cho Cây Sự Sống."),
                verse1925 = obj.optString("verse1925", "Đức Giê-hô-va là Đấng chăn giữ tôi; tôi sẽ chẳng thiếu thốn gì. Ngài khiến tôi an nghỉ nơi đồng cỏ xanh tươi, dẫn tôi đến mé nước bình tịnh."),
                reference = obj.optString("reference", "Thi-thiên 23:1-2"),
                blessing = obj.optString("blessing", "Nguyện xin bình an của Chúa bao phủ trọn vẹn tâm hồn và mọi công việc của bạn hôm nay."),
                comfortText = obj.optString("comfortText", "Hãy luôn vững tin, Cây Sự Sống trong lòng bạn đang đâm rễ vững chắc và xanh tươi từng ngày."),
                updatedAt = obj.optLong("updatedAt", System.currentTimeMillis())
            )
        } catch (e: Exception) {
            getInitialHomeMessage()
        }
    }

    private fun getInitialHomeMessage(): HomeMessage {
        return HomeMessage(
            recentProblem = "Hôm nay tôi bắt đầu viết nhật ký và gieo mầm cho Cây Sự Sống.",
            verse1925 = "Đức Giê-hô-va là Đấng chăn giữ tôi; tôi sẽ chẳng thiếu thốn gì. Ngài khiến tôi an nghỉ nơi đồng cỏ xanh tươi, dẫn tôi đến mé nước bình tịnh.",
            reference = "Thi-thiên 23:1-2",
            blessing = "Nguyện xin bình an của Chúa bao phủ trọn vẹn tâm hồn và mọi công việc của bạn hôm nay.",
            comfortText = "Hãy luôn vững tin, Cây Sự Sống trong lòng bạn đang đâm rễ vững chắc và xanh tươi từng ngày."
        )
    }

    fun saveHomeMessage(username: String, msg: HomeMessage) {
        val obj = JSONObject()
        obj.put("recentProblem", msg.recentProblem)
        obj.put("verse1925", msg.verse1925)
        obj.put("reference", msg.reference)
        obj.put("blessing", msg.blessing)
        obj.put("comfortText", msg.comfortText)
        obj.put("updatedAt", msg.updatedAt)
        prefs.edit().putString(userKey("home_message", username), obj.toString()).apply()

        scope.launch {
            db.homeMessageDao().saveHomeMessage(
                HomeMessageEntity(
                    username = username,
                    recentProblem = msg.recentProblem,
                    verse1925 = msg.verse1925,
                    reference = msg.reference,
                    blessing = msg.blessing,
                    comfortText = msg.comfortText,
                    updatedAt = msg.updatedAt
                )
            )
        }
    }

    fun loadFruitMemories(username: String, fruitId: String): List<FruitMemory> {
        val jsonStr = prefs.getString(userKey("memories_$fruitId", username), null) ?: return emptyList()
        val list = mutableListOf<FruitMemory>()
        try {
            val arr = JSONArray(jsonStr)
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                list.add(
                    FruitMemory(
                        id = obj.getString("id"),
                        fruitId = fruitId,
                        text = obj.getString("text"),
                        date = obj.optLong("date", System.currentTimeMillis())
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    fun saveFruitMemories(username: String, fruitId: String, memories: List<FruitMemory>) {
        val arr = JSONArray()
        for (m in memories) {
            val obj = JSONObject()
            obj.put("id", m.id)
            obj.put("text", m.text)
            obj.put("date", m.date)
            arr.put(obj)
        }
        prefs.edit().putString(userKey("memories_$fruitId", username), arr.toString()).apply()

        scope.launch {
            memories.forEach { m ->
                db.fruitMemoryDao().insertMemory(
                    FruitMemoryEntity(
                        id = m.id,
                        username = username,
                        fruitId = fruitId,
                        text = m.text,
                        date = m.date
                    )
                )
            }
        }
    }

    fun loadSettings(): AppSettings {
        val jsonStr = prefs.getString(KEY_SETTINGS, null) ?: return AppSettings()
        return try {
            val obj = JSONObject(jsonStr)
            AppSettings(
                soundEnabled = obj.optBoolean("soundEnabled", true),
                ambientTrack = obj.optString("ambientTrack", "miracle_528"),
                customAudioUrl = obj.optString("customAudioUrl", ""),
                geminiApiKey = obj.optString("geminiApiKey", ""),
                userName = obj.optString("userName", "Bạn nhỏ")
            )
        } catch (e: Exception) {
            AppSettings()
        }
    }

    fun saveSettings(settings: AppSettings) {
        val obj = JSONObject()
        obj.put("soundEnabled", settings.soundEnabled)
        obj.put("ambientTrack", settings.ambientTrack)
        obj.put("customAudioUrl", settings.customAudioUrl)
        obj.put("geminiApiKey", settings.geminiApiKey)
        obj.put("userName", settings.userName)
        prefs.edit().putString(KEY_SETTINGS, obj.toString()).apply()
    }

    // --- Helpers: Growth Calculation & Negativity Detection ---

    fun calculateTreeGrowth(journalCount: Int, prayerCount: Int, currentDrops: Int): TreeState {
        val totalActivity = journalCount + (prayerCount / 2)
        val roots = minOf(3 + (totalActivity * 0.8).toInt(), 24)
        val leaves = minOf(8 + totalActivity * 3, 72)
        val branches = minOf(2 + (totalActivity * 0.5).toInt(), 14)
        val fruitCount = minOf(3 + totalActivity / 2, 9)
        val unlocked = NINE_FRUITS.map { it.id }.take(fruitCount)
        val level = (totalActivity / 4) + 1

        return TreeState(
            waterDrops = currentDrops,
            rootsCount = roots,
            leavesCount = leaves,
            branchesCount = branches,
            unlockedFruits = unlocked,
            level = level,
            isWilted = false,
            lastWatered = System.currentTimeMillis()
        )
    }

    fun detectNegativity(text: String): Pair<Boolean, Int> {
        val lower = text.lowercase()
        val negativeKeywords = listOf(
            "ghen", "so sánh", "giàu hơn", "hơn tôi", "thua kém", "đố kỵ", "bất công",
            "lo lắng", "bất an", "sợ", "hoang mang", "căng thẳng", "áp lực", "stress",
            "mệt", "kiệt sức", "đuối", "nặng nề", "chán nản", "tuyệt vọng", "buồn",
            "đau", "khóc", "cô đơn", "tổn thương", "đổ vỡ", "thất vọng", "tức giận",
            "bực", "bực mình", "cãi vã", "thù hằn", "khó chịu"
        )
        val isNeg = negativeKeywords.any { lower.contains(it) }
        return Pair(isNeg, if (isNeg) 2 else 0)
    }

    fun exportAllDataJson(): String {
        val root = JSONObject()
        val accountsArr = JSONArray()
        getAllAccounts().forEach {
            val obj = JSONObject()
            obj.put("username", it.username)
            obj.put("password", it.password)
            obj.put("fullName", it.fullName)
            obj.put("isAdmin", it.isAdmin)
            accountsArr.put(obj)
        }
        root.put("registeredUsers", accountsArr)

        val currentUser = getCurrentUsername() ?: "admin"
        root.put("currentUsername", currentUser)

        val uObj = JSONObject()
        val tState = loadTreeState(currentUser)
        val tObj = JSONObject().apply {
            put("waterDrops", tState.waterDrops)
            put("level", tState.level)
            put("rootsCount", tState.rootsCount)
            put("leavesCount", tState.leavesCount)
            put("branchesCount", tState.branchesCount)
        }
        uObj.put("treeState", tObj)
        root.put("userData", uObj)

        return root.toString(2)
    }
}
