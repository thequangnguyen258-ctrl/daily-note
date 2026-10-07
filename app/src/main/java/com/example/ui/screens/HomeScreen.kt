package com.example.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.SanctuaryAudioEngine
import com.example.data.model.*
import com.example.ui.components.TreeCanvasVisualizer
import com.example.ui.theme.GoldPrimary

@Composable
fun HomeScreen(
    treeState: TreeState,
    heartFieldState: HeartFieldState,
    homeMessage: HomeMessage,
    onUpdateHomeMessage: (HomeMessage) -> Unit,
    onFruitClick: (Fruit) -> Unit,
    onLeavesClick: () -> Unit,
    onRootsClick: () -> Unit,
    onOpenPond: () -> Unit,
    onOpenScriptureCard: () -> Unit,
    onNavigateJournal: () -> Unit,
    onNavigatePrayer: () -> Unit,
    onOpenSettings: () -> Unit,
    onToggleThorny: () -> Unit = {},
    onTriggerWatering: () -> Unit = {},
    onTriggerHolyFire: () -> Unit = {},
    onAddWaterDrops: () -> Unit = {},
    audioEngine: SanctuaryAudioEngine,
    isWatering: Boolean,
    isHolyFire: Boolean,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    val stageTitle = when {
        treeState.waterDrops < 350 -> "🌱 Giai đoạn 1: Bám Rễ & Nảy Mầm"
        treeState.waterDrops < 600 -> "🌿 Giai đoạn 2: Cây Non Đâm Chồi Ra Lá"
        treeState.waterDrops < 900 -> "🌳 Giai đoạn 3: Vươn Cành & Nở Hoa Trắng"
        else -> "🍎 Giai đoạn 4: 9 Trái Thánh Linh Trĩu Cành"
    }

    val isThorny = heartFieldState.isNegative || heartFieldState.thornsLevel > 0
    val isWilted = treeState.isWilted

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF090614))
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .padding(bottom = 90.dp)
    ) {
        // App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("🕊️", fontSize = 22.sp)
                Spacer(Modifier.width(8.dp))
                Column {
                    Text(
                        "CÂY SỰ SỐNG",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFFFDE68A),
                        letterSpacing = 1.sp
                    )
                    Text(
                        "Cánh Đồng Tấm Lòng • Bản 1925",
                        fontSize = 10.5.sp,
                        color = Color.White.copy(alpha = 0.65f)
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                // Water Drops Button
                Surface(
                    shape = CircleShape,
                    color = Color(0x33083344),
                    border = BorderStroke(1.dp, Color(0x6638BDF8)),
                    modifier = Modifier
                        .clickable { onOpenPond() }
                        .testTag("water_drops_counter")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.WaterDrop, contentDescription = null, tint = Color(0xFF38BDF8), modifier = Modifier.size(14.dp))
                        Spacer(Modifier.width(4.dp))
                        Text(
                            "${treeState.waterDrops} Giọt",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF7DD3FC)
                        )
                    }
                }

                Spacer(Modifier.width(8.dp))

                // Mute toggle
                IconButton(
                    onClick = {
                        val muted = !audioEngine.isMuted()
                        audioEngine.setMuted(muted)
                    },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        if (audioEngine.isMuted()) Icons.Default.VolumeOff else Icons.Default.VolumeUp,
                        contentDescription = "Âm thanh",
                        tint = if (audioEngine.isMuted()) Color(0xFFF87171) else Color(0xFF34D399),
                        modifier = Modifier.size(18.dp)
                    )
                }

                // Settings icon
                IconButton(
                    onClick = onOpenSettings,
                    modifier = Modifier.size(32.dp).testTag("settings_button")
                ) {
                    Icon(Icons.Default.Settings, contentDescription = "Cài đặt", tint = Color(0xFFFDE68A), modifier = Modifier.size(18.dp))
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        // Stage & Level banner
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = Color.Black.copy(alpha = 0.4f),
            border = BorderStroke(1.dp, Color(0x26FFFFFF)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    stageTitle,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFDE68A)
                )
                Text(
                    "Cấp ${treeState.level}",
                    fontSize = 10.5.sp,
                    color = Color.White.copy(alpha = 0.5f)
                )
            }
        }

        Spacer(Modifier.height(6.dp))

        // Alert banners
        if (isWilted) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xCC451A03),
                border = BorderStroke(1.dp, Color(0xFFF59E0B)),
                modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp)
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                        Text("🥀", fontSize = 18.sp)
                        Spacer(Modifier.width(8.dp))
                        Column {
                            Text("Đất đang thiếu nước, hãy tưới đi!", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFDE68A))
                            Text("Thiên Sứ cầm bình đổ Nước Hằng Sống hồi sinh cây (-100 Giọt).", fontSize = 9.5.sp, color = Color.White.copy(alpha = 0.8f))
                        }
                    }
                    Button(
                        onClick = onOpenPond,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38BDF8)),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                        modifier = Modifier.height(28.dp)
                    ) {
                        Text("Tưới Nước", color = Color.Black, fontSize = 10.5.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        } else if (isThorny) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xCC4D0218),
                border = BorderStroke(1.dp, Color(0xFFEF4444)),
                modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp)
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFF87171), modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Cỏ lùng mọc cao, bụi gai quấn gốc, sỏi đá chặn rễ!", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFCA5A5))
                    }
                    Spacer(Modifier.height(2.dp))
                    Text(
                        "Đất đang thiếu nước, hãy tưới đi! Hoặc dùng Thiên Sứ đổ hòn lửa thiêu rụi cỏ lùng và bụi gai.",
                        fontSize = 10.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Spacer(Modifier.height(6.dp))
                    Button(
                        onClick = onOpenPond,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444)),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
                        modifier = Modifier.fillMaxWidth().height(28.dp)
                    ) {
                        Text("🔥 Thiên Sứ đổ hòn lửa thiêu cỏ lùng bụi gai (-100 Giọt)", color = Color.White, fontSize = 10.5.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        } else {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = Color(0x33002C22),
                border = BorderStroke(1.dp, Color(0x4010B981)),
                modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp)
            ) {
                Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF34D399), modifier = Modifier.size(14.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Mảnh đất tấm lòng được tưới đẫm nước, bình an và sinh hoa kết trái.", fontSize = 10.5.sp, color = Color(0xFFA7F3D0))
                }
            }
        }

        // Tree of Life Interactive Canvas
        TreeCanvasVisualizer(
            treeState = treeState,
            heartFieldState = heartFieldState,
            isWatering = isWatering,
            isHolyFire = isHolyFire,
            onFruitClick = onFruitClick,
            onLeavesClick = onLeavesClick,
            onRootsClick = onRootsClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        // Thanh Thử Nghiệm Trực Quan
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFF140E2A),
            border = BorderStroke(1.dp, Color(0x33F59E0B)),
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    "⚡ Thử Nghiệm Trực Quan (Chạm để xem ngay):",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFDE68A)
                )
                Spacer(Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // 1. Cỏ lùng / Bụi gai / Sỏi đá
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = if (isThorny) Color(0x33EF4444) else Color(0x1AFFFFFF),
                        border = BorderStroke(1.dp, if (isThorny) Color(0xFFEF4444) else Color(0x26FFFFFF)),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onToggleThorny() }
                    ) {
                        Column(
                            modifier = Modifier.padding(vertical = 6.dp, horizontal = 4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(if (isThorny) "🥀 Có Gai/Cỏ" else "🌱 Đất Lành", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = if (isThorny) Color(0xFFFCA5A5) else Color(0xFF6EE7B7))
                            Text("Bụi gai, sỏi đá", fontSize = 8.5.sp, color = Color.White.copy(alpha = 0.5f))
                        }
                    }

                    // 2. Thiên Sứ Đổ Bình Nước
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0x26083344),
                        border = BorderStroke(1.dp, Color(0x6638BDF8)),
                        modifier = Modifier
                            .weight(1.2f)
                            .clickable { onTriggerWatering() }
                    ) {
                        Column(
                            modifier = Modifier.padding(vertical = 6.dp, horizontal = 4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("👼 Đổ Bình Nước", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF7DD3FC))
                            Text("Tưới cành lá & đất", fontSize = 8.5.sp, color = Color(0xFF38BDF8))
                        }
                    }

                    // 3. Thiên Sứ Thả Hòn Lửa
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0x264D0218),
                        border = BorderStroke(1.dp, Color(0x66F97316)),
                        modifier = Modifier
                            .weight(1.2f)
                            .clickable { onTriggerHolyFire() }
                    ) {
                        Column(
                            modifier = Modifier.padding(vertical = 6.dp, horizontal = 4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("🔥 Thả Hòn Lửa", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFDBA74))
                            Text("Thiêu cỏ lùng bụi gai", fontSize = 8.5.sp, color = Color(0xFFF97316))
                        }
                    }

                    // 4. Thêm Giọt Nước
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0x1AF59E0B),
                        border = BorderStroke(1.dp, Color(0x33F59E0B)),
                        modifier = Modifier
                            .weight(0.9f)
                            .clickable { onAddWaterDrops() }
                    ) {
                        Column(
                            modifier = Modifier.padding(vertical = 6.dp, horizontal = 4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("💧 +100", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFDE68A))
                            Text("Nước", fontSize = 8.5.sp, color = Color(0xFFF59E0B))
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(10.dp))

        // Lời Chúa Soi Sáng Nan Đề Gần Nhất
        Surface(
            shape = RoundedCornerShape(22.dp),
            color = Color(0xFF130E26),
            border = BorderStroke(1.2.dp, Color(0x40F59E0B)),
            modifier = Modifier.fillMaxWidth(),
            shadowElevation = 8.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.MenuBook, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(6.dp))
                        Text(
                            "Lời Chúa Soi Sáng Nan Đề Gần Nhất",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFDE68A)
                        )
                    }

                    // Cycle verse
                    TextButton(
                        onClick = {
                            audioEngine.playBellChime(528f)
                            val randomRoot = SEVEN_FAITH_ROOTS.random()
                            onUpdateHomeMessage(
                                homeMessage.copy(
                                    verse1925 = randomRoot.verse1925,
                                    reference = randomRoot.reference,
                                    blessing = "Nguyện ơn phước và sự gìn giữ của Chúa hằng tuôn đổ trên bạn suốt ngày hôm nay."
                                )
                            )
                        },
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                        modifier = Modifier.height(28.dp)
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(14.dp))
                        Spacer(Modifier.width(3.dp))
                        Text("Đổi câu", fontSize = 10.5.sp, color = GoldPrimary)
                    }
                }

                Spacer(Modifier.height(10.dp))

                // Gần đây bạn gặp phải
                Surface(
                    color = Color(0x33451A03),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, Color(0x33F59E0B)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            "📝 Gần đây bạn gặp phải / Tâm sự:",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFDE68A)
                        )
                        Spacer(Modifier.height(2.dp))
                        Text(
                            "\"${homeMessage.recentProblem}\"",
                            fontSize = 11.5.sp,
                            fontStyle = FontStyle.Italic,
                            color = Color(0xFFFEF3C7),
                            lineHeight = 16.sp
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                // Chúa Đã Nói Là (Bản 1925)
                Surface(
                    color = Color.Black.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, Color(0x4010B981)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "📖 Chúa Đã Nói Là (Bản 1925):",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF34D399)
                            )
                            Text(
                                homeMessage.reference,
                                fontSize = 10.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFDE68A)
                            )
                        }
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "\"${homeMessage.verse1925}\"",
                            fontSize = 11.5.sp,
                            fontStyle = FontStyle.Italic,
                            color = Color(0xFFD1FAE5),
                            lineHeight = 16.sp
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                // Hãy Cố Lên
                Surface(
                    color = Color(0x331E1A4D),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, Color(0x33818CF8)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            "✨ Hãy Cố Lên:",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFA5B4FC)
                        )
                        Spacer(Modifier.height(2.dp))
                        Text(
                            homeMessage.comfortText,
                            fontSize = 11.sp,
                            color = Color(0xFFE0E7FF),
                            lineHeight = 15.sp
                        )
                    }
                }

                Spacer(Modifier.height(10.dp))

                // Create Card Button
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    OutlinedButton(
                        onClick = onOpenScriptureCard,
                        shape = CircleShape,
                        border = BorderStroke(1.dp, Color(0x6638BDF8)),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        modifier = Modifier.height(30.dp)
                    ) {
                        Icon(Icons.Default.Share, contentDescription = null, tint = Color(0xFF7DD3FC), modifier = Modifier.size(13.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Tạo Thiệp Lời Chúa & Chia Sẻ", fontSize = 10.5.sp, color = Color(0xFF7DD3FC))
                    }
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        // Quick Navigation Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = Color(0xFF003822),
                border = BorderStroke(1.dp, Color(0x6610B981)),
                modifier = Modifier
                    .weight(1f)
                    .clickable { onNavigateJournal() }
                    .testTag("home_write_journal_button")
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.EditNote, contentDescription = null, tint = Color(0xFF34D399), modifier = Modifier.size(24.dp))
                    Spacer(Modifier.height(4.dp))
                    Text("Viết Nhật Ký", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFFD1FAE5))
                    Text("+10 Giọt Nước", fontSize = 10.sp, color = Color(0xFF6EE7B7).copy(alpha = 0.8f))
                }
            }

            Surface(
                shape = RoundedCornerShape(18.dp),
                color = Color(0xFF2E1065),
                border = BorderStroke(1.dp, Color(0x66818CF8)),
                modifier = Modifier
                    .weight(1f)
                    .clickable { onNavigatePrayer() }
                    .testTag("home_prayer_chat_button")
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("🕊️", fontSize = 22.sp)
                    Spacer(Modifier.height(4.dp))
                    Text("Tâm Sự Với Chúa", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFFEDE9FE))
                    Text("+10 Giọt Nước", fontSize = 10.sp, color = Color(0xFFC4B5FD).copy(alpha = 0.8f))
                }
            }
        }
    }
}
