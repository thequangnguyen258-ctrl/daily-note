package com.example.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.*
import java.text.SimpleDateFormat
import java.util.*

private data class TimelineItem(
    val id: String,
    val type: String, // "journal" or "prayer"
    val title: String,
    val content: String,
    val date: Long,
    val fruitId: String? = null
)

@Composable
fun JourneyHistoryScreen(
    treeState: TreeState,
    journals: List<JournalEntry>,
    prayerChats: List<PrayerMessage>,
    onFruitClick: (Fruit) -> Unit,
    onRootsClick: () -> Unit,
    onLeavesClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableStateOf("all") }
    val dateFormat = remember { SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()) }

    val fruitCounts = remember(journals, prayerChats) {
        NINE_FRUITS.map { fruit ->
            val jCount = journals.count { it.associatedFruit == fruit.id }
            val pCount = prayerChats.count { it.associatedFruit == fruit.id }
            Pair(fruit, jCount + pCount)
        }
    }

    val timelineItems = remember(journals, prayerChats, selectedFilter) {
        val list = mutableListOf<TimelineItem>()
        journals.forEach {
            list.add(TimelineItem(it.id, "journal", it.title, it.content, it.createdAt, it.associatedFruit))
        }
        prayerChats.filter { it.sender == "user" }.forEach {
            list.add(TimelineItem(it.id, "prayer", "Lời Tâm Sự Cùng Chúa", it.text, it.createdAt, it.associatedFruit))
        }
        val sorted = list.sortedByDescending { it.date }
        when (selectedFilter) {
            "journal" -> sorted.filter { it.type == "journal" }
            "prayer" -> sorted.filter { it.type == "prayer" }
            else -> sorted
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF090614))
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .padding(bottom = 90.dp)
    ) {
        item {
            // Header
            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                Text(
                    "Hành Trình & Kỷ Niệm",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFDE68A)
                )
                Text(
                    "Bản đồ tăng trưởng Cây Sự Sống và dòng thời gian",
                    fontSize = 11.sp,
                    color = Color.White.copy(alpha = 0.6f)
                )
            }

            Spacer(Modifier.height(14.dp))

            // Tree Growth Stats
            Surface(
                shape = RoundedCornerShape(22.dp),
                color = Color(0xFF130E26),
                border = BorderStroke(1.dp, Color(0x40F59E0B)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "🌱 Trạng Thái Cây Sự Sống",
                            fontSize = 12.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFDE68A)
                        )
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFF59E0B)
                        ) {
                            Text(
                                "Cấp ${treeState.level}",
                                fontSize = 10.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = Color(0x26083344),
                            border = BorderStroke(1.dp, Color(0x3338BDF8)),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("💧 Nước", fontSize = 10.sp, color = Color(0xFF7DD3FC))
                                Text("${treeState.waterDrops}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF38BDF8))
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = Color(0x26451A03),
                            border = BorderStroke(1.dp, Color(0x33F59E0B)),
                            modifier = Modifier.weight(1f).clickable { onRootsClick() }
                        ) {
                            Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("🌱 Rễ", fontSize = 10.sp, color = Color(0xFFFDE68A))
                                Text("${treeState.rootsCount}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFFF59E0B))
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = Color(0x26064E3B),
                            border = BorderStroke(1.dp, Color(0x3310B981)),
                            modifier = Modifier.weight(1f).clickable { onLeavesClick() }
                        ) {
                            Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("🍃 Lá", fontSize = 10.sp, color = Color(0xFF6EE7B7))
                                Text("${treeState.leavesCount}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF10B981))
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = Color(0x264D0218),
                            border = BorderStroke(1.dp, Color(0x33F43F5E)),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("🍎 Quả", fontSize = 10.sp, color = Color(0xFFFDA4AF))
                                Text("${treeState.unlockedFruits.size}/9", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFFF43F5E))
                            }
                        }
                    }

                    Spacer(Modifier.height(14.dp))

                    Text(
                        "9 Trái Thánh Linh Bạn Đã Gieo Trồng:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Spacer(Modifier.height(6.dp))

                    // 9 Fruits grid
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        fruitCounts.chunked(3).forEach { row ->
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                row.forEach { (fruit, count) ->
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = if (count > 0) Color(0x33F59E0B) else Color(0x14FFFFFF),
                                        border = BorderStroke(1.dp, if (count > 0) Color(0x66F59E0B) else Color(0x1AFFFFFF)),
                                        modifier = Modifier.weight(1f).clickable { onFruitClick(fruit) }
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 6.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Text(fruit.icon, fontSize = 12.sp)
                                                Spacer(Modifier.width(3.dp))
                                                Text(fruit.name, fontSize = 10.sp, color = Color.White)
                                            }
                                            Text(
                                                "x$count",
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = if (count > 0) Color(0xFFFDE68A) else Color.White.copy(alpha = 0.4f)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Filter Tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color.Black.copy(alpha = 0.5f))
                    .padding(3.dp)
            ) {
                listOf(
                    Pair("all", "Tất cả (${journals.size + prayerChats.count { it.sender == "user" }})"),
                    Pair("journal", "Nhật Ký (${journals.size})"),
                    Pair("prayer", "Tâm Sự (${prayerChats.count { it.sender == "user" }})")
                ).forEach { (key, label) ->
                    val isSelected = selectedFilter == key
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(11.dp))
                            .background(if (isSelected) Color.White.copy(alpha = 0.18f) else Color.Transparent)
                            .clickable { selectedFilter = key }
                            .padding(vertical = 7.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            label,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) Color.White else Color.White.copy(alpha = 0.5f)
                        )
                    }
                }
            }

            Spacer(Modifier.height(10.dp))
        }

        if (timelineItems.isEmpty()) {
            item {
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = Color(0x0DFFFFFF),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp)
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(24.dp)) {
                        Text("Chưa có ghi chép nào trong mục này.", fontSize = 11.5.sp, color = Color.White.copy(alpha = 0.5f))
                    }
                }
            }
        } else {
            items(timelineItems, key = { it.id }) { item ->
                val isJournal = item.type == "journal"
                val fruit = NINE_FRUITS.firstOrNull { it.id == item.fruitId }

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = if (isJournal) Color(0x26002C22) else Color(0x261E1A4D),
                    border = BorderStroke(1.dp, if (isJournal) Color(0x3310B981) else Color(0x33818CF8)),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = if (isJournal) Color(0x3310B981) else Color(0x33818CF8)
                            ) {
                                Text(
                                    if (isJournal) "📖 Nhật Ký" else "🕊️ Tâm Sự Với Chúa",
                                    fontSize = 9.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isJournal) Color(0xFF6EE7B7) else Color(0xFFC4B5FD),
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }

                            Text(
                                dateFormat.format(Date(item.date)),
                                fontSize = 10.sp,
                                color = Color.White.copy(alpha = 0.45f)
                            )
                        }

                        Spacer(Modifier.height(6.dp))

                        Text(
                            item.content,
                            fontSize = 11.5.sp,
                            color = Color.White.copy(alpha = 0.9f),
                            lineHeight = 16.sp
                        )

                        if (fruit != null) {
                            Spacer(Modifier.height(6.dp))
                            Text(
                                "${fruit.icon} ${fruit.name}",
                                fontSize = 9.5.sp,
                                color = Color(0xFFFDE68A)
                            )
                        }
                    }
                }
            }
        }
    }
}
