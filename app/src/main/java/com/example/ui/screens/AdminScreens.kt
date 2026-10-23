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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.*
import com.example.ui.components.KmplTopBar
import com.example.ui.theme.*
import com.example.viewmodel.KmplViewModel
import com.example.viewmodel.Screen

// ==========================================
// 1. ADMIN GATEWAY LOGIN (Image_5_d9a7b73d)
// ==========================================
@Composable
fun AdminLoginScreen(
    viewModel: KmplViewModel,
    language: Language
) {
    var adminIdentifier by remember { mutableStateOf("7760880282") }
    var adminEmail by remember { mutableStateOf("sudeepaml63@gmail.com") }
    var password by remember { mutableStateOf("••••••••••••") }
    var passwordVisible by remember { mutableStateOf(false) }
    var sessionKeepAlive by remember { mutableStateOf(true) }

    Scaffold(
        containerColor = SurfaceDark,
        topBar = {
            KmplTopBar(
                title = if (language == Language.KN) "ನಿರ್ವಾಹಕರ ಗೇಟ್‌ವೇ" else "KMPL Admin Gateway",
                subtitle = "SECURITY VERIFICATION • 2FA ACTIVE",
                language = language,
                onToggleLanguage = { viewModel.toggleLanguage() },
                showBackButton = true,
                onBackClick = { viewModel.navigateBack() },
                statusBadgeText = "ZONE A-1"
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
            // Unusual Login Security Banner
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = CardDarkTier1,
                border = BorderStroke(1.dp, RulesGold.copy(alpha = 0.8f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(RulesGold.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🛡️", fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = if (language == Language.KN) "ಅಸಾಮಾನ್ಯ ಲಾಗಿನ್ ರಕ್ಷಣೆ (2FA)" else "Unusual Login Security Verification",
                                style = MaterialTheme.typography.labelSmall,
                                color = RulesGold,
                                fontWeight = FontWeight.Bold
                            )
                            Text(text = "ACTIVE", style = MaterialTheme.typography.labelSmall, color = PitchGreen, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (language == Language.KN)
                                "ಹೆಚ್ಚುವರಿ ಭದ್ರತಾ ದೃಢೀಕರಣಕ್ಕಾಗಿ ಅಧಿಕೃತ ಇಮೇಲ್ ವಿಳಾಸ (Email ID) ಕಡ್ಡಾಯವಾಗಿದೆ."
                            else
                                "Enhanced security active: Admin Email ID is strictly required to verify unusual login identity.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextPrimary,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Quick Fill Super Admin Shortcut Card
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = CardDarkTier2,
                border = BorderStroke(1.dp, PitchGreen.copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "👑", fontSize = 14.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "Super Admin Profile", style = MaterialTheme.typography.labelSmall, color = PitchGreen, fontWeight = FontWeight.Bold)
                        }
                        Text(text = "Sudeep A M L • 7760880282", style = MaterialTheme.typography.bodySmall, color = TextPrimary, fontSize = 11.sp)
                        Text(text = "sudeepaml63@gmail.com", style = MaterialTheme.typography.bodySmall, color = TextSecondary, fontSize = 10.sp)
                    }

                    Button(
                        onClick = {
                            adminEmail = "sudeepaml63@gmail.com"
                            adminIdentifier = "7760880282"
                            password = "••••••••••••"
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PitchGreen),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        modifier = Modifier.height(34.dp).testTag("quick_fill_sudeep_button")
                    ) {
                        Text(text = "Autofill", color = OnPitchGreen, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Direct Credential Auth Card
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Key, contentDescription = null, tint = PitchGreen, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Admin Credentials & 2FA", style = MaterialTheme.typography.titleMedium, color = TextPrimary, fontWeight = FontWeight.Bold)
                        }
                        Text(text = "NODE #092", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(text = "ADMIN EMAIL ADDRESS * (ಅಧಿಕೃತ ಇಮೇಲ್)", style = MaterialTheme.typography.labelSmall, color = RulesGold, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = adminEmail,
                        onValueChange = { adminEmail = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("admin_email_input"),
                        leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null, tint = RulesGold) },
                        placeholder = { Text("e.g. sudeepaml63@gmail.com", color = TextSecondary, fontSize = 12.sp) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = RulesGold,
                            unfocusedBorderColor = BorderSubtle,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(text = "ADMIN PHONE / IDENTIFIER * (ಖಾತೆ ಅಥವಾ ಮೊಬೈಲ್)", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = adminIdentifier,
                        onValueChange = { adminIdentifier = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("admin_identifier_input"),
                        leadingIcon = { Icon(imageVector = Icons.Default.Badge, contentDescription = null, tint = PitchGreen) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PitchGreen,
                            unfocusedBorderColor = BorderSubtle,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "ಖಾತೆ ಸಂಖ್ಯೆ ಅಥವಾ ನೋಂದಾಯಿತ ಮೊಬೈಲ್ ಸಂಖ್ಯೆ (ಉದಾ: 7760880282)", style = MaterialTheme.typography.bodySmall, color = PitchGreen, fontSize = 10.sp)

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(text = "MASTER PASSWORD *", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("admin_password_input"),
                        leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = PitchGreen) },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = "Toggle password",
                                    tint = TextSecondary
                                )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PitchGreen,
                            unfocusedBorderColor = BorderSubtle,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "🔒 Argon2id / bcrypt salted server-side verification", style = MaterialTheme.typography.bodySmall, color = TextSecondary, fontSize = 10.sp)

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = sessionKeepAlive,
                                onCheckedChange = { sessionKeepAlive = it },
                                colors = CheckboxDefaults.colors(checkedColor = PitchGreen)
                            )
                            Text(text = "Session Keep-Alive", style = MaterialTheme.typography.bodySmall, color = TextPrimary)
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(CardDarkTier2)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(text = "15m Timeout", style = MaterialTheme.typography.labelSmall, color = RulesGold, fontSize = 10.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { viewModel.loginAdmin(adminIdentifier, password, adminEmail) },
                        colors = ButtonDefaults.buttonColors(containerColor = PitchGreen),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .shadow(8.dp, RoundedCornerShape(14.dp), spotColor = PitchGreen.copy(alpha = 0.5f))
                            .testTag("authorize_admin_button")
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.VerifiedUser, contentDescription = null, tint = OnPitchGreen)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "AUTHORIZE ADMIN ACCESS",
                                style = MaterialTheme.typography.labelLarge,
                                color = OnPitchGreen,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Player Portal Preview Quick Card
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
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(PortalPink.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.SportsCricket, contentDescription = null, tint = PortalPink)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(text = "Player Portal Preview", style = MaterialTheme.typography.titleSmall, color = TextPrimary, fontWeight = FontWeight.Bold)
                            Text(text = "VIEW ONLY • Test live passbook", style = MaterialTheme.typography.bodySmall, color = TextSecondary, fontSize = 10.sp)
                        }
                    }

                    Button(
                        onClick = { viewModel.navigateTo(Screen.PLAYER_PORTAL_DASHBOARD) },
                        colors = ButtonDefaults.buttonColors(containerColor = CardDarkTier2),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.testTag("launch_preview_button")
                    ) {
                        Text(text = "LAUNCH 👁️", color = TextPrimary, style = MaterialTheme.typography.labelSmall)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Cryptographic Audit Logging
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = CardDarkTier1,
                border = BorderStroke(1.dp, BorderSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(text = "📡 CRYPTOGRAPHIC AUDIT LOGGING ACTIVE", style = MaterialTheme.typography.labelSmall, color = PitchGreen, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "All authorization timestamps, device fingerprint hashes, and ledger adjustments are sequentially recorded to a tamper-proof audit trail for tournament accountability.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Log Ref: #KMPL-AUDIT-2025-V4", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                        Text(text = "INTEGRITY 100%", style = MaterialTheme.typography.labelSmall, color = PitchGreen, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Administrative Scopes List
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Administrative Scopes", style = MaterialTheme.typography.titleMedium, color = TextPrimary, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                ScopeItem("Financial Ledger Adjustments", "ಹಣಕಾಸು ನಿರ್ವಹಣೆ & ಆಡಿಟ್ ಲಾಗ್", "L1 Scope")
                ScopeItem("Member & Player Records", "ಆಟಗಾರರ ನೋಂದಣಿ & ತಂಡದ ವಿವರ", "Full RW")
                ScopeItem("Rules & Fixtures Publisher", "ನಿಯಮಾವಳಿ ಹಾಗೂ ಪಂದ್ಯಾವಳಿ ವೇಳಾಪಟ್ಟಿ", "Publish")
                ScopeItem("Tournament Operations", "ಅಂಪೈರ್ ಮಂಡಳಿ ಮತ್ತು ಫಲಿತಾಂಶ", "SuperOp")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "⚡ KMPL Cricket Association • Digital Security Grid\nKarnataka Mandal Premier League Governance Portal",
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
private fun ScopeItem(title: String, subtitle: String, scopeBadge: String) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = CardDarkTier1,
        border = BorderStroke(1.dp, BorderSubtle),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = title, style = MaterialTheme.typography.bodyMedium, color = TextPrimary, fontWeight = FontWeight.SemiBold)
                Text(text = subtitle, style = MaterialTheme.typography.bodySmall, color = TextSecondary, fontSize = 10.sp)
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(PitchGreen.copy(alpha = 0.15f))
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text(text = scopeBadge, style = MaterialTheme.typography.labelSmall, color = PitchGreen, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// ==========================================
// 2. ADMIN DASHBOARD (Image_15_37a7711d & Image_11_78075c6e)
// ==========================================
@Composable
fun AdminDashboardScreen(
    viewModel: KmplViewModel,
    language: Language
) {
    val fundSummary = viewModel.getFundSummary()
    val auditLogs by viewModel.auditLogs.collectAsState()
    val players by viewModel.players.collectAsState()
    var searchPendingQuery by remember { mutableStateOf("") }
    var listFilter by remember { mutableStateOf("PENDING") } // "PAID", "PENDING"

    Scaffold(
        containerColor = SurfaceDark,
        topBar = {
            KmplTopBar(
                title = "KMPL ADMIN",
                subtitle = "Fund & Operations",
                language = language,
                onToggleLanguage = { viewModel.toggleLanguage() },
                showBackButton = true,
                onBackClick = { viewModel.logoutAdmin() },
                statusBadgeText = "ಶಿವಕುಮಾರ್ (Shivakumar)"
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
            // Month Cycle Bar
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = CardDarkTier1,
                border = BorderStroke(1.dp, BorderSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {}, modifier = Modifier.size(28.dp)) {
                        Icon(imageVector = Icons.Default.ChevronLeft, contentDescription = null, tint = TextSecondary)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "October 2025", style = MaterialTheme.typography.titleMedium, color = TextPrimary, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(PitchGreen)
                                    .padding(horizontal = 6.dp, vertical = 1.dp)
                            ) {
                                Text(text = "ಚಾಲ್ತಿ", style = MaterialTheme.typography.labelSmall, color = OnPitchGreen, fontSize = 9.sp)
                            }
                        }
                        Text(text = "Due: 15th Oct • ೧೫ನೇ ತಾರೀಕು ನಿಯಮಿತ ದಿನ", style = MaterialTheme.typography.labelSmall, color = RulesGold)
                    }
                    IconButton(onClick = {}, modifier = Modifier.size(28.dp)) {
                        Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = TextSecondary)
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 4 Admin Cards Grid (Player Mgmt, Monthly Fund, Baddi Chitty, Rules & Sched)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                AdminQuickTile(
                    title = "Player Mgmt",
                    kannada = "ಆಟಗಾರರ ನಿರ್ವಹಣೆ",
                    badge = "${players.size} Squad",
                    backgroundColor = StatusOrange,
                    onClick = { viewModel.navigateTo(Screen.ADMIN_PLAYERS_SUITE) },
                    modifier = Modifier.weight(1f)
                )
                AdminQuickTile(
                    title = "Monthly Fund",
                    kannada = "ಮಾಸಿಕ ನಿಧಿ",
                    badge = "₹8.4k / 75%",
                    backgroundColor = PitchGreenDeep,
                    onClick = { viewModel.navigateTo(Screen.ADMIN_TRANSACTION_DETAIL) },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                AdminQuickTile(
                    title = "Baddi Chitty",
                    kannada = "ಬಡ್ಡಿ ಚಿಟ್ಟಿ",
                    badge = "7/15 Loans",
                    backgroundColor = StatusPurple,
                    onClick = { viewModel.navigateTo(Screen.BUDDY_CHITTY_LEDGER) },
                    modifier = Modifier.weight(1f)
                )
                AdminQuickTile(
                    title = "Rules & Sched",
                    kannada = "ನಿಯಮಾವಳಿಗಳು",
                    badge = "15th Cycle",
                    backgroundColor = StatusBlue,
                    onClick = { viewModel.navigateTo(Screen.RULES) },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Monthly Fund Progress Card
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
                            Text(text = "Monthly Fund Progress", style = MaterialTheme.typography.titleMedium, color = TextPrimary, fontWeight = FontWeight.Bold)
                            Text(text = "ಮಾಸಿಕ ನಿಧಿ ಸಂಗ್ರಹ ಪ್ರಗತಿ (Junior ₹100 / Senior ₹200)", style = MaterialTheme.typography.bodySmall, color = TextSecondary, fontSize = 11.sp)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "75%", style = MaterialTheme.typography.headlineMedium, color = PitchGreen, fontWeight = FontWeight.ExtraBold)
                            Text(text = "15th Oct Deadline", style = MaterialTheme.typography.labelSmall, color = PitchGreen, fontSize = 10.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            Text(text = "Collected (ಸಂಗ್ರಹ)", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                            Text(text = "₹${fundSummary.collectedAmount}", style = MaterialTheme.typography.titleLarge, color = PitchGreen, fontWeight = FontWeight.Bold)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "Target (ಗುರಿ)", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                            Text(text = "₹${fundSummary.expectedTarget}", style = MaterialTheme.typography.titleLarge, color = TextPrimary, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    LinearProgressIndicator(
                        progress = { 0.75f },
                        modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
                        color = PitchGreen,
                        trackColor = CardDarkTier3
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "● 52 ಪಾವತಿಸಲಾಗಿದೆ (Paid)", style = MaterialTheme.typography.labelSmall, color = PitchGreen)
                        Text(text = "● 16 ಬಾಕಿ (Pending)", style = MaterialTheme.typography.labelSmall, color = RulesGold)
                        Text(text = "68 ಒಟ್ಟು", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Three Interactive Actions: Collect Fee, Paid (52), Pending (16)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { viewModel.openPaymentDialog(null) },
                            colors = ButtonDefaults.buttonColors(containerColor = StatusBlue),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                                .testTag("admin_collect_fee_button")
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "Collect Fee", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                                Text(text = "ಹಣ ಸ್ವೀಕರಿಸಿ", style = MaterialTheme.typography.labelSmall, fontSize = 8.sp)
                            }
                        }

                        Button(
                            onClick = { listFilter = "PAID" },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (listFilter == "PAID") PitchGreenDeep else CardDarkTier2
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                                .testTag("admin_paid_list_button")
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "Paid (${fundSummary.paidCount})", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                                Text(text = "ಪಾವತಿಸಿದವರು", style = MaterialTheme.typography.labelSmall, fontSize = 8.sp)
                            }
                        }

                        Button(
                            onClick = { listFilter = "PENDING" },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (listFilter == "PENDING") RulesGold else CardDarkTier2
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                                .testTag("admin_pending_list_button")
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "Pending (${fundSummary.pendingCount})",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = if (listFilter == "PENDING") Color.Black else TextPrimary
                                )
                                Text(
                                    text = "ಬಾಕಿ ಇರುವವರು",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontSize = 8.sp,
                                    color = if (listFilter == "PENDING") Color.Black else TextSecondary
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Pending / Paid Roster Live List
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = CardDarkTier1,
                border = BorderStroke(1.dp, BorderSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = if (listFilter == "PENDING") "Pending Roster (ಆಟಗಾರರ ಬಾಕಿ ಪಟ್ಟಿ)" else "Paid Roster (ಪಾವತಿಸಿದವರ ಪಟ್ಟಿ)",
                        style = MaterialTheme.typography.titleSmall,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = searchPendingQuery,
                        onValueChange = { searchPendingQuery = it },
                        modifier = Modifier.fillMaxWidth().testTag("admin_roster_search_input"),
                        placeholder = { Text("Search by name or ID (e.g. Girish, KMPL-012)...", fontSize = 11.sp, color = TextSecondary) },
                        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = TextSecondary) },
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PitchGreen,
                            unfocusedBorderColor = BorderSubtle,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    val displayList = players.filter { p ->
                        val matchesStatus = if (listFilter == "PENDING") !p.isPaidThisMonth else p.isPaidThisMonth
                        val matchesQuery = p.name.contains(searchPendingQuery, ignoreCase = true) ||
                                p.kannadaName.contains(searchPendingQuery, ignoreCase = true) ||
                                p.id.contains(searchPendingQuery, ignoreCase = true)
                        matchesStatus && matchesQuery
                    }

                    if (displayList.isEmpty()) {
                        Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
                            Text(text = "No players in this filter", color = TextSecondary)
                        }
                    } else {
                        displayList.take(6).forEach { player ->
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = CardDarkTier2,
                                border = BorderStroke(1.dp, BorderSubtle),
                                modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(34.dp)
                                                .clip(RoundedCornerShape(6.dp))
                                                .background(if (player.isPaidThisMonth) PitchGreen.copy(alpha = 0.2f) else RulesGold.copy(alpha = 0.2f)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = player.name.take(2).uppercase(),
                                                style = MaterialTheme.typography.labelSmall,
                                                color = if (player.isPaidThisMonth) PitchGreen else RulesGold,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Column {
                                            Text(text = "${player.name} (${player.id})", style = MaterialTheme.typography.titleSmall, color = TextPrimary, fontWeight = FontWeight.Bold)
                                            Text(text = "${player.phone} • ${player.squad.labelEn}", style = MaterialTheme.typography.bodySmall, color = TextSecondary, fontSize = 10.sp)
                                        }
                                    }

                                    Column(horizontalAlignment = Alignment.End) {
                                        Text(text = "₹${player.squad.fee}", style = MaterialTheme.typography.titleMedium, color = if (player.isPaidThisMonth) PitchGreen else RulesGold, fontWeight = FontWeight.Bold)
                                        if (!player.isPaidThisMonth) {
                                            Button(
                                                onClick = { viewModel.openPaymentDialog(player) },
                                                colors = ButtonDefaults.buttonColors(containerColor = PitchGreen),
                                                shape = RoundedCornerShape(6.dp),
                                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                                modifier = Modifier.height(28.dp).testTag("quick_collect_${player.id}")
                                            ) {
                                                Text(text = "Collect", color = OnPitchGreen, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                            }
                                        } else {
                                            Text(text = "Cleared", color = PitchGreen, fontSize = 10.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Audit Log Feed
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = CardDarkTier1,
                border = BorderStroke(1.dp, BorderSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Audit Log (ಲೆಕ್ಕಪರಿಶೋಧನೆ ಲಾಗ್)", style = MaterialTheme.typography.titleMedium, color = TextPrimary, fontWeight = FontWeight.Bold)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(999.dp))
                                .background(CardDarkTier2)
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(text = "ಇತ್ತೀಚಿನ ನವೀಕರಣ", style = MaterialTheme.typography.labelSmall, color = PitchGreen, fontSize = 9.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    auditLogs.take(4).forEach { log ->
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = CardDarkTier2,
                            border = BorderStroke(1.dp, BorderSubtle),
                            modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = when (log.type) {
                                            "PAYMENT" -> Icons.Default.QrCode
                                            "ADMIN" -> Icons.Default.Shield
                                            else -> Icons.Default.Warning
                                        },
                                        contentDescription = null,
                                        tint = when (log.type) {
                                            "PAYMENT" -> PitchGreen
                                            "ADMIN" -> StatusPurple
                                            else -> RulesGold
                                        },
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(text = log.title, style = MaterialTheme.typography.bodyMedium, color = TextPrimary, fontWeight = FontWeight.SemiBold)
                                        Text(text = log.details, style = MaterialTheme.typography.bodySmall, color = TextSecondary, fontSize = 10.sp)
                                    }
                                }

                                if (log.amountTag != null) {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(CardDarkTier3)
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(text = log.amountTag, style = MaterialTheme.typography.labelSmall, color = PitchGreen, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "🔒 100% Encrypted KMPL Grassroots Database",
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
private fun AdminQuickTile(
    title: String,
    kannada: String,
    badge: String,
    backgroundColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        color = backgroundColor,
        modifier = modifier.height(106.dp)
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
                Icon(imageVector = Icons.Default.Group, contentDescription = null, tint = Color.White, modifier = Modifier.size(22.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(Color.Black.copy(alpha = 0.25f))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(text = badge, style = MaterialTheme.typography.labelSmall, color = Color.White, fontWeight = FontWeight.Bold)
                }
            }

            Column {
                Text(text = title, style = MaterialTheme.typography.titleSmall, color = Color.White, fontWeight = FontWeight.Bold)
                Text(text = kannada, style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.85f), fontSize = 10.sp)
            }
        }
    }
}

// ==========================================
// 3. ADMIN PLAYERS SUITE (Image_9_fc3cb96f)
// ==========================================
@Composable
fun AdminPlayersSuiteScreen(
    viewModel: KmplViewModel,
    language: Language
) {
    val players by viewModel.players.collectAsState()
    val admins by viewModel.admins.collectAsState()
    var searchSuiteQuery by remember { mutableStateOf("") }
    var selectedSuiteTab by remember { mutableStateOf("ALL") } // "ALL", "ADD", "ADMINS"
    var adminToRemove by remember { mutableStateOf<AdminUser?>(null) }
    val context = LocalContext.current

    val seniors = players.filter { it.squad == SquadType.SENIOR }
    val juniors = players.filter { it.squad == SquadType.JUNIOR }

    Scaffold(
        containerColor = SurfaceDark,
        topBar = {
            KmplTopBar(
                title = if (language == Language.KN) "ಆಟಗಾರರ ಸೂಟ್" else "Players Suite",
                subtitle = "KMPL ADMIN • Fund & Operations",
                language = language,
                onToggleLanguage = { viewModel.toggleLanguage() },
                showBackButton = true,
                onBackClick = { viewModel.navigateBack() }
            )
        },
        bottomBar = {
            Surface(
                color = CardDarkTier1,
                border = BorderStroke(1.dp, BorderSubtle),
                modifier = Modifier.fillMaxWidth().navigationBarsPadding()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Verified, contentDescription = null, tint = PitchGreen, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(text = "Directory fully synchronized", style = MaterialTheme.typography.labelSmall, color = TextPrimary, fontWeight = FontWeight.Bold)
                            Text(text = "${players.size} squad players enrolled", style = MaterialTheme.typography.bodySmall, color = TextSecondary, fontSize = 10.sp)
                        }
                    }

                    Button(
                        onClick = { viewModel.openAddPlayerDialog() },
                        colors = ButtonDefaults.buttonColors(containerColor = StatusOrange),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("admin_register_new_fab")
                    ) {
                        Text(text = "+ Register New", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // 3 Tabs: All Players, Add Player, Admins
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { selectedSuiteTab = "ALL" },
                    colors = ButtonDefaults.buttonColors(containerColor = if (selectedSuiteTab == "ALL") PitchGreenDeep else CardDarkTier2),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f).height(48.dp).testTag("tab_all_players_button")
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "● All Players [${players.size}]", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                        Text(text = "ಎಲ್ಲಾ ಆಟಗಾರರು", style = MaterialTheme.typography.labelSmall, fontSize = 8.sp)
                    }
                }

                Button(
                    onClick = { viewModel.openAddPlayerDialog() },
                    colors = ButtonDefaults.buttonColors(containerColor = CardDarkTier2),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f).height(48.dp).testTag("tab_add_player_button")
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "👤+ Add Player +", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                        Text(text = "ಹೊಸ ಆಟಗಾರ", style = MaterialTheme.typography.labelSmall, fontSize = 8.sp)
                    }
                }

                Button(
                    onClick = { selectedSuiteTab = "ADMINS" },
                    colors = ButtonDefaults.buttonColors(containerColor = if (selectedSuiteTab == "ADMINS") RulesGold else CardDarkTier2),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f).height(48.dp).testTag("tab_admins_button")
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "🛡️ Admins [${admins.size}]",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (selectedSuiteTab == "ADMINS") Color.Black else TextPrimary
                        )
                        Text(
                            text = "ಆಡಳಿತಗಾರರು",
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 8.sp,
                            color = if (selectedSuiteTab == "ADMINS") Color.Black else TextSecondary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            if (selectedSuiteTab == "ADMINS") {
                // Admin Management View
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = CardDarkTier1,
                    border = BorderStroke(1.dp, PitchGreen.copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(text = "COMMITTEE ADMINISTRATORS", style = MaterialTheme.typography.labelSmall, color = TextSecondary, fontWeight = FontWeight.Bold)
                                Text(text = "${admins.size} Authorized Admins", style = MaterialTheme.typography.titleMedium, color = TextPrimary, fontWeight = FontWeight.Bold)
                            }
                            Button(
                                onClick = { viewModel.openAddAdminDialog() },
                                colors = ButtonDefaults.buttonColors(containerColor = PitchGreen),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.testTag("add_admin_button")
                            ) {
                                Text(text = "+ Add Admin", color = OnPitchGreen, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                val filteredAdmins = admins.filter {
                    it.name.contains(searchSuiteQuery, ignoreCase = true) ||
                            it.phone.contains(searchSuiteQuery, ignoreCase = true) ||
                            it.role.contains(searchSuiteQuery, ignoreCase = true)
                }

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(filteredAdmins) { admin ->
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = CardDarkTier1,
                            border = BorderStroke(1.dp, BorderSubtle),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(40.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(PitchGreen.copy(alpha = 0.15f)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(imageVector = Icons.Default.Shield, contentDescription = null, tint = PitchGreen, modifier = Modifier.size(22.dp))
                                        }
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Column {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Text(text = admin.name, style = MaterialTheme.typography.titleSmall, color = TextPrimary, fontWeight = FontWeight.Bold)
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Box(
                                                    modifier = Modifier.clip(RoundedCornerShape(4.dp)).background(CardDarkTier2).padding(horizontal = 4.dp, vertical = 1.dp)
                                                ) {
                                                    Text(text = admin.id, style = MaterialTheme.typography.labelSmall, fontSize = 9.sp, color = RulesGold)
                                                }
                                            }
                                            Text(text = admin.role, style = MaterialTheme.typography.bodySmall, color = PitchGreen, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                                            Text(text = "✉ ${admin.email}", style = MaterialTheme.typography.bodySmall, color = TextSecondary, fontSize = 10.sp)
                                        }
                                    }

                                    if (admin.id == "ADM-01" || admin.email == "sudeepaml63@gmail.com") {
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(999.dp))
                                                .background(RulesGold.copy(alpha = 0.18f))
                                                .border(1.dp, RulesGold, RoundedCornerShape(999.dp))
                                                .padding(horizontal = 8.dp, vertical = 3.dp)
                                        ) {
                                            Text(text = "👑 SUPER ADMIN", style = MaterialTheme.typography.labelSmall, fontSize = 9.sp, color = RulesGold, fontWeight = FontWeight.Bold)
                                        }
                                    } else {
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(999.dp))
                                                .background(CardDarkTier2)
                                                .padding(horizontal = 8.dp, vertical = 3.dp)
                                        ) {
                                            Text(text = "Active", style = MaterialTheme.typography.labelSmall, fontSize = 9.sp, color = PitchGreen)
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = admin.phone, style = MaterialTheme.typography.bodySmall, color = TextSecondary)

                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Button(
                                            onClick = {
                                                val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${admin.phone}"))
                                                context.startActivity(dialIntent)
                                            },
                                            colors = ButtonDefaults.buttonColors(containerColor = CardDarkTier2),
                                            shape = RoundedCornerShape(8.dp),
                                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
                                            modifier = Modifier.height(32.dp).testTag("call_admin_${admin.id}")
                                        ) {
                                            Icon(imageVector = Icons.Default.Call, contentDescription = null, tint = PitchGreen, modifier = Modifier.size(14.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(text = "Call", color = TextPrimary, fontSize = 11.sp)
                                        }

                                        if (admins.size > 1) {
                                            OutlinedButton(
                                                onClick = { adminToRemove = admin },
                                                colors = ButtonDefaults.outlinedButtonColors(contentColor = StatusRed),
                                                border = BorderStroke(1.dp, StatusRed.copy(alpha = 0.5f)),
                                                shape = RoundedCornerShape(8.dp),
                                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
                                                modifier = Modifier.height(32.dp).testTag("remove_admin_${admin.id}")
                                            ) {
                                                Icon(imageVector = Icons.Default.Delete, contentDescription = null, tint = StatusRed, modifier = Modifier.size(14.dp))
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text(text = "Remove", color = StatusRed, fontSize = 11.sp)
                                            }
                                        } else {
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(6.dp))
                                                    .background(CardDarkTier2)
                                                    .padding(horizontal = 8.dp, vertical = 6.dp)
                                            ) {
                                                Text(text = "Primary (Protected)", style = MaterialTheme.typography.labelSmall, color = RulesGold, fontSize = 9.sp)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                // Squad Roster Overview Card
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = CardDarkTier1,
                    border = BorderStroke(1.dp, BorderSubtle),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "SQUAD ROSTER OVERVIEW", style = MaterialTheme.typography.labelSmall, color = TextSecondary, fontWeight = FontWeight.Bold)
                            Box(
                                modifier = Modifier.clip(RoundedCornerShape(999.dp)).background(PitchGreen.copy(alpha = 0.2f)).padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(text = "${players.size} Active", style = MaterialTheme.typography.labelSmall, color = PitchGreen, fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text(text = "Total Squad", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                                Text(text = "${players.size}", style = MaterialTheme.typography.titleMedium, color = TextPrimary, fontWeight = FontWeight.Bold)
                                Text(text = "ಒಟ್ಟು ಆಟಗಾರರು", style = MaterialTheme.typography.labelSmall, color = TextSecondary, fontSize = 9.sp)
                            }
                            Column {
                                Text(text = "Seniors", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                                Text(text = "${seniors.size} Sqr", style = MaterialTheme.typography.titleMedium, color = StatusOrange, fontWeight = FontWeight.Bold)
                                Text(text = "ಹಿರಿಯರು", style = MaterialTheme.typography.labelSmall, color = TextSecondary, fontSize = 9.sp)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text(text = "Juniors", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                                Text(text = "${juniors.size} Sqr", style = MaterialTheme.typography.titleMedium, color = StatusBlue, fontWeight = FontWeight.Bold)
                                Text(text = "ಕಿರಿಯರು", style = MaterialTheme.typography.labelSmall, color = TextSecondary, fontSize = 9.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        val seniorRatio = if (players.isNotEmpty()) seniors.size.toFloat() / players.size else 0.59f
                        LinearProgressIndicator(
                            progress = { seniorRatio },
                            modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                            color = StatusOrange,
                            trackColor = StatusBlue
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "● Senior Squad (${(seniorRatio * 100).toInt()}%)", style = MaterialTheme.typography.labelSmall, color = StatusOrange, fontSize = 10.sp)
                            Text(text = "● Junior Squad (${100 - (seniorRatio * 100).toInt()}%)", style = MaterialTheme.typography.labelSmall, color = StatusBlue, fontSize = 10.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Search input
                OutlinedTextField(
                    value = searchSuiteQuery,
                    onValueChange = { searchSuiteQuery = it },
                    modifier = Modifier.fillMaxWidth().testTag("admin_suite_search_input"),
                    placeholder = { Text("Search by Name, Kannada or ID (KMPL-001)...", style = MaterialTheme.typography.bodySmall, color = TextSecondary) },
                    leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = TextSecondary) },
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PitchGreen,
                        unfocusedBorderColor = BorderSubtle,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                val filteredList = players.filter { p ->
                    p.name.contains(searchSuiteQuery, ignoreCase = true) ||
                            p.kannadaName.contains(searchSuiteQuery, ignoreCase = true) ||
                            p.id.contains(searchSuiteQuery, ignoreCase = true)
                }

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(filteredList) { player ->
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = CardDarkTier1,
                            border = BorderStroke(1.dp, BorderSubtle),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(40.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(
                                                    if (player.squad == SquadType.SENIOR) StatusOrange.copy(alpha = 0.2f) else StatusBlue.copy(alpha = 0.2f)
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = player.name.take(1).uppercase(),
                                                style = MaterialTheme.typography.titleMedium,
                                                color = if (player.squad == SquadType.SENIOR) StatusOrange else StatusBlue,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Column {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Text(text = player.name, style = MaterialTheme.typography.titleSmall, color = TextPrimary, fontWeight = FontWeight.Bold)
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Box(
                                                    modifier = Modifier.clip(RoundedCornerShape(4.dp)).background(CardDarkTier2).padding(horizontal = 4.dp, vertical = 1.dp)
                                                ) {
                                                    Text(text = player.id, style = MaterialTheme.typography.labelSmall, fontSize = 9.sp, color = RulesGold)
                                                }
                                            }
                                            Text(text = "${player.kannadaName} • ${player.squad.labelEn}", style = MaterialTheme.typography.bodySmall, color = TextSecondary, fontSize = 10.sp)
                                        }
                                    }

                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(999.dp))
                                            .background(if (player.squad == SquadType.SENIOR) StatusOrange.copy(alpha = 0.15f) else StatusBlue.copy(alpha = 0.15f))
                                            .padding(horizontal = 8.dp, vertical = 3.dp)
                                    ) {
                                        Text(
                                            text = "${player.squad.labelEn} • ${player.role}",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontSize = 9.sp,
                                            color = if (player.squad == SquadType.SENIOR) StatusOrange else StatusBlue
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = player.phone, style = MaterialTheme.typography.bodySmall, color = TextSecondary)

                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Button(
                                            onClick = {
                                                val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${player.phone}"))
                                                context.startActivity(dialIntent)
                                            },
                                            colors = ButtonDefaults.buttonColors(containerColor = CardDarkTier2),
                                            shape = RoundedCornerShape(8.dp),
                                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
                                            modifier = Modifier.height(32.dp).testTag("call_player_${player.id}")
                                        ) {
                                            Icon(imageVector = Icons.Default.Call, contentDescription = null, tint = PitchGreen, modifier = Modifier.size(14.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(text = "Call", color = TextPrimary, fontSize = 11.sp)
                                        }

                                        Button(
                                            onClick = { viewModel.openManagePlayerDialog(player) },
                                            colors = ButtonDefaults.buttonColors(containerColor = CardDarkTier3),
                                            shape = RoundedCornerShape(8.dp),
                                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
                                            modifier = Modifier.height(32.dp).testTag("manage_player_${player.id}")
                                        ) {
                                            Icon(imageVector = Icons.Default.Edit, contentDescription = null, tint = StatusPurple, modifier = Modifier.size(14.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(text = "Manage", color = TextPrimary, fontSize = 11.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Remove Admin Confirmation Dialog
            if (adminToRemove != null) {
                AlertDialog(
                    onDismissRequest = { adminToRemove = null },
                    containerColor = CardDarkTier2,
                    title = {
                        Text(text = "Revoke Admin Privileges?", color = TextPrimary, fontWeight = FontWeight.Bold)
                    },
                    text = {
                        Text(
                            text = "Are you sure you want to revoke administrative access for ${adminToRemove?.name} (${adminToRemove?.id})? They will no longer be able to log in to the admin gateway or authorize financial transactions.",
                            color = TextSecondary
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                adminToRemove?.let { viewModel.removeAdmin(it.id) }
                                adminToRemove = null
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = StatusRed)
                        ) {
                            Text("Confirm Revoke", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    },
                    dismissButton = {
                        OutlinedButton(onClick = { adminToRemove = null }) {
                            Text("Cancel", color = TextSecondary)
                        }
                    }
                )
            }
        }
    }
}

// ==========================================
// 4. TRANSACTION DETAIL (Image_13_c30d4fd7)
// ==========================================
@Composable
fun AdminTransactionDetailScreen(
    viewModel: KmplViewModel,
    language: Language
) {
    val fundSummary = viewModel.getFundSummary()
    val players by viewModel.players.collectAsState()
    var selectedTab by remember { mutableStateOf("PENDING") } // "PAID", "PENDING"

    Scaffold(
        containerColor = SurfaceDark,
        topBar = {
            KmplTopBar(
                title = if (language == Language.KN) "ವಹಿವಾಟಿನ ವಿವರ" else "Transaction Detail",
                subtitle = "OCTOBER • MONTHLY AUDIT CYCLE",
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
                .padding(16.dp)
        ) {
            // Month Bar
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = CardDarkTier1,
                border = BorderStroke(1.dp, BorderSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {}, modifier = Modifier.size(28.dp)) {
                        Icon(imageVector = Icons.Default.ChevronLeft, contentDescription = null, tint = TextSecondary)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "October", style = MaterialTheme.typography.titleMedium, color = TextPrimary, fontWeight = FontWeight.Bold)
                        Text(text = "ಅಕ್ಟೋಬರ್ • Monthly Audit Cycle", style = MaterialTheme.typography.labelSmall, color = PitchGreen)
                    }
                    IconButton(onClick = {}, modifier = Modifier.size(28.dp)) {
                        Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = TextSecondary)
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Paid vs Pending Metric Cards
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = CardDarkTier1,
                    border = BorderStroke(1.dp, PitchGreen.copy(alpha = 0.5f)),
                    modifier = Modifier.weight(1f).padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "PAID / ಪಾವತಿಸಿದ", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = PitchGreen, modifier = Modifier.size(18.dp))
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "₹${fundSummary.collectedAmount}", style = MaterialTheme.typography.headlineMedium, color = PitchGreen, fontWeight = FontWeight.Bold)
                        Text(text = "● ${fundSummary.paidCount} Players Cleared", style = MaterialTheme.typography.labelSmall, color = PitchGreen)
                    }
                }

                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = CardDarkTier1,
                    border = BorderStroke(1.dp, RulesGold.copy(alpha = 0.5f)),
                    modifier = Modifier.weight(1f).padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "PENDING / ಬಾಕಿ", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                            Icon(imageVector = Icons.Default.Schedule, contentDescription = null, tint = RulesGold, modifier = Modifier.size(18.dp))
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "₹${fundSummary.pendingAmount}", style = MaterialTheme.typography.headlineMedium, color = RulesGold, fontWeight = FontWeight.Bold)
                        Text(text = "● ${fundSummary.pendingCount} Outstanding", style = MaterialTheme.typography.labelSmall, color = RulesGold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Tabs: Paid List (52) vs Pending List (16)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { selectedTab = "PAID" },
                    colors = ButtonDefaults.buttonColors(containerColor = if (selectedTab == "PAID") CardDarkTier2 else CardDarkTier1),
                    border = BorderStroke(1.dp, if (selectedTab == "PAID") PitchGreen else BorderSubtle),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f).height(46.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "✔ Paid List (${fundSummary.paidCount})", style = MaterialTheme.typography.labelSmall, color = if (selectedTab == "PAID") PitchGreen else TextPrimary, fontWeight = FontWeight.Bold)
                        Text(text = "ಪಾವತಿಸಿದವರು • Time Sorted", style = MaterialTheme.typography.labelSmall, color = TextSecondary, fontSize = 8.sp)
                    }
                }

                Button(
                    onClick = { selectedTab = "PENDING" },
                    colors = ButtonDefaults.buttonColors(containerColor = if (selectedTab == "PENDING") CardDarkTier2 else CardDarkTier1),
                    border = BorderStroke(1.dp, if (selectedTab == "PENDING") RulesGold else BorderSubtle),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f).height(46.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "⏳ Pending (${fundSummary.pendingCount})", style = MaterialTheme.typography.labelSmall, color = if (selectedTab == "PENDING") RulesGold else TextPrimary, fontWeight = FontWeight.Bold)
                        Text(text = "ಬಾಕಿ ಇರುವವರು • A–Z", style = MaterialTheme.typography.labelSmall, color = TextSecondary, fontSize = 8.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            val currentDisplayList = if (selectedTab == "PAID") {
                players.filter { it.isPaidThisMonth }
            } else {
                players.filter { !it.isPaidThisMonth }
            }

            Text(
                text = if (selectedTab == "PAID")
                    "✔ ${fundSummary.paidCount} Cleared Records • ₹${fundSummary.collectedAmount} in Treasury"
                else
                    "A–Z ${fundSummary.pendingCount} Pending Records (Alphabetical A–Z) ₹${fundSummary.pendingAmount} to collect",
                style = MaterialTheme.typography.labelSmall,
                color = if (selectedTab == "PAID") PitchGreen else RulesGold,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(currentDisplayList) { player ->
                    Surface(
                        onClick = {
                            if (!player.isPaidThisMonth) {
                                viewModel.openPaymentDialog(player)
                            } else {
                                viewModel.openReceipt(player)
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        color = CardDarkTier1,
                        border = BorderStroke(1.dp, BorderSubtle),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(CardDarkTier2),
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
                                    Text(text = player.name, style = MaterialTheme.typography.titleSmall, color = TextPrimary, fontWeight = FontWeight.Bold)
                                    Text(text = "${player.id} • ${player.kannadaName} (${player.squad.labelEn.take(4)})", style = MaterialTheme.typography.bodySmall, color = TextSecondary, fontSize = 10.sp)
                                }
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(999.dp))
                                        .background(if (player.isPaidThisMonth) PitchGreen.copy(alpha = 0.15f) else RulesGold.copy(alpha = 0.15f))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = if (player.isPaidThisMonth) "Cleared" else "Pending",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (player.isPaidThisMonth) PitchGreen else RulesGold,
                                        fontSize = 9.sp
                                    )
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "₹${player.squad.fee} ${if (player.isPaidThisMonth) "Paid" else "Due"}",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = if (player.isPaidThisMonth) PitchGreen else RulesGold,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
