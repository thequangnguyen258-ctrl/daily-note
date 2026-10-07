package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.audio.SanctuaryAudioEngine
import com.example.data.model.*
import com.example.ui.theme.GoldPrimary

@Composable
fun LeafDetailDialog(
    leaf: CoreNeedLeaf,
    recentProblem: String,
    onAmenConfirm: () -> Unit,
    onDismiss: () -> Unit,
    audioEngine: SanctuaryAudioEngine
) {
    var amenInput by remember { mutableStateOf("") }
    var amenSuccess by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    val verse = leaf.verses.firstOrNull() ?: LeafVerse(
        verse1925 = "Đức Giê-hô-va là Đấng chăn giữ tôi; tôi sẽ chẳng thiếu thốn gì.",
        reference = "Thi-thiên 23:1",
        comfort = "Chúa luôn ở bên bạn."
    )

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = Color(0xFF0F111A),
            border = BorderStroke(1.5.dp, Color(0xFF10B981)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(leaf.icon, fontSize = 28.sp)
                        Spacer(Modifier.width(10.dp))
                        Column {
                            Text(
                                "Lá Nan Đề: ${leaf.title}",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFDE68A)
                            )
                            Text(
                                "Thấu cảm & chữa lành nội tâm",
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.6f)
                            )
                        }
                    }
                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Đóng", tint = Color.White.copy(alpha = 0.7f))
                    }
                }

                Spacer(Modifier.height(14.dp))

                // Gần đây bạn gặp phải
                Surface(
                    color = Color(0x33451A03),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Color(0x40F59E0B)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            "📝 Gần đây bạn gặp phải / Tâm sự:",
                            fontSize = 10.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFDE68A)
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "\"$recentProblem\"",
                            fontSize = 11.5.sp,
                            fontStyle = FontStyle.Italic,
                            color = Color(0xFFFEF3C7),
                            lineHeight = 16.sp
                        )
                    }
                }

                Spacer(Modifier.height(10.dp))

                // Căn nguyên tâm lý
                Surface(
                    color = Color(0x334D0218),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Color(0x40F43F5E)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            "🌿 Phân Tích Cội Rễ Tâm Hồn:",
                            fontSize = 10.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFDA4AF)
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "Lý do mà bạn có suy nghĩ như này là đến từ việc ${leaf.wound.trimEnd('.')}, nên đã hình thành niềm tin: ${leaf.belief}",
                            fontSize = 11.5.sp,
                            color = Color(0xFFFFE4E6),
                            lineHeight = 16.sp
                        )
                    }
                }

                Spacer(Modifier.height(10.dp))

                // Khối Lời Chúa 1925 Chuẩn Mực
                Surface(
                    color = Color(0xFF064E3B).copy(alpha = 0.4f),
                    shape = RoundedCornerShape(18.dp),
                    border = BorderStroke(1.2.dp, Color(0xFF10B981)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "📖 Chúa Đã Nói Là (Bản 1925):",
                                fontSize = 10.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF34D399)
                            )
                            Text(
                                verse.reference,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFDE68A)
                            )
                        }
                        Spacer(Modifier.height(6.dp))
                        Text(
                            "\"${verse.verse1925}\"",
                            fontSize = 12.sp,
                            fontStyle = FontStyle.Italic,
                            color = Color(0xFFD1FAE5),
                            lineHeight = 17.sp
                        )
                    }
                }

                Spacer(Modifier.height(10.dp))

                // Lời Khích Lệ
                Surface(
                    color = Color(0x331E1A4D),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Color(0x40818CF8)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            "✨ Lời Khích Lệ:",
                            fontSize = 10.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFA5B4FC)
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            verse.comfort,
                            fontSize = 11.5.sp,
                            color = Color(0xFFE0E7FF),
                            lineHeight = 16.sp
                        )
                    }
                }

                Spacer(Modifier.height(14.dp))

                // Amen Action
                Divider(color = Color.White.copy(alpha = 0.1f))
                Spacer(Modifier.height(10.dp))

                if (amenSuccess) {
                    Surface(
                        color = Color(0x4D002C22),
                        border = BorderStroke(1.dp, Color(0xFF10B981)),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF34D399), modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text(
                                "Amen! Bạn đã nhận +1 Giọt Nước Sự Sống.",
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF6EE7B7)
                            )
                        }
                    }
                } else {
                    Text(
                        "Gõ 'amen' để xác quyết Lời Chúa và nhận +1 giọt nước:",
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                    Spacer(Modifier.height(6.dp))
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        OutlinedTextField(
                            value = amenInput,
                            onValueChange = {
                                amenInput = it
                                showError = false
                            },
                            placeholder = { Text("Gõ 'amen'...", fontSize = 11.sp, color = Color.White.copy(alpha = 0.4f)) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFFF59E0B),
                                unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            ),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f).testTag("amen_input")
                        )
                        Spacer(Modifier.width(8.dp))
                        Button(
                            onClick = {
                                val clean = amenInput.trim().lowercase()
                                if (clean.isEmpty() || clean.contains("amen")) {
                                    audioEngine.playBellChime(528f)
                                    amenSuccess = true
                                    onAmenConfirm()
                                } else {
                                    showError = true
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("amen_button")
                        ) {
                            Text("Amen!", fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 12.sp)
                        }
                    }
                    if (showError) {
                        Text(
                            "Vui lòng gõ chữ 'amen' hoặc nhấn Amen!",
                            color = Color(0xFFF87171),
                            fontSize = 10.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FruitDetailDialog(
    fruit: Fruit,
    recentProblem: String,
    memories: List<FruitMemory>,
    onAddMemory: (String) -> Unit,
    onAmenConfirm: () -> Unit,
    onDismiss: () -> Unit,
    audioEngine: SanctuaryAudioEngine
) {
    var memoryText by remember { mutableStateOf("") }
    var amenSuccess by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = Color(0xFF0F111A),
            border = BorderStroke(1.5.dp, fruit.color),
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(fruit.icon, fontSize = 28.sp)
                        Spacer(Modifier.width(10.dp))
                        Column {
                            Text(
                                "Trái: ${fruit.name}",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFDE68A)
                            )
                            Text(
                                "${fruit.latin} • Ga-la-ti 5:22-23",
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.6f)
                            )
                        }
                    }
                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Đóng", tint = Color.White.copy(alpha = 0.7f))
                    }
                }

                Spacer(Modifier.height(14.dp))

                // Kinh Thánh Bản 1925
                Surface(
                    color = Color.Black.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, fruit.color.copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("📖 Lời Chúa 1925:", fontSize = 10.5.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFDE68A))
                            Text(fruit.reference, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = fruit.color)
                        }
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "\"${fruit.verse1925}\"",
                            fontSize = 12.sp,
                            fontStyle = FontStyle.Italic,
                            color = Color.White.copy(alpha = 0.95f),
                            lineHeight = 17.sp
                        )
                    }
                }

                Spacer(Modifier.height(10.dp))

                // Ý nghĩa
                Surface(
                    color = Color(0x1AFFFFFF),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("💡 Ý Nghĩa Thuộc Linh:", fontSize = 10.5.sp, fontWeight = FontWeight.Bold, color = Color(0xFF34D399))
                        Spacer(Modifier.height(4.dp))
                        Text(fruit.meaning, fontSize = 11.5.sp, color = Color.White.copy(alpha = 0.85f))
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Ghi lại kỷ niệm đã sống với trái này
                Text("Ghi lại một lần bạn đã sống với '${fruit.name}':", fontSize = 11.sp, color = Color.White.copy(alpha = 0.8f))
                Spacer(Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = memoryText,
                        onValueChange = { memoryText = it },
                        placeholder = { Text("Ghi nhớ ơn phước...", fontSize = 11.sp, color = Color.White.copy(alpha = 0.4f)) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = fruit.color,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (memoryText.isNotBlank()) {
                                audioEngine.playWaterDrop()
                                onAddMemory(memoryText.trim())
                                memoryText = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = fruit.color),
                        shape = RoundedCornerShape(12.dp),
                        enabled = memoryText.isNotBlank()
                    ) {
                        Text("Lưu", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 11.5.sp)
                    }
                }

                if (memories.isNotEmpty()) {
                    Spacer(Modifier.height(8.dp))
                    Text("Kỷ niệm đã ghi (${memories.size}):", fontSize = 10.5.sp, color = Color.White.copy(alpha = 0.6f))
                    Spacer(Modifier.height(4.dp))
                    Column(modifier = Modifier.fillMaxWidth()) {
                        memories.takeLast(3).forEach { m ->
                            Text(
                                "• ${m.text}",
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.8f),
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                        }
                    }
                }

                Spacer(Modifier.height(14.dp))

                // Amen Action
                Button(
                    onClick = {
                        audioEngine.playBellChime(528f)
                        amenSuccess = true
                        onAmenConfirm()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth().testTag("fruit_amen_button")
                ) {
                    Text(
                        if (amenSuccess) "Amen! Đã thêm +1 giọt nước" else "Gõ Amen Xác Quyết (+1 Giọt Nước)",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
fun RootDetailDialog(
    recentProblem: String,
    onAmenConfirm: () -> Unit,
    onDismiss: () -> Unit,
    audioEngine: SanctuaryAudioEngine
) {
    var amenSuccess by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = Color(0xFF0F111A),
            border = BorderStroke(1.5.dp, Color(0xFFF59E0B)),
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🌱", fontSize = 28.sp)
                        Spacer(Modifier.width(10.dp))
                        Column {
                            Text(
                                "7 Tầng Rễ Đức Tin Nương Cậy",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFDE68A)
                            )
                            Text(
                                "Bám sâu vào nguồn nước hằng sống",
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.6f)
                            )
                        }
                    }
                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Đóng", tint = Color.White.copy(alpha = 0.7f))
                    }
                }

                Spacer(Modifier.height(14.dp))

                Text(
                    "Trước mọi bão tố và nan đề cuộc sống, hãy bám rễ vững vàng trong Lời Đức Chúa Trời:",
                    fontSize = 11.5.sp,
                    color = Color.White.copy(alpha = 0.85f),
                    lineHeight = 16.sp
                )

                Spacer(Modifier.height(10.dp))

                SEVEN_FAITH_ROOTS.forEach { root ->
                    Surface(
                        color = Color(0x1AFFFFFF),
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(0.8.dp, Color(0x33F59E0B)),
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(root.theme, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFDE68A))
                                Text(root.reference, fontSize = 10.5.sp, fontWeight = FontWeight.Bold, color = Color(0xFFF59E0B))
                            }
                            Spacer(Modifier.height(2.dp))
                            Text(
                                "\"${root.verse1925}\"",
                                fontSize = 11.sp,
                                fontStyle = FontStyle.Italic,
                                color = Color.White.copy(alpha = 0.9f),
                                lineHeight = 15.sp
                            )
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = {
                        audioEngine.playBellChime(528f)
                        amenSuccess = true
                        onAmenConfirm()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth().testTag("root_amen_button")
                ) {
                    Text(
                        if (amenSuccess) "Amen! Đã nhận +1 giọt nước" else "Amen! Nương Cậy Lời Chúa (+1 Giọt)",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
fun ScriptureCardDialog(
    homeMessage: HomeMessage,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    val shareText = "📖 ${homeMessage.reference} (Bản 1925):\n" +
            "\"${homeMessage.verse1925}\"\n\n" +
            "✨ Lời Chúc: ${homeMessage.blessing}\n\n" +
            "— Tạo từ ứng dụng 'Cây Sự Sống'"

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = Color(0xFF090614),
            border = BorderStroke(2.dp, Color(0xFFF59E0B)),
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("✨ Thiệp Lời Chúa 1925", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFDE68A))
                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Đóng", tint = Color.White.copy(alpha = 0.7f))
                    }
                }

                Spacer(Modifier.height(16.dp))

                // The Sacred Card View
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    border = BorderStroke(1.5.dp, Color(0x66F59E0B)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(4f / 5f)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(
                                        Color(0xFF2E1065),
                                        Color(0xFF0F172A),
                                        Color(0xFF050814)
                                    )
                                )
                            )
                            .padding(20.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("🕊️ 🌳", fontSize = 32.sp)
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    "KINH THÁNH TRUYỀN THỐNG 1925",
                                    fontSize = 9.sp,
                                    letterSpacing = 2.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFFDE68A).copy(alpha = 0.8f)
                                )
                            }

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    "\"${homeMessage.verse1925}\"",
                                    fontSize = 14.sp,
                                    fontStyle = FontStyle.Italic,
                                    textAlign = TextAlign.Center,
                                    color = Color(0xFFFEF3C7),
                                    lineHeight = 20.sp
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    "— ${homeMessage.reference} —",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFF59E0B)
                                )
                            }

                            Text(
                                "✨ ${homeMessage.blessing}",
                                fontSize = 10.5.sp,
                                textAlign = TextAlign.Center,
                                color = Color(0xFFA7F3D0),
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("Lời Chúa 1925", shareText)
                            clipboard.setPrimaryClip(clip)
                            Toast.makeText(context, "Đã sao chép Lời Chúa vào bộ nhớ tạm!", Toast.LENGTH_SHORT).show()
                        },
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f)),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.ContentCopy, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Sao Chép", color = Color.White, fontSize = 11.5.sp)
                    }

                    Spacer(Modifier.width(8.dp))

                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_SUBJECT, "Lời Chúa 1925 - Cây Sự Sống")
                                putExtra(Intent.EXTRA_TEXT, shareText)
                            }
                            context.startActivity(Intent.createChooser(intent, "Chia sẻ Lời Chúa"))
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Share, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Chia Sẻ", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 11.5.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun MiraclePondDialog(
    waterDrops: Int,
    onAngelResurrect: () -> Unit,
    onHolyFire: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = Color(0xFF0F172A),
            border = BorderStroke(1.5.dp, Color(0xFF38BDF8)),
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.WaterDrop, contentDescription = null, tint = Color(0xFF38BDF8), modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Kho Nước Hằng Sống", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE0F2FE))
                    }
                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Đóng", tint = Color.White.copy(alpha = 0.7f))
                    }
                }

                Spacer(Modifier.height(12.dp))

                Surface(
                    color = Color(0x33083344),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Color(0x4D38BDF8)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Hiện có trong nguồn nước:", fontSize = 11.sp, color = Color(0xFFBAE6FD))
                        Text(
                            "$waterDrops Giọt",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF38BDF8)
                        )
                    }
                }

                Spacer(Modifier.height(14.dp))

                // Angel Rain
                Surface(
                    color = Color(0x26083344),
                    shape = RoundedCornerShape(18.dp),
                    border = BorderStroke(1.dp, Color(0x6638BDF8)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onAngelResurrect() }
                        .padding(bottom = 10.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("👼 Thiên Sứ Cầm Bình Đổ Nước", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFFBAE6FD))
                            Surface(
                                color = Color(0x3338BDF8),
                                shape = CircleShape
                            ) {
                                Text("-100 Giọt", fontSize = 10.sp, color = Color(0xFF38BDF8), fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                            }
                        }
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "Đất đang thiếu nước, hãy tưới đi! Thiên Sứ từ trời giáng lâm cầm bình ngọc đổ Nước Hằng Sống xuống tưới đẫm cành lá và gốc rễ.",
                            fontSize = 11.sp,
                            color = Color.White.copy(alpha = 0.75f),
                            lineHeight = 15.sp
                        )
                    }
                }

                // Holy Fire
                Surface(
                    color = Color(0x264D0218),
                    shape = RoundedCornerShape(18.dp),
                    border = BorderStroke(1.dp, Color(0x66F43F5E)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onHolyFire() }
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("🔥 Thiên Sứ Đổ Hòn Lửa Thiêu Cỏ Lùng", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFDA4AF))
                            Surface(
                                color = Color(0x33F43F5E),
                                shape = CircleShape
                            ) {
                                Text("-100 Giọt", fontSize = 10.sp, color = Color(0xFFF43F5E), fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                            }
                        }
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "Dùng lửa thiêu cỏ lùng đi! Thiên Sứ giáng lâm thả 1 hòn lửa xuống đất rồi bùng cháy dữ dội thiêu rụi toàn bộ cỏ lùng, bụi gai và giải phóng sỏi đá chặn rễ.",
                            fontSize = 11.sp,
                            color = Color.White.copy(alpha = 0.75f),
                            lineHeight = 15.sp
                        )
                    }
                }
            }
        }
    }
}
