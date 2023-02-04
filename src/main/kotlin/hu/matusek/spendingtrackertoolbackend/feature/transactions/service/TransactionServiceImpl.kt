package hu.matusek.spendingtrackertoolbackend.feature.transactions.service

import hu.matusek.spendingtrackertoolbackend.feature.transactions.dto.TransactionResponse
import hu.matusek.spendingtrackertoolbackend.feature.transactions.dto.toTransactionResponse
import hu.matusek.spendingtrackertoolbackend.repository.TransactionRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class TransactionServiceImpl(private val transactionRepository: TransactionRepository) : TransactionService {

    override fun getTransactionById(id: Long): TransactionResponse =
        transactionRepository
            .findById(id)
            .orElseThrow { throw EntityNotFoundException("Transaction not found!") } // TODO localization
            .toTransactionResponse()

}