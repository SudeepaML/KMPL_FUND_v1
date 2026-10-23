package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.Language
import com.example.model.TournamentRule
import com.example.ui.components.KmplTopBar
import com.example.ui.theme.*
import com.example.viewmodel.KmplViewModel

@Composable
fun RulesScreen(
    viewModel: KmplViewModel,
    language: Language
) {
    val rules by viewModel.rules.collectAsState()
    val isAdmin by viewModel.isAdminLoggedIn.collectAsState()
    var selectedFilter by remember { mutableStateOf("ALL") }
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = SurfaceDark,
        topBar = {
            KmplTopBar(
                title = if (language == Language.KN) "ನಿಯಮಾವಳಿಗಳು" else "Rules & Regulations",
                subtitle = "KMPL FUND 2025",
                language = language,
                onToggleLanguage = { viewModel.toggleLanguage() },
                showBackButton = true,
                onBackClick = { viewModel.navigateBack() },
                statusBadgeText = if (isAdmin) "ADMIN ACCESS" else null
            )
        },
        floatingActionButton = {
            if (isAdmin) {
                FloatingActionButton(
                    onClick = { showAddDialog = true },
                    containerColor = RulesGold,
                    contentColor = Color.Black,
                    modifier = Modifier.testTag("admin_add_rule_fab")
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Rule")
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

            // Notice Card
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = CardDarkTier1,
                border = BorderStroke(1.dp, RulesGold.copy(alpha = 0.5f)),
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
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = RulesGold,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = if (language == Language.KN) "ಅಧಿಕೃತ ಸಮಿತಿ ನಿಯಮಾವಳಿಗಳು" else "Official Tournament Bylaws",
                            style = MaterialTheme.typography.titleSmall,
                            color = RulesGold,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (language == Language.KN)
                                "ಪ್ರತಿ ತಿಂಗಳ 15ನೇ ತಾರೀಕು ನಿಧಿ ಮತ್ತು ಬಡ್ಡಿ ಚಿಟ್ಟಿ ಸಂಗ್ರಹದ ದಿನಾಂಕವಾಗಿರುತ್ತದೆ."
                            else
                                "The 15th of every month is the official Fund and Buddy Chitty collection date.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Category filter chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(
                    "ALL" to (if (language == Language.KN) "ಎಲ್ಲಾ" else "All Rules"),
                    "FINANCIAL" to (if (language == Language.KN) "ವಂತಿಗೆ" else "Fund Dues"),
                    "CHITTY" to (if (language == Language.KN) "ಬಡ್ಡಿ ಚಿಟ್ಟಿ" else "Buddy Chitty")
                ).forEach { (key, label) ->
                    FilterChip(
                        selected = selectedFilter == key,
                        onClick = { selectedFilter = key },
                        label = { Text(label, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = PitchGreen,
                            selectedLabelColor = OnPitchGreen,
                            containerColor = CardDarkTier2,
                            labelColor = TextPrimary
                        ),
                        modifier = Modifier.testTag("filter_chip_$key")
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Non-admin players see only published rules!
            val visibleRules = rules.filter { if (isAdmin) true else it.isPublished }
            val filteredRules = if (selectedFilter == "ALL") visibleRules else visibleRules.filter { it.category == selectedFilter }

            if (filteredRules.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "📜", fontSize = 40.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = if (language == Language.KN) "ಯಾವುದೇ ನಿಯಮಾವಳಿಗಳನ್ನು ಪ್ರಕಟಿಸಲಾಗಿಲ್ಲ" else "No rules have been published yet",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (language == Language.KN)
                                "ನಿರ್ವಾಹಕರು ಅಧಿಕೃತ ನಿಯಮಗಳನ್ನು ಪ್ರಕಟಿಸಿದ ನಂತರ ಇಲ್ಲಿ ಕಾಣಿಸಿಕೊಳ್ಳುತ್ತವೆ."
                            else
                                "Published tournament rules and guidelines will appear here once authorized by committee.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredRules) { rule ->
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = CardDarkTier1,
                            border = BorderStroke(1.dp, if (!rule.isPublished) RulesGoldBorder else BorderSubtle),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "RULE #${rule.id}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = PitchGreen,
                                            fontWeight = FontWeight.Bold
                                        )
                                        if (isAdmin && !rule.isPublished) {
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(4.dp))
                                                    .background(RulesGold.copy(alpha = 0.2f))
                                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                                            ) {
                                                Text(
                                                    text = "DRAFT",
                                                    style = MaterialTheme.typography.labelSmall,
                                                    color = RulesGold,
                                                    fontSize = 9.sp,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                    }

                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(999.dp))
                                            .background(CardDarkTier2)
                                            .padding(horizontal = 8.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = rule.category,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = TextSecondary,
                                            fontSize = 10.sp
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = if (language == Language.KN) rule.titleKn else rule.titleEn,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = TextPrimary,
                                    fontWeight = FontWeight.Bold
                                )

                                if (language != Language.KN) {
                                    Text(
                                        text = rule.titleKn,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = PitchGreen
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = if (language == Language.KN) rule.descriptionKn else rule.descriptionEn,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextSecondary,
                                    lineHeight = 20.sp
                                )

                                if (isAdmin) {
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        TextButton(
                                            onClick = { viewModel.toggleRulePublish(rule.id) },
                                            modifier = Modifier.testTag("toggle_publish_rule_${rule.id}")
                                        ) {
                                            Text(
                                                text = if (rule.isPublished) "Set to Draft" else "Publish",
                                                color = if (rule.isPublished) RulesGold else PitchGreen,
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        IconButton(
                                            onClick = { viewModel.deleteRule(rule.id) },
                                            modifier = Modifier.size(32.dp).testTag("delete_rule_${rule.id}")
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "Delete",
                                                tint = StatusRed,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(70.dp))
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddRuleDialog(
            language = language,
            onDismiss = { showAddDialog = false },
            onAdd = { titleEn, titleKn, descEn, descKn, cat, pub ->
                viewModel.addRule(titleEn, titleKn, descEn, descKn, cat, pub)
                showAddDialog = false
            }
        )
    }
}

@Composable
private fun AddRuleDialog(
    language: Language,
    onDismiss: () -> Unit,
    onAdd: (titleEn: String, titleKn: String, descEn: String, descKn: String, category: String, isPublished: Boolean) -> Unit
) {
    var titleEn by remember { mutableStateOf("") }
    var titleKn by remember { mutableStateOf("") }
    var descEn by remember { mutableStateOf("") }
    var descKn by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("FINANCIAL") }
    var isPublished by remember { mutableStateOf(true) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(18.dp),
            color = CardDarkTier2,
            border = BorderStroke(1.dp, RulesGold),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(18.dp)) {
                Text(
                    text = if (language == Language.KN) "ಹೊಸ ನಿಯಮ ಸೇರಿಸಿ" else "Create Tournament Rule",
                    style = MaterialTheme.typography.titleMedium,
                    color = RulesGold,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = titleEn,
                    onValueChange = { titleEn = it },
                    label = { Text("Title (English)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = titleKn,
                    onValueChange = { titleKn = it },
                    label = { Text("ಶೀರ್ಷಿಕೆ (ಕನ್ನಡ)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = descEn,
                    onValueChange = { descEn = it },
                    label = { Text("Description (English)") },
                    modifier = Modifier.fillMaxWidth().height(90.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = descKn,
                    onValueChange = { descKn = it },
                    label = { Text("ವಿವರಣೆ (ಕನ್ನಡ)") },
                    modifier = Modifier.fillMaxWidth().height(90.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = isPublished,
                        onCheckedChange = { isPublished = it },
                        colors = CheckboxDefaults.colors(checkedColor = RulesGold)
                    )
                    Text(
                        text = if (isPublished) "Publish immediately (Visible to players)" else "Save as Draft (Admin only)",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextPrimary
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismiss) { Text("Cancel", color = TextSecondary) }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (titleEn.isNotBlank() && descEn.isNotBlank()) {
                                onAdd(titleEn, if (titleKn.isNotBlank()) titleKn else titleEn, descEn, if (descKn.isNotBlank()) descKn else descEn, category, isPublished)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = RulesGold),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Save Rule", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
