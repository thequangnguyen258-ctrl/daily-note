package com.example.ui

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.SanctuaryAudioEngine
import com.example.data.local.DevotionalStorage
import com.example.data.model.*
import com.example.ui.components.*
import com.example.ui.screens.*
import com.example.ui.theme.GoldPrimary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainApp(
    storage: DevotionalStorage,
    audioEngine: SanctuaryAudioEngine
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var currentUser by remember {
        // Mỗi lần mở ứng dụng đều là một lần đăng nhập (không tự động lưu phiên)
        mutableStateOf<UserAccount?>(null)
    }

    if (currentUser == null) {
        LoginScreen(
            storage = storage,
            onLoginSuccess = { user ->
                currentUser = user
                audioEngine.playBellChime(528f)
            }
        )
        return
    }

    val activeUser = currentUser!!
    val username = activeUser.username

    // App state loaded for this user
    var currentTab by remember { mutableStateOf("home") }
    var treeState by remember { mutableStateOf(storage.loadTreeState(username)) }
    var heartFieldState by remember { mutableStateOf(storage.loadHeartFieldState(username)) }
    var journals by remember { mutableStateOf(storage.loadJournals(username)) }
    var prayerChats by remember { mutableStateOf(storage.loadPrayerChats(username)) }
    var homeMessage by remember { mutableStateOf(storage.loadHomeMessage(username)) }
    var appSettings by remember { mutableStateOf(storage.loadSettings()) }

    // Dialog states
    var selectedFruitForModal by remember { mutableStateOf<Fruit?>(null) }
    var selectedLeafForModal by remember { mutableStateOf<CoreNeedLeaf?>(null) }
    var showRootModal by remember { mutableStateOf(false) }
    var showScriptureCard by remember { mutableStateOf(false) }
    var showMiraclePond by remember { mutableStateOf(false) }

    // Animations
    var isWateringAnimation by remember { mutableStateOf(false) }
    var isHolyFireAnimation by remember { mutableStateOf(false) }

    // Handle back button
    BackHandler(enabled = currentTab != "home") {
        currentTab = "home"
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF090614))
    ) {
        // Main Screen Content
        when (currentTab) {
            "home" -> {
                HomeScreen(
                    treeState = treeState,
                    heartFieldState = heartFieldState,
                    homeMessage = homeMessage,
                    onUpdateHomeMessage = {
                        homeMessage = it
                        storage.saveHomeMessage(username, it)
                    },
                    onFruitClick = { fruit ->
                        selectedFruitForModal = fruit
                    },
                    onLeavesClick = {
                        // Open the first leaf or match recent problem
                        val lower = homeMessage.recentProblem.lowercase()
                        val matched = THIRTEEN_CORE_LEAVES.firstOrNull { leaf ->
                            leaf.keywords.any { lower.contains(it) }
                        } ?: THIRTEEN_CORE_LEAVES.first()
                        selectedLeafForModal = matched
                    },
                    onRootsClick = {
                        showRootModal = true
                    },
                    onOpenPond = {
                        showMiraclePond = true
                    },
                    onOpenScriptureCard = {
                        showScriptureCard = true
                    },
                    onNavigateJournal = {
                        currentTab = "journal"
                    },
                    onNavigatePrayer = {
                        currentTab = "prayer"
                    },
                    onOpenSettings = {
                        currentTab = "settings"
                    },
                    onToggleThorny = {
                        val newNeg = !heartFieldState.isNegative
                        val newThorns = if (newNeg) 2 else 0
                        val updated = heartFieldState.copy(isNegative = newNeg, thornsLevel = newThorns)
                        heartFieldState = updated
                        storage.saveHeartFieldState(username, updated)
                        if (newNeg) {
                            Toast.makeText(context, "Đã xuất hiện cỏ lùng mọc cao, bụi gai quấn gốc và sỏi đá chặn rễ!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Mảnh đất lòng đã được giải phóng, cỏ lùng và bụi gai đã tan biến!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    onTriggerWatering = {
                        audioEngine.playBellChime(528f)
                        audioEngine.playWaterDrop()
                        isWateringAnimation = true
                        val updated = treeState.copy(isWilted = false, lastWatered = System.currentTimeMillis())
                        treeState = updated
                        storage.saveTreeState(username, updated)
                        scope.launch {
                            delay(4000)
                            isWateringAnimation = false
                            val updatedHeart = heartFieldState.copy(isNegative = false, thornsLevel = 0)
                            heartFieldState = updatedHeart
                            storage.saveHeartFieldState(username, updatedHeart)
                            Toast.makeText(context, "Nước Hằng Sống đã tưới đẫm đất và làm tan biến các viên sỏi đá ngăn rễ!", Toast.LENGTH_SHORT).show()
                        }
                        Toast.makeText(context, "Thiên Sứ giáng lâm cầm bình ngọc đổ Nước Hằng Sống tưới đất cằn...", Toast.LENGTH_SHORT).show()
                    },
                    onTriggerHolyFire = {
                        audioEngine.playBellChime(741f)
                        isHolyFireAnimation = true
                        scope.launch {
                            delay(3500)
                            isHolyFireAnimation = false
                            val updatedHeart = heartFieldState.copy(isNegative = false, thornsLevel = 0)
                            heartFieldState = updatedHeart
                            storage.saveHeartFieldState(username, updatedHeart)
                            Toast.makeText(context, "Hòn lửa đã bùng cháy thiêu rụi sạch cỏ lùng, bụi gai và sỏi đá!", Toast.LENGTH_SHORT).show()
                        }
                        Toast.makeText(context, "Thiên Sứ thả hòn lửa từ trời xuống gốc cây...", Toast.LENGTH_SHORT).show()
                    },
                    onAddWaterDrops = {
                        audioEngine.playWaterDrop()
                        val updated = treeState.copy(waterDrops = treeState.waterDrops + 100)
                        treeState = updated
                        storage.saveTreeState(username, updated)
                        Toast.makeText(context, "Đã nhận thêm +100 Giọt Nước Sự Sống!", Toast.LENGTH_SHORT).show()
                    },
                    audioEngine = audioEngine,
                    isWatering = isWateringAnimation,
                    isHolyFire = isHolyFireAnimation
                )
            }
            "journal" -> {
                JournalScreen(
                    journals = journals,
                    onSaveJournal = { newEntry ->
                        val updatedList = journals + newEntry
                        journals = updatedList
                        storage.saveJournals(username, updatedList)

                        // Add +10 water drops & calculate tree growth
                        val newDrops = treeState.waterDrops + 10
                        val updatedTree = storage.calculateTreeGrowth(updatedList.size, prayerChats.size, newDrops)
                        treeState = updatedTree
                        storage.saveTreeState(username, updatedTree)

                        // Check negativity
                        val (isNeg, thorns) = storage.detectNegativity(newEntry.content)
                        val updatedHeart = heartFieldState.copy(
                            isNegative = isNeg,
                            thornsLevel = thorns
                        )
                        heartFieldState = updatedHeart
                        storage.saveHeartFieldState(username, updatedHeart)

                        // Update Home problem illumination
                        val updatedHome = homeMessage.copy(
                            recentProblem = newEntry.content.take(120),
                            updatedAt = System.currentTimeMillis()
                        )
                        homeMessage = updatedHome
                        storage.saveHomeMessage(username, updatedHome)

                        // Trigger watering visual effect
                        isWateringAnimation = true
                        scope.launch {
                            delay(2500)
                            isWateringAnimation = false
                        }
                    },
                    onDeleteJournal = { id ->
                        val updated = journals.filter { it.id != id }
                        journals = updated
                        storage.saveJournals(username, updated)
                    },
                    audioEngine = audioEngine
                )
            }
            "prayer" -> {
                PrayerChatScreen(
                    prayerChats = prayerChats,
                    onSendMessage = { msg, originalText ->
                        val updatedList = prayerChats + msg
                        prayerChats = updatedList
                        storage.savePrayerChats(username, updatedList)

                        if (msg.sender == "user") {
                            // Add +10 water drops
                            val newDrops = treeState.waterDrops + 10
                            val updatedTree = storage.calculateTreeGrowth(journals.size, updatedList.size, newDrops)
                            treeState = updatedTree
                            storage.saveTreeState(username, updatedTree)

                            // Check negativity
                            val (isNeg, thorns) = storage.detectNegativity(originalText)
                            val updatedHeart = heartFieldState.copy(
                                isNegative = isNeg,
                                thornsLevel = thorns
                            )
                            heartFieldState = updatedHeart
                            storage.saveHeartFieldState(username, updatedHeart)

                            // Update Home message
                            val updatedHome = homeMessage.copy(
                                recentProblem = originalText.take(120),
                                updatedAt = System.currentTimeMillis()
                            )
                            homeMessage = updatedHome
                            storage.saveHomeMessage(username, updatedHome)

                            // Trigger watering effect
                            isWateringAnimation = true
                            scope.launch {
                                delay(2500)
                                isWateringAnimation = false
                            }
                        }
                    },
                    geminiApiKey = appSettings.geminiApiKey,
                    audioEngine = audioEngine
                )
            }
            "history" -> {
                JourneyHistoryScreen(
                    treeState = treeState,
                    journals = journals,
                    prayerChats = prayerChats,
                    onFruitClick = { fruit ->
                        selectedFruitForModal = fruit
                    },
                    onRootsClick = {
                        showRootModal = true
                    },
                    onLeavesClick = {
                        selectedLeafForModal = THIRTEEN_CORE_LEAVES.first()
                    }
                )
            }
            "settings" -> {
                SettingsScreen(
                    storage = storage,
                    currentUser = activeUser,
                    onUserUpdated = { updated ->
                        currentUser = updated
                    },
                    onLogout = {
                        currentUser = null
                    },
                    onBack = {
                        currentTab = "home"
                    }
                )
            }
        }

        // Floating Sanctuary Audio Player Widget (above bottom nav)
        SanctuaryAudioPlayerWidget(
            audioEngine = audioEngine,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 76.dp)
        )

        // Bottom Navigation Bar
        Surface(
            color = Color(0xF2090614),
            border = BorderStroke(1.dp, Color(0x26FFFFFF)),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(68.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 1. Home
                NavigationBarItem(
                    selected = currentTab == "home",
                    onClick = {
                        audioEngine.playTouchChime(0)
                        currentTab = "home"
                    },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Trang chủ") },
                    label = { Text("Trang Chủ", fontSize = 10.sp, fontWeight = if (currentTab == "home") FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = GoldPrimary,
                        selectedTextColor = GoldPrimary,
                        unselectedIconColor = Color.White.copy(alpha = 0.5f),
                        unselectedTextColor = Color.White.copy(alpha = 0.5f),
                        indicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.testTag("nav_home")
                )

                // 2. Journal
                NavigationBarItem(
                    selected = currentTab == "journal",
                    onClick = {
                        audioEngine.playTouchChime(1)
                        currentTab = "journal"
                    },
                    icon = { Icon(Icons.Default.MenuBook, contentDescription = "Nhật ký") },
                    label = { Text("Nhật Ký", fontSize = 10.sp, fontWeight = if (currentTab == "journal") FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF10B981),
                        selectedTextColor = Color(0xFF10B981),
                        unselectedIconColor = Color.White.copy(alpha = 0.5f),
                        unselectedTextColor = Color.White.copy(alpha = 0.5f),
                        indicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.testTag("nav_journal")
                )

                // 3. Prayer Chat (Prominent Dove Center)
                NavigationBarItem(
                    selected = currentTab == "prayer",
                    onClick = {
                        audioEngine.playTouchChime(2)
                        currentTab = "prayer"
                    },
                    icon = {
                        Surface(
                            shape = CircleShape,
                            color = if (currentTab == "prayer") GoldPrimary else Color(0x33F59E0B),
                            border = BorderStroke(1.dp, Color(0x66F59E0B)),
                            modifier = Modifier.size(32.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("🕊️", fontSize = 16.sp)
                            }
                        }
                    },
                    label = { Text("Tâm Sự", fontSize = 10.sp, fontWeight = if (currentTab == "prayer") FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = GoldPrimary,
                        selectedTextColor = GoldPrimary,
                        unselectedIconColor = Color.White.copy(alpha = 0.5f),
                        unselectedTextColor = Color.White.copy(alpha = 0.5f),
                        indicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.testTag("nav_prayer")
                )

                // 4. History
                NavigationBarItem(
                    selected = currentTab == "history",
                    onClick = {
                        audioEngine.playTouchChime(3)
                        currentTab = "history"
                    },
                    icon = { Icon(Icons.Default.Timeline, contentDescription = "Hành trình") },
                    label = { Text("Hành Trình", fontSize = 10.sp, fontWeight = if (currentTab == "history") FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF38BDF8),
                        selectedTextColor = Color(0xFF38BDF8),
                        unselectedIconColor = Color.White.copy(alpha = 0.5f),
                        unselectedTextColor = Color.White.copy(alpha = 0.5f),
                        indicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.testTag("nav_history")
                )

                // 5. Settings
                NavigationBarItem(
                    selected = currentTab == "settings",
                    onClick = {
                        audioEngine.playTouchChime(4)
                        currentTab = "settings"
                    },
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Cài đặt") },
                    label = { Text("Cài Đặt", fontSize = 10.sp, fontWeight = if (currentTab == "settings") FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        unselectedIconColor = Color.White.copy(alpha = 0.5f),
                        unselectedTextColor = Color.White.copy(alpha = 0.5f),
                        indicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.testTag("nav_settings")
                )
            }
        }

        // ================= Modals =================

        // 1. Leaf Detail Modal
        selectedLeafForModal?.let { leaf ->
            LeafDetailDialog(
                leaf = leaf,
                recentProblem = homeMessage.recentProblem,
                onAmenConfirm = {
                    val newDrops = treeState.waterDrops + 1
                    val newLeaves = (heartFieldState.amenLeaves + leaf.id).distinct()
                    val updatedTree = treeState.copy(waterDrops = newDrops)
                    val updatedHeart = heartFieldState.copy(
                        amenLeaves = newLeaves,
                        isNegative = if (newLeaves.size >= 3) false else heartFieldState.isNegative,
                        thornsLevel = if (newLeaves.size >= 3) 0 else heartFieldState.thornsLevel
                    )
                    treeState = updatedTree
                    heartFieldState = updatedHeart
                    storage.saveTreeState(username, updatedTree)
                    storage.saveHeartFieldState(username, updatedHeart)
                },
                onDismiss = { selectedLeafForModal = null },
                audioEngine = audioEngine
            )
        }

        // 2. Fruit Detail Modal
        selectedFruitForModal?.let { fruit ->
            val memories = storage.loadFruitMemories(username, fruit.id)
            FruitDetailDialog(
                fruit = fruit,
                recentProblem = homeMessage.recentProblem,
                memories = memories,
                onAddMemory = { text ->
                    val newMem = FruitMemory(
                        id = "mem_${System.currentTimeMillis()}",
                        fruitId = fruit.id,
                        text = text,
                        date = System.currentTimeMillis()
                    )
                    val updated = memories + newMem
                    storage.saveFruitMemories(username, fruit.id, updated)
                },
                onAmenConfirm = {
                    val newDrops = treeState.waterDrops + 1
                    val newFruits = (heartFieldState.amenFruits + fruit.id).distinct()
                    val updatedTree = treeState.copy(waterDrops = newDrops)
                    val updatedHeart = heartFieldState.copy(
                        amenFruits = newFruits,
                        isNegative = if (newFruits.size >= 3) false else heartFieldState.isNegative,
                        thornsLevel = if (newFruits.size >= 3) 0 else heartFieldState.thornsLevel
                    )
                    treeState = updatedTree
                    heartFieldState = updatedHeart
                    storage.saveTreeState(username, updatedTree)
                    storage.saveHeartFieldState(username, updatedHeart)
                },
                onDismiss = { selectedFruitForModal = null },
                audioEngine = audioEngine
            )
        }

        // 3. Root Detail Modal
        if (showRootModal) {
            RootDetailDialog(
                recentProblem = homeMessage.recentProblem,
                onAmenConfirm = {
                    val newDrops = treeState.waterDrops + 1
                    val newRoots = (heartFieldState.amenRoots + "root_${System.currentTimeMillis()}").distinct()
                    val updatedTree = treeState.copy(waterDrops = newDrops)
                    val updatedHeart = heartFieldState.copy(
                        amenRoots = newRoots,
                        isNegative = if (newRoots.size >= 2) false else heartFieldState.isNegative,
                        thornsLevel = if (newRoots.size >= 2) 0 else heartFieldState.thornsLevel
                    )
                    treeState = updatedTree
                    heartFieldState = updatedHeart
                    storage.saveTreeState(username, updatedTree)
                    storage.saveHeartFieldState(username, updatedHeart)
                },
                onDismiss = { showRootModal = false },
                audioEngine = audioEngine
            )
        }

        // 4. Scripture Card Modal
        if (showScriptureCard) {
            ScriptureCardDialog(
                homeMessage = homeMessage,
                onDismiss = { showScriptureCard = false }
            )
        }

        // 5. Miracle Pond Modal (Angel Rain & Holy Fire)
        if (showMiraclePond) {
            MiraclePondDialog(
                waterDrops = treeState.waterDrops,
                onAngelResurrect = {
                    if (treeState.waterDrops < 100) {
                        Toast.makeText(context, "Cần tối thiểu 100 giọt nước để gọi Thiên Sứ làm mưa hồi sinh.", Toast.LENGTH_SHORT).show()
                    } else {
                        audioEngine.playBellChime(528f)
                        showMiraclePond = false
                        isWateringAnimation = true

                        val updatedTree = treeState.copy(
                            waterDrops = (treeState.waterDrops - 100).coerceAtLeast(0),
                            isWilted = false,
                            lastWatered = System.currentTimeMillis()
                        )
                        treeState = updatedTree
                        storage.saveTreeState(username, updatedTree)

                        scope.launch {
                            delay(4000)
                            isWateringAnimation = false
                            val updatedHeart = heartFieldState.copy(isNegative = false, thornsLevel = 0)
                            heartFieldState = updatedHeart
                            storage.saveHeartFieldState(username, updatedHeart)
                            Toast.makeText(context, "Nước Hằng Sống đã tưới đẫm đất và làm tan biến toàn bộ sỏi đá cản rễ!", Toast.LENGTH_SHORT).show()
                        }
                        Toast.makeText(context, "Thiên Sứ đang cầm bình đổ Nước Hằng Sống tưới đất...", Toast.LENGTH_SHORT).show()
                    }
                },
                onHolyFire = {
                    if (treeState.waterDrops < 100) {
                        Toast.makeText(context, "Cần tối thiểu 100 giọt nước để hóa Lửa Thánh.", Toast.LENGTH_SHORT).show()
                    } else {
                        audioEngine.playBellChime(741f)
                        showMiraclePond = false
                        isHolyFireAnimation = true

                        val updatedTree = treeState.copy(
                            waterDrops = (treeState.waterDrops - 100).coerceAtLeast(0)
                        )
                        val updatedHeart = heartFieldState.copy(
                            isNegative = false,
                            thornsLevel = 0,
                            lastHealedAt = System.currentTimeMillis()
                        )
                        treeState = updatedTree
                        heartFieldState = updatedHeart
                        storage.saveTreeState(username, updatedTree)
                        storage.saveHeartFieldState(username, updatedHeart)

                        scope.launch {
                            delay(3500)
                            isHolyFireAnimation = false
                        }
                        Toast.makeText(context, "Lửa Thánh đã thiêu rụi toàn bộ bụi gai trong tấm lòng!", Toast.LENGTH_SHORT).show()
                    }
                },
                onDismiss = { showMiraclePond = false }
            )
        }
    }
}
