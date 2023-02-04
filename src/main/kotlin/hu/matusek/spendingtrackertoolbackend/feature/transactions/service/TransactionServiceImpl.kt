package hu.matusek.spendingtrackertoolbackend.feature.transactions.service

import hu.matusek.spendingtrackertoolbackend.feature.transactions.dto.*
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

    override fun createTransaction(createTransactionRequest: CreateTransactionRequest) =
        transactionRepository.save(createTransactionRequest.toTransaction()).toCreateTransactionResponse()

    override fun deleteTransactionById(id: Long) {
        if (transactionRepository.existsById(id)) {
            transactionRepository.deleteById(id)
        }
    }

}