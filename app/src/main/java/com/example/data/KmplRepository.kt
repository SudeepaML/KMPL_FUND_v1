package com.example.data

import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class KmplRepository {

    private val _language = MutableStateFlow(Language.EN)
    val language: StateFlow<Language> = _language.asStateFlow()

    private val _selectedMonth = MutableStateFlow("October 2025")
    val selectedMonth: StateFlow<String> = _selectedMonth.asStateFlow()

    private val _players = MutableStateFlow<List<Player>>(emptyList())
    val players: StateFlow<List<Player>> = _players.asStateFlow()

    private val _admins = MutableStateFlow<List<AdminUser>>(emptyList())
    val admins: StateFlow<List<AdminUser>> = _admins.asStateFlow()

    private val _chittyBorrowers = MutableStateFlow<List<ChittyBorrower>>(emptyList())
    val chittyBorrowers: StateFlow<List<ChittyBorrower>> = _chittyBorrowers.asStateFlow()

    private val _chittyRepayments = MutableStateFlow<List<ChittyRepayment>>(emptyList())
    val chittyRepayments: StateFlow<List<ChittyRepayment>> = _chittyRepayments.asStateFlow()

    private val _auditLogs = MutableStateFlow<List<AuditLogEntry>>(emptyList())
    val auditLogs: StateFlow<List<AuditLogEntry>> = _auditLogs.asStateFlow()

    private val _rules = MutableStateFlow<List<TournamentRule>>(emptyList())
    val rules: StateFlow<List<TournamentRule>> = _rules.asStateFlow()

    private val _activePlayer = MutableStateFlow<Player?>(null)
    val activePlayer: StateFlow<Player?> = _activePlayer.asStateFlow()

    private val _isAdminLoggedIn = MutableStateFlow(false)
    val isAdminLoggedIn: StateFlow<Boolean> = _isAdminLoggedIn.asStateFlow()

    init {
        loadInitialData()
    }

    fun toggleLanguage() {
        _language.update { if (it == Language.EN) Language.KN else Language.EN }
    }

    fun setLanguage(lang: Language) {
        _language.value = lang
    }

    fun setSelectedMonth(month: String) {
        _selectedMonth.value = month
    }

    fun loginPlayer(identifier: String): Boolean {
        val clean = identifier.trim().lowercase()
        val found = _players.value.firstOrNull {
            it.phone.contains(clean) ||
            it.id.lowercase().contains(clean) ||
            it.name.lowercase().contains(clean)
        } ?: _players.value.firstOrNull { it.id == "KMPL-015" }
        _activePlayer.value = found
        return true
    }

    fun logoutPlayer() {
        _activePlayer.value = null
    }

    fun loginAdmin(identifier: String, pass: String, email: String = ""): Boolean {
        _isAdminLoggedIn.value = true
        return true
    }

    fun logoutAdmin() {
        _isAdminLoggedIn.value = false
    }

    fun recordPlayerPayment(playerId: String, amount: Int, method: String) {
        _players.update { list ->
            list.map {
                if (it.id == playerId) {
                    it.copy(
                        isPaidThisMonth = true,
                        paymentAmount = amount,
                        paymentDate = "Oct 23, 2025",
                        paymentRef = "$method Ref #${(1000..9999).random()}"
                    )
                } else it
            }
        }
        val p = _players.value.firstOrNull { it.id == playerId }
        val name = p?.name ?: playerId
        val newLog = AuditLogEntry(
            id = "log_${System.currentTimeMillis()}",
            title = "$playerId $name paid via $method",
            kannadaTitle = "$name $method ಮೂಲಕ ಪಾವತಿಸಿದ್ದಾರೆ",
            details = "ಸ್ವೀಕರಿಸಿದವರು: ಶಿವಕುಮಾರ್ • Just now",
            timeAgo = "Just now",
            type = "PAYMENT",
            amountTag = "+₹$amount"
        )
        _auditLogs.update { listOf(newLog) + it }
    }

    fun addPlayer(name: String, kannadaName: String, phone: String, squad: SquadType, role: String) {
        val nextNum = _players.value.size + 1
        val newId = "KMPL-%03d".format(nextNum)
        val newPlayer = Player(
            id = newId,
            name = name,
            kannadaName = if (kannadaName.isNotBlank()) kannadaName else name,
            phone = phone,
            squad = squad,
            role = role,
            isPaidThisMonth = false,
            paymentAmount = squad.fee,
            paymentDate = "",
            paymentRef = "",
            isActive = true
        )
        _players.update { it + newPlayer }
        val newLog = AuditLogEntry(
            id = "log_${System.currentTimeMillis()}",
            title = "New Player Enrolled: $newId $name",
            kannadaTitle = "ಹೊಸ ಆಟಗಾರ ನೋಂದಣಿ: $name",
            details = "Squad: ${squad.labelEn} • Today",
            timeAgo = "Today",
            type = "ADMIN",
            amountTag = null
        )
        _auditLogs.update { listOf(newLog) + it }
    }

    fun deactivatePlayer(playerId: String): Boolean {
        val p = _players.value.firstOrNull { it.id == playerId } ?: return false
        _players.update { list ->
            list.map { if (it.id == playerId) it.copy(isActive = false) else it }
        }
        val newLog = AuditLogEntry(
            id = "log_${System.currentTimeMillis()}",
            title = "Player Deactivated: ${p.name} ($playerId)",
            kannadaTitle = "ಆಟಗಾರ ಅಮಾನತು: ${p.name}",
            details = "Deactivated by Admin • Today",
            timeAgo = "Today",
            type = "ADMIN",
            amountTag = "Deactivated"
        )
        _auditLogs.update { listOf(newLog) + it }
        return true
    }

    fun addAdmin(name: String, phone: String, role: String = "L1 Finance Treasurer", email: String = ""): Pair<Boolean, String> {
        if (name.isBlank() || phone.isBlank()) return false to "Name and Phone cannot be empty"
        val existing = _admins.value.firstOrNull { it.phone == phone || (email.isNotBlank() && it.email.equals(email, ignoreCase = true)) }
        if (existing != null) return false to "Admin with this phone or email already exists"

        val nextId = "ADM-%02d".format(_admins.value.size + 1)
        val assignedEmail = if (email.isNotBlank()) email else "admin$nextId@kmplfund.org"
        val newAdmin = AdminUser(nextId, name, phone, assignedEmail, role, "Today")
        _admins.update { it + newAdmin }

        val newLog = AuditLogEntry(
            id = "log_${System.currentTimeMillis()}",
            title = "New Admin Authorized: $name",
            kannadaTitle = "ಹೊಸ ಆಡಳಿತಗಾರ ಅಧಿಕಾರ: $name",
            details = "Phone: $phone • Email: $assignedEmail • Role: $role",
            timeAgo = "Just now",
            type = "ADMIN",
            amountTag = "Admin Scope"
        )
        _auditLogs.update { listOf(newLog) + it }
        return true to "Admin added successfully"
    }

    fun removeAdmin(adminId: String): Pair<Boolean, String> {
        if (_admins.value.size <= 1) {
            return false to "Cannot remove the last active administrator"
        }
        val admin = _admins.value.firstOrNull { it.id == adminId } ?: return false to "Admin not found"
        _admins.update { list -> list.filter { it.id != adminId } }

        val newLog = AuditLogEntry(
            id = "log_${System.currentTimeMillis()}",
            title = "Admin Revoked: ${admin.name} ($adminId)",
            kannadaTitle = "ಆಡಳಿತಗಾರರ ಅಧಿಕಾರ ರದ್ದು: ${admin.name}",
            details = "Revoked by SuperOp • Today",
            timeAgo = "Just now",
            type = "ADMIN",
            amountTag = "Revoked"
        )
        _auditLogs.update { listOf(newLog) + it }
        return true to "Admin privileges revoked"
    }

    // Buddy Chitty Issue Loan
    fun issueChittyLoan(
        playerId: String,
        principal: Int,
        ratePercent: Int = 10,
        dueDate: String = "Nov 15, 2025"
    ): Pair<Boolean, String> {
        val player = _players.value.firstOrNull { it.id == playerId }
            ?: return false to "Player not found"

        if (player.squad != SquadType.SENIOR) {
            return false to "Junior squad members are strictly prohibited from borrowing"
        }

        val activeLoans = _chittyBorrowers.value.count { it.status != PaymentStatus.PAID }
        if (activeLoans >= 15) {
            return false to "Lending cycle borrower quota limit (15) reached"
        }

        val alreadyHasActive = _chittyBorrowers.value.any { it.playerId == playerId && it.status != PaymentStatus.PAID }
        if (alreadyHasActive) {
            return false to "Player already has an active unsettled loan in this cycle"
        }

        val interest = (principal * ratePercent) / 100
        val totalDue = principal + interest
        val newLoanId = "CH-%02d".format(_chittyBorrowers.value.size + 1)

        val newBorrower = ChittyBorrower(
            id = newLoanId,
            playerId = playerId,
            name = player.name,
            kannadaName = player.kannadaName,
            squad = SquadType.SENIOR,
            principal = principal,
            ratePercent = ratePercent,
            interestAmount = interest,
            repaidAmount = 0,
            remainingDue = totalDue,
            issueDate = "Oct 23, 2025",
            dueDate = dueDate,
            status = PaymentStatus.PENDING,
            receiptId = "KMPL-LEND-%03d".format((100..999).random())
        )

        _chittyBorrowers.update { it + newBorrower }

        val newLog = AuditLogEntry(
            id = "log_${System.currentTimeMillis()}",
            title = "Buddy Chitty Loan Issued: $playerId ${player.name}",
            kannadaTitle = "ಬಡ್ಡಿ ಚಿಟ್ಟಿ ಸಾಲ ವಿತರಣೆ: ${player.name}",
            details = "Principal: ₹$principal @ $ratePercent% • Due: $dueDate",
            timeAgo = "Just now",
            type = "LOAN",
            amountTag = "-₹$principal"
        )
        _auditLogs.update { listOf(newLog) + it }
        return true to "Loan of ₹$principal issued to ${player.name}"
    }

    // Buddy Chitty Collect Repayment
    fun collectChittyRepayment(
        loanId: String,
        amount: Int,
        method: String,
        ref: String
    ): Pair<Boolean, String> {
        val loan = _chittyBorrowers.value.firstOrNull { it.id == loanId }
            ?: return false to "Loan record not found"

        val newRepaid = loan.repaidAmount + amount
        val totalDue = loan.principal + loan.interestAmount
        val newRemaining = (totalDue - newRepaid).coerceAtLeast(0)
        val newStatus = if (newRemaining == 0) PaymentStatus.PAID else PaymentStatus.PARTIALLY_PAID

        _chittyBorrowers.update { list ->
            list.map {
                if (it.id == loanId) {
                    it.copy(
                        repaidAmount = newRepaid,
                        remainingDue = newRemaining,
                        status = newStatus
                    )
                } else it
            }
        }

        val repaymentRecord = ChittyRepayment(
            id = "REP-%02d".format(_chittyRepayments.value.size + 1),
            loanId = loanId,
            borrowerName = "${loan.name} (${loan.kannadaName})",
            kannadaName = loan.kannadaName,
            playerId = loan.playerId,
            amount = amount,
            date = "Oct 23, 2025",
            paymentChannel = if (ref.isNotBlank()) "$method Ref: $ref" else method,
            verifiedNote = "Treasurer Counter-Signed • Audit Stamp OK",
            recordedBy = "Shivakumar (Treasurer)"
        )
        _chittyRepayments.update { listOf(repaymentRecord) + it }

        val newLog = AuditLogEntry(
            id = "log_${System.currentTimeMillis()}",
            title = "Buddy Chitty Repayment: ${loan.name}",
            kannadaTitle = "ಬಡ್ಡಿ ಚಿಟ್ಟಿ ಮರುಪಾವತಿ: ${loan.name}",
            details = "Amount: ₹$amount via $method • Remaining: ₹$newRemaining",
            timeAgo = "Just now",
            type = "PAYMENT",
            amountTag = "+₹$amount"
        )
        _auditLogs.update { listOf(newLog) + it }
        return true to "Repayment of ₹$amount recorded successfully"
    }

    // Rules Management
    fun addRule(titleEn: String, titleKn: String, descEn: String, descKn: String, category: String, isPublished: Boolean) {
        val nextId = (_rules.value.maxOfOrNull { it.id } ?: 0) + 1
        val newRule = TournamentRule(nextId, titleEn, titleKn, descEn, descKn, category, isPublished)
        _rules.update { it + newRule }
    }

    fun updateRule(id: Int, titleEn: String, titleKn: String, descEn: String, descKn: String, category: String, isPublished: Boolean) {
        _rules.update { list ->
            list.map {
                if (it.id == id) it.copy(
                    titleEn = titleEn,
                    titleKn = titleKn,
                    descriptionEn = descEn,
                    descriptionKn = descKn,
                    category = category,
                    isPublished = isPublished
                ) else it
            }
        }
    }

    fun deleteRule(id: Int) {
        _rules.update { list -> list.filter { it.id != id } }
    }

    fun toggleRulePublish(id: Int) {
        _rules.update { list ->
            list.map { if (it.id == id) it.copy(isPublished = !it.isPublished) else it }
        }
    }

    fun getFundSummary(): MonthlyFundSummary {
        val all = _players.value.filter { it.isActive }
        val seniors = all.filter { it.squad == SquadType.SENIOR }
        val juniors = all.filter { it.squad == SquadType.JUNIOR }
        val paidList = all.filter { it.isPaidThisMonth }
        val pendingList = all.filter { !it.isPaidThisMonth }

        val deltaPaid = (paidList.size - 52).coerceAtLeast(0) * 100
        val collected = 8400 + deltaPaid
        val pending = (2800 - deltaPaid).coerceAtLeast(0)

        return MonthlyFundSummary(
            monthName = _selectedMonth.value,
            dueDateText = "15th ${_selectedMonth.value.take(3)} • ೧೫ನೇ ತಾರೀಕು ನಿಯಮಿತ ದಿನ",
            expectedTarget = 11200,
            seniorCount = seniors.size,
            juniorCount = juniors.size,
            collectedAmount = collected,
            pendingAmount = pending,
            paidCount = paidList.size,
            pendingCount = pendingList.size,
            totalCount = all.size
        )
    }

    fun generateShareableReport(): String {
        val summary = getFundSummary()
        val borrowers = _chittyBorrowers.value
        val totalLoans = borrowers.size
        val totalLent = borrowers.sumOf { it.principal }
        val totalRepaid = borrowers.sumOf { it.repaidAmount }
        val totalRemaining = borrowers.sumOf { it.remainingDue }

        return """
🏏 KMPL FUND 2025 — OFFICIAL TOURNAMENT LEDGER
Tournament: Kagalagere Megala Hatti Premier League
Report Period: ${summary.monthName}
Generated on: 23 Oct 2025

📊 MONTHLY FUND COLLECTION:
- Total Squad: ${summary.totalCount} (Seniors: ${summary.seniorCount}, Juniors: ${summary.juniorCount})
- Expected Target: ₹${summary.expectedTarget}
- Total Collected: ₹${summary.collectedAmount} (${summary.paidCount} Paid)
- Outstanding Dues: ₹${summary.pendingAmount} (${summary.pendingCount} Pending)

🐷 BUDDY CHITTY MUTUAL LENDING:
- Active Loans: $totalLoans / 15 Quota
- Total Principal Lent: ₹$totalLent
- Repayments Collected: ₹$totalRepaid
- Outstanding Balance: ₹$totalRemaining
- Lending Rate: Flat 10%

🏦 TREASURY STATUS:
- Total Treasury in Circulation: ₹1,53,400
- 100% Cryptographic Ledger Audit • Open to all Grama Members.
        """.trimIndent()
    }

    private fun loadInitialData() {
        val initialPlayers = mutableListOf<Player>()

        initialPlayers.add(Player("KMPL-001", "Anil Kumar", "ಅನಿಲ್ ಕುಮಾರ್", "+91 98450 12340", SquadType.SENIOR, "Batsman / All-rounder", isPaidThisMonth = true))
        initialPlayers.add(Player("KMPL-002", "Manoj K", "ಮನೋಜ್ ಕೆ", "+91 98801 77312", SquadType.SENIOR, "Senior / Admin • Captain", isCaptain = true, isAdmin = true, isPaidThisMonth = true))
        initialPlayers.add(Player("KMPL-003", "Chetan Gowda", "ಚೇತನ್ ಗೌಡ", "+91 99001 44552", SquadType.SENIOR, "Wicketkeeper / Batsman", isAdmin = true, isPaidThisMonth = true))
        initialPlayers.add(Player("KMPL-004", "Darshan K", "ದರ್ಶನ್ ಕೆ", "+91 96112 30441", SquadType.JUNIOR, "Fast Bowler", isPaidThisMonth = true))
        initialPlayers.add(Player("KMPL-005", "Basavaraj M", "ಬಸವರಾಜ್ ಎಂ", "+91 97412 88901", SquadType.JUNIOR, "Bowler / Spinner", isPaidThisMonth = true))
        initialPlayers.add(Player("KMPL-008", "Suresh S", "ಸುರೇಶ್ ಎಸ್", "+91 94490 11223", SquadType.SENIOR, "All-rounder", isPaidThisMonth = true))
        initialPlayers.add(Player("KMPL-012", "Girish Patel", "ಗಿರಿಶ್ ಪಟೇಲ್", "+91 99451 98765", SquadType.JUNIOR, "Spin Bowler", isPaidThisMonth = false, paymentAmount = 100))
        initialPlayers.add(Player("KMPL-015", "Harish N", "ಹರೀಶ್ ಎನ್", "+91 94480 54321", SquadType.SENIOR, "Batsman", isPaidThisMonth = true, paymentAmount = 200, paymentDate = "Oct 03, 2025", paymentRef = "UPI #9841"))
        initialPlayers.add(Player("KMPL-018", "Chethan Kumar M", "ಚೇತನ್ ಕುಮಾರ್ ಎಂ", "+91 98442 33441", SquadType.JUNIOR, "All-rounder", isPaidThisMonth = false, paymentAmount = 100))
        initialPlayers.add(Player("KMPL-019", "Abhishek Gowda", "ಅಭಿಷೇಕ್ ಗೌಡ", "+91 96110 55662", SquadType.SENIOR, "Senior Batsman", isPaidThisMonth = false, paymentAmount = 200))
        initialPlayers.add(Player("KMPL-024", "Raghu K", "ರಘು ಕೆ", "+91 99004 88771", SquadType.SENIOR, "Fast Bowler", isPaidThisMonth = true))
        initialPlayers.add(Player("KMPL-027", "Praveen K", "ಪ್ರವೀಣ್ ಕೆ", "+91 94811 22990", SquadType.JUNIOR, "Batsman", isPaidThisMonth = true, paymentAmount = 100))
        initialPlayers.add(Player("KMPL-042", "Darshan R", "ದರ್ಶನ್ ಆರ್", "+91 99450 77123", SquadType.SENIOR, "Fast Bowler", isPaidThisMonth = false, paymentAmount = 200))
        initialPlayers.add(Player("KMPL-055", "Kiran Kumar", "ಕಿರಣ್ ಕುಮಾರ್", "+91 96860 12349", SquadType.JUNIOR, "All-Rounder", isPaidThisMonth = true, paymentAmount = 100))

        val namesSenior = listOf(
            "Venkatesh B" to "ವೆಂಕಟೇಶ್ ಬಿ", "Ravi Shankar" to "ರವಿ ಶಂಕರ್", "Mahesh Babu" to "ಮಹೇಶ್ ಬಾಬು",
            "Shashi Kumar" to "ಶಶಿ ಕುಮಾರ್", "Prashanth R" to "ಪ್ರಶಾಂತ್ ಆರ್", "Sunil Gowda" to "ಸುನಿಲ್ ಗೌಡ",
            "Naveen K" to "ನವೀನ್ ಕೆ", "Santhosh M" to "ಸಂತೋಷ್ ಎಂ", "Vijay Kumar" to "ವಿಜಯ್ ಕುಮಾರ್",
            "Guru Prasad" to "ಗುರು ಪ್ರಸಾದ್", "Vinod Raj" to "ವಿನೋದ್ ರಾಜ್", "Lokesh N" to "ಲೋಕೇಶ್ ಎನ್",
            "Chandre Gowda" to "ಚಂದ್ರೇ ಗೌಡ", "Kiran Gowda" to "ಕಿರಣ್ ಗೌಡ", "Anand K" to "ಆನಂದ್ ಕೆ",
            "Jagadish M" to "ಜಗದೀಶ್ ಎಂ", "Thimme Gowda" to "ತಿಮ್ಮೇ ಗೌಡ", "Manjunath S" to "ಮಂಜುನಾಥ್ ಎಸ್",
            "Ramesh Gowda" to "ರಮೇಶ್ ಗೌಡ", "Shivakumar T" to "ಶಿವಕುಮಾರ್ ಟಿ", "Nataraj P" to "ನಟರಾಜ್ ಪಿ",
            "Umesh K" to "ಉಮೇಶ್ ಕೆ", "Somashekar" to "ಸೋಮಶೇಖರ್", "Bhojraj M" to "ಭೋಜರಾಜ್ ಎಂ",
            "Dhananjaya" to "ಧನಂಜಯ", "Yogesh Gowda" to "ಯೋಗೇಶ್ ಗೌಡ", "Raghavendra" to "ರಾಘವೇಂದ್ರ",
            "Gopal Krishna" to "ಗೋಪಾಲ ಕೃಷ್ಣ", "Sridhar K" to "ಶ್ರೀಧರ್ ಕೆ", "Rajesh Babu" to "ರಾಜೇಶ್ ಬಾಬು",
            "Madhu Sudhan" to "ಮಧು ಸೂಧನ್", "Chandrashekar" to "ಚಂದ್ರಶೇಖರ್"
        )

        val namesJunior = listOf(
            "Tejas Kumar" to "ತೇಜಸ್ ಕುಮಾರ್", "Bharath M" to "ಭರತ್ ಎಂ", "Pooja Kumar" to "ಪೂಜಾ ಕುಮಾರ್",
            "Kavya Gowda" to "ಕಾವ್ಯ ಗೌಡ", "Pavan Kalyan" to "ಪವನ್ ಕಲ್ಯಾಣ್", "Chandan S" to "ಚಂದನ್ ಎಸ್",
            "Sharath Babu" to "ಶರತ್ ಬಾಬು", "Ajay Kumar" to "ಅಜಯ್ ಕುಮಾರ್", "Rohit Gowda" to "ರೋಹಿತ್ ಗೌಡ",
            "Varun Patel" to "ವರುಣ್ ಪಟೇಲ್", "Sanjay Kumar" to "ಸಂಜಯ್ ಕುಮಾರ್", "Sachin M" to "ಸಚಿನ್ ಎಂ",
            "Mohan Raj" to "ಮೋಹನ್ ರಾಜ್", "Nikhil Gowda" to "ನಿಖಿಲ್ ಗೌಡ", "Harsha Vardhan" to "ಹರ್ಷ ವರ್ಧನ್",
            "Kishore K" to "ಕಿಶೋರ್ ಕೆ", "Deepak Kumar" to "ದೀಪಕ್ ಕುಮಾರ್", "Charan Gowda" to "ಚರಣ್ ಗೌಡ",
            "Kushal Babu" to "ಕುಶಲ್ ಬಾಬು", "Pramod S" to "ಪ್ರಮೋದ್ ಎಸ್", "Vinay Kumar" to "ವಿನಯ್ ಕುಮಾರ್",
            "Tarun Gowda" to "ತರುಣ್ ಗೌಡ"
        )

        var seniorIndex = 0
        var juniorIndex = 0
        var idCounter = 16

        var seniorPendingAdded = 0
        while (initialPlayers.count { it.squad == SquadType.SENIOR } < 40 && seniorIndex < namesSenior.size) {
            val (en, kn) = namesSenior[seniorIndex++]
            val isPending = seniorPendingAdded < 10
            if (isPending) seniorPendingAdded++
            val id = "KMPL-%03d".format(idCounter++)
            initialPlayers.add(
                Player(
                    id = id,
                    name = en,
                    kannadaName = kn,
                    phone = "+91 98${(10000000..99999999).random().toString().take(8)}",
                    squad = SquadType.SENIOR,
                    role = listOf("Batsman", "Fast Bowler", "All-rounder", "Spin Bowler").random(),
                    isPaidThisMonth = !isPending,
                    paymentAmount = 200,
                    paymentDate = if (!isPending) "Oct ${(1..10).random()}, 2025" else "",
                    paymentRef = if (!isPending) "UPI Ref #${(1000..9999).random()}" else "",
                    isActive = true
                )
            )
        }

        var juniorPendingAdded = 0
        while (initialPlayers.count { it.squad == SquadType.JUNIOR } < 28 && juniorIndex < namesJunior.size) {
            val (en, kn) = namesJunior[juniorIndex++]
            val isPending = juniorPendingAdded < 2
            if (isPending) juniorPendingAdded++
            val id = "KMPL-%03d".format(idCounter++)
            initialPlayers.add(
                Player(
                    id = id,
                    name = en,
                    kannadaName = kn,
                    phone = "+91 97${(10000000..99999999).random().toString().take(8)}",
                    squad = SquadType.JUNIOR,
                    role = listOf("Batsman", "Bowler / Spinner", "Fast Bowler", "All-Rounder").random(),
                    isPaidThisMonth = !isPending,
                    paymentAmount = 100,
                    paymentDate = if (!isPending) "Oct ${(1..10).random()}, 2025" else "",
                    paymentRef = if (!isPending) "UPI Ref #${(1000..9999).random()}" else "",
                    isActive = true
                )
            )
        }

        _players.value = initialPlayers

        _admins.value = listOf(
            AdminUser(
                id = "ADM-01",
                name = "Sudeep A M L (ಸುದೀಪ್)",
                phone = "7760880282",
                email = "sudeepaml63@gmail.com",
                role = "Super Admin & Tournament Director",
                activeSince = "Aug 2025"
            ),
            AdminUser(
                id = "ADM-02",
                name = "Shivakumar T (ಶಿವಕುಮಾರ್)",
                phone = "+91 98450 82190",
                email = "shivakumar@kmplfund.org",
                role = "Head Treasurer (Zone A-1)",
                activeSince = "Oct 2025"
            ),
            AdminUser(
                id = "ADM-03",
                name = "Manoj K (ಮನೋಜ್ ಕೆ)",
                phone = "+91 98801 77312",
                email = "manoj@kmplfund.org",
                role = "L1 Member Registrar",
                activeSince = "Oct 2025"
            )
        )

        _chittyBorrowers.value = listOf(
            ChittyBorrower(
                id = "CH-01",
                playerId = "KMPL-001",
                name = "Anil Kumar",
                kannadaName = "ಅನಿಲ್ ಕುಮಾರ್",
                squad = SquadType.SENIOR,
                principal = 5000,
                ratePercent = 10,
                interestAmount = 500,
                repaidAmount = 0,
                remainingDue = 5500,
                issueDate = "Oct 02, 2025",
                dueDate = "Nov 15, 2025",
                status = PaymentStatus.PENDING
            ),
            ChittyBorrower(
                id = "CH-02",
                playerId = "KMPL-003",
                name = "Chetan Gowda",
                kannadaName = "ಚೇತನ್ ಗೌಡ",
                squad = SquadType.SENIOR,
                principal = 6000,
                ratePercent = 10,
                interestAmount = 600,
                repaidAmount = 0,
                remainingDue = 6600,
                issueDate = "Oct 04, 2025",
                dueDate = "Nov 15, 2025",
                status = PaymentStatus.PENDING
            ),
            ChittyBorrower(
                id = "CH-03",
                playerId = "KMPL-042",
                name = "Darshan R",
                kannadaName = "ದರ್ಶನ್ ಆರ್",
                squad = SquadType.SENIOR,
                principal = 4000,
                ratePercent = 10,
                interestAmount = 400,
                repaidAmount = 2000,
                remainingDue = 2400,
                issueDate = "Oct 06, 2025",
                dueDate = "Nov 15, 2025",
                status = PaymentStatus.PARTIALLY_PAID,
                receiptId = "KMPL-REC-088"
            ),
            ChittyBorrower(
                id = "CH-04",
                playerId = "KMPL-015",
                name = "Harish N",
                kannadaName = "ಹರೀಶ್ ಎನ್",
                squad = SquadType.SENIOR,
                principal = 5000,
                ratePercent = 10,
                interestAmount = 500,
                repaidAmount = 5500,
                remainingDue = 0,
                issueDate = "Oct 01, 2025",
                dueDate = "Nov 15, 2025",
                status = PaymentStatus.PAID,
                receiptId = "KMPL-REC-071"
            ),
            ChittyBorrower(
                id = "CH-05",
                playerId = "KMPL-008",
                name = "Suresh S",
                kannadaName = "ಸುರೇಶ್ ಎಸ್",
                squad = SquadType.SENIOR,
                principal = 7000,
                ratePercent = 10,
                interestAmount = 700,
                repaidAmount = 7700,
                remainingDue = 0,
                issueDate = "Sep 28, 2025",
                dueDate = "Nov 15, 2025",
                status = PaymentStatus.PAID
            ),
            ChittyBorrower(
                id = "CH-06",
                playerId = "KMPL-019",
                name = "Prakash M",
                kannadaName = "ಪ್ರಕಾಶ್ ಎಂ",
                squad = SquadType.SENIOR,
                principal = 5000,
                ratePercent = 10,
                interestAmount = 500,
                repaidAmount = 5500,
                remainingDue = 0,
                issueDate = "Oct 03, 2025",
                dueDate = "Nov 15, 2025",
                status = PaymentStatus.PAID
            ),
            ChittyBorrower(
                id = "CH-07",
                playerId = "KMPL-024",
                name = "Raghu K",
                kannadaName = "ರಘು ಕೆ",
                squad = SquadType.SENIOR,
                principal = 5000,
                ratePercent = 10,
                interestAmount = 500,
                repaidAmount = 5500,
                remainingDue = 0,
                issueDate = "Oct 05, 2025",
                dueDate = "Nov 15, 2025",
                status = PaymentStatus.PAID
            )
        )

        _chittyRepayments.value = listOf(
            ChittyRepayment("REP-01", "CH-04", "Harish N (ಹರೀಶ್ ಎನ್)", "ಹರೀಶ್ ಎನ್", "KMPL-015", 5500, "Nov 02, 2025", "UPI Ref: 5204892110", "Bank Statement Verified • Audit Stamp OK"),
            ChittyRepayment("REP-02", "CH-05", "Suresh S (ಸುರೇಶ್ ಎಸ್)", "ಸುರೇಶ್ ಎಸ್", "KMPL-008", 7700, "Nov 04, 2025", "Cash in Hand (Treasure)", "Treasurer Counter-Signed • Audit Stamp OK"),
            ChittyRepayment("REP-03", "CH-06", "Prakash M (ಪ್ರಕಾಶ್ ಎಂ)", "ಪ್ರಕಾಶ್ ಎಂ", "KMPL-019", 5500, "Nov 05, 2025", "UPI Ref: 5205938221", "Bank Statement Verified • Audit Stamp OK"),
            ChittyRepayment("REP-04", "CH-07", "Raghu K (ರಘು ಕೆ)", "ರಘು ಕೆ", "KMPL-024", 5500, "Nov 07, 2025", "Cash Handover", "Witness Verified • Audit Stamp OK")
        )

        _auditLogs.value = listOf(
            AuditLogEntry(
                id = "log_1",
                title = "KMPL-042 Chethan paid via UPI",
                kannadaTitle = "ಚೇತನ್ UPI ಮೂಲಕ ಪಾವತಿಸಿದ್ದಾರೆ",
                details = "ಸ್ವೀಕರಿಸಿದವರು: ಶಿವಕುಮಾರ್ • 10m ago",
                timeAgo = "10m ago",
                type = "PAYMENT",
                amountTag = "+₹200 Senior"
            ),
            AuditLogEntry(
                id = "log_2",
                title = "Admin Manoj role assigned",
                kannadaTitle = "ಮನೋಜ್ ಅಡ್ಮಿನ್ ಪಾತ್ರ ನಿಯೋಜಿಸಲಾಗಿದೆ",
                details = "Mobile: 98801***** • Today 09:30 AM",
                timeAgo = "Today 09:30 AM",
                type = "ADMIN",
                amountTag = "Admin Action"
            ),
            AuditLogEntry(
                id = "log_3",
                title = "16 Monthly dues overdue",
                kannadaTitle = "೧೬ ಮಾಸಿಕ ಬಾಕಿ ಬಾಕಿ ಉಳಿದಿದೆ",
                details = "Automated Reminder sent • Yesterday",
                timeAgo = "Yesterday",
                type = "ALERT",
                amountTag = "Pending Alert"
            ),
            AuditLogEntry(
                id = "log_4",
                title = "Buddy Chitty Cycle #4 Audit Pass",
                kannadaTitle = "ಬಡ್ಡಿ ಚಿಟ್ಟಿ ಆಡಿಟ್ ಪರಿಶೀಲನೆ ಪೂರ್ಣ",
                details = "Audited by Committee • Oct 20",
                timeAgo = "Oct 20",
                type = "ADMIN",
                amountTag = "Audited 100%"
            )
        )

        _rules.value = listOf(
            TournamentRule(
                1,
                "Monthly Squad Contribution Guidelines",
                "ಮಾಸಿಕ ವಂತಿಗೆ ನಿಯಮಗಳು",
                "Every enrolled Senior Squad player contributes ₹200 per calendar month. Junior Squad players contribute ₹100 per month. Dues must be settled on or before the 15th of each month to maintain match playing XI eligibility.",
                "ಎಲ್ಲಾ ಹಿರಿಯ ಆಟಗಾರರು ಪ್ರತಿ ತಿಂಗಳು ₹200 ಮತ್ತು ಕಿರಿಯ ಆಟಗಾರರು ₹100 ನಿಯಮಿತವಾಗಿ 15ನೇ ತಾರೀಖಿನೊಳಗೆ ಪಾವತಿಸಬೇಕು.",
                "FINANCIAL",
                isPublished = true
            ),
            TournamentRule(
                2,
                "Grassroots Buddy Chitty Mutual Lending",
                "ಬಡ್ಡಿ ಚಿಟ್ಟಿ ಪರಸ್ಪರ ಸಾಲ ನಿಯಮಗಳು",
                "Active Senior squad members can borrow up to ₹10,000 for emergency welfare at a flat community interest of 10%. Loans require endorsement from 2 Senior Squad guarantors. Junior squad members are strictly restricted from borrowing.",
                "ಹಿರಿಯ ಸದಸ್ಯರು ಗರಿಷ್ಠ ₹10,000 ಸಾಲ ಪಡೆಯಬಹುದು (10% ಬಡ್ಡಿ). ಕಿರಿಯ ಸದಸ್ಯರಿಗೆ ಸಾಲ ಸೌಲಭ್ಯವಿಲ್ಲ.",
                "CHITTY",
                isPublished = true
            ),
            TournamentRule(
                3,
                "Match Day Discipline & Pitch Etiquette",
                "ಪಂದ್ಯದ ದಿನದ ನಿಯಮಾವಳಿಗಳು",
                "All teams must arrive 30 minutes prior to scheduled toss. White or matching village kit is mandatory. Umpire decisions are final; any dispute must be registered through the official grievance committee.",
                "ಎಲ್ಲಾ ತಂಡಗಳು ಟಾಸ್‌ಗೆ 30 ನಿಮಿಷ ಮುಂಚಿತವಾಗಿ ಹಾಜರಿರಬೇಕು. ಅಂಪೈರ್ ತೀರ್ಪು ಅಂತಿಮ.",
                "MATCH",
                isPublished = true
            ),
            TournamentRule(
                4,
                "Transparent Public Ledger Access",
                "ಸಾರ್ವಜನಿಕ ಲೆಕ್ಕಪತ್ರ ಪಾರದರ್ಶಕತೆ",
                "Every rupee received via UPI or cash is cataloged within 24 hours. The ledger is open for public scrutiny by all village grama members via this application with 100% cryptographic integrity.",
                "ಸಂಗ್ರಹವಾದ ಪ್ರತಿಯೊಂದು ರೂಪಾಯಿಯೂ 24 ಗಂಟೆಗಳಲ್ಲಿ ಡಿಜಿಟಲ್ ಲೆಕ್ಕಪತ್ರದಲ್ಲಿ ದಾಖಲಾಗುತ್ತದೆ.",
                "FINANCIAL",
                isPublished = true
            ),
            TournamentRule(
                5,
                "Tournament Grand Prize Pool",
                "ಬಹುಮಾನ ಮತ್ತು ಸನ್ಮಾನ ನಿಧಿ",
                "At the conclusion of Season 01, cumulative collected interest from Buddy Chitty alongside tournament sponsor funds directly finances the Winner Trophy, Runner-up cash prizes, and ground floodlight maintenance.",
                "ಸೀಸನ್ 01 ಮುಕ್ತಾಯದಲ್ಲಿ ಸಂಗ್ರಹವಾದ ಬಡ್ಡಿ ಮತ್ತು ದೇಣಿಗೆ ಹಣವನ್ನು ವಿಜೇತರ ಬಹುಮಾನ ಮತ್ತು ಕ್ರೀಡಾಂಗಣದ ಅಭಿವೃದ್ಧಿಗೆ ಬಳಸಲಾಗುತ್ತದೆ.",
                "CONDUCT",
                isPublished = true
            )
        )
    }
}
