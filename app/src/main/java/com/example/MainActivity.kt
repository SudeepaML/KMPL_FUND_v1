package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.SquadType
import com.example.ui.components.*
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.KmplViewModel
import com.example.viewmodel.Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                KmplApp()
            }
        }
    }
}

@Composable
fun KmplApp(viewModel: KmplViewModel = viewModel()) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val language by viewModel.language.collectAsState()
    val players by viewModel.players.collectAsState()

    val receiptPlayer by viewModel.showReceiptDialog.collectAsState()
    val paymentPlayer by viewModel.showPaymentDialog.collectAsState()
    val showAddPlayerDialog by viewModel.showAddPlayerDialog.collectAsState()
    val showLendLoanDialog by viewModel.showLendLoanDialog.collectAsState()
    val showRepaymentDialog by viewModel.showRepaymentDialog.collectAsState()
    val showAddAdminDialog by viewModel.showAddAdminDialog.collectAsState()
    val showManagePlayerDialog by viewModel.showManagePlayerDialog.collectAsState()
    val toastMessage by viewModel.toastMessage.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(toastMessage) {
        toastMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearToast()
        }
    }

    // Intercept back button if not on Home screen
    BackHandler(enabled = currentScreen != Screen.HOME) {
        viewModel.navigateBack()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedContent(
            targetState = currentScreen,
            transitionSpec = {
                fadeIn() togetherWith fadeOut()
            },
            label = "ScreenTransition"
        ) { screen ->
            when (screen) {
                Screen.HOME -> HomeScreen(viewModel, language)
                Screen.RULES -> RulesScreen(viewModel, language)
                Screen.PLAYER_LOGIN -> PlayerLoginScreen(viewModel, language)
                Screen.PLAYER_PORTAL_DASHBOARD -> PlayerPortalDashboard(viewModel, language)
                Screen.REGISTERED_PLAYERS -> RegisteredPlayersScreen(viewModel, language)
                Screen.BUDDY_CHITTY_LEDGER -> BuddyChittyLedgerScreen(viewModel, language)
                Screen.TOTAL_COLLECTION_REPORTS -> TotalCollectionReportsScreen(viewModel, language)
                Screen.ADMIN_LOGIN -> AdminLoginScreen(viewModel, language)
                Screen.ADMIN_DASHBOARD -> AdminDashboardScreen(viewModel, language)
                Screen.ADMIN_PLAYERS_SUITE -> AdminPlayersSuiteScreen(viewModel, language)
                Screen.ADMIN_TRANSACTION_DETAIL -> AdminTransactionDetailScreen(viewModel, language)
            }
        }

        // Modals
        if (receiptPlayer != null) {
            DigitalReceiptModal(
                player = receiptPlayer!!,
                language = language,
                onDismiss = { viewModel.closeReceipt() }
            )
        }

        if (paymentPlayer != null) {
            RecordPaymentModal(
                player = paymentPlayer,
                allPlayers = players,
                language = language,
                onDismiss = { viewModel.closePaymentDialog() },
                onConfirm = { pId, amount, method ->
                    viewModel.recordPayment(pId, amount, method)
                }
            )
        }

        if (showAddPlayerDialog) {
            RegisterPlayerModal(
                language = language,
                onDismiss = { viewModel.closeAddPlayerDialog() },
                onRegister = { name, knName, phone, squad, role ->
                    viewModel.addNewPlayer(name, knName, phone, squad, role)
                }
            )
        }

        if (showLendLoanDialog) {
            val seniorPlayers = players.filter { it.squad == SquadType.SENIOR }
            LendChittyLoanModal(
                seniorPlayers = seniorPlayers,
                language = language,
                onDismiss = { viewModel.closeLendLoanDialog() },
                onIssue = { playerId, principal, rate, dueDate ->
                    viewModel.issueChittyLoan(playerId, principal, rate, dueDate)
                }
            )
        }

        if (showRepaymentDialog != null) {
            CollectChittyRepaymentModal(
                borrower = showRepaymentDialog!!,
                language = language,
                onDismiss = { viewModel.closeRepaymentDialog() },
                onConfirm = { loanId, amount, method, ref ->
                    viewModel.collectChittyRepayment(loanId, amount, method, ref)
                }
            )
        }

        if (showAddAdminDialog) {
            AddAdminModal(
                language = language,
                onDismiss = { viewModel.closeAddAdminDialog() },
                onConfirm = { name, phone, role, email ->
                    viewModel.addAdmin(name, phone, role, email)
                }
            )
        }

        if (showManagePlayerDialog != null) {
            val managed = showManagePlayerDialog!!
            ManagePlayerModal(
                player = managed,
                language = language,
                onDismiss = { viewModel.closeManagePlayerDialog() },
                onCollectPayment = {
                    viewModel.closeManagePlayerDialog()
                    viewModel.openPaymentDialog(managed)
                },
                onViewReceipt = {
                    viewModel.closeManagePlayerDialog()
                    viewModel.openReceipt(managed)
                },
                onDeactivate = { playerId ->
                    viewModel.deactivatePlayer(playerId)
                    viewModel.closeManagePlayerDialog()
                }
            )
        }
    }
}

