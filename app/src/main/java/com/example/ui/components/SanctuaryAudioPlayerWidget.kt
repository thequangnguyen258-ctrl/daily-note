package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.SanctuaryAudioEngine
import com.example.ui.theme.GoldPrimary

data class AudioTrackInfo(
    val id: String,
    val title: String,
    val subtitle: String,
    val iconName: String,
    val badgeColor: Color
)

val SANCTUARY_TRACKS = listOf(
    AudioTrackInfo("miracle_528", "528Hz Chữa Lành", "Tần số phép màu & chuông pha lê", "✨", Color(0xFFFACC15)),
    AudioTrackInfo("living_water", "Suối Nước Hằng Sống", "Dòng suối bình tịnh & giọt nước thánh", "💧", Color(0xFF38BDF8)),
    AudioTrackInfo("wind_chimes", "Chuông Gió Thiên Đường", "Ngũ âm thiên quốc ngân nga dịu êm", "🎐", Color(0xFF34D399)),
    AudioTrackInfo("deep_night", "Đêm Tĩnh Lặng 432Hz", "Tần số vũ trụ & sự bình an sâu sắc", "🌙", Color(0xFF818CF8))
)

@Composable
fun SanctuaryAudioPlayerWidget(
    audioEngine: SanctuaryAudioEngine,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var isPlaying by remember { mutableStateOf(audioEngine.isPlayingAudio()) }
    var currentTrack by remember { mutableStateOf(audioEngine.getCurrentTrack()) }
    var volume by remember { mutableStateOf(audioEngine.getVolume()) }

    val trackInfo = SANCTUARY_TRACKS.firstOrNull { it.id == currentTrack } ?: SANCTUARY_TRACKS.first()

    val infiniteTransition = rememberInfiniteTransition(label = "eq_bars")
    val barHeight1 by infiniteTransition.animateFloat(
        initialValue = 0.3f, targetValue = 0.95f,
        animationSpec = infiniteRepeatable(tween(400, easing = LinearEasing), RepeatMode.Reverse),
        label = "b1"
    )
    val barHeight2 by infiniteTransition.animateFloat(
        initialValue = 0.8f, targetValue = 0.2f,
        animationSpec = infiniteRepeatable(tween(550, easing = LinearEasing), RepeatMode.Reverse),
        label = "b2"
    )
    val barHeight3 by infiniteTransition.animateFloat(
        initialValue = 0.4f, targetValue = 0.85f,
        animationSpec = infiniteRepeatable(tween(350, easing = LinearEasing), RepeatMode.Reverse),
        label = "b3"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.End
    ) {
        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            Surface(
                modifier = Modifier
                    .width(320.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .border(1.dp, Color(0x40F59E0B), RoundedCornerShape(24.dp)),
                color = Color(0xF2090614),
                shadowElevation = 12.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("🎶", fontSize = 16.sp)
                            Spacer(Modifier.width(8.dp))
                            Column {
                                Text(
                                    "Không Gian Âm Thanh Tĩnh Nguyện",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFFDE68A)
                                )
                                Text(
                                    "Tần số Solfeggio 528Hz & chuông thiên đình",
                                    fontSize = 10.sp,
                                    color = Color.White.copy(alpha = 0.6f)
                                )
                            }
                        }
                        IconButton(
                            onClick = { expanded = false },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Đóng", tint = Color.White.copy(alpha = 0.7f), modifier = Modifier.size(16.dp))
                        }
                    }

                    Spacer(Modifier.height(12.dp))

                    // Track options
                    SANCTUARY_TRACKS.forEach { t ->
                        val isSelected = t.id == currentTrack
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSelected) Color(0x33F59E0B) else Color(0x14FFFFFF))
                                .border(
                                    1.dp,
                                    if (isSelected) Color(0x66F59E0B) else Color.Transparent,
                                    RoundedCornerShape(12.dp)
                                )
                                .clickable {
                                    currentTrack = t.id
                                    audioEngine.playAmbient(t.id)
                                    isPlaying = true
                                }
                                .padding(horizontal = 10.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(t.iconName, fontSize = 16.sp)
                                Spacer(Modifier.width(8.dp))
                                Column {
                                    Text(
                                        t.title,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Color(0xFFFDE68A) else Color.White
                                    )
                                    Text(
                                        t.subtitle,
                                        fontSize = 9.5.sp,
                                        color = Color.White.copy(alpha = 0.5f)
                                    )
                                }
                            }
                            if (isSelected && isPlaying) {
                                Row(
                                    verticalAlignment = Alignment.Bottom,
                                    modifier = Modifier.height(14.dp)
                                ) {
                                    Box(Modifier.width(2.5.dp).fillMaxHeight(barHeight1).background(GoldPrimary, CircleShape))
                                    Spacer(Modifier.width(2.dp))
                                    Box(Modifier.width(2.5.dp).fillMaxHeight(barHeight2).background(Color(0xFFFDE68A), CircleShape))
                                    Spacer(Modifier.width(2.dp))
                                    Box(Modifier.width(2.5.dp).fillMaxHeight(barHeight3).background(GoldPrimary, CircleShape))
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(12.dp))

                    // Volume & Play controls
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                if (volume > 0f) Icons.Default.VolumeUp else Icons.Default.VolumeOff,
                                contentDescription = "Âm lượng",
                                tint = Color.White.copy(alpha = 0.7f),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(
                                "${(volume * 100).toInt()}%",
                                fontSize = 10.sp,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                        }

                        Button(
                            onClick = {
                                isPlaying = audioEngine.toggleAmbient(currentTrack)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                            shape = CircleShape,
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 4.dp),
                            modifier = Modifier.height(32.dp).testTag("play_ambient_button")
                        ) {
                            Icon(
                                if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = if (isPlaying) "Tạm dừng" else "Phát",
                                tint = Color.Black,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(
                                if (isPlaying) "Tạm Dừng" else "Phát Tĩnh Tâm",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                    }

                    Slider(
                        value = volume,
                        onValueChange = {
                            volume = it
                            audioEngine.setVolume(it)
                        },
                        colors = SliderDefaults.colors(
                            thumbColor = GoldPrimary,
                            activeTrackColor = GoldPrimary,
                            inactiveTrackColor = Color.White.copy(alpha = 0.2f)
                        ),
                        modifier = Modifier.fillMaxWidth().height(24.dp)
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        // Collapsed Pill Button
        Surface(
            modifier = Modifier
                .clip(CircleShape)
                .border(1.dp, if (isPlaying) Color(0x66F59E0B) else Color(0x33FFFFFF), CircleShape)
                .clickable { expanded = !expanded }
                .testTag("audio_pill_widget"),
            color = if (isPlaying) Color(0xEE1A1333) else Color(0xDD0F0A1E),
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(trackInfo.iconName, fontSize = 14.sp)
                Spacer(Modifier.width(6.dp))
                Column {
                    Text(
                        trackInfo.title,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isPlaying) Color(0xFFFDE68A) else Color.White
                    )
                    Text(
                        if (isPlaying) "Đang phát tĩnh tâm" else "Chạm để mở",
                        fontSize = 9.sp,
                        color = Color.White.copy(alpha = 0.5f)
                    )
                }

                if (isPlaying) {
                    Spacer(Modifier.width(8.dp))
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        modifier = Modifier.height(12.dp)
                    ) {
                        Box(Modifier.width(2.dp).fillMaxHeight(barHeight1).background(GoldPrimary, CircleShape))
                        Spacer(Modifier.width(1.5.dp))
                        Box(Modifier.width(2.dp).fillMaxHeight(barHeight2).background(Color(0xFFFDE68A), CircleShape))
                        Spacer(Modifier.width(1.5.dp))
                        Box(Modifier.width(2.dp).fillMaxHeight(barHeight3).background(GoldPrimary, CircleShape))
                    }
                }

                Spacer(Modifier.width(8.dp))

                IconButton(
                    onClick = {
                        isPlaying = audioEngine.toggleAmbient(currentTrack)
                    },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (isPlaying) "Tạm dừng" else "Phát",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
