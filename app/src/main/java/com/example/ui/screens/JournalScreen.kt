package com.example.ui.screens

import androidx.compose.animation.*
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.SanctuaryAudioEngine
import com.example.data.model.*
import com.example.ui.theme.GoldPrimary
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JournalScreen(
    journals: List<JournalEntry>,
    onSaveJournal: (JournalEntry) -> Unit,
    onDeleteJournal: (String) -> Unit,
    audioEngine: SanctuaryAudioEngine,
    modifier: Modifier = Modifier
) {
    var isWriting by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var selectedMoods by remember { mutableStateOf(setOf("cam_ta")) }
    var selectedCategory by remember { mutableStateOf("Cuộc sống") }
    var selectedFruit by remember { mutableStateOf("peace") }

    val categories = listOf("Cuộc sống", "Công việc", "Gia đình", "Học tập", "Tình cảm", "Sức khỏe", "Tâm linh & Đức tin", "Tài chính")
    val dateFormat = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF090614))
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .padding(bottom = 90.dp)
    ) {
        item {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "Nhật Ký Hàng Ngày",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6EE7B7)
                    )
                    Text(
                        "Ghi lại câu chuyện và cảm xúc trong ngày",
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.6f)
                    )
                }

                if (!isWriting) {
                    Button(
                        onClick = { isWriting = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                        shape = CircleShape,
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                        modifier = Modifier.testTag("open_write_journal_button")
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Viết Mới", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 11.5.sp)
                    }
                }
            }

            Spacer(Modifier.height(12.dp))
        }

        if (isWriting) {
            item {
                Surface(
                    shape = RoundedCornerShape(22.dp),
                    color = Color(0xFF130E26),
                    border = BorderStroke(1.2.dp, Color(0xFF10B981)),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    shadowElevation = 8.dp
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Trang Nhật Ký Hôm Nay", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6EE7B7))
                            TextButton(onClick = { isWriting = false }) {
                                Text("Hủy", color = Color.White.copy(alpha = 0.6f), fontSize = 11.sp)
                            }
                        }

                        Spacer(Modifier.height(8.dp))

                        // Title
                        OutlinedTextField(
                            value = title,
                            onValueChange = { title = it },
                            placeholder = { Text("Tiêu đề nhật ký (tùy chọn)...", fontSize = 11.5.sp, color = Color.White.copy(alpha = 0.35f)) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF10B981),
                                unfocusedBorderColor = Color.White.copy(alpha = 0.15f),
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            ),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth().testTag("journal_title_input")
                        )

                        Spacer(Modifier.height(12.dp))

                        // Multi-mood selector
                        Text(
                            "Tâm trạng hôm nay (Có thể chọn nhiều):",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                        Spacer(Modifier.height(6.dp))

                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            MOOD_ITEMS.forEach { mood ->
                                val isSelected = selectedMoods.contains(mood.id)
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = if (isSelected) Color(0xFF10B981).copy(alpha = 0.35f) else Color(0x1AFFFFFF),
                                    border = BorderStroke(1.dp, if (isSelected) Color(0xFF34D399) else Color(0x1AFFFFFF)),
                                    modifier = Modifier.clickable {
                                        selectedMoods = if (isSelected) {
                                            if (selectedMoods.size > 1) selectedMoods - mood.id else selectedMoods
                                        } else {
                                            selectedMoods + mood.id
                                        }
                                    }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(mood.icon, fontSize = 12.sp)
                                        Spacer(Modifier.width(4.dp))
                                        Text(
                                            mood.label,
                                            fontSize = 10.5.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                            color = if (isSelected) Color(0xFFD1FAE5) else Color.White.copy(alpha = 0.7f)
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(Modifier.height(12.dp))

                        // Category & Fruit selection
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            // Category
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Chủ đề:", fontSize = 11.sp, color = Color.White.copy(alpha = 0.7f))
                                Spacer(Modifier.height(4.dp))
                                var categoryMenuExpanded by remember { mutableStateOf(false) }
                                Box {
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = Color.Black.copy(alpha = 0.4f),
                                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.15f)),
                                        modifier = Modifier.fillMaxWidth().clickable { categoryMenuExpanded = true }
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(selectedCategory, fontSize = 11.sp, color = Color.White)
                                            Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color.White.copy(alpha = 0.6f))
                                        }
                                    }
                                    DropdownMenu(
                                        expanded = categoryMenuExpanded,
                                        onDismissRequest = { categoryMenuExpanded = false }
                                    ) {
                                        categories.forEach { cat ->
                                            DropdownMenuItem(
                                                text = { Text(cat, fontSize = 12.sp) },
                                                onClick = {
                                                    selectedCategory = cat
                                                    categoryMenuExpanded = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }

                            // Associated Fruit
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Gắn kết Trái:", fontSize = 11.sp, color = Color.White.copy(alpha = 0.7f))
                                Spacer(Modifier.height(4.dp))
                                var fruitMenuExpanded by remember { mutableStateOf(false) }
                                val currentFruitObj = NINE_FRUITS.firstOrNull { it.id == selectedFruit } ?: NINE_FRUITS.first()
                                Box {
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = Color.Black.copy(alpha = 0.4f),
                                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.15f)),
                                        modifier = Modifier.fillMaxWidth().clickable { fruitMenuExpanded = true }
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text("${currentFruitObj.icon} ${currentFruitObj.name}", fontSize = 11.sp, color = Color.White)
                                            Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color.White.copy(alpha = 0.6f))
                                        }
                                    }
                                    DropdownMenu(
                                        expanded = fruitMenuExpanded,
                                        onDismissRequest = { fruitMenuExpanded = false }
                                    ) {
                                        NINE_FRUITS.forEach { f ->
                                            DropdownMenuItem(
                                                text = { Text("${f.icon} ${f.name}", fontSize = 12.sp) },
                                                onClick = {
                                                    selectedFruit = f.id
                                                    fruitMenuExpanded = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(Modifier.height(12.dp))

                        // Content
                        OutlinedTextField(
                            value = content,
                            onValueChange = { content = it },
                            placeholder = { Text("Kể lại những gì đã diễn ra, niềm vui, khó khăn mệt mỏi, hoặc điều bạn muốn giãi bày hôm nay...", fontSize = 11.5.sp, color = Color.White.copy(alpha = 0.35f)) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF10B981),
                                unfocusedBorderColor = Color.White.copy(alpha = 0.15f),
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            ),
                            minLines = 5,
                            maxLines = 10,
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.fillMaxWidth().testTag("journal_content_input")
                        )

                        Spacer(Modifier.height(14.dp))

                        Button(
                            onClick = {
                                if (content.isNotBlank()) {
                                    audioEngine.playWaterDrop()
                                    val newEntry = JournalEntry(
                                        id = "journal_${System.currentTimeMillis()}",
                                        title = if (title.isNotBlank()) title.trim() else "Nhật ký ${dateFormat.format(Date())}",
                                        content = content.trim(),
                                        moods = selectedMoods.toList(),
                                        category = selectedCategory,
                                        associatedFruit = selectedFruit,
                                        createdAt = System.currentTimeMillis()
                                    )
                                    onSaveJournal(newEntry)
                                    title = ""
                                    content = ""
                                    isWriting = false
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                            shape = RoundedCornerShape(14.dp),
                            enabled = content.isNotBlank(),
                            modifier = Modifier.fillMaxWidth().height(44.dp).testTag("save_journal_button")
                        ) {
                            Icon(Icons.Default.WaterDrop, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(6.dp))
                            Text(
                                "Lưu Nhật Ký & Tưới Cây (+10 Giọt Nước)",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }

        item {
            Text(
                "LỊCH SỬ NHẬT KÝ (${journals.size})",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White.copy(alpha = 0.5f),
                letterSpacing = 1.sp
            )
            Spacer(Modifier.height(8.dp))
        }

        if (journals.isEmpty()) {
            item {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color(0x0DFFFFFF),
                    border = BorderStroke(1.dp, Color(0x1AFFFFFF)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("📖", fontSize = 32.sp)
                        Spacer(Modifier.height(8.dp))
                        Text("Chưa có bài viết nhật ký nào", fontSize = 12.sp, color = Color.White.copy(alpha = 0.6f))
                        Text("Hãy bấm 'Viết Mới' để bắt đầu ghi nhớ và chăm sóc Cây Sự Sống!", fontSize = 10.5.sp, color = Color.White.copy(alpha = 0.4f))
                    }
                }
            }
        } else {
            items(journals.reversed(), key = { it.id }) { entry ->
                val fruit = NINE_FRUITS.firstOrNull { it.id == entry.associatedFruit }
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = Color(0xFF130E26),
                    border = BorderStroke(1.dp, Color(0x26FFFFFF)),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                entry.title,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF6EE7B7)
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    dateFormat.format(Date(entry.createdAt)),
                                    fontSize = 10.5.sp,
                                    color = Color.White.copy(alpha = 0.5f)
                                )
                                Spacer(Modifier.width(6.dp))
                                IconButton(
                                    onClick = { onDeleteJournal(entry.id) },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(Icons.Default.Delete, contentDescription = "Xóa", tint = Color.White.copy(alpha = 0.35f), modifier = Modifier.size(14.dp))
                                }
                            }
                        }

                        Spacer(Modifier.height(6.dp))

                        Text(
                            entry.content,
                            fontSize = 11.5.sp,
                            color = Color.White.copy(alpha = 0.9f),
                            lineHeight = 16.5.sp
                        )

                        Spacer(Modifier.height(10.dp))

                        // Badges
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            entry.moods.forEach { moodId ->
                                val moodObj = MOOD_ITEMS.firstOrNull { it.id == moodId }
                                if (moodObj != null) {
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = Color(0x33002C22)
                                    ) {
                                        Text(
                                            "${moodObj.icon} ${moodObj.label}",
                                            fontSize = 9.5.sp,
                                            color = Color(0xFFA7F3D0),
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }

                            Surface(shape = RoundedCornerShape(6.dp), color = Color(0x1AFFFFFF)) {
                                Text("🏷️ ${entry.category}", fontSize = 9.5.sp, color = Color.White.copy(alpha = 0.7f), modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                            }

                            if (fruit != null) {
                                Surface(shape = RoundedCornerShape(6.dp), color = Color(0x1AFFFFFF)) {
                                    Text("${fruit.icon} ${fruit.name}", fontSize = 9.5.sp, color = Color(0xFFFDE68A), modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
