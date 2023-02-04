package hu.matusek.spendingtrackertoolbackend.feature.transactions.service

import hu.matusek.spendingtrackertoolbackend.feature.transactions.dto.TransactionResponse

interface TransactionService {

    fun getTransactionById(id: Long): TransactionResponse

}