package hu.matusek.spendingtrackertoolbackend.feature.transactions.service

import hu.matusek.spendingtrackertoolbackend.feature.transactions.dto.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface TransactionService {

    fun getTransactionById(id: Long): TransactionResponse

    fun createTransaction(createTransactionRequest: CreateTransactionRequest): CreateTransactionResponse

    fun editTransaction(id: Long, editTransactionRequest: EditTransactionRequest): EditTransactionResponse

    fun deleteTransactionById(id: Long)
    fun searchAndFilterTransactions(
        pageable: Pageable,
        transactionsFilter: TransactionsFilter
    ): Page<TransactionResponse>

}