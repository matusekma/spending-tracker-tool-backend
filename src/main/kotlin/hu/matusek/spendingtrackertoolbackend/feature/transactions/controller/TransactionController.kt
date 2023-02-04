package hu.matusek.spendingtrackertoolbackend.feature.transactions.controller

import hu.matusek.spendingtrackertoolbackend.feature.transactions.dto.TransactionResponse
import hu.matusek.spendingtrackertoolbackend.feature.transactions.service.TransactionService
import org.springframework.web.bind.annotation.RestController

@RestController
class TransactionController(private val transactionService: TransactionService) : TransactionApi {

    override fun getTransaction(id: Long): TransactionResponse = transactionService.getTransactionById(id)
}