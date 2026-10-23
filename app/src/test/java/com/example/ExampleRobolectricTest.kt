package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.model.Language
import com.example.model.SquadType
import com.example.viewmodel.KmplViewModel
import com.example.viewmodel.Screen
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("KMPL Fund", appName)
  }

  @Test
  fun `verify initial repository players and fund summary`() {
    val viewModel = KmplViewModel()
    val players = viewModel.players.value
    assertEquals(68, players.size)

    val fundSummary = viewModel.getFundSummary()
    assertEquals(68, fundSummary.totalCount)
    assertEquals(11200, fundSummary.expectedTarget)
    assertEquals(8400, fundSummary.collectedAmount)
    assertEquals(2800, fundSummary.pendingAmount)
  }

  @Test
  fun `verify navigation flow and language toggle`() {
    val viewModel = KmplViewModel()
    assertEquals(Screen.HOME, viewModel.currentScreen.value)

    viewModel.navigateTo(Screen.RULES)
    assertEquals(Screen.RULES, viewModel.currentScreen.value)

    viewModel.navigateBack()
    assertEquals(Screen.HOME, viewModel.currentScreen.value)

    assertEquals(Language.EN, viewModel.language.value)
    viewModel.toggleLanguage()
    assertEquals(Language.KN, viewModel.language.value)
  }

  @Test
  fun `verify payment recording updates player status`() {
    val viewModel = KmplViewModel()
    viewModel.recordPayment("KMPL-012", 100, "UPI")
    val player = viewModel.players.value.first { it.id == "KMPL-012" }
    assertTrue(player.isPaidThisMonth)
  }

  @Test
  fun `verify admin management and last admin protection`() {
    val viewModel = KmplViewModel()
    val initialAdminCount = viewModel.admins.value.size
    assertEquals(3, initialAdminCount)

    // Add new admin
    viewModel.addAdmin("Sunil Kumar", "+91 99000 11223", "Committee Auditor")
    assertEquals(4, viewModel.admins.value.size)

    // Remove added admin
    val added = viewModel.admins.value.first { it.phone == "+91 99000 11223" }
    viewModel.removeAdmin(added.id)
    assertEquals(3, viewModel.admins.value.size)

    // Remove until 1 left
    viewModel.removeAdmin(viewModel.admins.value[0].id)
    viewModel.removeAdmin(viewModel.admins.value[0].id)
    assertEquals(1, viewModel.admins.value.size)

    // Attempting to remove the last admin must fail safely
    val lastAdmin = viewModel.admins.value[0]
    viewModel.removeAdmin(lastAdmin.id)
    assertEquals(1, viewModel.admins.value.size)
  }

  @Test
  fun `verify chitty loan lending and 10 percent flat interest`() {
    val viewModel = KmplViewModel()
    val initialBorrowers = viewModel.chittyBorrowers.value.size
    assertEquals(7, initialBorrowers)

    // Attempting loan to Junior must fail
    val juniorPlayer = viewModel.players.value.first { it.squad == SquadType.JUNIOR }
    viewModel.issueChittyLoan(juniorPlayer.id, 5000, 10, "Nov 15, 2025")
    assertEquals(initialBorrowers, viewModel.chittyBorrowers.value.size)

    // Finding an eligible Senior player without an active loan
    val eligibleSenior = viewModel.players.value.first { player ->
      player.squad == SquadType.SENIOR && viewModel.chittyBorrowers.value.none { it.playerId == player.id }
    }
    viewModel.issueChittyLoan(eligibleSenior.id, 10000, 10, "Nov 15, 2025")
    assertEquals(initialBorrowers + 1, viewModel.chittyBorrowers.value.size)

    val newLoan = viewModel.chittyBorrowers.value.last()
    assertEquals(10000, newLoan.principal)
    assertEquals(10, newLoan.ratePercent)
    assertEquals(1000, newLoan.interestAmount) // 10% of 10000 = 1000
    assertEquals(11000, newLoan.remainingDue) // 10000 + 1000 = 11000
  }

  @Test
  fun `verify chitty repayment updates remaining due and status`() {
    val viewModel = KmplViewModel()
    // CH-01 has principal 5000, interest 500, remainingDue 5500
    val loan = viewModel.chittyBorrowers.value.first { it.id == "CH-01" }
    val initialRemaining = loan.remainingDue
    assertEquals(5500, initialRemaining)

    // Partial repayment of ₹2,500
    viewModel.collectChittyRepayment(loan.id, 2500, "UPI", "UPI123456")
    val updatedLoan = viewModel.chittyBorrowers.value.first { it.id == "CH-01" }
    assertEquals(3000, updatedLoan.remainingDue)
    assertEquals(2500, updatedLoan.repaidAmount)

    // Final settlement of ₹3,000
    viewModel.collectChittyRepayment(loan.id, 3000, "Cash", "REC999")
    val settledLoan = viewModel.chittyBorrowers.value.first { it.id == "CH-01" }
    assertEquals(0, settledLoan.remainingDue)
    assertEquals(com.example.model.PaymentStatus.PAID, settledLoan.status)
  }

  @Test
  fun `verify player deactivation marks inactive while preserving count`() {
    val viewModel = KmplViewModel()
    val initialCount = viewModel.players.value.size
    viewModel.deactivatePlayer("KMPL-030")
    assertEquals(initialCount, viewModel.players.value.size)
    val player = viewModel.players.value.first { it.id == "KMPL-030" }
    assertEquals(false, player.isActive)
  }
}
