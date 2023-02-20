package hu.matusek.spendingtrackertoolbackend.feature.transactions.dto

import hu.matusek.spendingtrackertoolbackend.domain.Transaction
import hu.matusek.spendingtrackertoolbackend.repository.dto.TransactionStatistic

fun Transaction.toTransactionResponse() =
    TransactionResponse(
        id = id!!,
        summary = summary,
        category = category.name.lowercase(),
        sum = sum,
        currency = currency,
        paid = paid
    )

fun Transaction.toCreateTransactionResponse() =
    CreateTransactionResponse(
        id = id!!,
        summary = summary,
        category = category.name.lowercase(),
        sum = sum,
        currency = currency,
        paid = paid
    )

fun CreateTransactionRequest.toTransaction() =
    Transaction(
        null,
        summary!!,
        category!!,
        sum!!,
        currency!!,
        paid!!
    )

fun Transaction.toEditTransactionResponse() =
    EditTransactionResponse(
        id = id!!,
        summary = summary,
        category = category.name.lowercase(),
        sum = sum,
        currency = currency,
        paid = paid
    )

fun TransactionStatistic.toTransactionStatisticResponse() =
    TransactionStatisticResponse(
        currency = currency,
        category = category.name.lowercase(),
        sum = sum
    )