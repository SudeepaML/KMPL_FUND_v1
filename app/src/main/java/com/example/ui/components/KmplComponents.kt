package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.R
import com.example.model.ChittyBorrower
import com.example.model.Language
import com.example.model.PaymentStatus
import com.example.model.Player
import com.example.model.SquadType
import com.example.ui.theme.*

@Composable
fun KmplTopBar(
    title: String,
    subtitle: String,
    language: Language,
    onToggleLanguage: () -> Unit,
    showBackButton: Boolean = false,
    onBackClick: () -> Unit = {},
    statusBadgeText: String? = null,
    onProfileClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Surface(
        color = SurfaceDark,
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f, fill = false)
            ) {
                if (showBackButton) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .testTag("top_bar_back_button")
                            .size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = TextPrimary
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                } else {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(CardDarkTier2)
                            .border(1.dp, BorderSubtle, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.kmpl_logo),
                            contentDescription = "KMPL",
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }

                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = PitchGreen,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                if (statusBadgeText != null) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(999.dp))
                            .background(PitchGreen.copy(alpha = 0.12f))
                            .border(1.dp, PitchGreen, RoundedCornerShape(999.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = statusBadgeText,
                            style = MaterialTheme.typography.labelSmall,
                            color = PitchGreen,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }

                // Language Toggle Pill
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = CardDarkTier2,
                    border = BorderStroke(1.dp, BorderMedium),
                    modifier = Modifier
                        .height(34.dp)
                        .clickable(onClick = onToggleLanguage)
                        .testTag("language_toggle_button")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (language == Language.EN) PitchGreen else Color.Transparent)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "EN",
                                color = if (language == Language.EN) OnPitchGreen else TextSecondary,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (language == Language.KN) PitchGreen else Color.Transparent)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "ಕನ್ನಡ",
                                color = if (language == Language.KN) OnPitchGreen else TextSecondary,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                if (onProfileClick != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(PitchGreen.copy(alpha = 0.2f))
                            .border(1.dp, PitchGreen, CircleShape)
                            .clickable(onClick = onProfileClick),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            tint = PitchGreen,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PrimaryGlowButton(
    text: String,
    subtext: String? = null,
    icon: ImageVector? = null,
    backgroundColor: Color = PitchGreen,
    contentColor: Color = OnPitchGreen,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    testTag: String = "primary_glow_button"
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor,
        modifier = modifier
            .fillMaxWidth()
            .height(58.dp)
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(16.dp), spotColor = backgroundColor)
            .testTag(testTag)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (icon != null) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(contentColor.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = contentColor,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                }
                Column {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = contentColor
                    )
                    if (subtext != null) {
                        Text(
                            text = subtext,
                            style = MaterialTheme.typography.bodySmall,
                            color = contentColor.copy(alpha = 0.85f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(22.dp)
            )
        }
    }
}

@Composable
fun StatusPill(
    text: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(999.dp))
            .background(color.copy(alpha = 0.12f))
            .border(1.dp, color.copy(alpha = 0.8f), RoundedCornerShape(999.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = color,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun DigitalReceiptModal(
    player: Player,
    language: Language,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = CardDarkTier2,
            border = BorderStroke(1.dp, PitchGreen),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = PitchGreen,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (language == Language.KN) "ಅಧಿಕೃತ ಡಿಜಿಟಲ್ ರಸೀದಿ" else "OFFICIAL DIGITAL RECEIPT",
                            style = MaterialTheme.typography.titleSmall,
                            color = PitchGreen,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = TextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(CardDarkTier1)
                        .border(1.dp, BorderSubtle, RoundedCornerShape(12.dp))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "KMPL TOURNAMENT FUND 2025",
                            style = MaterialTheme.typography.labelMedium,
                            color = TextSecondary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "₹${player.paymentAmount}.00",
                            style = MaterialTheme.typography.headlineLarge,
                            color = PitchGreen,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                            text = if (language == Language.KN) "ಪಾವತಿಸಲಾಗಿದೆ (PAID)" else "PAYMENT CONFIRMED",
                            style = MaterialTheme.typography.labelSmall,
                            color = PitchGreen
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ReceiptRow(label = if (language == Language.KN) "ಆಟಗಾರನ ಹೆಸರು" else "Player Name", value = "${player.name} (${player.kannadaName})")
                    ReceiptRow(label = if (language == Language.KN) "ಸದಸ್ಯತ್ವ ಸಂಖ್ಯೆ" else "Member ID", value = player.id)
                    ReceiptRow(label = if (language == Language.KN) "ವಿಭಾಗ" else "Squad Tier", value = player.squad.labelEn)
                    ReceiptRow(label = if (language == Language.KN) "ಪಾವತಿ ಉಲ್ಲೇಖ" else "Transaction Ref", value = if (player.paymentRef.isNotBlank()) player.paymentRef else "UPI #98410-REC")
                    ReceiptRow(label = if (language == Language.KN) "ದಿನಾಂಕ" else "Date & Time", value = if (player.paymentDate.isNotBlank()) player.paymentDate else "Oct 03, 2025")
                    ReceiptRow(label = if (language == Language.KN) "ಖಜಾಂಚಿ / ಸ್ವೀಕರಿಸಿದವರು" else "Treasurer / Collector", value = "ಶಿವಕುಮಾರ್ (Shivakumar)")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(PitchGreen.copy(alpha = 0.08f))
                        .border(1.dp, PitchGreen.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🔒 Cryptographically Sealed • Verified by KMPL Committee",
                        style = MaterialTheme.typography.labelSmall,
                        color = PitchGreen,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = PitchGreen),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .testTag("dismiss_receipt_button")
                ) {
                    Text(
                        text = if (language == Language.KN) "ಮುಚ್ಚಿ (Close)" else "Close Receipt",
                        color = OnPitchGreen,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun ReceiptRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodySmall, color = TextSecondary)
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            color = TextPrimary,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun RecordPaymentModal(
    player: Player?,
    allPlayers: List<Player>,
    language: Language,
    onDismiss: () -> Unit,
    onConfirm: (playerId: String, amount: Int, method: String) -> Unit
) {
    var selectedPlayer by remember { mutableStateOf(player ?: allPlayers.firstOrNull()) }
    var selectedMethod by remember { mutableStateOf("UPI") }
    var amount by remember { mutableStateOf(selectedPlayer?.squad?.fee ?: 200) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = CardDarkTier2,
            border = BorderStroke(1.dp, PitchGreen),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (language == Language.KN) "ಶುಲ್ಕ ಸಂಗ್ರಹಣೆ" else "Collect Tournament Fee",
                        style = MaterialTheme.typography.titleMedium,
                        color = PitchGreen,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = if (language == Language.KN) "ಆಟಗಾರನನ್ನು ಆಯ್ಕೆಮಾಡಿ:" else "Select Player:",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )

                Spacer(modifier = Modifier.height(6.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(CardDarkTier3)
                        .padding(12.dp)
                ) {
                    Column {
                        Text(
                            text = "${selectedPlayer?.id ?: ""} - ${selectedPlayer?.name ?: ""}",
                            style = MaterialTheme.typography.titleSmall,
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${selectedPlayer?.kannadaName ?: ""} • ${selectedPlayer?.squad?.labelEn ?: ""}",
                            style = MaterialTheme.typography.bodySmall,
                            color = PitchGreen
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = if (language == Language.KN) "ಮೊತ್ತ (Amount):" else "Fee Amount (₹):",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(100, 200).forEach { amt ->
                        FilterChip(
                            selected = amount == amt,
                            onClick = { amount = amt },
                            label = { Text("₹$amt") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = PitchGreen,
                                selectedLabelColor = OnPitchGreen,
                                containerColor = CardDarkTier3,
                                labelColor = TextPrimary
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = if (language == Language.KN) "ಪಾವತಿ ವಿಧಾನ:" else "Payment Channel:",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("UPI", "Cash", "Bank Transfer").forEach { method ->
                        FilterChip(
                            selected = selectedMethod == method,
                            onClick = { selectedMethod = method },
                            label = { Text(method) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = PitchGreen,
                                selectedLabelColor = OnPitchGreen,
                                containerColor = CardDarkTier3,
                                labelColor = TextPrimary
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        val pId = selectedPlayer?.id ?: "KMPL-001"
                        onConfirm(pId, amount, selectedMethod)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PitchGreen),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("submit_payment_button")
                ) {
                    Text(
                        text = if (language == Language.KN) "ಖಚಿತಪಡಿಸಿ ಮತ್ತು ಸ್ವೀಕೃತಿ ರಸೀದಿ ನೀಡಿ" else "Confirm & Issue Receipt",
                        color = OnPitchGreen,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun RegisterPlayerModal(
    language: Language,
    onDismiss: () -> Unit,
    onRegister: (name: String, kannadaName: String, phone: String, squad: SquadType, role: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var kannadaName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var squad by remember { mutableStateOf(SquadType.SENIOR) }
    var role by remember { mutableStateOf("Batsman") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = CardDarkTier2,
            border = BorderStroke(1.dp, PitchGreen),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (language == Language.KN) "ಹೊಸ ಆಟಗಾರನ ನೋಂದಣಿ" else "Register New Player",
                        style = MaterialTheme.typography.titleMedium,
                        color = PitchGreen,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Player Name (English)") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("new_player_name_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PitchGreen,
                        unfocusedBorderColor = BorderSubtle,
                        focusedLabelColor = PitchGreen
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = kannadaName,
                    onValueChange = { kannadaName = it },
                    label = { Text("ಆಟಗಾರನ ಹೆಸರು (ಕನ್ನಡ)") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("new_player_kn_name_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PitchGreen,
                        unfocusedBorderColor = BorderSubtle,
                        focusedLabelColor = PitchGreen
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number (+91)") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("new_player_phone_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PitchGreen,
                        unfocusedBorderColor = BorderSubtle,
                        focusedLabelColor = PitchGreen
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = if (language == Language.KN) "ವಿಭಾಗ (Squad Category):" else "Squad Category:",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    SquadType.values().forEach { sq ->
                        FilterChip(
                            selected = squad == sq,
                            onClick = { squad = sq },
                            label = { Text("${sq.labelEn} (₹${sq.fee}/mo)") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = PitchGreen,
                                selectedLabelColor = OnPitchGreen,
                                containerColor = CardDarkTier3,
                                labelColor = TextPrimary
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = if (language == Language.KN) "ಪಾತ್ರ (Role):" else "Playing Role:",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf("Batsman", "Bowler", "All-rounder", "Keeper").forEach { r ->
                        FilterChip(
                            selected = role == r,
                            onClick = { role = r },
                            label = { Text(r, fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = PitchGreen,
                                selectedLabelColor = OnPitchGreen,
                                containerColor = CardDarkTier3,
                                labelColor = TextPrimary
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = {
                        if (name.isNotBlank()) {
                            onRegister(name, kannadaName, phone, squad, role)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PitchGreen),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("submit_register_player_button")
                ) {
                    Text(
                        text = if (language == Language.KN) "ಖಾತೆಯನ್ನು ನೋಂದಾಯಿಸಿ" else "Enroll Player to Roster",
                        color = OnPitchGreen,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun AddAdminModal(
    language: Language,
    onDismiss: () -> Unit,
    onConfirm: (name: String, phone: String, role: String, email: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("+91 ") }
    var email by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf("L1 Finance Treasurer") }

    val roles = listOf(
        "L1 Finance Treasurer",
        "KMPL President",
        "General Secretary",
        "Committee Auditor"
    )

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = CardDarkTier2,
            border = BorderStroke(1.dp, PitchGreen),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Shield, contentDescription = null, tint = PitchGreen, modifier = Modifier.size(22.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (language == Language.KN) "ನಿರ್ವಾಹಕರನ್ನು ಸೇರಿಸಿ" else "Authorize New Admin",
                            style = MaterialTheme.typography.titleMedium,
                            color = PitchGreen,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full Name (ಪೂರ್ಣ ಹೆಸರು)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("new_admin_name_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PitchGreen,
                        unfocusedBorderColor = BorderSubtle,
                        focusedLabelColor = PitchGreen
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Registered Mobile Number (+91)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("new_admin_phone_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PitchGreen,
                        unfocusedBorderColor = BorderSubtle,
                        focusedLabelColor = PitchGreen
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Admin Email (ಇಮೇಲ್)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("new_admin_email_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PitchGreen,
                        unfocusedBorderColor = BorderSubtle,
                        focusedLabelColor = PitchGreen
                    )
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = if (language == Language.KN) "ಅಧಿಕಾರ ಪಾತ್ರ (Administrative Scope):" else "Administrative Scope & Role:",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(6.dp))

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    roles.forEach { r ->
                        FilterChip(
                            selected = selectedRole == r,
                            onClick = { selectedRole = r },
                            label = { Text(r, fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = PitchGreen,
                                selectedLabelColor = OnPitchGreen,
                                containerColor = CardDarkTier3,
                                labelColor = TextPrimary
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = {
                        if (name.isNotBlank() && phone.trim().length >= 10) {
                            onConfirm(name.trim(), phone.trim(), selectedRole, email.trim())
                        }
                    },
                    enabled = name.isNotBlank() && phone.trim().length >= 10,
                    colors = ButtonDefaults.buttonColors(containerColor = PitchGreen),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().height(48.dp).testTag("submit_add_admin_button")
                ) {
                    Text(
                        text = if (language == Language.KN) "ಅಧಿಕಾರ ದೃಢೀಕರಿಸಿ" else "Grant Admin Scope",
                        color = OnPitchGreen,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun LendChittyLoanModal(
    seniorPlayers: List<Player>,
    language: Language,
    onDismiss: () -> Unit,
    onIssue: (playerId: String, principal: Int, rate: Int, dueDate: String) -> Unit
) {
    var selectedPlayer by remember { mutableStateOf(seniorPlayers.firstOrNull()) }
    var principalAmount by remember { mutableStateOf(5000) }
    var customPrincipal by remember { mutableStateOf("5000") }
    var dueDate by remember { mutableStateOf("Nov 15, 2025") }

    val rate = 10
    val interest = (principalAmount * rate) / 100
    val totalPayable = principalAmount + interest

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = CardDarkTier2,
            border = BorderStroke(1.dp, StatusPurple),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "🤝", fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (language == Language.KN) "ಬಡ್ಡಿ ಚಿಟ್ಟಿ ಸಾಲ ವಿತರಣೆ" else "Issue Buddy Chitty Loan",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(RulesGold.copy(alpha = 0.12f))
                        .border(1.dp, RulesGold.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                        .padding(8.dp)
                ) {
                    Text(
                        text = "⚠️ Senior Squad Restricted: Junior players prohibited. Quota: 15 loans/cycle.",
                        style = MaterialTheme.typography.labelSmall,
                        color = RulesGold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "Eligible Senior Borrower:", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                Spacer(modifier = Modifier.height(6.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(CardDarkTier3)
                        .padding(10.dp)
                ) {
                    Column {
                        Text(
                            text = "${selectedPlayer?.name ?: "None"} (${selectedPlayer?.id ?: ""})",
                            style = MaterialTheme.typography.titleSmall,
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${selectedPlayer?.kannadaName ?: ""} • ${selectedPlayer?.phone ?: ""}",
                            style = MaterialTheme.typography.bodySmall,
                            color = PitchGreen
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "Principal Amount (ಅಸಲು ಮೊತ್ತ):", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                Spacer(modifier = Modifier.height(6.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(5000, 10000, 15000).forEach { amt ->
                        FilterChip(
                            selected = principalAmount == amt,
                            onClick = {
                                principalAmount = amt
                                customPrincipal = amt.toString()
                            },
                            label = { Text("₹$amt") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = StatusPurple,
                                selectedLabelColor = Color.White,
                                containerColor = CardDarkTier3,
                                labelColor = TextPrimary
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Live Financial Computation Box
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = CardDarkTier1,
                    border = BorderStroke(1.dp, BorderSubtle),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Principal (ಅಸಲು):", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                            Text(text = "₹$principalAmount", style = MaterialTheme.typography.bodySmall, color = TextPrimary, fontWeight = FontWeight.Bold)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Interest @ 10% Flat:", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                            Text(text = "+₹$interest", style = MaterialTheme.typography.bodySmall, color = PitchGreen, fontWeight = FontWeight.Bold)
                        }
                        HorizontalDivider(color = BorderSubtle, modifier = Modifier.padding(vertical = 6.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Total Repayable (ಒಟ್ಟು ಮರುಪಾವತಿ):", style = MaterialTheme.typography.titleSmall, color = RulesGold, fontWeight = FontWeight.Bold)
                            Text(text = "₹$totalPayable", style = MaterialTheme.typography.titleMedium, color = RulesGold, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Due Date:", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                            Text(text = dueDate, style = MaterialTheme.typography.labelSmall, color = PitchGreen)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = {
                        val pId = selectedPlayer?.id ?: "KMPL-001"
                        onIssue(pId, principalAmount, rate, dueDate)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = StatusPurple),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().height(48.dp).testTag("submit_chitty_loan_button")
                ) {
                    Text(
                        text = if (language == Language.KN) "ಸಾಲ ವಿತರಣೆ ಖಚಿತಪಡಿಸಿ" else "Confirm & Issue ₹$principalAmount Loan",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun CollectChittyRepaymentModal(
    borrower: ChittyBorrower,
    language: Language,
    onDismiss: () -> Unit,
    onConfirm: (loanId: String, amount: Int, method: String, ref: String) -> Unit
) {
    var amountText by remember { mutableStateOf(borrower.remainingDue.toString()) }
    var selectedMethod by remember { mutableStateOf("UPI") }
    var refNote by remember { mutableStateOf("") }

    val totalLoanDue = borrower.principal + borrower.interestAmount

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = CardDarkTier2,
            border = BorderStroke(1.dp, PitchGreen),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = PitchGreen, modifier = Modifier.size(22.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (language == Language.KN) "ಚಿಟ್ಟಿ ಮರುಪಾವತಿ ಸಂಗ್ರಹ" else "Collect Chitty Repayment",
                            style = MaterialTheme.typography.titleMedium,
                            color = PitchGreen,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Loan details banner
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = CardDarkTier1,
                    border = BorderStroke(1.dp, BorderSubtle),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "${borrower.name} (${borrower.playerId})", style = MaterialTheme.typography.titleSmall, color = TextPrimary, fontWeight = FontWeight.Bold)
                            Text(text = borrower.id, style = MaterialTheme.typography.labelSmall, color = RulesGold)
                        }
                        Text(text = "${borrower.kannadaName} • Due ${borrower.dueDate}", style = MaterialTheme.typography.bodySmall, color = TextSecondary, fontSize = 11.sp)
                        HorizontalDivider(color = BorderSubtle, modifier = Modifier.padding(vertical = 6.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Total with 10% Interest:", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                            Text(text = "₹$totalLoanDue", style = MaterialTheme.typography.bodySmall, color = TextPrimary, fontWeight = FontWeight.Bold)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Already Repaid:", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                            Text(text = "₹${borrower.repaidAmount}", style = MaterialTheme.typography.bodySmall, color = PitchGreen, fontWeight = FontWeight.Bold)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Remaining Outstanding:", style = MaterialTheme.typography.bodySmall, color = RulesGold, fontWeight = FontWeight.Bold)
                            Text(text = "₹${borrower.remainingDue}", style = MaterialTheme.typography.titleSmall, color = RulesGold, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = amountText,
                    onValueChange = { amountText = it },
                    label = { Text("Repayment Amount (₹)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("chitty_repayment_amount_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PitchGreen,
                        unfocusedBorderColor = BorderSubtle,
                        focusedLabelColor = PitchGreen
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = "Payment Channel:", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                Spacer(modifier = Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("UPI", "Cash", "Bank Transfer").forEach { ch ->
                        FilterChip(
                            selected = selectedMethod == ch,
                            onClick = { selectedMethod = ch },
                            label = { Text(ch) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = PitchGreen,
                                selectedLabelColor = OnPitchGreen,
                                containerColor = CardDarkTier3,
                                labelColor = TextPrimary
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = refNote,
                    onValueChange = { refNote = it },
                    label = { Text("Transaction Reference / Notes (Optional)") },
                    placeholder = { Text("UPI Ref / Receipt #") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PitchGreen,
                        unfocusedBorderColor = BorderSubtle,
                        focusedLabelColor = PitchGreen
                    )
                )

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = {
                        val parsed = amountText.toIntOrNull() ?: borrower.remainingDue
                        if (parsed > 0) {
                            onConfirm(borrower.id, parsed, selectedMethod, refNote)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PitchGreen),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().height(48.dp).testTag("submit_chitty_repayment_button")
                ) {
                    Text(
                        text = if (language == Language.KN) "ಮರುಪಾವತಿ ದಾಖಲಿಸಿ" else "Record Repayment & Update Ledger",
                        color = OnPitchGreen,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun ManagePlayerModal(
    player: Player,
    language: Language,
    onDismiss: () -> Unit,
    onCollectPayment: () -> Unit,
    onViewReceipt: () -> Unit,
    onDeactivate: (playerId: String) -> Unit
) {
    var showConfirmDeactivate by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = CardDarkTier2,
            border = BorderStroke(1.dp, BorderMedium),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(20.dp)
            ) {
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
                                .background(if (player.squad == SquadType.SENIOR) StatusOrange.copy(alpha = 0.2f) else StatusBlue.copy(alpha = 0.2f)),
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
                            Text(text = player.name, style = MaterialTheme.typography.titleMedium, color = TextPrimary, fontWeight = FontWeight.Bold)
                            Text(text = "${player.id} • ${player.kannadaName}", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                        }
                    }
                    IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Info Rows
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = CardDarkTier1,
                    border = BorderStroke(1.dp, BorderSubtle),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        ReceiptRow("Squad Tier:", "${player.squad.labelEn} (₹${player.squad.fee}/mo)")
                        ReceiptRow("Playing Role:", player.role)
                        ReceiptRow("Phone Number:", player.phone)
                        ReceiptRow("October Status:", if (player.isPaidThisMonth) "PAID (ಪಾವತಿಸಲಾಗಿದೆ)" else "PENDING (ಬಾಕಿ)")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (!showConfirmDeactivate) {
                    // Actions
                    if (!player.isPaidThisMonth) {
                        Button(
                            onClick = onCollectPayment,
                            colors = ButtonDefaults.buttonColors(containerColor = PitchGreen),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth().height(44.dp).testTag("modal_collect_payment_button")
                        ) {
                            Text(text = "Collect Monthly Fee (₹${player.squad.fee})", color = OnPitchGreen, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    } else {
                        Button(
                            onClick = onViewReceipt,
                            colors = ButtonDefaults.buttonColors(containerColor = PitchGreenDeep),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth().height(44.dp).testTag("modal_view_receipt_button")
                        ) {
                            Text(text = "View Digital Clearance Receipt", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    OutlinedButton(
                        onClick = { showConfirmDeactivate = true },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = StatusRed),
                        border = BorderStroke(1.dp, StatusRed.copy(alpha = 0.5f)),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth().height(44.dp).testTag("deactivate_player_button")
                    ) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = null, tint = StatusRed, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Deactivate Player Profile", fontWeight = FontWeight.Bold)
                    }
                } else {
                    // Deactivation confirmation warning card
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = StatusRed.copy(alpha = 0.1f),
                        border = BorderStroke(1.dp, StatusRed),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.Warning, contentDescription = null, tint = StatusRed, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = "Confirm Deactivation", style = MaterialTheme.typography.titleSmall, color = StatusRed, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Deactivating ${player.name} (${player.id}) will prevent active roster selection while preserving all historical payments, audit receipts, and tournament records permanently.",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextPrimary,
                                fontSize = 11.sp
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlinedButton(
                                    onClick = { showConfirmDeactivate = false },
                                    modifier = Modifier.weight(1f).height(40.dp)
                                ) {
                                    Text("Cancel", color = TextSecondary)
                                }
                                Button(
                                    onClick = { onDeactivate(player.id) },
                                    colors = ButtonDefaults.buttonColors(containerColor = StatusRed),
                                    modifier = Modifier.weight(1f).height(40.dp).testTag("confirm_deactivate_button")
                                ) {
                                    Text("Confirm", color = Color.White, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

