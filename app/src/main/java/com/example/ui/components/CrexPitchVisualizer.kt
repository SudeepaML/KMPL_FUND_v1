package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SportsCricket
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.Language
import com.example.ui.theme.*
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Ball delivery outcome types in the CREX Live Visualizer
 */
enum class CrexBallOutcome(
    val ballSymbol: String,
    val titleEn: String,
    val titleKn: String,
    val subtitleEn: String,
    val subtitleKn: String,
    val speed: String,
    val lengthType: String,
    val badgeColor: Color,
    val accentColor: Color,
    val distanceMeters: String? = null
) {
    SIX(
        ballSymbol = "6",
        titleEn = "MAXIMUM SIX! 108m",
        titleKn = "ಗಗನಚುಂಬಿ ಸಿಕ್ಸರ್! 108 ಮೀ",
        subtitleEn = "Smoked Over Deep Mid-Wicket",
        subtitleKn = "ಡೀಪ್ ಮಿಡ್-ವಿಕೆಟ್ ಮೇಲೆ ಚೆಂಡು ಬೌಂಡರಿ ದಾಟಿತು",
        speed = "138.6 km/h",
        lengthType = "SLOT BALL",
        badgeColor = Color(0xFFA855F7), // Purple like CREX Six
        accentColor = Color(0xFFFFD700), // Gold
        distanceMeters = "108m"
    ),
    FOUR(
        ballSymbol = "4",
        titleEn = "CRACKING FOUR!",
        titleKn = "ಮಿಂಚಿನ ಬೌಂಡರಿ! 4 ರನ್",
        subtitleEn = "Textbook Cover Drive to the Fence",
        subtitleKn = "ಅದ್ಭುತ ಕವರ್ ಡ್ರೈವ್ ಬೌಂಡರಿ",
        speed = "142.4 km/h",
        lengthType = "OVER-PITCHED",
        badgeColor = PitchGreen,
        accentColor = Color(0xFF10B981)
    ),
    WICKET(
        ballSymbol = "W",
        titleEn = "TIMBER! CLEAN BOWLED!",
        titleKn = "ಕ್ಲೀನ್ ಬೌಲ್ಡ್! ವಿಕೆಟ್!",
        subtitleEn = "146 km/h In-Dipper Shatters Stumps",
        subtitleKn = "ಮಿಂಚಿನ ವೇಗದ ಚೆಂಡು ಸ್ಟಂಪ್ಸ್ ಧ್ವಂಸಗೊಳಿಸಿತು",
        speed = "146.2 km/h",
        lengthType = "PERFECT YORKER",
        badgeColor = Color(0xFFEF4444), // Crimson Red
        accentColor = Color(0xFFDC2626)
    ),
    DOT(
        ballSymbol = "•",
        titleEn = "DOT BALL • PLAY & MISS",
        titleKn = "ಡಾಟ್ ಬಾಲ್ • ಬೀಟ್ ಆಯಿತು",
        subtitleEn = "Beaten by Outswing Outside Off",
        subtitleKn = "ಚೆಂಡಿನ ವೇಗಕ್ಕೆ ಬ್ಯಾಟರ್ ಮಂಕಾದರು",
        speed = "144.1 km/h",
        lengthType = "GOOD LENGTH",
        badgeColor = Color(0xFF64748B),
        accentColor = Color(0xFF94A3B8)
    ),
    SINGLE(
        ballSymbol = "1",
        titleEn = "1 RUN • QUICK SINGLE",
        titleKn = "1 ರನ್ • ಚುರುಕಾದ ಓಟ",
        subtitleEn = "Soft Hands Pushed to Mid-On",
        subtitleKn = "ಮಿಡ್-ಆನ್ ಕಡೆಗೆ ತಳ್ಳಿ ಒಂದು ರನ್",
        speed = "133.5 km/h",
        lengthType = "BACK OF LENGTH",
        badgeColor = Color(0xFF0284C7),
        accentColor = Color(0xFF38BDF8)
    ),
    BOUNDARY_SIX(
        ballSymbol = "6",
        titleEn = "MASSIVE SIX! 112m",
        titleKn = "ಭವ್ಯ ಸಿಕ್ಸರ್! 112 ಮೀ",
        subtitleEn = "High into the Night Sky over Long-On",
        subtitleKn = "ಲಾಂಗ್-ಆನ್ ಮೇಲಿಂದ ಭಾರಿ ಹೊಡೆತ",
        speed = "135.2 km/h",
        lengthType = "HALF VOLLEY",
        badgeColor = Color(0xFFA855F7),
        accentColor = Color(0xFFFFD700),
        distanceMeters = "112m"
    )
}

/**
 * Authentic CREX-style 3D Live Cricket Match & Pitch Visualizer
 * Replicates the Cricket Exchange (CREX) perspective pitch, bowler run-up,
 * pitching zone radar, batsman shot physics, Hawkeye trajectory lines,
 * ball-by-ball timeline, and live match situation.
 */
@Composable
fun CrexPitchVisualizer(
    language: Language,
    modifier: Modifier = Modifier
) {
    // Current over deliveries
    val overDeliveries = remember {
        listOf(
            CrexBallOutcome.DOT,
            CrexBallOutcome.FOUR,
            CrexBallOutcome.SINGLE,
            CrexBallOutcome.SIX,
            CrexBallOutcome.WICKET,
            CrexBallOutcome.BOUNDARY_SIX
        )
    }

    var selectedBallIndex by remember { mutableIntStateOf(3) } // default to the iconic Six
    var isAutoPlay by remember { mutableStateOf(true) }

    // Auto-advance the over in live match simulation mode
    LaunchedEffect(isAutoPlay) {
        while (isAutoPlay) {
            delay(3400L) // Each ball plays for 3.4 seconds
            selectedBallIndex = (selectedBallIndex + 1) % overDeliveries.size
        }
    }

    val activeOutcome = overDeliveries[selectedBallIndex]

    // 3.0s cycle per delivery:
    // 0.00 -> 0.35: Bowler delivery & ball traveling towards pitch bounce spot
    // 0.35 -> 0.44: Ball bounces with pitch radar rings & batsman swings bat
    // 0.44 -> 0.50: Sweet spot contact / Stumps shatter! Sparks & shockwaves
    // 0.50 -> 0.88: Hawkeye 3D tracer path & outcome banner
    // 0.88 -> 1.00: Settle & reset
    val infiniteTransition = rememberInfiniteTransition(label = "CrexDeliveryTransition")
    val cycleProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "CrexCycle"
    )

    Surface(
        shape = RoundedCornerShape(20.dp),
        color = CardDarkTier1,
        border = BorderStroke(1.5.dp, activeOutcome.badgeColor.copy(alpha = 0.7f)),
        modifier = modifier
            .fillMaxWidth()
            .shadow(12.dp, RoundedCornerShape(20.dp), spotColor = activeOutcome.badgeColor.copy(alpha = 0.4f))
            .testTag("crex_pitch_visualizer")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // CREX Top Match Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFFDC2626))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "LIVE",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 10.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "KMPL 2025 • 20th OVER",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }

                // Auto Live Simulation Chip
                Surface(
                    onClick = { isAutoPlay = !isAutoPlay },
                    shape = RoundedCornerShape(12.dp),
                    color = if (isAutoPlay) PitchGreen.copy(alpha = 0.2f) else CardDarkTier2,
                    border = BorderStroke(1.dp, if (isAutoPlay) PitchGreen else BorderMedium)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            tint = if (isAutoPlay) PitchGreen else TextSecondary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isAutoPlay) "AUTO LIVE" else "MANUAL",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isAutoPlay) PitchGreen else TextSecondary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Score Banner (CREX Style Live Scoreboard)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF06180C))
                    .border(1.dp, BorderSubtle, RoundedCornerShape(10.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "VILLAGE WARRIORS  158/4",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Need 8 runs in 3 balls to win",
                        style = MaterialTheme.typography.labelSmall,
                        color = RulesGold,
                        fontSize = 10.sp
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "CRR: 8.12 • REQ: 16.0",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondary,
                        fontSize = 10.sp
                    )
                    Text(
                        text = "Batsman: Sudeep A M L (44*)",
                        style = MaterialTheme.typography.labelSmall,
                        color = PitchGreen,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // CREX 3D STADIUM PITCH ARENA
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(290.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF030D06)),
                contentAlignment = Alignment.Center
            ) {
                // Official KMPL Tournament watermark emblem in corner
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White.copy(alpha = 0.9f))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.kmpl_logo),
                        contentDescription = "KMPL Watermark",
                        modifier = Modifier.fillMaxSize().padding(2.dp),
                        contentScale = ContentScale.Fit
                    )
                }

                // 3D Canvas Visualizer
                Canvas(
                    modifier = Modifier.fillMaxSize()
                ) {
                    drawCrexStadiumArena(
                        progress = cycleProgress,
                        outcome = activeOutcome
                    )
                }

                // CREX Speed & Radar Pill (Floating top-left during delivery)
                if (cycleProgress in 0.15f..0.85f) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(10.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.Black.copy(alpha = 0.85f))
                            .border(1.dp, activeOutcome.accentColor.copy(alpha = 0.7f), RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(activeOutcome.accentColor)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "${activeOutcome.speed} • ${activeOutcome.lengthType}",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp
                            )
                        }
                    }
                }

                // CREX Outcome Callout Banner (Appears at impact)
                if (cycleProgress >= 0.44f) {
                    val bannerAlpha = if (cycleProgress in 0.44f..0.88f) 1f else ((1f - cycleProgress) / 0.12f).coerceIn(0f, 1f)
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 12.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(activeOutcome.badgeColor.copy(alpha = bannerAlpha * 0.95f))
                            .border(1.5.dp, activeOutcome.accentColor.copy(alpha = bannerAlpha), RoundedCornerShape(12.dp))
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = if (language == Language.KN) activeOutcome.titleKn else activeOutcome.titleEn,
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 14.sp
                            )
                            Text(
                                text = if (language == Language.KN) activeOutcome.subtitleKn else activeOutcome.subtitleEn,
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // CREX Over Ball-by-Ball Strip (Interactive)
            Text(
                text = if (language == Language.KN) "ಈ ಓವರ್‌ನ ಎಸೆತಗಳು (ಟ್ಯಾಪ್ ಮಾಡಿ ಮರುಪ್ಲೇ ಮಾಡಿ):" else "THIS OVER (Tap any ball to replay in 3D):",
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondary,
                fontSize = 10.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                overDeliveries.forEachIndexed { index, outcome ->
                    val isSelected = selectedBallIndex == index
                    Surface(
                        onClick = {
                            selectedBallIndex = index
                            isAutoPlay = false // pause auto to let user explore
                        },
                        shape = CircleShape,
                        color = when (outcome) {
                            CrexBallOutcome.SIX, CrexBallOutcome.BOUNDARY_SIX -> Color(0xFFA855F7)
                            CrexBallOutcome.FOUR -> PitchGreen
                            CrexBallOutcome.WICKET -> Color(0xFFEF4444)
                            CrexBallOutcome.DOT -> Color(0xFF334155)
                            CrexBallOutcome.SINGLE -> Color(0xFF0284C7)
                        },
                        border = if (isSelected) BorderStroke(2.5.dp, Color.White) else BorderStroke(1.dp, BorderSubtle),
                        modifier = Modifier
                            .size(38.dp)
                            .shadow(if (isSelected) 8.dp else 2.dp, CircleShape, spotColor = outcome.badgeColor)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = outcome.ballSymbol,
                                color = Color.White,
                                fontWeight = FontWeight.ExtraBold,
                                style = MaterialTheme.typography.labelMedium,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Interactive Shot Type Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                ShotSelectorChip(
                    label = "🏏 Six (108m)",
                    isSelected = activeOutcome == CrexBallOutcome.SIX,
                    onClick = {
                        selectedBallIndex = 3
                        isAutoPlay = false
                    },
                    modifier = Modifier.weight(1f)
                )
                ShotSelectorChip(
                    label = "⚡ Four",
                    isSelected = activeOutcome == CrexBallOutcome.FOUR,
                    onClick = {
                        selectedBallIndex = 1
                        isAutoPlay = false
                    },
                    modifier = Modifier.weight(1f)
                )
                ShotSelectorChip(
                    label = "🎯 Bowled",
                    isSelected = activeOutcome == CrexBallOutcome.WICKET,
                    onClick = {
                        selectedBallIndex = 4
                        isAutoPlay = false
                    },
                    modifier = Modifier.weight(1f)
                )
                ShotSelectorChip(
                    label = "🛡️ Dot",
                    isSelected = activeOutcome == CrexBallOutcome.DOT,
                    onClick = {
                        selectedBallIndex = 0
                        isAutoPlay = false
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun ShotSelectorChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        color = if (isSelected) PitchGreen else CardDarkTier2,
        border = BorderStroke(1.dp, if (isSelected) PitchGreen else BorderMedium),
        modifier = modifier.height(30.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = if (isSelected) OnPitchGreen else TextSecondary,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp
            )
        }
    }
}

/**
 * Custom 3D Stadium Drawing Engine mimicking CREX / Hawkeye Visualizer:
 * - 3D Perspective turf pitch
 * - Crease markings (Popping, Bowling, Return)
 * - Stumps and bails at striker & non-striker ends
 * - Batsman figure in stance and swing
 * - Bowler delivery release
 * - Pitch bounce ripple target
 * - Sweet-spot impact spark explosion
 * - Hawkeye 3D glowing tracer projectile
 */
private fun DrawScope.drawCrexStadiumArena(
    progress: Float,
    outcome: CrexBallOutcome
) {
    val w = size.width
    val h = size.height

    // 1. OUTFIELD LAWN WITH CONCENTRIC MOWING RINGS (CREX Aesthetic)
    val centerX = w * 0.5f
    val centerY = h * 0.55f

    // Alternating dark emerald outfield stripes
    for (radius in listOf(w * 0.75f, w * 0.60f, w * 0.45f, w * 0.30f)) {
        drawCircle(
            color = Color(0xFF092012),
            radius = radius,
            center = Offset(centerX, centerY)
        )
    }

    // 30-Yard Fielding Circle (Dashed Cyan/White)
    drawOval(
        color = Color(0xFF1E4C2B).copy(alpha = 0.5f),
        topLeft = Offset(w * 0.08f, h * 0.08f),
        size = Size(w * 0.84f, h * 0.80f),
        style = Stroke(
            width = 1.5.dp.toPx(),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        )
    )

    // 2. 3D PERSPECTIVE PITCH (Perspective Trapezoid from Bowler to Batsman)
    // Striker's End (Top): narrower in perspective
    val strikerWidth = w * 0.32f
    val strikerY = h * 0.24f

    // Bowler's End (Bottom): wider in perspective
    val bowlerWidth = w * 0.48f
    val bowlerY = h * 0.86f

    val pitchPath = Path().apply {
        moveTo(centerX - strikerWidth / 2, strikerY)
        lineTo(centerX + strikerWidth / 2, strikerY)
        lineTo(centerX + bowlerWidth / 2, bowlerY)
        lineTo(centerX - bowlerWidth / 2, bowlerY)
        close()
    }

    // Pitch clay/turf color with subtle linear gradient
    drawPath(
        path = pitchPath,
        brush = Brush.verticalGradient(
            colors = listOf(Color(0xFF8D6E40), Color(0xFFA5824E), Color(0xFF7A5C30)),
            startY = strikerY,
            endY = bowlerY
        )
    )

    // Pitch border
    drawPath(
        path = pitchPath,
        color = Color(0xFF5A411F),
        style = Stroke(width = 1.5.dp.toPx())
    )

    // CREASE LINES
    // Striker Popping Crease
    val popCreaseY = strikerY + 28.dp.toPx()
    val popWidth = strikerWidth * 1.08f
    drawLine(
        color = Color.White,
        start = Offset(centerX - popWidth / 2, popCreaseY),
        end = Offset(centerX + popWidth / 2, popCreaseY),
        strokeWidth = 2.dp.toPx()
    )

    // Striker Bowling Crease (Behind Popping Crease)
    drawLine(
        color = Color.White.copy(alpha = 0.8f),
        start = Offset(centerX - strikerWidth * 0.45f, strikerY + 12.dp.toPx()),
        end = Offset(centerX + strikerWidth * 0.45f, strikerY + 12.dp.toPx()),
        strokeWidth = 1.5.dp.toPx()
    )

    // Bowler Popping Crease
    val bowlerPopY = bowlerY - 24.dp.toPx()
    drawLine(
        color = Color.White,
        start = Offset(centerX - bowlerWidth * 0.48f, bowlerPopY),
        end = Offset(centerX + bowlerWidth * 0.48f, bowlerPopY),
        strokeWidth = 2.dp.toPx()
    )

    // 3. STUMPS & BAILS AT STRIKER END
    val stumpsX = centerX
    val stumpsY = strikerY + 12.dp.toPx()
    val stumpHeight = 22.dp.toPx()
    val stumpSpacing = 4.dp.toPx()

    val isWicketHit = outcome == CrexBallOutcome.WICKET && progress >= 0.44f

    for (i in -1..1) {
        val sx = stumpsX + (i * stumpSpacing)
        // If bowled and wicket hit, middle & off stump tilts backwards
        if (isWicketHit && i >= 0) {
            val flyAngle = if (i == 0) -25f else 35f
            val flyOffset = (progress - 0.44f) * 40.dp.toPx()
            rotate(flyAngle, pivot = Offset(sx, stumpsY)) {
                drawLine(
                    color = Color(0xFFFFD54F), // Flashing LED stumps
                    start = Offset(sx, stumpsY - flyOffset),
                    end = Offset(sx, stumpsY - stumpHeight - flyOffset),
                    strokeWidth = 2.5.dp.toPx()
                )
            }
        } else {
            drawLine(
                color = Color(0xFFE2A458),
                start = Offset(sx, stumpsY),
                end = Offset(sx, stumpsY - stumpHeight),
                strokeWidth = 2.5.dp.toPx()
            )
        }
    }

    // Bails on top of stumps (fly if bowled)
    if (!isWicketHit) {
        drawLine(
            color = Color(0xFFFFF176),
            start = Offset(stumpsX - stumpSpacing * 1.2f, stumpsY - stumpHeight),
            end = Offset(stumpsX + stumpSpacing * 1.2f, stumpsY - stumpHeight),
            strokeWidth = 1.8.dp.toPx()
        )
    } else {
        // Red flashing flying bails
        val bailFly = (progress - 0.44f) * 60.dp.toPx()
        drawCircle(
            color = Color(0xFFFF1744),
            radius = 3.dp.toPx(),
            center = Offset(stumpsX - 10.dp.toPx() - bailFly * 0.5f, stumpsY - stumpHeight - bailFly)
        )
        drawCircle(
            color = Color(0xFFFF1744),
            radius = 3.dp.toPx(),
            center = Offset(stumpsX + 12.dp.toPx() + bailFly * 0.7f, stumpsY - stumpHeight - bailFly * 0.8f)
        )
    }

    // 4. BATSMAN FIGURE AT CREASE (Striker stance & swing)
    val batsmanX = centerX + 18.dp.toPx()
    val batsmanY = popCreaseY

    // Batsman swing angle based on progress:
    // Backlift: -40deg -> Impact at 0.44 -> Follow-through: +60deg
    val batAngle = when {
        progress < 0.32f -> -40f
        progress in 0.32f..0.44f -> -40f + ((progress - 0.32f) / 0.12f) * 40f
        progress in 0.44f..0.70f -> 0f + ((progress - 0.44f) / 0.26f) * 65f
        else -> -40f
    }

    // Draw Batsman Silhouette
    // Legs / Pads
    drawLine(
        color = Color.White,
        start = Offset(batsmanX - 2.dp.toPx(), batsmanY),
        end = Offset(batsmanX - 2.dp.toPx(), batsmanY - 14.dp.toPx()),
        strokeWidth = 4.dp.toPx()
    )
    drawLine(
        color = Color.White,
        start = Offset(batsmanX + 4.dp.toPx(), batsmanY),
        end = Offset(batsmanX + 4.dp.toPx(), batsmanY - 14.dp.toPx()),
        strokeWidth = 4.dp.toPx()
    )
    // Jersey Torso (KMPL Pitch Green)
    drawRoundRect(
        color = PitchGreen,
        topLeft = Offset(batsmanX - 4.dp.toPx(), batsmanY - 26.dp.toPx()),
        size = Size(10.dp.toPx(), 13.dp.toPx()),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(2.dp.toPx())
    )
    // Helmet
    drawCircle(
        color = Color(0xFF1E3A8A),
        radius = 4.5.dp.toPx(),
        center = Offset(batsmanX + 1.dp.toPx(), batsmanY - 30.dp.toPx())
    )

    // Cricket Bat
    rotate(degrees = batAngle, pivot = Offset(batsmanX - 2.dp.toPx(), batsmanY - 18.dp.toPx())) {
        drawLine(
            color = Color(0xFFD4A373), // Willow wood
            start = Offset(batsmanX - 2.dp.toPx(), batsmanY - 18.dp.toPx()),
            end = Offset(batsmanX - 20.dp.toPx(), batsmanY - 4.dp.toPx()),
            strokeWidth = 4.5.dp.toPx()
        )
    }

    // 5. BALL TRAJECTORY & IMPACT CALCULATIONS
    val bowlerReleaseX = centerX + 12.dp.toPx()
    val bowlerReleaseY = bowlerY - 10.dp.toPx()

    // Pitch bounce location (depends on outcome)
    val pitchBounceX = when (outcome) {
        CrexBallOutcome.SIX, CrexBallOutcome.BOUNDARY_SIX -> centerX - 4.dp.toPx()
        CrexBallOutcome.FOUR -> centerX - 8.dp.toPx()
        CrexBallOutcome.WICKET -> centerX // right on stumps line
        CrexBallOutcome.DOT -> centerX + 14.dp.toPx() // outside off
        CrexBallOutcome.SINGLE -> centerX - 2.dp.toPx()
    }
    val pitchBounceY = when (outcome) {
        CrexBallOutcome.WICKET -> popCreaseY + 10.dp.toPx() // Yorker near popping crease
        CrexBallOutcome.SIX, CrexBallOutcome.BOUNDARY_SIX -> popCreaseY + 36.dp.toPx() // in the slot
        CrexBallOutcome.FOUR -> popCreaseY + 45.dp.toPx() // full length
        CrexBallOutcome.DOT -> popCreaseY + 60.dp.toPx() // good length
        CrexBallOutcome.SINGLE -> popCreaseY + 70.dp.toPx()
    }

    // Contact point with bat
    val impactX = centerX - 2.dp.toPx()
    val impactY = popCreaseY

    // Phase A: INCOMING DELIVERY TO PITCH BOUNCE SPOT (0.00 to 0.36)
    if (progress < 0.36f) {
        val t = (progress / 0.36f).coerceIn(0f, 1f)
        val curX = bowlerReleaseX + (pitchBounceX - bowlerReleaseX) * t
        val curY = bowlerReleaseY + (pitchBounceY - bowlerReleaseY) * t
        val ballSize = (4.dp.toPx() + (3.dp.toPx() * t))

        // Inward flight glow trail
        drawLine(
            color = Color.White.copy(alpha = 0.4f * t),
            start = Offset(bowlerReleaseX, bowlerReleaseY),
            end = Offset(curX, curY),
            strokeWidth = 2.dp.toPx()
        )

        // Cricket ball
        drawCircle(
            color = Color(0xFFC5221F),
            radius = ballSize,
            center = Offset(curX, curY)
        )
    }

    // Phase B: BOUNCE RADAR RIPPLE ON PITCH (0.34 to 0.48)
    if (progress in 0.34f..0.48f) {
        val rippleT = (progress - 0.34f) / 0.14f
        val rippleRadius = 6.dp.toPx() + (18.dp.toPx() * rippleT)
        val rippleAlpha = (1f - rippleT).coerceIn(0f, 1f)

        // Concentric radar ring
        drawCircle(
            color = outcome.accentColor.copy(alpha = rippleAlpha),
            radius = rippleRadius,
            center = Offset(pitchBounceX, pitchBounceY),
            style = Stroke(width = 2.dp.toPx())
        )
        // Inner bounce mark
        drawCircle(
            color = Color.White.copy(alpha = rippleAlpha * 0.9f),
            radius = 3.dp.toPx(),
            center = Offset(pitchBounceX, pitchBounceY)
        )
    }

    // Phase C: BOUNCE SPOT TO IMPACT (0.36 to 0.44)
    if (progress in 0.36f..0.44f) {
        val t = ((progress - 0.36f) / 0.08f).coerceIn(0f, 1f)
        val curX = pitchBounceX + (impactX - pitchBounceX) * t
        val curY = pitchBounceY + (impactY - pitchBounceY) * t

        drawCircle(
            color = Color(0xFFC5221F),
            radius = 6.dp.toPx(),
            center = Offset(curX, curY)
        )
    }

    // Phase D: IMPACT SPARK / TIMBER SHOCKWAVE (0.43 to 0.52)
    if (progress in 0.43f..0.52f) {
        val sparkT = (progress - 0.43f) / 0.09f
        val shockRadius = 10.dp.toPx() + (28.dp.toPx() * sparkT)
        val shockAlpha = (1f - sparkT).coerceIn(0f, 1f)

        // Shockwave ring
        drawCircle(
            color = outcome.accentColor.copy(alpha = shockAlpha),
            radius = shockRadius,
            center = Offset(impactX, impactY),
            style = Stroke(width = 2.5.dp.toPx())
        )

        // Bright flash center
        drawCircle(
            color = Color.White.copy(alpha = shockAlpha),
            radius = shockRadius * 0.4f,
            center = Offset(impactX, impactY)
        )

        // 8 Radiating sparks
        for (i in 0 until 8) {
            val angleRad = (i * 45f * PI / 180f).toFloat()
            val sx = impactX + cos(angleRad) * shockRadius * 1.2f
            val sy = impactY + sin(angleRad) * shockRadius * 1.2f
            drawLine(
                color = outcome.accentColor.copy(alpha = shockAlpha),
                start = Offset(impactX, impactY),
                end = Offset(sx, sy),
                strokeWidth = 2.dp.toPx()
            )
        }
    }

    // Phase E: HAWKEYE 3D TRAJECTORY FLIGHT (0.44 to 0.90)
    if (progress in 0.44f..0.90f) {
        val t = ((progress - 0.44f) / 0.46f).coerceIn(0f, 1f)

        val targetX: Float
        val targetY: Float
        val arcApexHeight: Float

        when (outcome) {
            CrexBallOutcome.SIX, CrexBallOutcome.BOUNDARY_SIX -> {
                // Flying high out of the ground over deep mid-wicket
                targetX = -w * 0.12f
                targetY = h * 0.02f
                arcApexHeight = 75.dp.toPx()
            }
            CrexBallOutcome.FOUR -> {
                // Bullet cover drive across grass to offside boundary
                targetX = w * 1.08f
                targetY = h * 0.28f
                arcApexHeight = 10.dp.toPx()
            }
            CrexBallOutcome.WICKET -> {
                // Ball rebounds gently off middle stump
                targetX = centerX + 8.dp.toPx()
                targetY = strikerY - 12.dp.toPx()
                arcApexHeight = 4.dp.toPx()
            }
            CrexBallOutcome.DOT -> {
                // Carries through to wicket keeper gloves
                targetX = centerX + 16.dp.toPx()
                targetY = strikerY - 26.dp.toPx()
                arcApexHeight = 6.dp.toPx()
            }
            CrexBallOutcome.SINGLE -> {
                // Pushed to mid-on
                targetX = centerX - 35.dp.toPx()
                targetY = h * 0.52f
                arcApexHeight = 8.dp.toPx()
            }
        }

        val curX = impactX + (targetX - impactX) * t
        val arcY = arcApexHeight * sin(t * PI).toFloat()
        val curY = impactY + (targetY - impactY) * t - arcY

        // 3D Hawkeye Trajectory Trail (Glowing segments)
        val trailSegments = 16
        for (step in 1..trailSegments) {
            val prevT = (t - (step * 0.02f)).coerceIn(0f, 1f)
            val px = impactX + (targetX - impactX) * prevT
            val pArc = arcApexHeight * sin(prevT * PI).toFloat()
            val py = impactY + (targetY - impactY) * prevT - pArc
            val segmentAlpha = ((1f - (step.toFloat() / trailSegments)) * 0.8f).coerceIn(0f, 1f)

            drawCircle(
                color = outcome.badgeColor.copy(alpha = segmentAlpha),
                radius = (7.dp.toPx() - (step * 0.35.dp.toPx())).coerceAtLeast(1.dp.toPx()),
                center = Offset(px, py)
            )
        }

        // Flying ball
        drawCircle(
            color = Color(0xFFC5221F),
            radius = 7.dp.toPx(),
            center = Offset(curX, curY)
        )
        // White seam
        drawLine(
            color = Color.White,
            start = Offset(curX - 4.dp.toPx(), curY - 2.dp.toPx()),
            end = Offset(curX + 4.dp.toPx(), curY + 2.dp.toPx()),
            strokeWidth = 1.6.dp.toPx()
        )
    }
}
