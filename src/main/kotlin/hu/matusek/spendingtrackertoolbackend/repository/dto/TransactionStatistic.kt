package hu.matusek.spendingtrackertoolbackend.repository.dto

import hu.matusek.spendingtrackertoolbackend.domain.Category
import hu.matusek.spendingtrackertoolbackend.domain.Currency
import java.math.BigDecimal


data class TransactionStatistic(
    val currency: Currency,
    val category: Category,
    val sum: BigDecimal
)
