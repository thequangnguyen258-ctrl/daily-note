package com.example.data.local

import android.content.Context
import com.example.data.local.room.*
import com.example.data.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class LocalDevotionalRepository(context: Context) {

    private val db = AppDatabase.getInstance(context)
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    val userAccountDao = db.userAccountDao()
    val journalDao = db.journalDao()
    val prayerChatDao = db.prayerChatDao()
    val treeStateDao = db.treeStateDao()
    val heartFieldDao = db.heartFieldDao()
    val fruitMemoryDao = db.fruitMemoryDao()
    val homeMessageDao = db.homeMessageDao()

    init {
        scope.launch {
            seedInitialDatabaseIfEmpty()
        }
    }

    private suspend fun seedInitialDatabaseIfEmpty() {
        val existing = userAccountDao.getAllAccountsList()
        if (existing.isEmpty()) {
            val defaultAccounts = mutableListOf<UserAccountEntity>()
            defaultAccounts.add(
                UserAccountEntity(
                    username = "admin",
                    password = "123",
                    fullName = "Quản Trị Viên",
                    isAdmin = true
                )
            )
            for (i in 1..20) {
                defaultAccounts.add(
                    UserAccountEntity(
                        username = "nguoidung$i",
                        password = "12345678",
                        fullName = "Người Dùng $i",
                        isAdmin = false
                    )
                )
            }
            userAccountDao.insertAllAccounts(defaultAccounts)

            // Seed initial data for admin
            val now = System.currentTimeMillis()
            journalDao.insertJournal(
                JournalEntity(
                    id = "journal_init_1",
                    username = "admin",
                    title = "Khởi đầu ngày mới bình an",
                    content = "Hôm nay tôi bắt đầu viết nhật ký và gieo mầm cho Cây Sự Sống. Ước mong mỗi ngày đều tìm thấy sự tĩnh lặng và niềm vui trong từng việc nhỏ.",
                    moodsCsv = "binh_an,cam_ta",
                    category = "Cuộc sống",
                    associatedFruit = "peace",
                    createdAt = now - 86400000L
                )
            )

            prayerChatDao.insertChat(
                PrayerChatEntity(
                    id = "chat_init_1",
                    username = "admin",
                    sender = "user",
                    text = "Lạy Chúa, con cảm ơn Ngài vì ban cho con một ngày mới và ban cho con sự sống.",
                    associatedFruit = "joy",
                    createdAt = now - 86400000L
                )
            )
            prayerChatDao.insertChat(
                PrayerChatEntity(
                    id = "chat_init_2",
                    username = "admin",
                    sender = "god_ai",
                    text = "Hỡi con yêu dấu, Ta nghe tiếng con. Hãy luôn giữ sự vui mừng và bình an trong lòng. Ta hằng ở cùng con trong mọi nẻo đường.",
                    verse1925 = "Hãy vui mừng trong Chúa luôn luôn. Tôi lại còn nói nữa: hãy vui mừng đi!",
                    reference = "Phi-líp 4:4",
                    createdAt = now - 86390000L
                )
            )

            treeStateDao.saveTreeState(
                TreeStateEntity(
                    username = "admin",
                    waterDrops = 250,
                    rootsCount = 3,
                    leavesCount = 8,
                    branchesCount = 2,
                    unlockedFruitsCsv = "love,peace,joy",
                    level = 1,
                    isWilted = false,
                    lastWatered = now
                )
            )

            heartFieldDao.saveHeartField(
                HeartFieldEntity(
                    username = "admin",
                    isNegative = false,
                    negativityType = "",
                    thornsLevel = 0,
                    amenLeavesCsv = "",
                    amenFruitsCsv = "",
                    amenRootsCsv = ""
                )
            )

            homeMessageDao.saveHomeMessage(
                HomeMessageEntity(
                    username = "admin",
                    recentProblem = "Hôm nay tôi bắt đầu viết nhật ký và gieo mầm cho Cây Sự Sống.",
                    verse1925 = "Đức Giê-hô-va là Đấng chăn giữ tôi; tôi sẽ chẳng thiếu thốn gì. Ngài khiến tôi an nghỉ nơi đồng cỏ xanh tươi, dẫn tôi đến mé nước bình tịnh.",
                    reference = "Thi-thiên 23:1-2",
                    blessing = "Nguyện xin bình an của Chúa bao phủ trọn vẹn tâm hồn và mọi công việc của bạn hôm nay.",
                    comfortText = "Hãy luôn vững tin, Cây Sự Sống trong lòng bạn đang đâm rễ vững chắc và xanh tươi từng ngày."
                )
            )
        }
    }

    // --- Accounts ---

    fun getAllAccountsFlow(): Flow<List<UserAccount>> {
        return userAccountDao.getAllAccounts().map { entities ->
            entities.map {
                UserAccount(
                    username = it.username,
                    password = it.password,
                    fullName = it.fullName,
                    isAdmin = it.isAdmin
                )
            }
        }
    }

    suspend fun getAccount(username: String): UserAccount? = withContext(Dispatchers.IO) {
        val entity = userAccountDao.getAccountByUsername(username) ?: return@withContext null
        UserAccount(
            username = entity.username,
            password = entity.password,
            fullName = entity.fullName,
            isAdmin = entity.isAdmin
        )
    }

    suspend fun saveAccount(account: UserAccount) = withContext(Dispatchers.IO) {
        userAccountDao.insertAccount(
            UserAccountEntity(
                username = account.username,
                password = account.password,
                fullName = account.fullName,
                isAdmin = account.isAdmin
            )
        )
    }

    suspend fun deleteAccount(username: String) = withContext(Dispatchers.IO) {
        userAccountDao.deleteAccountByUsername(username)
    }

    // --- Journals ---

    fun getJournalsFlow(username: String): Flow<List<JournalEntry>> {
        return journalDao.getJournalsForUser(username).map { entities ->
            entities.map {
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

    suspend fun insertJournal(username: String, journal: JournalEntry) = withContext(Dispatchers.IO) {
        journalDao.insertJournal(
            JournalEntity(
                id = journal.id,
                username = username,
                title = journal.title,
                content = journal.content,
                moodsCsv = journal.moods.joinToString(","),
                category = journal.category,
                associatedFruit = journal.associatedFruit,
                createdAt = journal.createdAt
            )
        )
    }

    suspend fun deleteJournal(id: String) = withContext(Dispatchers.IO) {
        journalDao.deleteJournalById(id)
    }

    // --- Prayer Chats ---

    fun getPrayerChatsFlow(username: String): Flow<List<PrayerMessage>> {
        return prayerChatDao.getChatsForUser(username).map { entities ->
            entities.map {
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

    suspend fun insertPrayerChat(username: String, chat: PrayerMessage) = withContext(Dispatchers.IO) {
        prayerChatDao.insertChat(
            PrayerChatEntity(
                id = chat.id,
                username = username,
                sender = chat.sender,
                text = chat.text,
                verse1925 = chat.verse1925,
                reference = chat.reference,
                associatedFruit = chat.associatedFruit,
                createdAt = chat.createdAt
            )
        )
    }

    // --- Tree State ---

    fun getTreeStateFlow(username: String): Flow<TreeState> {
        return treeStateDao.getTreeStateForUser(username).map { entity ->
            if (entity != null) {
                TreeState(
                    waterDrops = entity.waterDrops,
                    rootsCount = entity.rootsCount,
                    leavesCount = entity.leavesCount,
                    branchesCount = entity.branchesCount,
                    unlockedFruits = entity.unlockedFruitsCsv.split(",").filter { it.isNotBlank() },
                    level = entity.level,
                    isWilted = entity.isWilted,
                    lastWatered = entity.lastWatered
                )
            } else {
                TreeState()
            }
        }
    }

    suspend fun saveTreeState(username: String, state: TreeState) = withContext(Dispatchers.IO) {
        treeStateDao.saveTreeState(
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

    // --- Heart Field State ---

    fun getHeartFieldFlow(username: String): Flow<HeartFieldState> {
        return heartFieldDao.getHeartFieldForUser(username).map { entity ->
            if (entity != null) {
                HeartFieldState(
                    isNegative = entity.isNegative,
                    negativityType = entity.negativityType,
                    thornsLevel = entity.thornsLevel,
                    amenLeaves = entity.amenLeavesCsv.split(",").filter { it.isNotBlank() },
                    amenFruits = entity.amenFruitsCsv.split(",").filter { it.isNotBlank() },
                    amenRoots = entity.amenRootsCsv.split(",").filter { it.isNotBlank() },
                    lastHealedAt = entity.lastHealedAt
                )
            } else {
                HeartFieldState()
            }
        }
    }

    suspend fun saveHeartField(username: String, state: HeartFieldState) = withContext(Dispatchers.IO) {
        heartFieldDao.saveHeartField(
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

    // --- Home Message ---

    fun getHomeMessageFlow(username: String): Flow<HomeMessage> {
        return homeMessageDao.getHomeMessageForUser(username).map { entity ->
            if (entity != null) {
                HomeMessage(
                    recentProblem = entity.recentProblem,
                    verse1925 = entity.verse1925,
                    reference = entity.reference,
                    blessing = entity.blessing,
                    comfortText = entity.comfortText,
                    updatedAt = entity.updatedAt
                )
            } else {
                HomeMessage(
                    recentProblem = "Hôm nay tôi bắt đầu viết nhật ký và gieo mầm cho Cây Sự Sống.",
                    verse1925 = "Đức Giê-hô-va là Đấng chăn giữ tôi; tôi sẽ chẳng thiếu thốn gì. Ngài khiến tôi an nghỉ nơi đồng cỏ xanh tươi, dẫn tôi đến mé nước bình tịnh.",
                    reference = "Thi-thiên 23:1-2",
                    blessing = "Nguyện xin bình an của Chúa bao phủ trọn vẹn tâm hồn và mọi công việc của bạn hôm nay.",
                    comfortText = "Hãy luôn vững tin, Cây Sự Sống trong lòng bạn đang đâm rễ vững chắc và xanh tươi từng ngày."
                )
            }
        }
    }

    suspend fun saveHomeMessage(username: String, msg: HomeMessage) = withContext(Dispatchers.IO) {
        homeMessageDao.saveHomeMessage(
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

    // --- Fruit Memories ---

    fun getFruitMemoriesFlow(username: String, fruitId: String): Flow<List<FruitMemory>> {
        return fruitMemoryDao.getMemoriesForFruit(username, fruitId).map { entities ->
            entities.map {
                FruitMemory(
                    id = it.id,
                    fruitId = it.fruitId,
                    text = it.text,
                    date = it.date
                )
            }
        }
    }

    suspend fun insertFruitMemory(username: String, memory: FruitMemory) = withContext(Dispatchers.IO) {
        fruitMemoryDao.insertMemory(
            FruitMemoryEntity(
                id = memory.id,
                username = username,
                fruitId = memory.fruitId,
                text = memory.text,
                date = memory.date
            )
        )
    }

    // --- Tree Growth & Negativity Logic ---

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

    suspend fun exportJson(username: String): String = withContext(Dispatchers.IO) {
        val root = JSONObject()
        val accounts = userAccountDao.getAllAccountsList()
        val accArr = JSONArray()
        accounts.forEach {
            accArr.put(JSONObject().apply {
                put("username", it.username)
                put("fullName", it.fullName)
                put("isAdmin", it.isAdmin)
            })
        }
        root.put("registeredUsers", accArr)
        root.put("currentUsername", username)

        val journals = journalDao.getJournalsListForUser(username)
        val jArr = JSONArray()
        journals.forEach {
            jArr.put(JSONObject().apply {
                put("id", it.id)
                put("title", it.title)
                put("content", it.content)
                put("category", it.category)
                put("moods", it.moodsCsv)
                put("createdAt", it.createdAt)
            })
        }
        root.put("journals", jArr)

        root.toString(2)
    }
}
