package hu.matusek.spendingtrackertoolbackend.feature.transactions.service

import hu.matusek.spendingtrackertoolbackend.feature.transactions.dto.CreateTransactionRequest
import hu.matusek.spendingtrackertoolbackend.feature.transactions.dto.CreateTransactionResponse
import hu.matusek.spendingtrackertoolbackend.feature.transactions.dto.TransactionResponse

interface TransactionService {

    fun getTransactionById(id: Long): TransactionResponse
    fun deleteTransactionById(id: Long)
    fun createTransaction(createTransactionRequest: CreateTransactionRequest): CreateTransactionResponse

}