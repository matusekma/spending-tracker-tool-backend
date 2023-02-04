package hu.matusek.spendingtrackertoolbackend.feature.transactions.dto

import hu.matusek.spendingtrackertoolbackend.domain.Category
import hu.matusek.spendingtrackertoolbackend.domain.Currency
import jakarta.validation.constraints.Size
import java.math.BigDecimal
import java.time.OffsetDateTime

class EditTransactionRequest(
    @field:Size(min = 1)
    val summary: String?,
    val category: Category?,
    val sum: BigDecimal?,
    val currency: Currency?,
    val paid: OffsetDateTime?,
)