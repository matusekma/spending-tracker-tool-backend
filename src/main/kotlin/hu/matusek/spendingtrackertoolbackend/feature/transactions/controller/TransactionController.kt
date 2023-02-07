package hu.matusek.spendingtrackertoolbackend.feature.transactions.controller

import hu.matusek.spendingtrackertoolbackend.feature.transactions.dto.CreateTransactionRequest
import hu.matusek.spendingtrackertoolbackend.feature.transactions.dto.EditTransactionRequest
import hu.matusek.spendingtrackertoolbackend.feature.transactions.dto.TransactionResponse
import hu.matusek.spendingtrackertoolbackend.feature.transactions.dto.TransactionsFilter
import hu.matusek.spendingtrackertoolbackend.feature.transactions.service.TransactionService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.RestController

@RestController
class TransactionController(
    private val transactionService: TransactionService
) : TransactionApi {

    override fun getTransaction(id: Long): TransactionResponse =
        transactionService.getTransactionById(id)

    override fun getTransactions(
        pageable: Pageable,
        transactionsFilter: TransactionsFilter
    ): Page<TransactionResponse> =
        transactionService.searchAndFilterTransactions(pageable, transactionsFilter)


    override fun createTransaction(createTransactionRequest: CreateTransactionRequest) =
        transactionService.createTransaction(createTransactionRequest)

    override fun editTransaction(id: Long, editTransactionRequest: EditTransactionRequest) =
        transactionService.editTransaction(id, editTransactionRequest)

    override fun deleteTransaction(id: Long) {
        transactionService.deleteTransactionById(id)
    }
}