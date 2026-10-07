package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.data.model.*
import kotlin.math.*

private data class FruitNodePos(
    val fruit: Fruit,
    val xRatio: Float,
    val yRatio: Float,
    val radiusDp: Float = 22f
)

private data class LeafNodePos(
    val index: Int,
    val xRatio: Float,
    val yRatio: Float,
    val angleDeg: Float
)

@Composable
fun TreeCanvasVisualizer(
    treeState: TreeState,
    heartFieldState: HeartFieldState,
    isWatering: Boolean = false,
    isHolyFire: Boolean = false,
    onFruitClick: (Fruit) -> Unit = {},
    onLeavesClick: () -> Unit = {},
    onRootsClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "tree_anim")

    val windPhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * Math.PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wind"
    )

    val doveProgress by infiniteTransition.animateFloat(
        initialValue = -0.1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "dove"
    )

    val haloPulse by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "halo"
    )

    val fireFlicker by infiniteTransition.animateFloat(
        initialValue = 0.75f,
        targetValue = 1.35f,
        animationSpec = infiniteRepeatable(
            animation = tween(300, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "fire"
    )

    // Animated water progress (3.5 seconds)
    val angelWaterProgress by animateFloatAsState(
        targetValue = if (isWatering) 1f else 0f,
        animationSpec = tween(durationMillis = 3500, easing = FastOutSlowInEasing),
        label = "angel_water"
    )

    // Animated fire progress (3.5 seconds)
    val angelFireProgress by animateFloatAsState(
        targetValue = if (isHolyFire) 1f else 0f,
        animationSpec = tween(durationMillis = 3500, easing = FastOutSlowInEasing),
        label = "angel_fire"
    )

    // Fruit positions scaled relative to coordinate space
    val fruitPositions = remember {
        listOf(
            FruitNodePos(NINE_FRUITS[0], 0.50f, 0.20f), // Yêu Thương (love)
            FruitNodePos(NINE_FRUITS[1], 0.33f, 0.27f), // Vui Mừng (joy)
            FruitNodePos(NINE_FRUITS[2], 0.67f, 0.27f), // Bình An (peace)
            FruitNodePos(NINE_FRUITS[3], 0.22f, 0.39f), // Nhịn Nhục (patience)
            FruitNodePos(NINE_FRUITS[4], 0.78f, 0.39f), // Nhân Từ (kindness)
            FruitNodePos(NINE_FRUITS[5], 0.36f, 0.46f), // Hiền Lành (goodness)
            FruitNodePos(NINE_FRUITS[6], 0.64f, 0.46f), // Trung Tín (faithfulness)
            FruitNodePos(NINE_FRUITS[7], 0.50f, 0.34f), // Mềm Mại (gentleness)
            FruitNodePos(NINE_FRUITS[8], 0.50f, 0.49f)  // Tiết Độ (selfcontrol)
        )
    }

    // 13 Leaf clusters coordinates
    val leafClusters = remember {
        listOf(
            LeafNodePos(0, 0.28f, 0.16f, -25f),
            LeafNodePos(1, 0.39f, 0.12f, 10f),
            LeafNodePos(2, 0.50f, 0.10f, 0f),
            LeafNodePos(3, 0.61f, 0.12f, -10f),
            LeafNodePos(4, 0.72f, 0.16f, 25f),
            LeafNodePos(5, 0.18f, 0.26f, -45f),
            LeafNodePos(6, 0.24f, 0.32f, -30f),
            LeafNodePos(7, 0.76f, 0.32f, 30f),
            LeafNodePos(8, 0.82f, 0.26f, 45f),
            LeafNodePos(9, 0.13f, 0.38f, -60f),
            LeafNodePos(10, 0.87f, 0.38f, 60f),
            LeafNodePos(11, 0.34f, 0.46f, -20f),
            LeafNodePos(12, 0.66f, 0.46f, 20f)
        )
    }

    val isThorny = heartFieldState.isNegative || heartFieldState.thornsLevel > 0
    val isWilted = treeState.isWilted

    // Dynamic alpha for rocks as water dissolves them
    val rockAlpha = when {
        isHolyFire -> (1f - (angelFireProgress - 0.2f) / 0.5f).coerceIn(0f, 1f)
        isWatering -> (1f - (angelWaterProgress - 0.2f) / 0.5f).coerceIn(0f, 1f)
        isThorny -> 1f
        else -> 0f
    }

    // Dynamic alpha for weeds/thorns
    val weedAlpha = when {
        isHolyFire -> (1f - (angelFireProgress - 0.25f) / 0.5f).coerceIn(0f, 1f)
        isWatering -> (1f - (angelWaterProgress - 0.3f) / 0.5f).coerceIn(0f, 1f)
        isThorny -> 1f
        else -> 0f
    }

    BoxWithConstraints(modifier = modifier.aspectRatio(4f / 4.9f)) {
        val width = constraints.maxWidth.toFloat()
        val height = constraints.maxHeight.toFloat()

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(width, height) {
                    detectTapGestures { offset ->
                        // 1. Check fruits first
                        val tapRadius = 32.dp.toPx()
                        for (fp in fruitPositions) {
                            val fx = fp.xRatio * width
                            val fy = fp.yRatio * height
                            val dist = hypot(offset.x - fx, offset.y - fy)
                            if (dist <= tapRadius) {
                                onFruitClick(fp.fruit)
                                return@detectTapGestures
                            }
                        }

                        // 2. Check roots (bottom area)
                        if (offset.y >= height * 0.78f && offset.x in (width * 0.15f)..(width * 0.85f)) {
                            onRootsClick()
                            return@detectTapGestures
                        }

                        // 3. Check leaves canopy (top half)
                        if (offset.y in (height * 0.04f)..(height * 0.55f)) {
                            onLeavesClick()
                            return@detectTapGestures
                        }
                    }
                }
        ) {
            val windSway = sin(windPhase) * 6f

            // 1. Celestial Heavenly Light Beam
            val beamBrush = Brush.verticalGradient(
                colors = listOf(
                    Color(0x33FEF08A),
                    Color(0x10FEF08A),
                    Color.Transparent
                ),
                startY = 0f,
                endY = height * 0.75f
            )
            val beamPath = Path().apply {
                moveTo(width * 0.35f, 0f)
                lineTo(width * 0.65f, 0f)
                lineTo(width * 0.95f, height * 0.72f)
                lineTo(width * 0.05f, height * 0.72f)
                close()
            }
            drawPath(beamPath, beamBrush)

            // 2. Soil and Underground Area
            val soilY = height * 0.78f
            val soilPath = Path().apply {
                moveTo(0f, soilY)
                quadraticTo(width * 0.5f, soilY - height * 0.04f, width, soilY)
                lineTo(width, height)
                lineTo(0f, height)
                close()
            }
            val soilColors = if (isThorny && !isWatering && !isHolyFire) {
                listOf(Color(0xFF381C0F), Color(0xFF241006), Color(0xFF140A05))
            } else {
                listOf(Color(0xFF3D2112), Color(0xFF2C160C), Color(0xFF140A05))
            }
            drawPath(soilPath, Brush.verticalGradient(soilColors, startY = soilY, endY = height))

            // Subterranean Living Water Seepage Veins when watering
            if (isWatering && angelWaterProgress > 0.15f) {
                val seepAlpha = (angelWaterProgress * 1.3f).coerceIn(0f, 0.85f)
                val waterVeinBrush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF38BDF8).copy(alpha = seepAlpha),
                        Color(0xFF67E8F9).copy(alpha = seepAlpha * 0.7f),
                        Color(0xFFA7F3D0).copy(alpha = seepAlpha * 0.5f)
                    ),
                    startY = soilY,
                    endY = height * 0.95f
                )

                // Multiple water streams soaking deep into the soil around the roots
                val veinXOffsets = listOf(0.28f, 0.38f, 0.48f, 0.56f, 0.68f)
                for (vx in veinXOffsets) {
                    val vStart = Offset(width * vx, soilY)
                    val vEnd = Offset(width * (vx + sin(windPhase + vx * 10f) * 0.04f), height * 0.92f)
                    val vPath = Path().apply {
                        moveTo(vStart.x, vStart.y)
                        quadraticTo((vStart.x + vEnd.x) / 2f + 10f, (vStart.y + vEnd.y) / 2f, vEnd.x, vEnd.y)
                    }
                    drawPath(vPath, waterVeinBrush, style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round))
                }
            }

            // ================= SỎI ĐÁ NẰM TRONG LÒNG ĐẤT CHẶN RỄ (CÓ HIỆU ỨNG TAN BIẾN KHI TƯỚI NƯỚC) =================
            if (rockAlpha > 0.01f) {
                val rockColor = Color(0xFF64748B).copy(alpha = rockAlpha)
                val rockDark = Color(0xFF334155).copy(alpha = rockAlpha)
                val isDissolving = isWatering && angelWaterProgress in 0.15f..0.85f

                // Rock 1 (Blocking left root)
                val rock1 = Path().apply {
                    moveTo(width * 0.28f, soilY + 30f)
                    lineTo(width * 0.36f, soilY + 22f)
                    lineTo(width * 0.42f, soilY + 45f)
                    lineTo(width * 0.35f, soilY + 58f)
                    lineTo(width * 0.26f, soilY + 48f)
                    close()
                }
                drawPath(rock1, rockColor)
                drawLine(rockDark, Offset(width * 0.30f, soilY + 28f), Offset(width * 0.37f, soilY + 48f), strokeWidth = 2f)

                // Rock 2 (Blocking center root)
                val rock2 = Path().apply {
                    moveTo(width * 0.46f, soilY + 45f)
                    lineTo(width * 0.54f, soilY + 40f)
                    lineTo(width * 0.58f, soilY + 65f)
                    lineTo(width * 0.50f, soilY + 74f)
                    lineTo(width * 0.44f, soilY + 60f)
                    close()
                }
                drawPath(rock2, rockColor)
                drawLine(rockDark, Offset(width * 0.47f, soilY + 50f), Offset(width * 0.55f, soilY + 62f), strokeWidth = 2f)

                // Rock 3 (Blocking right root)
                val rock3 = Path().apply {
                    moveTo(width * 0.62f, soilY + 26f)
                    lineTo(width * 0.72f, soilY + 20f)
                    lineTo(width * 0.76f, soilY + 46f)
                    lineTo(width * 0.66f, soilY + 56f)
                    lineTo(width * 0.60f, soilY + 42f)
                    close()
                }
                drawPath(rock3, rockColor)
                drawLine(rockDark, Offset(width * 0.64f, soilY + 28f), Offset(width * 0.71f, soilY + 48f), strokeWidth = 2f)

                // Khi đang tưới nước: Vẽ các vết nứt phát sáng & hạt bụi đá tan rã
                if (isDissolving) {
                    val crackGlow = Color(0xFF67E8F9).copy(alpha = sin(angelWaterProgress * Math.PI.toFloat()).coerceIn(0f, 1f))
                    // Cracks on rock 1
                    drawLine(crackGlow, Offset(width * 0.29f, soilY + 28f), Offset(width * 0.38f, soilY + 50f), strokeWidth = 3f)
                    drawLine(crackGlow, Offset(width * 0.33f, soilY + 38f), Offset(width * 0.41f, soilY + 32f), strokeWidth = 2f)

                    // Cracks on rock 2
                    drawLine(crackGlow, Offset(width * 0.48f, soilY + 42f), Offset(width * 0.54f, soilY + 68f), strokeWidth = 3f)
                    drawLine(crackGlow, Offset(width * 0.45f, soilY + 56f), Offset(width * 0.56f, soilY + 52f), strokeWidth = 2f)

                    // Cracks on rock 3
                    drawLine(crackGlow, Offset(width * 0.64f, soilY + 24f), Offset(width * 0.73f, soilY + 50f), strokeWidth = 3f)

                    // Dissolving bubbly mineral particles floating away
                    val centroids = listOf(
                        Offset(width * 0.34f, soilY + 40f),
                        Offset(width * 0.50f, soilY + 55f),
                        Offset(width * 0.68f, soilY + 38f)
                    )
                    for (c in centroids) {
                        for (p in 0..5) {
                            val pOffset = Offset(
                                c.x + sin(windPhase * 2f + p) * 16f,
                                c.y - (angelWaterProgress * 30f + p * 6f) % 25f
                            )
                            drawCircle(
                                color = Color(0xFF67E8F9).copy(alpha = 0.8f),
                                radius = 2.5f,
                                center = pOffset
                            )
                        }
                    }
                }
            }

            // 3. Roots of Faith (7 Roots burrowing deep into ground)
            val rootBrush = Brush.verticalGradient(
                listOf(Color(0xFF8D532B), Color(0xFFB47446), Color(0xFFD89B6B)),
                startY = soilY,
                endY = height * 0.95f
            )
            val roots = listOf(
                Pair(Offset(width * 0.50f, soilY), Offset(width * 0.45f, height * 0.96f)),
                Pair(Offset(width * 0.47f, soilY), Offset(width * 0.20f, height * 0.94f)),
                Pair(Offset(width * 0.40f, soilY + 20f), Offset(width * 0.32f, height * 0.95f)),
                Pair(Offset(width * 0.35f, soilY + 30f), Offset(width * 0.15f, height * 0.90f)),
                Pair(Offset(width * 0.53f, soilY), Offset(width * 0.80f, height * 0.94f)),
                Pair(Offset(width * 0.60f, soilY + 20f), Offset(width * 0.68f, height * 0.95f)),
                Pair(Offset(width * 0.65f, soilY + 30f), Offset(width * 0.85f, height * 0.90f))
            )
            for ((start, end) in roots) {
                val rPath = Path().apply {
                    moveTo(start.x, start.y)
                    // If blocked by rocks (rockAlpha > 0.4f), bend awkwardly. When water dissolves rocks, straighten out!
                    val bendX = if (rockAlpha > 0.4f) (start.x + end.x) / 2f + 18f * rockAlpha else (start.x + end.x) / 2f + 6f
                    quadraticTo(bendX, (start.y + end.y) / 2f, end.x, end.y)
                }
                drawPath(rPath, rootBrush, style = Stroke(width = 5.5f, cap = StrokeCap.Round))

                // When rocks dissolve and water flows, roots glow with divine life!
                if (isWatering && angelWaterProgress > 0.3f) {
                    val rootGlowAlpha = ((angelWaterProgress - 0.3f) * 1.4f).coerceIn(0f, 0.7f)
                    drawPath(
                        rPath,
                        Color(0xFFFEF08A).copy(alpha = rootGlowAlpha),
                        style = Stroke(width = 2.5f, cap = StrokeCap.Round)
                    )
                }
            }

            // Grass crust
            val grassColor = when {
                isWatering -> {
                    // Smoothly transition from parched brown to lush emerald green
                    val t = angelWaterProgress
                    Color(
                        red = (0xA1 * (1f - t) + 0x16 * t) / 255f,
                        green = (0x62 * (1f - t) + 0xA3 * t) / 255f,
                        blue = (0x07 * (1f - t) + 0x4A * t) / 255f
                    )
                }
                isThorny -> Color(0xFFA16207)
                isWilted -> Color(0xFF785B19)
                else -> Color(0xFF16A34A)
            }
            val grassPath = Path().apply {
                moveTo(0f, soilY)
                quadraticTo(width * 0.5f, soilY - height * 0.045f, width, soilY)
                quadraticTo(width * 0.5f, soilY - height * 0.025f, 0f, soilY)
                close()
            }
            drawPath(grassPath, grassColor)

            // Root label pill
            val pillCenter = Offset(width * 0.5f, height * 0.95f)
            drawRoundRect(
                color = Color(0xE624140B),
                topLeft = Offset(pillCenter.x - 85.dp.toPx() / 2f, pillCenter.y - 12.dp.toPx()),
                size = Size(85.dp.toPx(), 24.dp.toPx()),
                cornerRadius = CornerRadius(12.dp.toPx()),
                style = Fill
            )
            drawRoundRect(
                color = Color(0xFFF59E0B),
                topLeft = Offset(pillCenter.x - 85.dp.toPx() / 2f, pillCenter.y - 12.dp.toPx()),
                size = Size(85.dp.toPx(), 24.dp.toPx()),
                cornerRadius = CornerRadius(12.dp.toPx()),
                style = Stroke(width = 1.2f)
            )

            // 4. Sturdy Tree Trunk
            val trunkPath = Path().apply {
                moveTo(width * 0.40f, soilY)
                cubicTo(width * 0.44f, height * 0.65f, width * 0.45f, height * 0.45f, width * 0.38f, height * 0.28f)
                cubicTo(width * 0.45f, height * 0.35f, width * 0.48f, height * 0.42f, width * 0.50f, height * 0.50f)
                cubicTo(width * 0.52f, height * 0.42f, width * 0.55f, height * 0.35f, width * 0.62f, height * 0.28f)
                cubicTo(width * 0.55f, height * 0.45f, width * 0.56f, height * 0.65f, width * 0.60f, soilY)
                close()
            }
            val trunkBrush = Brush.horizontalGradient(
                listOf(Color(0xFF452715), Color(0xFF784423), Color(0xFF9C5B31), Color(0xFF452715)),
                startX = width * 0.35f,
                endX = width * 0.65f
            )
            drawPath(trunkPath, trunkBrush)

            // ================= BỤI GAI MỌC QUANH GỐC CÂY (VỚI WEED ALPHA) =================
            if (weedAlpha > 0.01f) {
                val thornBrambleColor = Color(0xFF991B1B).copy(alpha = weedAlpha)
                val thornWood = Color(0xFF78350F).copy(alpha = weedAlpha)

                // Vines wrapping around tree trunk base
                val vine1 = Path().apply {
                    moveTo(width * 0.39f, soilY)
                    quadraticTo(width * 0.46f, soilY - 25f, width * 0.54f, soilY - 20f)
                    quadraticTo(width * 0.58f, soilY - 35f, width * 0.62f, soilY - 45f)
                }
                drawPath(vine1, thornWood, style = Stroke(width = 4f, cap = StrokeCap.Round))

                val vine2 = Path().apply {
                    moveTo(width * 0.61f, soilY)
                    quadraticTo(width * 0.53f, soilY - 15f, width * 0.47f, soilY - 25f)
                    quadraticTo(width * 0.42f, soilY - 40f, width * 0.37f, soilY - 50f)
                }
                drawPath(vine2, thornWood, style = Stroke(width = 3.5f, cap = StrokeCap.Round))

                // Sharp red thorn spikes jutting out from trunk
                val thornSpikes = listOf(
                    Pair(Offset(width * 0.43f, soilY - 22f), Offset(width * 0.38f, soilY - 28f)),
                    Pair(Offset(width * 0.50f, soilY - 22f), Offset(width * 0.52f, soilY - 32f)),
                    Pair(Offset(width * 0.57f, soilY - 28f), Offset(width * 0.63f, soilY - 35f)),
                    Pair(Offset(width * 0.46f, soilY - 12f), Offset(width * 0.40f, soilY - 16f)),
                    Pair(Offset(width * 0.54f, soilY - 12f), Offset(width * 0.59f, soilY - 17f)),
                    Pair(Offset(width * 0.40f, soilY - 45f), Offset(width * 0.35f, soilY - 52f)),
                    Pair(Offset(width * 0.60f, soilY - 42f), Offset(width * 0.65f, soilY - 49f))
                )
                for ((base, tip) in thornSpikes) {
                    val spikePath = Path().apply {
                        moveTo(base.x - 3f, base.y)
                        lineTo(tip.x, tip.y)
                        lineTo(base.x + 3f, base.y)
                        close()
                    }
                    drawPath(spikePath, thornBrambleColor)
                }

                // ================= CỎ LÙNG LÀ CỎ MỌC CAO LÊN (THIÊU ĐỐT CỎ LÙNG KHI CÓ LỬA THÁNH) =================
                val isTareBurning = isHolyFire && angelFireProgress in 0.25f..0.92f
                val tareStalkColor = if (isTareBurning) {
                    Color(0xFFF97316).copy(alpha = weedAlpha)
                } else {
                    Color(0xFF78350F).copy(alpha = weedAlpha)
                }
                val tareHeadColor = if (isTareBurning) {
                    Color(0xFFEA580C).copy(alpha = weedAlpha)
                } else {
                    Color(0xFF451A03).copy(alpha = weedAlpha)
                }

                val tarePositions = listOf(
                    Triple(0.24f, 85f, -12f),  // left high tare
                    Triple(0.30f, 105f, -6f),
                    Triple(0.35f, 90f, 4f),
                    Triple(0.65f, 95f, -4f),
                    Triple(0.70f, 110f, 8f),   // right high tare
                    Triple(0.76f, 80f, 14f)
                )

                for ((xRatio, stalkHeight, lean) in tarePositions) {
                    val tx = width * xRatio
                    val topY = soilY - stalkHeight

                    val stalkPath = Path().apply {
                        moveTo(tx, soilY)
                        quadraticTo(tx + lean * 0.5f, soilY - stalkHeight * 0.6f, tx + lean, topY)
                    }
                    drawPath(stalkPath, tareStalkColor, style = Stroke(width = if (isTareBurning) 3.5f else 2.5f, cap = StrokeCap.Round))

                    drawLine(tareStalkColor, Offset(tx + lean * 0.3f, soilY - stalkHeight * 0.3f), Offset(tx + lean * 0.3f - 14f, soilY - stalkHeight * 0.45f), strokeWidth = 2f)
                    drawLine(tareStalkColor, Offset(tx + lean * 0.6f, soilY - stalkHeight * 0.6f), Offset(tx + lean * 0.6f + 15f, soilY - stalkHeight * 0.75f), strokeWidth = 2f)

                    drawOval(
                        color = tareHeadColor,
                        topLeft = Offset(tx + lean - 5f, topY - 14f),
                        size = Size(10f, 20f)
                    )
                    drawLine(Color(0xFF291205).copy(alpha = weedAlpha), Offset(tx + lean, topY - 14f), Offset(tx + lean + lean * 0.3f, topY - 24f), strokeWidth = 1.5f)

                    // Hiệu ứng thiêu đốt cỏ lùng rực lửa
                    if (isTareBurning) {
                        // Ngọn lửa liếm dọc thân cỏ lùng
                        val flameTongue = Path().apply {
                            moveTo(tx - 6f, soilY - 20f)
                            quadraticTo(tx + lean * 0.5f, soilY - stalkHeight * 0.7f, tx + lean, topY - 18f * fireFlicker)
                            quadraticTo(tx + lean * 0.8f, soilY - stalkHeight * 0.5f, tx + 6f, soilY - 20f)
                            close()
                        }
                        drawPath(
                            flameTongue,
                            Brush.verticalGradient(
                                listOf(Color(0xFFFEF08A), Color(0xFFF97316), Color(0xFFEF4444)),
                                startY = topY - 18f * fireFlicker,
                                endY = soilY
                            )
                        )

                        // Đầu cỏ lùng bốc cháy thành đuốc lửa
                        drawCircle(
                            color = Color(0xFFFEF08A).copy(alpha = 0.9f),
                            radius = 6.dp.toPx() * fireFlicker,
                            center = Offset(tx + lean, topY - 5f)
                        )

                        // Tàn tro và tia lửa bay lên từ cỏ lùng bị thiêu rụi
                        for (sp in 0..2) {
                            val sparkY = topY - (sp * 14f + windPhase * 25f) % 45f
                            val sparkX = tx + lean + sin(windPhase * 3f + sp) * 10f
                            drawCircle(
                                color = Color(0xFFFDE047),
                                radius = 2.dp.toPx(),
                                center = Offset(sparkX, sparkY)
                            )
                        }
                    }
                }
            }

            // Branches spreading out
            val branches = listOf(
                Pair(Offset(width * 0.45f, height * 0.55f), Offset(width * 0.18f, height * 0.40f)),
                Pair(Offset(width * 0.55f, height * 0.55f), Offset(width * 0.82f, height * 0.40f)),
                Pair(Offset(width * 0.46f, height * 0.42f), Offset(width * 0.28f, height * 0.25f)),
                Pair(Offset(width * 0.54f, height * 0.42f), Offset(width * 0.72f, height * 0.25f))
            )
            for ((start, end) in branches) {
                val bPath = Path().apply {
                    moveTo(start.x, start.y)
                    quadraticTo((start.x + end.x) / 2f, start.y - 15f, end.x, end.y)
                }
                drawPath(bPath, trunkBrush, style = Stroke(width = 8f, cap = StrokeCap.Round))
            }

            // 5. Lush Canopy Clusters
            val foliageColors1 = if (isThorny && !isWatering) Color(0xFFA16207) else if (isWilted && !isWatering) Color(0xFF856417) else Color(0xFF059669)
            val foliageColors2 = if (isThorny && !isWatering) Color(0xFFCA8A04) else if (isWilted && !isWatering) Color(0xFFA17E23) else Color(0xFF10B981)

            val canopies = listOf(
                Triple(0.50f, 0.18f, width * 0.18f),
                Triple(0.33f, 0.25f, width * 0.17f),
                Triple(0.67f, 0.25f, width * 0.17f),
                Triple(0.20f, 0.38f, width * 0.15f),
                Triple(0.80f, 0.38f, width * 0.15f),
                Triple(0.35f, 0.42f, width * 0.14f),
                Triple(0.65f, 0.42f, width * 0.14f),
                Triple(0.50f, 0.35f, width * 0.20f),
                Triple(0.50f, 0.50f, width * 0.12f)
            )

            for ((cx, cy, r) in canopies) {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(foliageColors2, foliageColors1),
                        center = Offset(cx * width + windSway * 0.5f, cy * height),
                        radius = r
                    ),
                    radius = r,
                    center = Offset(cx * width + windSway * 0.5f, cy * height)
                )
            }

            // 6. 13 Leaf Nodes
            for (leaf in leafClusters) {
                val lx = leaf.xRatio * width + windSway
                val ly = leaf.yRatio * height
                val leafCol = if (isThorny && !isWatering) Color(0xFFD97706) else Color(0xFF86EFAC)

                drawOval(
                    color = leafCol,
                    topLeft = Offset(lx - 6f, ly - 12f),
                    size = Size(12f, 24f)
                )
                // small dewdrop highlight
                drawCircle(
                    color = Color.White.copy(alpha = 0.8f),
                    radius = 2.5f,
                    center = Offset(lx - 1f, ly - 4f)
                )
            }

            // Top leaf category banner
            val leafBannerCenter = Offset(width * 0.5f, height * 0.05f)
            drawRoundRect(
                color = Color(0xE6064E3B),
                topLeft = Offset(leafBannerCenter.x - 90.dp.toPx() / 2f, leafBannerCenter.y - 11.dp.toPx()),
                size = Size(90.dp.toPx(), 22.dp.toPx()),
                cornerRadius = CornerRadius(11.dp.toPx()),
                style = Fill
            )
            drawRoundRect(
                color = Color(0xFF34D399),
                topLeft = Offset(leafBannerCenter.x - 90.dp.toPx() / 2f, leafBannerCenter.y - 11.dp.toPx()),
                size = Size(90.dp.toPx(), 22.dp.toPx()),
                cornerRadius = CornerRadius(11.dp.toPx()),
                style = Stroke(width = 1.2f)
            )

            // 7. 9 Fruits of the Holy Spirit (Gleaming Gem Nodes)
            for (fn in fruitPositions) {
                val fx = fn.xRatio * width
                val fy = fn.yRatio * height + sin(windPhase + fn.xRatio * 10f) * 3f

                // Pulsating glow aura
                drawCircle(
                    color = fn.fruit.color.copy(alpha = 0.35f * haloPulse),
                    radius = fn.radiusDp.dp.toPx() * haloPulse,
                    center = Offset(fx, fy)
                )

                // Outer border
                drawCircle(
                    color = Color.White,
                    radius = fn.radiusDp.dp.toPx() * 0.75f + 2f,
                    center = Offset(fx, fy)
                )

                // Main fruit orb
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(Color.White, fn.fruit.color),
                        center = Offset(fx - 4f, fy - 4f),
                        radius = fn.radiusDp.dp.toPx() * 0.8f
                    ),
                    radius = fn.radiusDp.dp.toPx() * 0.75f,
                    center = Offset(fx, fy)
                )
            }

            // 8. White Dove Gliding Across Upper Sky
            val doveX = doveProgress * width
            val doveY = height * 0.12f + sin(doveProgress * 15f) * 8f
            drawCircle(
                color = Color.White.copy(alpha = 0.95f),
                radius = 7f,
                center = Offset(doveX, doveY)
            )
            drawOval(
                color = Color.White,
                topLeft = Offset(doveX - 12f, doveY - 5f),
                size = Size(20f, 10f)
            )
            val wingFlap = sin(windPhase * 3f) * 8f
            val wingPath = Path().apply {
                moveTo(doveX - 2f, doveY)
                quadraticTo(doveX + 4f, doveY - 14f + wingFlap, doveX + 10f, doveY - 8f + wingFlap)
                quadraticTo(doveX + 5f, doveY, doveX - 2f, doveY)
            }
            drawPath(wingPath, Color.White)

            // ================= 9. HOẠT HỌA THIÊN SỨ ĐẾN LÀM VIỆC =================

            // A. THIÊN SỨ CẦM BÌNH ĐỔ NƯỚC HẰNG SỐNG TƯỚI ĐẤT & LÀM BIẾN MẤT CÁC VIÊN SỎI ĐÁ
            if (isWatering || angelWaterProgress > 0.05f) {
                val angelY = height * 0.16f
                val angelX = width * 0.5f

                // Angel Wings
                val aWingFlap = sin(windPhase * 4f) * 12f
                val leftWing = Path().apply {
                    moveTo(angelX - 8f, angelY)
                    quadraticTo(angelX - 42f, angelY - 26f + aWingFlap, angelX - 52f, angelY - 6f + aWingFlap)
                    quadraticTo(angelX - 38f, angelY + 16f, angelX - 8f, angelY + 8f)
                    close()
                }
                drawPath(leftWing, Color.White.copy(alpha = 0.95f))

                val rightWing = Path().apply {
                    moveTo(angelX + 8f, angelY)
                    quadraticTo(angelX + 42f, angelY - 26f + aWingFlap, angelX + 52f, angelY - 6f + aWingFlap)
                    quadraticTo(angelX + 38f, angelY + 16f, angelX + 8f, angelY + 8f)
                    close()
                }
                drawPath(rightWing, Color.White.copy(alpha = 0.95f))

                // Angel Body & White Robe
                val robe = Path().apply {
                    moveTo(angelX - 12f, angelY - 2f)
                    quadraticTo(angelX, angelY - 8f, angelX + 12f, angelY - 2f)
                    lineTo(angelX + 16f, angelY + 34f)
                    quadraticTo(angelX, angelY + 28f, angelX - 16f, angelY + 34f)
                    close()
                }
                drawPath(robe, Color.White)
                // Golden sash
                drawRect(Color(0xFFF59E0B), Offset(angelX - 10f, angelY + 12f), Size(20f, 3.5f))

                // Angel Head & Golden Halo
                drawCircle(Color(0xFFFEF08A), 10f, Offset(angelX, angelY - 14f))
                drawOval(
                    color = Color(0xFFFDE047),
                    topLeft = Offset(angelX - 14f, angelY - 28f),
                    size = Size(28f, 7f),
                    style = Stroke(width = 2.5f)
                )

                // Alabaster/Golden Water Vessel (Bình đổ nước)
                val jarX = angelX + 18f
                val jarY = angelY + 16f
                drawOval(
                    color = Color(0xFFF59E0B),
                    topLeft = Offset(jarX - 8f, jarY - 12f),
                    size = Size(16f, 22f)
                )
                drawRect(Color(0xFFFDE68A), Offset(jarX + 2f, jarY - 4f), Size(8f, 8f))

                // Descending stream of Living Water pouring from jar to soil
                val waterBrush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF67E8F9),
                        Color(0xFF38BDF8),
                        Color(0xFFA7F3D0)
                    ),
                    startY = jarY,
                    endY = soilY + 20f
                )
                val streamPath = Path().apply {
                    moveTo(jarX + 8f, jarY)
                    quadraticTo(angelX + 25f, height * 0.45f, width * 0.5f, soilY)
                }
                drawPath(streamPath, waterBrush, style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round))

                // Pool of Living Water washing across ground surface
                val surfacePoolBrush = Brush.radialGradient(
                    colors = listOf(Color(0xFF67E8F9).copy(alpha = 0.7f), Color.Transparent),
                    center = Offset(width * 0.5f, soilY),
                    radius = width * 0.45f * angelWaterProgress
                )
                drawCircle(surfacePoolBrush, width * 0.45f * angelWaterProgress, Offset(width * 0.5f, soilY))

                // Water shower sprays & ripples across the soil
                for (i in 0..16) {
                    val rx = width * (0.20f + (i * 0.04f))
                    val ry = (height * 0.22f + (i * 26f + windPhase * 50f) % (height * 0.58f))
                    drawCircle(
                        color = Color(0xFF67E8F9).copy(alpha = 0.85f),
                        radius = 4.dp.toPx(),
                        center = Offset(rx, ry)
                    )
                }
            }

            // B. THIÊN SỨ THẢ HÒN LỬA RỒI BÙNG CHÁY THIÊU RỤI CỎ LÙNG & BỤI GAI
            if (isHolyFire || angelFireProgress > 0.05f) {
                val angelY = height * 0.16f
                val angelX = width * 0.5f

                // Angel in heaven releasing fire
                val aWingFlap = sin(windPhase * 5f) * 14f
                val leftWing = Path().apply {
                    moveTo(angelX - 8f, angelY)
                    quadraticTo(angelX - 42f, angelY - 26f + aWingFlap, angelX - 52f, angelY - 6f + aWingFlap)
                    quadraticTo(angelX - 38f, angelY + 16f, angelX - 8f, angelY + 8f)
                    close()
                }
                drawPath(leftWing, Color.White.copy(alpha = 0.95f))

                val rightWing = Path().apply {
                    moveTo(angelX + 8f, angelY)
                    quadraticTo(angelX + 42f, angelY - 26f + aWingFlap, angelX + 52f, angelY - 6f + aWingFlap)
                    quadraticTo(angelX + 38f, angelY + 16f, angelX + 8f, angelY + 8f)
                    close()
                }
                drawPath(rightWing, Color.White.copy(alpha = 0.95f))

                // Robe
                val robe = Path().apply {
                    moveTo(angelX - 12f, angelY - 2f)
                    quadraticTo(angelX, angelY - 8f, angelX + 12f, angelY - 2f)
                    lineTo(angelX + 16f, angelY + 34f)
                    quadraticTo(angelX, angelY + 28f, angelX - 16f, angelY + 34f)
                    close()
                }
                drawPath(robe, Color.White)

                // Angel Head & Fiery Aura
                drawCircle(Color(0xFFFEF08A), 10f, Offset(angelX, angelY - 14f))
                drawOval(
                    color = Color(0xFFF97316),
                    topLeft = Offset(angelX - 15f, angelY - 28f),
                    size = Size(30f, 8f),
                    style = Stroke(width = 3f)
                )

                // 1 HÒN LỬA RƠI TỪ TAY THIÊN SỨ XUỐNG ĐẤT
                val emberY = (angelY + 20f) + (soilY - (angelY + 20f)) * (angelFireProgress * 1.5f).coerceAtMost(1f)
                val emberX = angelX + sin(windPhase * 2f) * 6f

                // Fiery stone with glowing aura
                drawCircle(
                    color = Color(0xFFFF7043).copy(alpha = 0.5f),
                    radius = 16.dp.toPx() * fireFlicker,
                    center = Offset(emberX, emberY)
                )
                drawCircle(
                    color = Color(0xFFEF4444),
                    radius = 9.dp.toPx(),
                    center = Offset(emberX, emberY)
                )
                drawCircle(
                    color = Color(0xFFFEF08A),
                    radius = 5.dp.toPx(),
                    center = Offset(emberX, emberY)
                )

                // Fire trail trailing behind the falling ember
                drawLine(
                    brush = Brush.verticalGradient(
                        listOf(Color(0xFFFEF08A), Color(0xFFF97316), Color.Transparent),
                        startY = (emberY - 40f).coerceAtLeast(angelY),
                        endY = emberY
                    ),
                    start = Offset(emberX, (emberY - 35f).coerceAtLeast(angelY)),
                    end = Offset(emberX, emberY),
                    strokeWidth = 5f,
                    cap = StrokeCap.Round
                )

                // BÙNG CHÁY THIÊU RỤI CỎ LÙNG & BỤI GAI (Biển Lửa Thánh dữ dội)
                val fireBrush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFEF08A),
                        Color(0xFFF97316),
                        Color(0xFFEF4444),
                        Color.Transparent
                    ),
                    startY = soilY - 110f * fireFlicker,
                    endY = soilY + 20f
                )

                // Raging tongues of flame consuming the ground
                for (fi in 0..10) {
                    val fx = width * (0.12f + fi * 0.08f)
                    val fh = (60f + (fi % 4) * 22f) * fireFlicker
                    val flameP = Path().apply {
                        moveTo(fx - 18f, soilY + 15f)
                        quadraticTo(fx, soilY - fh, fx + 18f, soilY + 15f)
                        close()
                    }
                    drawPath(flameP, fireBrush)
                }

                // Sparks bursting in air
                for (s in 0..12) {
                    val sx = width * (0.15f + (s * 0.06f))
                    val sy = soilY - (30f + (s * 15f + windPhase * 30f) % 90f)
                    drawCircle(
                        color = Color(0xFFFDE047),
                        radius = 2.5f,
                        center = Offset(sx, sy)
                    )
                }
            }
        }
    }
}
