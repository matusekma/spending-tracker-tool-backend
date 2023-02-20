package hu.matusek.spendingtrackertoolbackend.feature.transactions.dto

import hu.matusek.spendingtrackertoolbackend.domain.Currency
import java.math.BigDecimal

class TransactionStatisticResponse(
    val category: String,
    val currency: Currency,
    val sum: BigDecimal
)