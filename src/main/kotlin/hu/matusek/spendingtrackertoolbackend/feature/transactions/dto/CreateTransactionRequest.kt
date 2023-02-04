package hu.matusek.spendingtrackertoolbackend.feature.transactions.dto

import hu.matusek.spendingtrackertoolbackend.domain.Currency
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.time.OffsetDateTime

data class CreateTransactionRequest(
    @field:NotBlank
    val summary: String?,
    @field:NotBlank
    val category: String?,
    @field:NotNull
    val sum: BigDecimal?,
    @field:NotBlank
    val currency: Currency?,
    @field:NotNull
    val paid: OffsetDateTime?,
)
