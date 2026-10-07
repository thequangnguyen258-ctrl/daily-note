package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.SanctuaryAudioEngine
import com.example.data.model.*
import com.example.data.remote.GeminiDevotionalService
import com.example.ui.theme.GoldPrimary
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PrayerChatScreen(
    prayerChats: List<PrayerMessage>,
    onSendMessage: (PrayerMessage, String) -> Unit,
    geminiApiKey: String,
    audioEngine: SanctuaryAudioEngine,
    modifier: Modifier = Modifier
) {
    var inputText by remember { mutableStateOf("") }
    var isThinking by remember { mutableStateOf(false) }
    var selectedFruit by remember { mutableStateOf("joy") }
    var showDoveOverlay by remember { mutableStateOf(false) }
    var doveBurdenText by remember { mutableStateOf("") }

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val devotionalService = remember { GeminiDevotionalService() }
    val timeFormat = remember { SimpleDateFormat("HH:mm", Locale.getDefault()) }

    LaunchedEffect(prayerChats.size, isThinking) {
        if (prayerChats.isNotEmpty()) {
            listState.animateScrollToItem(prayerChats.size - 1)
        }
    }

    Box(modifier = modifier.fillMaxSize().background(Color(0xFF090614))) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFF59E0B),
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("🕊️", fontSize = 18.sp)
                        }
                    }
                    Spacer(Modifier.width(10.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "Tâm Sự Cùng Chúa",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFDE68A)
                            )
                            Spacer(Modifier.width(6.dp))
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = Color(0x3338BDF8)
                            ) {
                                Text(
                                    "Gemini 3.5 Flash",
                                    fontSize = 8.5.sp,
                                    color = Color(0xFF7DD3FC),
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                )
                            }
                        }
                        Text(
                            "Trò chuyện thời gian thực • Lời Chúa 1925",
                            fontSize = 10.5.sp,
                            color = Color.White.copy(alpha = 0.6f)
                        )
                    }
                }

                Surface(
                    shape = CircleShape,
                    color = Color(0x33F59E0B),
                    border = BorderStroke(1.dp, Color(0x40F59E0B))
                ) {
                    Text(
                        if (isThinking) "Chúa đang lắng nghe..." else "Trực tuyến",
                        fontSize = 10.5.sp,
                        color = Color(0xFFFDE68A),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            Divider(color = Color.White.copy(alpha = 0.1f))

            // Chat Messages List
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    // Encouraging scripture banner
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0x332E1065),
                        border = BorderStroke(1.dp, Color(0x40818CF8)),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "\"Hãy trút mọi điều lo lắng mình cho Ngài, vì Ngài hay săn sóc anh em.\"",
                                fontSize = 11.5.sp,
                                fontStyle = FontStyle.Italic,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                color = Color(0xFFEDE9FE)
                            )
                            Spacer(Modifier.height(3.dp))
                            Text(
                                "I Phi-e-rơ 5:7",
                                fontSize = 10.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFC4B5FD)
                            )
                        }
                    }
                }

                items(prayerChats, key = { it.id }) { msg ->
                    val isUser = msg.sender == "user"
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
                    ) {
                        Text(
                            "${if (isUser) "Con" else "🕊️ Chúa Phán"} • ${timeFormat.format(Date(msg.createdAt))}",
                            fontSize = 9.5.sp,
                            color = Color.White.copy(alpha = 0.45f),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )

                        Surface(
                            shape = RoundedCornerShape(
                                topStart = 20.dp,
                                topEnd = 20.dp,
                                bottomStart = if (isUser) 20.dp else 4.dp,
                                bottomEnd = if (isUser) 4.dp else 20.dp
                            ),
                            color = if (isUser) Color(0xFF065F46) else Color(0xFF130E26),
                            border = BorderStroke(
                                1.dp,
                                if (isUser) Color(0x6610B981) else Color(0x40F59E0B)
                            ),
                            modifier = Modifier.widthIn(max = 310.dp),
                            shadowElevation = 6.dp
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    msg.text,
                                    fontSize = 12.sp,
                                    color = if (isUser) Color.White else Color(0xFFFEF3C7),
                                    lineHeight = 17.sp
                                )

                                if (!isUser && msg.verse1925 != null) {
                                    Spacer(Modifier.height(8.dp))
                                    Surface(
                                        color = Color.Black.copy(alpha = 0.5f),
                                        shape = RoundedCornerShape(12.dp),
                                        border = BorderStroke(1.dp, Color(0x33F59E0B)),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Column(modifier = Modifier.padding(10.dp)) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Text(
                                                    "📖 ${msg.reference ?: ""}",
                                                    fontSize = 10.5.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color(0xFFFDE68A)
                                                )
                                                Text("Bản 1925", fontSize = 9.5.sp, color = Color.White.copy(alpha = 0.5f))
                                            }
                                            Spacer(Modifier.height(3.dp))
                                            Text(
                                                "\"${msg.verse1925}\"",
                                                fontSize = 11.sp,
                                                fontStyle = FontStyle.Italic,
                                                color = Color(0xFFD1FAE5),
                                                lineHeight = 15.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if (isThinking) {
                    item {
                        Surface(
                            shape = RoundedCornerShape(18.dp),
                            color = Color(0xFF1A1333),
                            border = BorderStroke(1.dp, Color(0x40F59E0B))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(14.dp),
                                    color = GoldPrimary,
                                    strokeWidth = 2.dp
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    "Chúa đang lắng nghe & hồi đáp...",
                                    fontSize = 11.sp,
                                    fontStyle = FontStyle.Italic,
                                    color = Color(0xFFFDE68A)
                                )
                            }
                        }
                    }
                }
            }

            // Input Bar & Action controls
            Surface(
                color = Color(0xF2090614),
                border = BorderStroke(1.dp, Color(0x26FFFFFF)),
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Trao Gánh Nặng button
                        Button(
                            onClick = {
                                if (inputText.isNotBlank()) {
                                    doveBurdenText = inputText.trim()
                                    showDoveOverlay = true
                                    audioEngine.playBellChime(639f)

                                    val userMsg = PrayerMessage(
                                        id = "chat_user_${System.currentTimeMillis()}",
                                        sender = "user",
                                        text = "[Trao Gánh Nặng] ${inputText.trim()}",
                                        associatedFruit = selectedFruit,
                                        createdAt = System.currentTimeMillis()
                                    )
                                    val sentText = inputText.trim()
                                    inputText = ""
                                    onSendMessage(userMsg, sentText)

                                    // Trigger God's pastoral response
                                    isThinking = true
                                    scope.launch {
                                        kotlinx.coroutines.delay(800)
                                        val pastoral = devotionalService.getCounsel(sentText, prayerChats, geminiApiKey)
                                        isThinking = false
                                        audioEngine.playBellChime(528f)
                                        val godMsg = PrayerMessage(
                                            id = "chat_god_${System.currentTimeMillis()}",
                                            sender = "god_ai",
                                            text = pastoral.text,
                                            verse1925 = pastoral.verse1925,
                                            reference = pastoral.reference,
                                            createdAt = System.currentTimeMillis()
                                        )
                                        onSendMessage(godMsg, sentText)
                                    }
                                }
                            },
                            enabled = inputText.isNotBlank() && !isThinking,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0x33F59E0B)),
                            border = BorderStroke(1.dp, Color(0x66F59E0B)),
                            shape = CircleShape,
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
                            modifier = Modifier.height(28.dp).testTag("surrender_burden_button")
                        ) {
                            Text("🕊️ Trao Gánh Nặng Lên Trời", fontSize = 10.5.sp, color = Color(0xFFFDE68A), fontWeight = FontWeight.Bold)
                        }

                        // Fruit selector
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Trái:", fontSize = 10.sp, color = Color.White.copy(alpha = 0.5f))
                            Spacer(Modifier.width(4.dp))
                            var fruitMenu by remember { mutableStateOf(false) }
                            val curFruit = NINE_FRUITS.firstOrNull { it.id == selectedFruit } ?: NINE_FRUITS.first()
                            Box {
                                Text(
                                    "${curFruit.icon} ${curFruit.name}",
                                    fontSize = 10.5.sp,
                                    color = Color(0xFFFDE68A),
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(Color(0x1AFFFFFF))
                                        .clickable { fruitMenu = true }
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                                DropdownMenu(expanded = fruitMenu, onDismissRequest = { fruitMenu = false }) {
                                    NINE_FRUITS.forEach { f ->
                                        DropdownMenuItem(
                                            text = { Text("${f.icon} ${f.name}", fontSize = 11.sp) },
                                            onClick = {
                                                selectedFruit = f.id
                                                fruitMenu = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = inputText,
                            onValueChange = { inputText = it },
                            placeholder = { Text("Tâm sự, giãi bày hoặc cầu xin Chúa điều gì hôm nay...", fontSize = 11.5.sp, color = Color.White.copy(alpha = 0.35f)) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = GoldPrimary,
                                unfocusedBorderColor = Color.White.copy(alpha = 0.15f),
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            ),
                            maxLines = 3,
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.weight(1f).testTag("prayer_input_field")
                        )

                        Spacer(Modifier.width(8.dp))

                        IconButton(
                            onClick = {
                                if (inputText.isNotBlank() && !isThinking) {
                                    audioEngine.playWaterDrop()
                                    val sentText = inputText.trim()
                                    val userMsg = PrayerMessage(
                                        id = "chat_user_${System.currentTimeMillis()}",
                                        sender = "user",
                                        text = sentText,
                                        associatedFruit = selectedFruit,
                                        createdAt = System.currentTimeMillis()
                                    )
                                    inputText = ""
                                    onSendMessage(userMsg, sentText)

                                    isThinking = true
                                    scope.launch {
                                        kotlinx.coroutines.delay(900)
                                        val pastoral = devotionalService.getCounsel(sentText, prayerChats, geminiApiKey)
                                        isThinking = false
                                        audioEngine.playBellChime(528f)
                                        val godMsg = PrayerMessage(
                                            id = "chat_god_${System.currentTimeMillis()}",
                                            sender = "god_ai",
                                            text = pastoral.text,
                                            verse1925 = pastoral.verse1925,
                                            reference = pastoral.reference,
                                            createdAt = System.currentTimeMillis()
                                        )
                                        onSendMessage(godMsg, sentText)
                                    }
                                }
                            },
                            enabled = inputText.isNotBlank() && !isThinking,
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(if (inputText.isNotBlank() && !isThinking) GoldPrimary else Color.White.copy(alpha = 0.1f))
                                .testTag("send_prayer_button")
                        ) {
                            Icon(
                                Icons.Default.Send,
                                contentDescription = "Gửi",
                                tint = if (inputText.isNotBlank() && !isThinking) Color.Black else Color.White.copy(alpha = 0.4f),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }

        // Dove flying overlay
        if (showDoveOverlay) {
            DoveReleaseOverlay(
                burdenText = doveBurdenText,
                onDismiss = { showDoveOverlay = false }
            )
        }
    }
}

@Composable
fun DoveReleaseOverlay(
    burdenText: String,
    onDismiss: () -> Unit
) {
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(3000)
        onDismiss()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.7f))
            .clickable { onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Text("🕊️", fontSize = 64.sp)
            Spacer(Modifier.height(14.dp))
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color(0xF21E1065),
                border = BorderStroke(1.5.dp, Color(0xFFF59E0B)),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "\"Gánh nặng đã được trao dâng...\"",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFDE68A),
                        fontStyle = FontStyle.Italic
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "\"$burdenText\"",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.85f),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        lineHeight = 17.sp
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        "✨ Thi-thiên 55:22 • Ngài sẽ nâng đỡ ngươi",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF34D399)
                    )
                }
            }
        }
    }
}
