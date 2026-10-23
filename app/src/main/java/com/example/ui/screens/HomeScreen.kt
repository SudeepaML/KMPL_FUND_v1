package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.SportsCricket
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.Language
import com.example.ui.components.KmplTopBar
import com.example.ui.theme.*
import com.example.viewmodel.KmplViewModel
import com.example.viewmodel.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun HomeScreen(
    viewModel: KmplViewModel,
    language: Language
) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    // Real-time live seconds clock so user can immediately verify the app is running live
    var currentTimeStr by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        while (true) {
            val cal = Calendar.getInstance()
            currentTimeStr = String.format(
                Locale.getDefault(),
                "%02d:%02d:%02d",
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                cal.get(Calendar.SECOND)
            )
            delay(1000L)
        }
    }

    // Live heartbeat pulse animation
    val pulseTransition = rememberInfiniteTransition(label = "LivePulse")
    val pulseScale by pulseTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "PulseScale"
    )

    Scaffold(
        containerColor = SurfaceDark,
        topBar = {
            KmplTopBar(
                title = if (language == Language.KN) "ಟೂರ್ನಮೆಂಟ್ ಹೋಮ್" else "Tournament Home",
                subtitle = "KMPL FUND 2025",
                language = language,
                onToggleLanguage = { viewModel.toggleLanguage() },
                onProfileClick = { viewModel.navigateTo(Screen.PLAYER_LOGIN) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                // Enables smooth mouse cursor dragging on web streaming emulator as well as standard scrolling
                .pointerInput(Unit) {
                    detectVerticalDragGestures { change, dragAmount ->
                        change.consume()
                        coroutineScope.launch {
                            scrollState.scrollBy(-dragAmount)
                        }
                    }
                }
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Live Status Beacon & Seconds Ticker (Proves app is running & informs user mouse drag is active)
            Surface(
                shape = RoundedCornerShape(999.dp),
                color = Color(0xFF071F11),
                border = BorderStroke(1.dp, PitchGreen.copy(alpha = 0.6f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .scale(pulseScale)
                                .clip(CircleShape)
                                .background(PitchGreen)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (language == Language.KN) "ಲೈವ್ ಸಿಸ್ಟಮ್ ಸಕ್ರಿಯವಾಗಿದೆ" else "APP LIVE & RUNNING",
                            style = MaterialTheme.typography.labelSmall,
                            color = PitchGreen,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "⏱ $currentTimeStr",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (language == Language.KN) "• ಸ್ಕ್ರೋಲ್ ಮಾಡಲು ಎಳೆಯಿರಿ" else "• Drag to scroll",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextSecondary,
                            fontSize = 10.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Main Title
            Text(
                text = "KMPL Fund",
                style = MaterialTheme.typography.displayLarge.copy(fontSize = 34.sp),
                color = PitchGreen,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = if (language == Language.KN)
                    "ಗ್ರಾಮೀಣ ಕ್ರಿಕೆಟ್ ಟೂರ್ನಮೆಂಟ್ ನಿಧಿ ಮತ್ತು ಸದಸ್ಯರ ನಿರ್ವಹಣೆ"
                else
                    "Village Cricket Tournament Fund & Member Management",
                style = MaterialTheme.typography.bodyMedium,
                color = TextPrimary.copy(alpha = 0.85f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Bilingual Toggle Segment Bar
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = CardDarkTier2,
                border = BorderStroke(1.dp, BorderMedium),
                modifier = Modifier.height(38.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (language == Language.EN) PitchGreen else Color.Transparent)
                            .clickable { viewModel.setLanguage(Language.EN) }
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (language == Language.EN) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = OnPitchGreen,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                            }
                            Text(
                                text = "English",
                                color = if (language == Language.EN) OnPitchGreen else TextSecondary,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (language == Language.KN) PitchGreen else Color.Transparent)
                            .clickable { viewModel.setLanguage(Language.KN) }
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (language == Language.KN) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = OnPitchGreen,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                            }
                            Text(
                                text = "ಕನ್ನಡ",
                                color = if (language == Language.KN) OnPitchGreen else TextSecondary,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // CREX Style 3D Live Cricket Match & Pitch Visualizer
            com.example.ui.components.CrexPitchVisualizer(language = language)

            Spacer(modifier = Modifier.height(16.dp))

            // Two Photos (Ground Prep & 12 Village Teams)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                PhotoCard(
                    drawableRes = R.drawable.ground_prep,
                    title = if (language == Language.KN) "ಕ್ರೀಡಾಂಗಣ ಸಿದ್ಧತೆ" else "Ground Prep",
                    modifier = Modifier.weight(1f)
                )
                PhotoCard(
                    drawableRes = R.drawable.village_teams,
                    title = if (language == Language.KN) "12 ಗ್ರಾಮೀಣ ತಂಡಗಳು" else "12 Village Teams",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 1. Rules & Regulations Button (Yellow Gold)
            Surface(
                onClick = { viewModel.navigateTo(Screen.RULES) },
                shape = RoundedCornerShape(16.dp),
                color = RulesGold,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .testTag("rules_and_regulations_button")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.Black.copy(alpha = 0.12f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Book,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = if (language == Language.KN) "ನಿಯಮಾವಳಿಗಳು" else "Rules & Regulations",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                text = if (language == Language.KN) "ಪ್ರಕಟಿತ ನಿಯಮಗಳು" else "Published Rules (ಸಾರ್ವಜನಿಕ ನಿಯಮಗಳು)",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Black.copy(alpha = 0.8f)
                            )
                        }
                    }
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 2. Player Portal LIVE Button with Pink-and-White Animated Border
            val infiniteTransition = rememberInfiniteTransition(label = "PortalBorderAnim")
            val borderShift by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 1000f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 2500, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                ),
                label = "BorderShift"
            )
            val animatedBorderBrush = Brush.linearGradient(
                colors = listOf(
                    PortalPink,
                    Color.White,
                    PortalPink,
                    Color.White,
                    PortalPink
                ),
                start = androidx.compose.ui.geometry.Offset(borderShift, 0f),
                end = androidx.compose.ui.geometry.Offset(borderShift + 400f, 400f)
            )

            Surface(
                onClick = { viewModel.navigateTo(Screen.PLAYER_LOGIN) },
                shape = RoundedCornerShape(16.dp),
                color = CardDarkTier1,
                border = BorderStroke(2.5.dp, animatedBorderBrush),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .shadow(10.dp, RoundedCornerShape(16.dp), spotColor = PortalPink.copy(alpha = 0.5f))
                    .testTag("player_portal_button")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(PortalPink.copy(alpha = 0.25f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.SportsCricket,
                                contentDescription = null,
                                tint = PortalPink,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = if (language == Language.KN) "ಆಟಗಾರರ ಪೋರ್ಟಲ್" else "Player Portal",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(PortalPink)
                                        .padding(horizontal = 5.dp, vertical = 1.dp)
                                ) {
                                    LouverLabel(text = "LIVE")
                                }
                            }
                            Text(
                                text = if (language == Language.KN) "ಖಾತೆಯ ವಿವರ ಮತ್ತು ಪಾಸ್‌ಬುಕ್ ವೀಕ್ಷಿಸಿ" else "View Member Contributions & Passbook",
                                style = MaterialTheme.typography.bodySmall,
                                color = PortalPinkLight
                            )
                        }
                    }
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = PortalPink
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 3. Admin Login Button (Pitch Green)
            Surface(
                onClick = { viewModel.navigateTo(Screen.ADMIN_LOGIN) },
                shape = RoundedCornerShape(16.dp),
                color = PitchGreen,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .shadow(8.dp, RoundedCornerShape(16.dp), spotColor = PitchGreen.copy(alpha = 0.4f))
                    .testTag("admin_login_button")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.Black.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = null,
                                tint = OnPitchGreen,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = if (language == Language.KN) "ನಿರ್ವಾಹಕರ ಲಾಗಿನ್ (Admin)" else "Admin Login",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = OnPitchGreen
                            )
                            Text(
                                text = if (language == Language.KN) "(ಅಧಿಕೃತ ಸಿಬ್ಬಂದಿ ಮಾತ್ರ)" else "(Authorized Personnel Only)",
                                style = MaterialTheme.typography.bodySmall,
                                color = OnPitchGreen.copy(alpha = 0.85f)
                            )
                        }
                    }
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = OnPitchGreen
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Developer Profile Placeholder (Pink Styling per spec)
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = CardDarkTier1,
                border = BorderStroke(1.5.dp, DeveloperPink.copy(alpha = 0.8f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(6.dp, RoundedCornerShape(16.dp), spotColor = DeveloperPink.copy(alpha = 0.3f))
                    .testTag("developer_profile_placeholder")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(DeveloperPink.copy(alpha = 0.15f))
                            .border(1.dp, DeveloperPink.copy(alpha = 0.5f), RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "💻", fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "DEVELOPER PROFILE",
                        style = MaterialTheme.typography.labelSmall,
                        color = DeveloperPink,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "[👨‍💻 Developer Profile Placeholder - Reserved for Developer Name & Portfolio Link - Final Stage]",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextPrimary.copy(alpha = 0.85f),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "ನಿಜವಾದ ಹೆಸರು ಮತ್ತು ಪೋರ್ಟ್‌ಫೋಲಿಯೊವನ್ನು ನಂತರ ಸೇರಿಸಲಾಗುತ್ತದೆ",
                        style = MaterialTheme.typography.labelSmall,
                        color = DeveloperPink.copy(alpha = 0.8f),
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Footer
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "🛡️", fontSize = 12.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (language == Language.KN)
                        "ಎಂಡ್-ಟು-ಎಂಡ್ ಕ್ರಿಪ್ಟೋಗ್ರಾಫಿಕ್ ಲೆಡ್ಜರ್ ಆಡಿಟ್ • ಎಲ್ಲಾ ಗ್ರಾಮ ಸದಸ್ಯರಿಗೆ ಮುಕ್ತವಾಗಿದೆ"
                    else
                        "End-to-End Cryptographic Ledger Audit • Open to all Grama Members",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun LouverLabel(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall,
        fontSize = 9.sp,
        color = Color.White,
        fontWeight = FontWeight.ExtraBold
    )
}

@Composable
private fun PhotoCard(drawableRes: Int, title: String, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = CardDarkTier1,
        border = BorderStroke(1.dp, BorderSubtle),
        modifier = modifier.height(110.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = drawableRes),
                contentDescription = title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.85f))
                        )
                    )
            )
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(10.dp)
            )
        }
    }
}

