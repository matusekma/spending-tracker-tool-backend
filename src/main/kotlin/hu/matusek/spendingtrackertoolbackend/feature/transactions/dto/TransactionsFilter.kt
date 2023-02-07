package hu.matusek.spendingtrackertoolbackend.feature.transactions.dto

import hu.matusek.spendingtrackertoolbackend.domain.Category
import hu.matusek.spendingtrackertoolbackend.domain.Currency
import java.math.BigDecimal
import java.time.OffsetDateTime


data class TransactionsFilter(
    val category: Category?,
    val currency: Currency?,
    val paidFrom: OffsetDateTime?,
    val paidTo: OffsetDateTime?,
    val sumFrom: BigDecimal?,
    val sumTo: BigDecimal?,
    val summary: String?
)
