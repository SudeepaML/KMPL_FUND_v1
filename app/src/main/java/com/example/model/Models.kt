package com.example.model

enum class Language {
    EN, KN
}

enum class SquadType(val labelEn: String, val labelKn: String, val fee: Int) {
    SENIOR("Senior Squad", "ಹಿರಿಯರು", 200),
    JUNIOR("Junior Squad", "ಕಿರಿಯರು", 100)
}

enum class PaymentStatus {
    PAID, PENDING, PARTIALLY_PAID, OVERDUE
}

data class Player(
    val id: String, // e.g. "KMPL-001"
    val name: String,
    val kannadaName: String,
    val phone: String,
    val squad: SquadType,
    val role: String, // Batsman, Bowler, All-rounder, Wicketkeeper
    val isCaptain: Boolean = false,
    val isAdmin: Boolean = false,
    val isActive: Boolean = true,
    val isPaidThisMonth: Boolean = true,
    val paymentAmount: Int = 200,
    val paymentDate: String = "Oct 03, 2025",
    val paymentRef: String = "UPI Ref #9841",
    val joiningDate: String = "Oct 01, 2025"
)

data class AdminUser(
    val id: String,
    val name: String,
    val phone: String,
    val email: String = "admin@kmplfund.org",
    val role: String = "L1 Finance Treasurer",
    val activeSince: String = "Oct 2025"
)

data class ChittyBorrower(
    val id: String,
    val playerId: String,
    val name: String,
    val kannadaName: String,
    val squad: SquadType,
    val principal: Int,
    val ratePercent: Int = 10,
    val interestAmount: Int,
    val repaidAmount: Int,
    val remainingDue: Int,
    val issueDate: String,
    val dueDate: String,
    val status: PaymentStatus,
    val receiptId: String = "",
    val guarantors: String = "Suresh S & Manoj K"
)

data class ChittyRepayment(
    val id: String,
    val loanId: String = "CH-01",
    val borrowerName: String,
    val kannadaName: String,
    val playerId: String,
    val amount: Int,
    val date: String,
    val paymentChannel: String, // e.g. "UPI Ref: 5204892110" or "Cash in Hand"
    val verifiedNote: String = "Bank Statement Verified",
    val recordedBy: String = "Shivakumar (Treasurer)"
)

data class AuditLogEntry(
    val id: String,
    val title: String,
    val kannadaTitle: String,
    val details: String,
    val timeAgo: String,
    val type: String, // "PAYMENT", "ADMIN", "ALERT", "LOAN"
    val amountTag: String? = null
)

data class MonthlyFundSummary(
    val monthName: String,
    val dueDateText: String,
    val expectedTarget: Int,
    val seniorCount: Int,
    val juniorCount: Int,
    val collectedAmount: Int,
    val pendingAmount: Int,
    val paidCount: Int,
    val pendingCount: Int,
    val totalCount: Int
)

data class TreasurySnapshot(
    val totalCirculation: Int = 153400,
    val monthlyTotalCollected4Months: Int = 41200,
    val chittyLoansIssued: Int = 7,
    val chittyPrincipalLent: Int = 38500,
    val chittyCollectedToDate: Int = 24200,
    val chittyOutstanding: Int = 18150,
    val cumulativePrincipalLent: Int = 124000,
    val cumulativePrincipalRepaid: Int = 102000,
    val cumulativeInterest: Int = 10200,
    val cumulativeTotalRepayments: Int = 112200,
    val vaultReserve: Int = 18500
)

data class TournamentRule(
    val id: Int,
    val titleEn: String,
    val titleKn: String,
    val descriptionEn: String,
    val descriptionKn: String,
    val category: String, // "FINANCIAL", "MEMBERSHIP", "CHITTY", "MATCH", "CONDUCT"
    val isPublished: Boolean = true
)
