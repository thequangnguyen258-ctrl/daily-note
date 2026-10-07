package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.DevotionalStorage
import com.example.data.model.AppSettings
import com.example.data.model.UserAccount
import com.example.ui.theme.GoldPrimary

@Composable
fun SettingsScreen(
    storage: DevotionalStorage,
    currentUser: UserAccount,
    onUserUpdated: (UserAccount) -> Unit,
    onLogout: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val accounts = remember { mutableStateOf(storage.getAllAccounts()) }

    var fullName by remember { mutableStateOf(currentUser.fullName) }
    var password by remember { mutableStateOf(currentUser.password) }
    var profileSaveSuccess by remember { mutableStateOf(false) }

    // Admin member management
    var newMemberUsername by remember { mutableStateOf("") }
    var memberErrorMsg by remember { mutableStateOf<String?>(null) }
    var memberSuccessMsg by remember { mutableStateOf<String?>(null) }

    // Settings
    val currentSettings = remember { storage.loadSettings() }
    var geminiKey by remember { mutableStateOf(currentSettings.geminiApiKey) }
    var customAudioUrl by remember { mutableStateOf(currentSettings.customAudioUrl) }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF090614))
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .padding(bottom = 90.dp)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack, modifier = Modifier.size(28.dp)) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Quay lại", tint = Color.White)
                }
                Spacer(Modifier.width(6.dp))
                Column {
                    Text(
                        "Hồ Sơ & Cài Đặt",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        "Bảo mật tài khoản & tùy chỉnh không gian",
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.6f)
                    )
                }
            }
        }

        Spacer(Modifier.height(14.dp))

        // 1. Hồ sơ tài khoản của bạn
        Surface(
            shape = RoundedCornerShape(22.dp),
            color = Color(0xFF130E26),
            border = BorderStroke(1.2.dp, Color(0x4010B981)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AccountCircle, contentDescription = null, tint = Color(0xFF34D399), modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Hồ Sơ Tài Khoản Của Bạn", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6EE7B7))
                    }
                    Surface(shape = CircleShape, color = Color(0x3310B981)) {
                        Text(
                            "@${currentUser.username}",
                            fontSize = 10.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF34D399),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(Modifier.height(6.dp))
                Text(
                    "Tên đăng nhập hệ thống: @${currentUser.username} (cố định dùng khi đăng nhập). Bạn có thể đổi tên hiển thị và mật khẩu bên dưới:",
                    fontSize = 10.5.sp,
                    color = Color.White.copy(alpha = 0.6f),
                    lineHeight = 15.sp
                )

                Spacer(Modifier.height(12.dp))

                Text("Tên hiển thị / Họ và tên:", fontSize = 11.sp, color = Color.White.copy(alpha = 0.8f))
                Spacer(Modifier.height(4.dp))
                OutlinedTextField(
                    value = fullName,
                    onValueChange = {
                        fullName = it
                        profileSaveSuccess = false
                    },
                    placeholder = { Text("Nhập tên của bạn...", fontSize = 11.5.sp, color = Color.White.copy(alpha = 0.35f)) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF10B981),
                        unfocusedBorderColor = Color.White.copy(alpha = 0.15f),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(10.dp))

                Text("Mật khẩu mới:", fontSize = 11.sp, color = Color.White.copy(alpha = 0.8f))
                Spacer(Modifier.height(4.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        profileSaveSuccess = false
                    },
                    placeholder = { Text("Nhập mật khẩu mới...", fontSize = 11.5.sp, color = Color.White.copy(alpha = 0.35f)) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF10B981),
                        unfocusedBorderColor = Color.White.copy(alpha = 0.15f),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                if (profileSaveSuccess) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "✅ Đã cập nhật tên và mật khẩu mới thành công!",
                        fontSize = 11.sp,
                        color = Color(0xFF34D399)
                    )
                }

                Spacer(Modifier.height(14.dp))

                Button(
                    onClick = {
                        if (password.length < 3) {
                            Toast.makeText(context, "Mật khẩu tối thiểu 3 ký tự.", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        val updated = currentUser.copy(
                            fullName = fullName.trim().ifEmpty { currentUser.username },
                            password = password.trim()
                        )
                        storage.updateAccount(updated)
                        onUserUpdated(updated)
                        accounts.value = storage.getAllAccounts()
                        profileSaveSuccess = true
                        Toast.makeText(context, "Đã lưu thông tin tài khoản!", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().height(40.dp)
                ) {
                    Icon(Icons.Default.Save, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Lưu Tên & Đổi Mật Khẩu", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }

        Spacer(Modifier.height(14.dp))

        // 2. Thành viên hệ thống (Admin & Danh sách)
        Surface(
            shape = RoundedCornerShape(22.dp),
            color = Color(0xFF130E26),
            border = BorderStroke(1.dp, Color(0x33F59E0B)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Group, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Thành Viên Hệ Thống (${accounts.value.size})", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFDE68A))
                    }
                    if (currentUser.isAdmin) {
                        Surface(shape = CircleShape, color = Color(0x33F59E0B)) {
                            Text("Quyền Admin", fontSize = 9.5.sp, fontWeight = FontWeight.Bold, color = Color(0xFFF59E0B), modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                        }
                    }
                }

                if (currentUser.isAdmin) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Cấp quyền cho thành viên mới (nhập username rồi bấm Thêm):",
                        fontSize = 10.5.sp,
                        color = Color.White.copy(alpha = 0.6f)
                    )
                    Spacer(Modifier.height(6.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = newMemberUsername,
                            onValueChange = {
                                newMemberUsername = it
                                memberErrorMsg = null
                                memberSuccessMsg = null
                            },
                            placeholder = { Text("username mới...", fontSize = 11.sp, color = Color.White.copy(alpha = 0.35f)) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = GoldPrimary,
                                unfocusedBorderColor = Color.White.copy(alpha = 0.15f),
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
                                val clean = newMemberUsername.trim().lowercase()
                                if (clean.isBlank()) {
                                    memberErrorMsg = "Vui lòng nhập username."
                                    return@Button
                                }
                                val ok = storage.addWhitelistedUser(clean)
                                if (ok) {
                                    accounts.value = storage.getAllAccounts()
                                    newMemberUsername = ""
                                    memberSuccessMsg = "Đã thêm thành công: @$clean (Mật khẩu mặc định: 12345678)"
                                } else {
                                    memberErrorMsg = "Tài khoản @$clean đã tồn tại!"
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Thêm", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                    if (memberErrorMsg != null) {
                        Text(memberErrorMsg ?: "", color = Color(0xFFF87171), fontSize = 10.5.sp, modifier = Modifier.padding(top = 4.dp))
                    }
                    if (memberSuccessMsg != null) {
                        Text(memberSuccessMsg ?: "", color = Color(0xFF34D399), fontSize = 10.5.sp, modifier = Modifier.padding(top = 4.dp))
                    }
                }

                Spacer(Modifier.height(10.dp))

                // List of members
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 180.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    accounts.value.forEach { acc ->
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0x14FFFFFF),
                            border = BorderStroke(0.6.dp, Color(0x1AFFFFFF)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        "@${acc.username}",
                                        fontSize = 11.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (acc.isAdmin) Color(0xFFFDE68A) else Color.White
                                    )
                                    Spacer(Modifier.width(6.dp))
                                    Text(
                                        "(${acc.fullName})",
                                        fontSize = 10.sp,
                                        color = Color.White.copy(alpha = 0.5f)
                                    )
                                }

                                if (currentUser.isAdmin && !acc.username.equals("admin", ignoreCase = true)) {
                                    IconButton(
                                        onClick = {
                                            storage.removeUser(acc.username)
                                            accounts.value = storage.getAllAccounts()
                                            Toast.makeText(context, "Đã xóa @${acc.username}", Toast.LENGTH_SHORT).show()
                                        },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(Icons.Default.Delete, contentDescription = "Xóa", tint = Color(0xFFF87171), modifier = Modifier.size(14.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(14.dp))

        // 3. Google Gemini API Key
        Surface(
            shape = RoundedCornerShape(22.dp),
            color = Color(0xFF130E26),
            border = BorderStroke(1.dp, Color(0x33818CF8)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Google Gemini API Key (Tùy chọn)", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFFC4B5FD))
                Text(
                    "Nếu để trống, ứng dụng sẽ dùng bộ máy suy luận Kinh Thánh 1925 ngoại tuyến 100%.",
                    fontSize = 10.sp,
                    color = Color.White.copy(alpha = 0.55f)
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = geminiKey,
                    onValueChange = { geminiKey = it },
                    placeholder = { Text("AIzaSy...", fontSize = 11.sp, color = Color.White.copy(alpha = 0.3f)) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF818CF8),
                        unfocusedBorderColor = Color.White.copy(alpha = 0.15f),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = {
                        val s = currentSettings.copy(geminiApiKey = geminiKey.trim())
                        storage.saveSettings(s)
                        Toast.makeText(context, "Đã lưu Gemini API Key!", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF818CF8)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.align(Alignment.End).height(32.dp)
                ) {
                    Text("Lưu API Key", fontSize = 11.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(Modifier.height(14.dp))

        // 4. Sao lưu & Dữ liệu
        Surface(
            shape = RoundedCornerShape(22.dp),
            color = Color(0xFF130E26),
            border = BorderStroke(1.dp, Color(0x33FFFFFF)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("💾 Sao Lưu & Dữ Liệu", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = {
                        val json = storage.exportAllDataJson()
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        clipboard.setPrimaryClip(ClipData.newPlainText("Cây Sự Sống JSON", json))
                        Toast.makeText(context, "Đã sao chép dữ liệu JSON sao lưu!", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0x33FFFFFF)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().height(38.dp)
                ) {
                    Icon(Icons.Default.Download, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Sao Chép File JSON Sao Lưu", fontSize = 11.5.sp, color = Color.White)
                }
            }
        }

        Spacer(Modifier.height(18.dp))

        // Logout
        Button(
            onClick = {
                storage.setCurrentUsername(null)
                onLogout()
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0x4DDC2626)),
            border = BorderStroke(1.dp, Color(0x80EF4444)),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth().height(44.dp).testTag("logout_button")
        ) {
            Icon(Icons.Default.ExitToApp, contentDescription = null, tint = Color(0xFFFCA5A5), modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text("Đăng Xuất Khỏi Tài Khoản", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFCA5A5))
        }
    }
}
