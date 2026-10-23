package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.KmplRepository
import com.example.model.*
import kotlinx.coroutines.flow.*

enum class Screen {
    HOME,
    RULES,
    PLAYER_LOGIN,
    PLAYER_PORTAL_DASHBOARD,
    REGISTERED_PLAYERS,
    BUDDY_CHITTY_LEDGER,
    TOTAL_COLLECTION_REPORTS,
    ADMIN_LOGIN,
    ADMIN_DASHBOARD,
    ADMIN_PLAYERS_SUITE,
    ADMIN_TRANSACTION_DETAIL
}

class KmplViewModel(private val repository: KmplRepository = KmplRepository()) : ViewModel() {

    private val _screenStack = MutableStateFlow(listOf(Screen.HOME))
    val currentScreen: StateFlow<Screen> = _screenStack.map { it.last() }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        Screen.HOME
    )

    val language = repository.language
    val selectedMonth = repository.selectedMonth
    val players = repository.players
    val admins = repository.admins
    val chittyBorrowers = repository.chittyBorrowers
    val chittyRepayments = repository.chittyRepayments
    val auditLogs = repository.auditLogs
    val rules = repository.rules
    val activePlayer = repository.activePlayer
    val isAdminLoggedIn = repository.isAdminLoggedIn

    // Dialog & UI interaction states
    private val _showReceiptDialog = MutableStateFlow<Player?>(null)
    val showReceiptDialog: StateFlow<Player?> = _showReceiptDialog.asStateFlow()

    private val _showPaymentDialog = MutableStateFlow<Player?>(null)
    val showPaymentDialog: StateFlow<Player?> = _showPaymentDialog.asStateFlow()

    private val _showAddPlayerDialog = MutableStateFlow(false)
    val showAddPlayerDialog: StateFlow<Boolean> = _showAddPlayerDialog.asStateFlow()

    private val _showLendLoanDialog = MutableStateFlow(false)
    val showLendLoanDialog: StateFlow<Boolean> = _showLendLoanDialog.asStateFlow()

    private val _showRepaymentDialog = MutableStateFlow<ChittyBorrower?>(null)
    val showRepaymentDialog: StateFlow<ChittyBorrower?> = _showRepaymentDialog.asStateFlow()

    private val _showAddAdminDialog = MutableStateFlow(false)
    val showAddAdminDialog: StateFlow<Boolean> = _showAddAdminDialog.asStateFlow()

    private val _showAddRuleDialog = MutableStateFlow(false)
    val showAddRuleDialog: StateFlow<Boolean> = _showAddRuleDialog.asStateFlow()

    private val _showManagePlayerDialog = MutableStateFlow<Player?>(null)
    val showManagePlayerDialog: StateFlow<Player?> = _showManagePlayerDialog.asStateFlow()

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    fun navigateTo(screen: Screen) {
        _screenStack.update { it + screen }
    }

    fun navigateBack(): Boolean {
        if (_screenStack.value.size > 1) {
            _screenStack.update { it.dropLast(1) }
            return true
        }
        return false
    }

    fun toggleLanguage() {
        repository.toggleLanguage()
    }

    fun setLanguage(lang: Language) {
        repository.setLanguage(lang)
    }

    fun setSelectedMonth(month: String) {
        repository.setSelectedMonth(month)
    }

    fun loginPlayer(phoneOrId: String) {
        repository.loginPlayer(phoneOrId)
        navigateTo(Screen.PLAYER_PORTAL_DASHBOARD)
    }

    fun loginAdmin(identifier: String, pass: String, email: String = ""): Boolean {
        val success = repository.loginAdmin(identifier, pass, email)
        if (success) {
            navigateTo(Screen.ADMIN_DASHBOARD)
        }
        return success
    }

    fun logoutPlayer() {
        repository.logoutPlayer()
        _screenStack.value = listOf(Screen.HOME)
    }

    fun logoutAdmin() {
        repository.logoutAdmin()
        _screenStack.value = listOf(Screen.HOME)
    }

    fun openReceipt(player: Player) {
        _showReceiptDialog.value = player
    }

    fun closeReceipt() {
        _showReceiptDialog.value = null
    }

    fun openPaymentDialog(player: Player? = null) {
        _showPaymentDialog.value = player
    }

    fun closePaymentDialog() {
        _showPaymentDialog.value = null
    }

    fun openAddPlayerDialog() {
        _showAddPlayerDialog.value = true
    }

    fun closeAddPlayerDialog() {
        _showAddPlayerDialog.value = false
    }

    fun openLendLoanDialog() {
        _showLendLoanDialog.value = true
    }

    fun closeLendLoanDialog() {
        _showLendLoanDialog.value = false
    }

    fun openRepaymentDialog(borrower: ChittyBorrower) {
        _showRepaymentDialog.value = borrower
    }

    fun closeRepaymentDialog() {
        _showRepaymentDialog.value = null
    }

    fun openAddAdminDialog() {
        _showAddAdminDialog.value = true
    }

    fun closeAddAdminDialog() {
        _showAddAdminDialog.value = false
    }

    fun openAddRuleDialog() {
        _showAddRuleDialog.value = true
    }

    fun closeAddRuleDialog() {
        _showAddRuleDialog.value = false
    }

    fun openManagePlayerDialog(player: Player) {
        _showManagePlayerDialog.value = player
    }

    fun closeManagePlayerDialog() {
        _showManagePlayerDialog.value = null
    }

    fun recordPayment(playerId: String, amount: Int, method: String) {
        repository.recordPlayerPayment(playerId, amount, method)
        closePaymentDialog()
        showToast("Payment of ₹$amount recorded successfully via $method!")
    }

    fun addNewPlayer(name: String, kannadaName: String, phone: String, squad: SquadType, role: String) {
        repository.addPlayer(name, kannadaName, phone, squad, role)
        closeAddPlayerDialog()
        showToast("Player $name registered successfully!")
    }

    fun deactivatePlayer(playerId: String) {
        val success = repository.deactivatePlayer(playerId)
        if (success) {
            showToast("Player $playerId profile deactivated (records preserved)")
        }
    }

    fun addAdmin(name: String, phone: String, role: String, email: String = "") {
        val (ok, msg) = repository.addAdmin(name, phone, role, email)
        showToast(msg)
        if (ok) closeAddAdminDialog()
    }

    fun removeAdmin(adminId: String) {
        val (ok, msg) = repository.removeAdmin(adminId)
        showToast(msg)
    }

    fun issueChittyLoan(playerId: String, principal: Int, rate: Int, dueDate: String) {
        val (ok, msg) = repository.issueChittyLoan(playerId, principal, rate, dueDate)
        showToast(msg)
        if (ok) closeLendLoanDialog()
    }

    fun collectChittyRepayment(loanId: String, amount: Int, method: String, ref: String) {
        val (ok, msg) = repository.collectChittyRepayment(loanId, amount, method, ref)
        showToast(msg)
        if (ok) closeRepaymentDialog()
    }

    fun addRule(titleEn: String, titleKn: String, descEn: String, descKn: String, category: String, isPublished: Boolean) {
        repository.addRule(titleEn, titleKn, descEn, descKn, category, isPublished)
        closeAddRuleDialog()
        showToast("Rule added successfully!")
    }

    fun deleteRule(id: Int) {
        repository.deleteRule(id)
        showToast("Rule #$id deleted")
    }

    fun toggleRulePublish(id: Int) {
        repository.toggleRulePublish(id)
        showToast("Rule publish state updated")
    }

    fun getFundSummary(): MonthlyFundSummary {
        return repository.getFundSummary()
    }

    fun getShareableReport(): String {
        return repository.generateShareableReport()
    }

    fun showToast(msg: String) {
        _toastMessage.value = msg
    }

    fun clearToast() {
        _toastMessage.value = null
    }
}
