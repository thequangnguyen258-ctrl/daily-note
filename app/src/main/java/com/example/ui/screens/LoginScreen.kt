package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.DevotionalStorage
import com.example.data.model.UserAccount
import com.example.ui.theme.GoldPrimary

@Composable
fun LoginScreen(
    storage: DevotionalStorage,
    onLoginSuccess: (UserAccount) -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF090614))
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Daily Journal Emblem
            Surface(
                modifier = Modifier
                    .size(76.dp)
                    .clip(RoundedCornerShape(26.dp))
                    .border(1.5.dp, Color(0x66F59E0B), RoundedCornerShape(26.dp)),
                color = Color(0xFF140B28),
                shadowElevation = 16.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("📝", fontSize = 36.sp)
                }
            }

            Spacer(Modifier.height(16.dp))

            Text(
                "NHẬT KÝ HÀNG NGÀY",
                fontSize = 21.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.sp,
                color = Color(0xFFFDE68A)
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "Sổ tay ghi chép cảm xúc & thói quen mỗi ngày",
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.6f)
            )

            Spacer(Modifier.height(28.dp))

            // Login Card
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = Color(0xFF130E26),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x33FFFFFF)),
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 12.dp
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "Đăng Nhập Sổ Tay",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        "Nhập tài khoản để mở khóa nhật ký riêng tư",
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.5f)
                    )

                    Spacer(Modifier.height(16.dp))

                    if (errorMessage != null) {
                        Surface(
                            color = Color(0x33DC2626),
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x66EF4444)),
                            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
                        ) {
                            Text(
                                errorMessage ?: "",
                                color = Color(0xFFFCA5A5),
                                fontSize = 11.sp,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }

                    // Username field
                    Text("Tên đăng nhập:", fontSize = 11.sp, color = Color.White.copy(alpha = 0.7f))
                    Spacer(Modifier.height(4.dp))
                    OutlinedTextField(
                        value = username,
                        onValueChange = {
                            username = it
                            errorMessage = null
                        },
                        placeholder = { Text("admin hoặc nguoidung1..20", fontSize = 11.5.sp, color = Color.White.copy(alpha = 0.3f)) },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = Color.White.copy(alpha = 0.5f)) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldPrimary,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.15f),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth().testTag("login_username_input")
                    )

                    Spacer(Modifier.height(12.dp))

                    // Password field
                    Text("Mật khẩu:", fontSize = 11.sp, color = Color.White.copy(alpha = 0.7f))
                    Spacer(Modifier.height(4.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            errorMessage = null
                        },
                        placeholder = { Text("Nhập mật khẩu...", fontSize = 11.5.sp, color = Color.White.copy(alpha = 0.3f)) },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Color.White.copy(alpha = 0.5f)) },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = null,
                                    tint = Color.White.copy(alpha = 0.5f)
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldPrimary,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.15f),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth().testTag("login_password_input")
                    )

                    Spacer(Modifier.height(20.dp))

                    Button(
                        onClick = {
                            val userClean = username.trim().lowercase()
                            val passClean = password.trim()
                            if (userClean.isEmpty() || passClean.isEmpty()) {
                                errorMessage = "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu."
                                return@Button
                            }

                            val accounts = storage.getAllAccounts()
                            val matched = accounts.firstOrNull { it.username.equals(userClean, ignoreCase = true) }
                            if (matched == null || matched.password != passClean) {
                                errorMessage = "Tên đăng nhập hoặc mật khẩu không chính xác."
                            } else {
                                storage.setCurrentUsername(matched.username)
                                onLoginSuccess(matched)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth().height(46.dp).testTag("login_submit_button")
                    ) {
                        Text(
                            "Đăng Nhập",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(Modifier.width(6.dp))
                        Icon(Icons.Default.ArrowForward, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            Text(
                "🔒 Sổ Tay Cá Nhân Bảo Mật\nDữ liệu ghi chép được lưu trữ cục bộ riêng tư 100% trên thiết bị",
                fontSize = 10.5.sp,
                textAlign = TextAlign.Center,
                color = Color.White.copy(alpha = 0.4f),
                lineHeight = 15.sp
            )
        }
    }
}
