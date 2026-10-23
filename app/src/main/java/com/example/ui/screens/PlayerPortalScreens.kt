package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.*
import com.example.ui.components.KmplTopBar
import com.example.ui.components.PrimaryGlowButton
import com.example.ui.components.StatusPill
import com.example.ui.theme.*
import com.example.viewmodel.KmplViewModel
import com.example.viewmodel.Screen

// ==========================================
// 1. PLAYER LOGIN SCREEN (Image_1_1d347074)
// ==========================================
@Composable
fun PlayerLoginScreen(
    viewModel: KmplViewModel,
    language: Language
) {
    var selectedTab by remember { mutableStateOf("MOBILE") }
    var inputIdentifier by remember { mutableStateOf("98450 82190") }

    Scaffold(
        containerColor = SurfaceDark,
        topBar = {
            KmplTopBar(
                title = if (language == Language.KN) "ಆಟಗಾರರ ಪೋರ್ಟಲ್" else "Player Portal",
                subtitle = "KMPL FUND 2025",
                language = language,
                onToggleLanguage = { viewModel.toggleLanguage() },
                showBackButton = true,
                onBackClick = { viewModel.navigateBack() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Live Status Header Card
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = CardDarkTier1,
                border = BorderStroke(1.dp, BorderSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(CardDarkTier2),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Badge,
                                    contentDescription = null,
                                    tint = PortalPink,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "Player Portal",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(CardDarkTier2)
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = "LIVE",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = PitchGreen,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                Text(
                                    text = "ಆಟಗಾರರ ಪೋರ್ಟಲ್ • KMPL 2025",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextSecondary
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(999.dp))
                                .background(CardDarkTier2)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "🛡️ 256-bit",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextSecondary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Access your tournament passbook, track fund payments, and review league fixture updates. Select your preferred login method below.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "ಲಾಗಿನ್ ವಿವರಗಳನ್ನು ಆಯ್ಕೆಮಾಡಿ ಪಾಸ್‌ಬುಕ್ ಪರಿಶೀಲಿಸಿ.",
                        style = MaterialTheme.typography.bodySmall,
                        color = PitchGreen
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Login Method Tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(CardDarkTier2)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf(
                    "MOBILE" to ("Mobile" to "ಮೊಬೈಲ್"),
                    "NAME" to ("Name" to "ಹೆಸರು"),
                    "ID" to ("Player ID" to "ಆಟಗಾರ ID")
                ).forEach { (key, labels) ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (selectedTab == key) CardDarkTier3 else Color.Transparent)
                            .clickable { selectedTab = key }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = labels.first,
                                style = MaterialTheme.typography.labelMedium,
                                color = if (selectedTab == key) TextPrimary else TextSecondary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = labels.second,
                                style = MaterialTheme.typography.labelSmall,
                                fontSize = 9.sp,
                                color = if (selectedTab == key) PitchGreen else TextSecondary.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Input Card
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = CardDarkTier1,
                border = BorderStroke(1.dp, BorderSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = when (selectedTab) {
                                "MOBILE" -> "Enter Registered Phone Number"
                                "NAME" -> "Enter Registered Player Name"
                                else -> "Enter KMPL Player ID"
                            },
                            style = MaterialTheme.typography.titleSmall,
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = when (selectedTab) {
                                "MOBILE" -> "ನೋಂದಾಯಿತ ಮೊಬೈಲ್ ಸಂಖ್ಯೆ"
                                "NAME" -> "ಆಟಗಾರನ ಹೆಸರು"
                                else -> "ಐಡಿ ಸಂಖ್ಯೆ"
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = PitchGreen
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = inputIdentifier,
                        onValueChange = { inputIdentifier = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("player_login_input"),
                        leadingIcon = {
                            Icon(
                                imageVector = when (selectedTab) {
                                    "MOBILE" -> Icons.Default.PhoneIphone
                                    "NAME" -> Icons.Default.Person
                                    else -> Icons.Default.Badge
                                },
                                contentDescription = null,
                                tint = PitchGreen
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PitchGreen,
                            unfocusedBorderColor = BorderSubtle,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "10-digit Indian Mobile Number linked to registered tournament profile",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary,
                        fontSize = 11.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Instant Member Access Banner
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = CardDarkTier2,
                        border = BorderStroke(1.dp, BorderSubtle),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Verified,
                                    contentDescription = null,
                                    tint = PitchGreen,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = "Instant Member Access — No PIN Required",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = TextPrimary,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Direct access for enrolled village players (Demo: Harish N)",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = TextSecondary,
                                        fontSize = 10.sp
                                    )
                                }
                            }

                            Button(
                                onClick = { inputIdentifier = "98450 82190" },
                                colors = ButtonDefaults.buttonColors(containerColor = CardDarkTier3),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                modifier = Modifier.testTag("auto_fill_login_button")
                            ) {
                                Text(text = "Auto-Fill", color = TextPrimary, style = MaterialTheme.typography.labelSmall)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Login Button (Pink)
                    Button(
                        onClick = { viewModel.loginPlayer(inputIdentifier) },
                        colors = ButtonDefaults.buttonColors(containerColor = PortalPink),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .shadow(8.dp, RoundedCornerShape(16.dp), spotColor = PortalPink.copy(alpha = 0.5f))
                            .testTag("login_open_passbook_button")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Login & Open Passbook",
                                color = Color.White,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Secured Member Environment Card
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = CardDarkTier1,
                border = BorderStroke(1.dp, BorderSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(text = "🔒", fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Secured Member Environment",
                            style = MaterialTheme.typography.labelMedium,
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Player portal provides verified, unalterable access to player accounts. Official entries are recorded only by certified committee treasurers.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }
    }
}

// ==========================================
// 2. PLAYER PORTAL DASHBOARD (Image_19_7da31bb3)
// ==========================================
@Composable
fun PlayerPortalDashboard(
    viewModel: KmplViewModel,
    language: Language
) {
    val activePlayer by viewModel.activePlayer.collectAsState()
    val player = activePlayer ?: Player("KMPL-015", "Harish N", "ಹರೀಶ್ ಎನ್", "+91 94480 54321", SquadType.SENIOR, "Batsman", isPaidThisMonth = true)

    Scaffold(
        containerColor = SurfaceDark,
        topBar = {
            KmplTopBar(
                title = "${player.name} (${player.id})",
                subtitle = "Senior Player Roster",
                language = language,
                onToggleLanguage = { viewModel.toggleLanguage() },
                showBackButton = true,
                onBackClick = { viewModel.logoutPlayer() },
                statusBadgeText = if (language == Language.KN) "ಓದುವಿಕೆ ಮಾತ್ರ" else "VIEW-ONLY"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Active Month Cycle Pill
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(999.dp))
                    .background(CardDarkTier2)
                    .border(1.dp, BorderSubtle, RoundedCornerShape(999.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(PitchGreen))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "October 2025 • ಅಕ್ಟೋಬರ್ ೨೦೨೫ • Real-time Active Cycle",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Hero Community Banner
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = CardDarkTier1,
                border = BorderStroke(1.dp, BorderSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "TOURNAMENT CYCLE 2025",
                            style = MaterialTheme.typography.labelSmall,
                            color = PitchGreen,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "₹ Transparent Accounts",
                            style = MaterialTheme.typography.labelSmall,
                            color = PitchGreen
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "KMPL SPORTS WELFARE COMMUNITY",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondary
                    )
                    Text(
                        text = "Community Cricket & Chitty Fund",
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "ಗ್ರಾಮೀಣ ಕ್ರೀಡಾ ಸಂಘದ ಪಾರದರ್ಶಕ ಲೆಕ್ಕಪತ್ರ ಪೋರ್ಟಲ್",
                        style = MaterialTheme.typography.bodySmall,
                        color = PitchGreen
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 4 Grid Navigation Cards
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                DashboardNavCard(
                    title = if (language == Language.KN) "ಆಟಗಾರರ ಪಟ್ಟಿ" else "Players List",
                    subtitle = "ಆಟಗಾರರ ಪಟ್ಟಿ",
                    metric = "68 Registered",
                    meta = "Junior & Senior Roster",
                    accentColor = StatusOrange,
                    onClick = { viewModel.navigateTo(Screen.REGISTERED_PLAYERS) },
                    modifier = Modifier.weight(1f)
                )
                DashboardNavCard(
                    title = if (language == Language.KN) "ಮಾಸಿಕ ನಿಧಿ" else "Monthly Fund",
                    subtitle = "ಮಾಸಿಕ ನಿಧಿ",
                    metric = "₹8,400 / ₹11,200",
                    meta = "75% Paid • Oct Cycle",
                    accentColor = PitchGreenDeep,
                    onClick = { viewModel.navigateTo(Screen.ADMIN_TRANSACTION_DETAIL) },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                DashboardNavCard(
                    title = if (language == Language.KN) "ಬಡ್ಡಿ ಚಿಟ್ಟಿ" else "Buddy Chitty",
                    subtitle = "ಬಡ್ಡಿ ಚಿಟ್ಟಿ",
                    metric = "7 Active Loans",
                    meta = "10% Int • Due Nov 15",
                    accentColor = StatusPurple,
                    onClick = { viewModel.navigateTo(Screen.BUDDY_CHITTY_LEDGER) },
                    modifier = Modifier.weight(1f)
                )
                DashboardNavCard(
                    title = if (language == Language.KN) "ಒಟ್ಟು ಸಂಗ್ರಹ ವರದಿ" else "Total Collection",
                    subtitle = "ಒಟ್ಟು ಸಂಗ್ರಹ ವರದಿ",
                    metric = "₹1,42,850",
                    meta = "Fund & Chitty Combined",
                    accentColor = StatusBlue,
                    onClick = { viewModel.navigateTo(Screen.TOTAL_COLLECTION_REPORTS) },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Personal Transparency Card
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = CardDarkTier1,
                border = BorderStroke(1.dp, PitchGreen.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "PERSONAL & CLUB TRANSPARENCY",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextSecondary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "My Contribution Status",
                                style = MaterialTheme.typography.titleMedium,
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "ನನ್ನ ಪಾವತಿ ಸ್ಥಿತಿ (ಅಕ್ಟೋಬರ್ ೨೦೨೫)",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondary
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(999.dp))
                                .background(PitchGreen.copy(alpha = 0.2f))
                                .border(1.dp, PitchGreen, RoundedCornerShape(999.dp))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "PAID (ಪಾವತಿಸಲಾಗಿದೆ)",
                                style = MaterialTheme.typography.labelSmall,
                                color = PitchGreen,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "${player.name} (${player.squad.labelEn})",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextPrimary,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Paid on Oct 03 via UPI (Ref #9841)",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondary
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "₹200.00",
                                style = MaterialTheme.typography.titleLarge,
                                color = PitchGreen,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Oct 2025 Due",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextSecondary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = { viewModel.openReceipt(player) },
                        colors = ButtonDefaults.buttonColors(containerColor = StatusPurple),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .testTag("view_digital_receipt_button")
                    ) {
                        Text(
                            text = "View Digital Receipt (ರಸೀದಿ ವೀಕ್ಷಿಸಿ)",
                            color = Color.White,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mandatory Notice
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = CardDarkTier1,
                border = BorderStroke(1.dp, RulesGold.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "🔔", fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "MANDATORY NOTICE / ಪ್ರಮುಖ ಸೂಚನೆ",
                            style = MaterialTheme.typography.labelSmall,
                            color = RulesGold,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Monthly Notice: The 15th of every month is the Buddy Chitty collection date. Check the app for latest repayment information and your payment status.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "ಮಾಸಿಕ ಸೂಚನೆ: ಪ್ರತಿ ತಿಂಗಳ 15ನೇ ತಾರೀಕು ಬಡ್ಡಿ ಚಿಟ್ಟಿ ಮರುಪಾವತಿ ದಿನವಾಗಿರುತ್ತದೆ.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "ℹ️ Published by Admin Shivakumar • Oct 01, 2025",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondary,
                        fontSize = 10.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "🔒 Strict Read-Only Access • No financial modification controls enabled for players.",
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondary.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun DashboardNavCard(
    title: String,
    subtitle: String,
    metric: String,
    meta: String,
    accentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        color = CardDarkTier1,
        border = BorderStroke(1.dp, BorderSubtle),
        modifier = modifier.height(126.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(accentColor.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(accentColor))
                }
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = TextSecondary,
                    modifier = Modifier.size(16.dp)
                )
            }

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = PitchGreen,
                    fontSize = 10.sp
                )
            }

            Column {
                Text(
                    text = metric,
                    style = MaterialTheme.typography.labelMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = meta,
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary,
                    fontSize = 9.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

// ==========================================
// 3. REGISTERED PLAYERS SCREEN (Image_17_2deacbe5)
// ==========================================
@Composable
fun RegisteredPlayersScreen(
    viewModel: KmplViewModel,
    language: Language
) {
    val players by viewModel.players.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("ALL") }

    Scaffold(
        containerColor = SurfaceDark,
        topBar = {
            KmplTopBar(
                title = if (language == Language.KN) "ನೋಂದಾಯಿತ ಆಟಗಾರರು" else "Registered Players",
                subtitle = "2025 SQUAD DIRECTORY",
                language = language,
                onToggleLanguage = { viewModel.toggleLanguage() },
                showBackButton = true,
                onBackClick = { viewModel.navigateBack() },
                statusBadgeText = "100% TRANSPARENT"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("registered_players_search_input"),
                placeholder = {
                    Text(
                        text = if (language == Language.KN) "ಆಟಗಾರರ ಹೆಸರು ಅಥವಾ ID ಮೂಲಕ ಹುಡುಕಿ..." else "Search by player name or KMPL ID...",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = TextSecondary)
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Clear", tint = TextSecondary)
                        }
                    }
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PitchGreen,
                    unfocusedBorderColor = BorderSubtle,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Squad Filters
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedFilter == "ALL",
                    onClick = { selectedFilter = "ALL" },
                    label = { Text("All (${players.size})", fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = PitchGreen,
                        selectedLabelColor = OnPitchGreen,
                        containerColor = CardDarkTier2,
                        labelColor = TextPrimary
                    )
                )
                FilterChip(
                    selected = selectedFilter == "SENIOR",
                    onClick = { selectedFilter = "SENIOR" },
                    label = { Text("Senior Squad (₹200)", fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = PitchGreen,
                        selectedLabelColor = OnPitchGreen,
                        containerColor = CardDarkTier2,
                        labelColor = TextPrimary
                    )
                )
                FilterChip(
                    selected = selectedFilter == "JUNIOR",
                    onClick = { selectedFilter = "JUNIOR" },
                    label = { Text("Junior Squad (₹100)", fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = PitchGreen,
                        selectedLabelColor = OnPitchGreen,
                        containerColor = CardDarkTier2,
                        labelColor = TextPrimary
                    )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            val filteredPlayers = players.filter { player ->
                val matchesFilter = when (selectedFilter) {
                    "SENIOR" -> player.squad == SquadType.SENIOR
                    "JUNIOR" -> player.squad == SquadType.JUNIOR
                    else -> true
                }
                val matchesSearch = player.name.contains(searchQuery, ignoreCase = true) ||
                        player.kannadaName.contains(searchQuery, ignoreCase = true) ||
                        player.id.contains(searchQuery, ignoreCase = true) ||
                        player.phone.contains(searchQuery)
                matchesFilter && matchesSearch
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(filteredPlayers) { player ->
                    Surface(
                        onClick = { viewModel.openReceipt(player) },
                        shape = RoundedCornerShape(12.dp),
                        color = CardDarkTier1,
                        border = BorderStroke(1.dp, BorderSubtle),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(CardDarkTier2)
                                        .border(1.dp, if (player.isPaidThisMonth) PitchGreen.copy(alpha = 0.5f) else RulesGold.copy(alpha = 0.5f), RoundedCornerShape(8.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = player.name.take(2).uppercase(),
                                        style = MaterialTheme.typography.titleSmall,
                                        color = if (player.isPaidThisMonth) PitchGreen else RulesGold,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Spacer(modifier = Modifier.width(10.dp))

                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = player.name,
                                            style = MaterialTheme.typography.titleSmall,
                                            color = TextPrimary,
                                            fontWeight = FontWeight.Bold
                                        )
                                        if (player.isCaptain) {
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(4.dp))
                                                    .background(PortalPink)
                                                    .padding(horizontal = 4.dp, vertical = 1.dp)
                                            ) {
                                                Text(
                                                    text = "CAPTAIN",
                                                    style = MaterialTheme.typography.labelSmall,
                                                    fontSize = 9.sp,
                                                    color = Color.White,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                        if (player.id == "KMPL-015") {
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(4.dp))
                                                    .background(PitchGreen)
                                                    .padding(horizontal = 4.dp, vertical = 1.dp)
                                            ) {
                                                Text(
                                                    text = "YOU",
                                                    style = MaterialTheme.typography.labelSmall,
                                                    fontSize = 9.sp,
                                                    color = OnPitchGreen,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                    }

                                    Text(
                                        text = "${player.id} • ${player.kannadaName} • ${player.role}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = TextSecondary,
                                        fontSize = 11.sp
                                    )
                                }
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "₹${player.squad.fee}/mo",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = if (player.isPaidThisMonth) PitchGreen else RulesGold,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = if (player.isPaidThisMonth) "PAID" else "DUE",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontSize = 9.sp,
                                    color = if (player.isPaidThisMonth) PitchGreen else RulesGold
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Squad Allocation Ratio Bar
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = CardDarkTier1,
                border = BorderStroke(1.dp, BorderSubtle),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Squad Allocation Ratio",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextSecondary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "2025 SEASON",
                            style = MaterialTheme.typography.labelSmall,
                            color = PitchGreen
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    LinearProgressIndicator(
                        progress = { 49f / 68f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = PitchGreen,
                        trackColor = RulesGold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "● Senior: 49 Players (₹200)",
                            style = MaterialTheme.typography.labelSmall,
                            color = PitchGreen,
                            fontSize = 10.sp
                        )
                        Text(
                            text = "● Junior: 19 Players (₹100)",
                            style = MaterialTheme.typography.labelSmall,
                            color = RulesGold,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
    }
}

// ==========================================
// 4. BUDDY CHITTY LEDGER (Image_23_ce6b5666)
// ==========================================
@Composable
fun BuddyChittyLedgerScreen(
    viewModel: KmplViewModel,
    language: Language
) {
    val borrowers by viewModel.chittyBorrowers.collectAsState()
    val repayments by viewModel.chittyRepayments.collectAsState()

    Scaffold(
        containerColor = SurfaceDark,
        topBar = {
            KmplTopBar(
                title = if (language == Language.KN) "ಬಡ್ಡಿ ಚಿಟ್ಟಿ ಲೆಡ್ಜರ್" else "Buddy Chitty Ledger",
                subtitle = "GRASSROOTS MUTUAL LENDING",
                language = language,
                onToggleLanguage = { viewModel.toggleLanguage() },
                showBackButton = true,
                onBackClick = { viewModel.navigateBack() },
                statusBadgeText = "Audit 100% Verified"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Lending Cycle Overview
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = CardDarkTier1,
                border = BorderStroke(1.dp, BorderSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "ಬಡ್ಡಿ ಚಿಟ್ಟಿ ಲೆಡ್ಜರ್ • Senior Member Trust Fund",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(text = "LENDING CYCLE (ಸಾಲದ ಅವಧಿ)", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                            Text(text = "October 2025", style = MaterialTheme.typography.titleMedium, color = TextPrimary, fontWeight = FontWeight.Bold)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "DUE DATE (ಅಂತಿಮ ದಿನ)", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                            Text(text = "Nov 15, 2025", style = MaterialTheme.typography.titleMedium, color = RulesGold, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Senior Squad Quota (ಗರಿಷ್ಠ 15 ಸಾಲಗಳು)", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                        Text(text = "7 / 15 Active Borrowers", style = MaterialTheme.typography.labelSmall, color = PitchGreen, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    LinearProgressIndicator(
                        progress = { 7f / 15f },
                        modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                        color = PitchGreen,
                        trackColor = CardDarkTier3
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "46.6% Utilized", style = MaterialTheme.typography.labelSmall, color = TextSecondary, fontSize = 10.sp)
                        Text(text = "8 Slots Available", style = MaterialTheme.typography.labelSmall, color = PitchGreen, fontSize = 10.sp)
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        ChittyMetricBox("Total Issued", "₹35,000", "Fixed 10% rate", PitchGreen)
                        ChittyMetricBox("Collected (ಪಾವತಿ)", "₹24,200", "4 Accounts Cleared", PitchGreen)
                        ChittyMetricBox("Pending (ಬಾಕಿ)", "₹18,150", "3 Active Loans", RulesGold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Section 1: Borrowers List
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "1. Borrowers List (ಸಾಲ ಪಡೆದವರು - ${borrowers.size})",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "Sorted by timestamp • Earliest loan issued first", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                }
                Button(
                    onClick = { viewModel.openLendLoanDialog() },
                    colors = ButtonDefaults.buttonColors(containerColor = StatusPurple),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                    modifier = Modifier.testTag("lend_chitty_loan_button")
                ) {
                    Text(text = "+ Issue Loan", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            borrowers.forEach { b ->
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = CardDarkTier1,
                    border = BorderStroke(1.dp, BorderSubtle),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(text = "${b.name} (${b.playerId})", style = MaterialTheme.typography.titleSmall, color = TextPrimary, fontWeight = FontWeight.Bold)
                                Text(text = "${b.kannadaName} • ${b.squad.labelEn}", style = MaterialTheme.typography.bodySmall, color = PitchGreen)
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(999.dp))
                                    .background(
                                        when (b.status) {
                                            PaymentStatus.PAID -> PitchGreen.copy(alpha = 0.15f)
                                            PaymentStatus.PARTIALLY_PAID -> RulesGold.copy(alpha = 0.15f)
                                            else -> StatusRed.copy(alpha = 0.15f)
                                        }
                                    )
                                    .border(
                                        1.dp,
                                        when (b.status) {
                                            PaymentStatus.PAID -> PitchGreen
                                            PaymentStatus.PARTIALLY_PAID -> RulesGold
                                            else -> StatusRed
                                        },
                                        RoundedCornerShape(999.dp)
                                    )
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = when (b.status) {
                                        PaymentStatus.PAID -> "Paid (ಪಾವತಿಸಲಾಗಿದೆ)"
                                        PaymentStatus.PARTIALLY_PAID -> "Partially Paid (ಭಾಗಶಃ)"
                                        else -> "Pending (ಬಾಕಿ)"
                                    },
                                    style = MaterialTheme.typography.labelSmall,
                                    color = when (b.status) {
                                        PaymentStatus.PAID -> PitchGreen
                                        PaymentStatus.PARTIALLY_PAID -> RulesGold
                                        else -> StatusRed
                                    },
                                    fontSize = 10.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text(text = "Principal & Rate", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                                Text(text = "₹${b.principal} @ ${b.ratePercent}%", style = MaterialTheme.typography.bodyMedium, color = TextPrimary, fontWeight = FontWeight.Bold)
                            }
                            Column {
                                Text(text = "Interest Amount", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                                Text(text = "+₹${b.interestAmount}", style = MaterialTheme.typography.bodyMedium, color = PitchGreen, fontWeight = FontWeight.Bold)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text(text = "Remaining Due", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                                Text(
                                    text = "₹${b.remainingDue}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = if (b.remainingDue == 0) PitchGreen else RulesGold,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        if (b.remainingDue > 0) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                                Button(
                                    onClick = { viewModel.openRepaymentDialog(b) },
                                    colors = ButtonDefaults.buttonColors(containerColor = PitchGreen),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                    modifier = Modifier.height(30.dp).testTag("collect_repayment_${b.id}")
                                ) {
                                    Text("Collect Repayment", color = OnPitchGreen, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Section 2: Paid Repayments
            Text(
                text = "2. Paid Repayments (ಮರುಪಾವತಿಸಿದವರು - ${repayments.size})",
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary,
                fontWeight = FontWeight.Bold
            )
            Text(text = "Total Collected: ₹24,200 • Earliest settled first", style = MaterialTheme.typography.labelSmall, color = PitchGreen)

            Spacer(modifier = Modifier.height(10.dp))

            repayments.forEach { rep ->
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = CardDarkTier1,
                    border = BorderStroke(1.dp, BorderSubtle),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(PitchGreen.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = PitchGreen, modifier = Modifier.size(20.dp))
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(text = rep.borrowerName, style = MaterialTheme.typography.titleSmall, color = TextPrimary, fontWeight = FontWeight.Bold)
                                Text(text = "${rep.paymentChannel} • ${rep.date}", style = MaterialTheme.typography.bodySmall, color = TextSecondary, fontSize = 10.sp)
                                Text(text = rep.verifiedNote, style = MaterialTheme.typography.labelSmall, color = PitchGreen, fontSize = 9.sp)
                            }
                        }
                        Text(text = "+₹${rep.amount}", style = MaterialTheme.typography.titleMedium, color = PitchGreen, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Section 3: Pending Repayments
            val pendingBorrowers = borrowers.filter { it.status != PaymentStatus.PAID }
            Text(
                text = "3. Pending Repayments (ಬಾಕಿ ಇರುವವರು - ${pendingBorrowers.size})",
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary,
                fontWeight = FontWeight.Bold
            )
            Text(text = "Total Outstanding: ₹${pendingBorrowers.sumOf { it.remainingDue }}", style = MaterialTheme.typography.labelSmall, color = RulesGold)

            Spacer(modifier = Modifier.height(10.dp))

            pendingBorrowers.forEach { pen ->
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = CardDarkTier1,
                    border = BorderStroke(1.dp, BorderSubtle),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "${pen.name} (${pen.playerId})", style = MaterialTheme.typography.titleSmall, color = TextPrimary, fontWeight = FontWeight.Bold)
                            Text(text = "Due ${pen.dueDate}", style = MaterialTheme.typography.bodySmall, color = RulesGold)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(text = "₹${pen.remainingDue}", style = MaterialTheme.typography.titleMedium, color = RulesGold, fontWeight = FontWeight.Bold)
                            Button(
                                onClick = { viewModel.openRepaymentDialog(pen) },
                                colors = ButtonDefaults.buttonColors(containerColor = RulesGold),
                                shape = RoundedCornerShape(6.dp),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                modifier = Modifier.height(28.dp).testTag("collect_repayment_pending_${pen.id}")
                            ) {
                                Text("Collect", color = Color.Black, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Bylaws notice
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = CardDarkTier2,
                border = BorderStroke(1.dp, BorderSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(text = "📢 TOURNAMENT BYLAWS & NOTICE", style = MaterialTheme.typography.labelSmall, color = RulesGold, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Notice: The 15th of every month is the Buddy Chitty collection date. Junior players are strictly restricted from borrowing. All mutual loans require approval from 2 Senior Squad guarantors.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextPrimary,
                        fontSize = 11.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ChittyMetricBox(label: String, value: String, subtext: String, valueColor: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(CardDarkTier2)
            .padding(8.dp)
    ) {
        Column {
            Text(text = label, style = MaterialTheme.typography.labelSmall, color = TextSecondary, fontSize = 10.sp)
            Text(text = value, style = MaterialTheme.typography.titleMedium, color = valueColor, fontWeight = FontWeight.Bold)
            Text(text = subtext, style = MaterialTheme.typography.labelSmall, color = TextSecondary, fontSize = 9.sp)
        }
    }
}

// ==========================================
// 5. TOTAL COLLECTION & REPORTS (Image_25_f8e47d54)
// ==========================================
@Composable
fun TotalCollectionReportsScreen(
    viewModel: KmplViewModel,
    language: Language
) {
    Scaffold(
        containerColor = SurfaceDark,
        topBar = {
            KmplTopBar(
                title = if (language == Language.KN) "ಒಟ್ಟು ಸಂಗ್ರಹ ವರದಿಗಳು" else "Total Collection & Reports",
                subtitle = "TRANSACTION RECEIPT & AUDIT",
                language = language,
                onToggleLanguage = { viewModel.toggleLanguage() },
                showBackButton = true,
                onBackClick = { viewModel.navigateBack() },
                statusBadgeText = "AUDIT PASS"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Total Treasury Circulation Banner
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = CardDarkTier1,
                border = BorderStroke(1.dp, PitchGreen.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "TOTAL TREASURY CIRCULATION (ಒಟ್ಟು ನಿಧಿ ಚಲಾವಣೆ)", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "₹1,53,400", style = MaterialTheme.typography.headlineLarge, color = PitchGreen, fontWeight = FontWeight.ExtraBold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "✅ AUDIT PASS • No disputes logged across 12 Grama wards", style = MaterialTheme.typography.labelSmall, color = PitchGreen)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 1. Monthly Fund Collection
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = CardDarkTier1,
                border = BorderStroke(1.dp, BorderSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "1. Monthly Fund Collection", style = MaterialTheme.typography.titleMedium, color = TextPrimary, fontWeight = FontWeight.Bold)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(999.dp))
                                .background(PitchGreen.copy(alpha = 0.15f))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(text = "Active Cycle", style = MaterialTheme.typography.labelSmall, color = PitchGreen)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        ReportMiniStat("ELIGIBLE", "68", "ಆಟಗಾರರು")
                        ReportMiniStat("PAID", "52", "ಪಾವತಿಸಿದವರು", PitchGreen)
                        ReportMiniStat("PENDING", "16", "ಬಾಕಿ", RulesGold)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    ReceiptRow(label = "Expected Target (ಗುರಿ)", value = "₹11,200")
                    ReceiptRow(label = "Collected (ಸಂಗ್ರಹ)", value = "+₹8,400")
                    ReceiptRow(label = "Outstanding (ಬಾಕಿ)", value = "₹2,800")

                    Spacer(modifier = Modifier.height(10.dp))

                    LinearProgressIndicator(
                        progress = { 0.75f },
                        modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                        color = PitchGreen,
                        trackColor = CardDarkTier3
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "₹41,200 Total Collected across 4 months (4 ತಿಂಗಳುಗಳ ಒಟ್ಟು ಮಾಸಿಕ ನಿಧಿ ಮೊತ್ತ)",
                        style = MaterialTheme.typography.bodySmall,
                        color = PitchGreen
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Buddy Chitty Monthly Cycle
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = CardDarkTier1,
                border = BorderStroke(1.dp, BorderSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "2. Buddy Chitty Monthly Cycle", style = MaterialTheme.typography.titleMedium, color = TextPrimary, fontWeight = FontWeight.Bold)
                        Text(text = "Cycle #4", style = MaterialTheme.typography.labelSmall, color = RulesGold)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            Text(text = "LOANS ISSUED", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                            Text(text = "7 Members", style = MaterialTheme.typography.titleSmall, color = TextPrimary, fontWeight = FontWeight.Bold)
                            Text(text = "₹38,500 Lent (ಅಸಲು)", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "COLLECTED TO DATE", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                            Text(text = "₹24,200", style = MaterialTheme.typography.titleSmall, color = PitchGreen, fontWeight = FontWeight.Bold)
                            Text(text = "₹18,150 Outstanding", style = MaterialTheme.typography.bodySmall, color = RulesGold)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(text = "3-MONTH VELOCITY TRENDS", style = MaterialTheme.typography.labelSmall, color = TextSecondary, fontWeight = FontWeight.Bold)

                    Spacer(modifier = Modifier.height(6.dp))

                    VelocityRow(month = "Sep 2025 (Settled)", ratio = "₹35,000 / ₹35,000", pct = 1.0f)
                    VelocityRow(month = "Oct 2025 (Current)", ratio = "₹24,200 / ₹42,350", pct = 0.57f)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 3. Cumulative Collections to Date
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = CardDarkTier1,
                border = BorderStroke(1.dp, BorderSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "3. Cumulative Collections to Date (ಇಲ್ಲಿಯವರೆಗಿನ ಒಟ್ಟು ಸಂಗ್ರಹ)",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            Text(text = "TOTAL PRINCIPAL LENT", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                            Text(text = "₹1,24,000", style = MaterialTheme.typography.titleSmall, color = TextPrimary, fontWeight = FontWeight.Bold)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "PRINCIPAL REPAID", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                            Text(text = "₹1,02,000", style = MaterialTheme.typography.titleSmall, color = PitchGreen, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            Text(text = "TOTAL INTEREST", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                            Text(text = "+₹10,200", style = MaterialTheme.typography.titleSmall, color = PitchGreen, fontWeight = FontWeight.Bold)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "TOTAL REPAYMENTS", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                            Text(text = "₹1,12,200", style = MaterialTheme.typography.titleSmall, color = TextPrimary, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(CardDarkTier2)
                            .padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "🔒", fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(text = "VAULT RESERVE (ಕ್ಲಬ್ ನಿಧಿ)", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                                    Text(text = "₹18,500 Available", style = MaterialTheme.typography.titleSmall, color = PitchGreen, fontWeight = FontWeight.Bold)
                                }
                            }
                            Text(text = "Ready for Nov Cycle", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Section 8.4: Share Report Button
            val context = LocalContext.current
            Button(
                onClick = {
                    val reportText = viewModel.getShareableReport()
                    val sendIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, reportText)
                        putExtra(Intent.EXTRA_SUBJECT, "KMPL Fund Official Report — October 2025")
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, "Share KMPL Collection Report")
                    context.startActivity(shareIntent)
                },
                colors = ButtonDefaults.buttonColors(containerColor = PitchGreen),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .shadow(8.dp, RoundedCornerShape(12.dp), spotColor = PitchGreen.copy(alpha = 0.4f))
                    .testTag("share_report_button")
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = null, tint = OnPitchGreen)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (language == Language.KN) "ವರದಿಯನ್ನು ಹಂಚಿಕೊಳ್ಳಿ (Share Report)" else "Share Official Collection Report",
                        color = OnPitchGreen,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "🛡️ 100% Transparent Grassroots Book • ಗ್ರಾಮೀಣ ಕ್ರೀಡಾ ಸಮಿತಿ ನೇರ ಡಿಜಿಟಲ್ ಲೆಕ್ಕ ಪುಸ್ತಕ",
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondary.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ReportMiniStat(label: String, count: String, subtext: String, countColor: Color = TextPrimary) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = TextSecondary, fontSize = 9.sp)
        Text(text = count, style = MaterialTheme.typography.titleLarge, color = countColor, fontWeight = FontWeight.Bold)
        Text(text = subtext, style = MaterialTheme.typography.labelSmall, color = TextSecondary, fontSize = 9.sp)
    }
}

@Composable
private fun VelocityRow(month: String, ratio: String, pct: Float) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = month, style = MaterialTheme.typography.labelSmall, color = TextPrimary)
            Text(text = ratio, style = MaterialTheme.typography.labelSmall, color = PitchGreen)
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { pct },
            modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(2.dp)),
            color = PitchGreen,
            trackColor = CardDarkTier3
        )
    }
}

@Composable
private fun ReceiptRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodySmall, color = TextSecondary)
        Text(text = value, style = MaterialTheme.typography.bodySmall, color = TextPrimary, fontWeight = FontWeight.SemiBold)
    }
}
