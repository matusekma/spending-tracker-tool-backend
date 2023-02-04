package hu.matusek.spendingtrackertoolbackend.feature.transactions.dto

import hu.matusek.spendingtrackertoolbackend.domain.Currency
import java.math.BigDecimal
import java.time.OffsetDateTime

data class CreateTransactionResponse(
    val id: Long,
    val summary: String,
    val category: String,
    val sum: BigDecimal,
    val currency: Currency,
    val paid: OffsetDateTime,
)
