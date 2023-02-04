package hu.matusek.spendingtrackertoolbackend.feature.transactions.dto

import hu.matusek.spendingtrackertoolbackend.domain.Transaction

fun Transaction.toTransactionResponse() =
    TransactionResponse(
        id = id!!,
        summary = summary,
        category = category.name.lowercase(),
        sum = sum,
        currency = currency,
        paid = paid
    )