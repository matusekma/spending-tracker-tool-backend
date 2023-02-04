package hu.matusek.spendingtrackertoolbackend.feature.transactions.service

import hu.matusek.spendingtrackertoolbackend.feature.transactions.dto.*

interface TransactionService {

    fun getTransactionById(id: Long): TransactionResponse

    fun createTransaction(createTransactionRequest: CreateTransactionRequest): CreateTransactionResponse

    fun editTransaction(id: Long, editTransactionRequest: EditTransactionRequest): EditTransactionResponse

    fun deleteTransactionById(id: Long)

}