package hu.matusek.spendingtrackertoolbackend.feature.transactions.dto

import hu.matusek.spendingtrackertoolbackend.domain.Category
import hu.matusek.spendingtrackertoolbackend.domain.Currency
import jakarta.validation.constraints.Size
import java.math.BigDecimal
import java.time.OffsetDateTime

data class EditTransactionRequest(
    @field:Size(min = 1)
    val summary: String? = null,
    val category: Category? = null,
    val sum: BigDecimal? = null,
    val currency: Currency? = null,
    val paid: OffsetDateTime? = null,
)