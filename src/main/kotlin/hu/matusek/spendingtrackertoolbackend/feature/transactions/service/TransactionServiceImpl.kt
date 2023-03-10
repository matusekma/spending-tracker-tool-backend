package hu.matusek.spendingtrackertoolbackend.feature.transactions.service

import hu.matusek.spendingtrackertoolbackend.feature.transactions.dto.*
import hu.matusek.spendingtrackertoolbackend.repository.TransactionRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime

@Service
class TransactionServiceImpl(private val transactionRepository: TransactionRepository) : TransactionService {

    override fun getTransactionById(id: Long): TransactionResponse =
        transactionRepository
            .findById(id)
            .orElseThrow { throw EntityNotFoundException("Transaction not found!") } // TODO localization
            .toTransactionResponse()

    override fun createTransaction(createTransactionRequest: CreateTransactionRequest) =
        transactionRepository.save(createTransactionRequest.toTransaction()).toCreateTransactionResponse()

    @Transactional
    override fun editTransaction(id: Long, editTransactionRequest: EditTransactionRequest): EditTransactionResponse {
        val transaction = transactionRepository
            .findById(id)
            .orElseThrow { EntityNotFoundException("Transaction not found!") }

        with(editTransactionRequest) {
            if (summary != null) transaction.summary = summary
            if (category != null) transaction.category = category
            if (sum != null) transaction.sum = sum
            if (currency != null) transaction.currency = currency
            if (paid != null) transaction.paid = paid
        }

        return transaction.toEditTransactionResponse()
    }

    override fun deleteTransactionById(id: Long) {
        if (transactionRepository.existsById(id)) {
            transactionRepository.deleteById(id)
        }
    }

    override fun searchAndFilterTransactions(
        pageable: Pageable,
        transactionsFilter: TransactionsFilter
    ): Page<TransactionResponse> =
        transactionRepository.findAllFilteredAndPaged(
            pageable,
            transactionsFilter.category,
            transactionsFilter.currency,
            transactionsFilter.paidFrom,
            transactionsFilter.paidTo,
            transactionsFilter.sumFrom,
            transactionsFilter.sumTo,
            transactionsFilter.summary
        ).map { it.toTransactionResponse() }

    override fun calculateTransactionStatistics(
        paidFrom: OffsetDateTime?,
        paidTo: OffsetDateTime?
    ): List<TransactionStatisticResponse> =
        transactionRepository.calculateTransactionStatisticsByCurrencyAndCategory(paidFrom, paidTo)
            .map { it.toTransactionStatisticResponse() }

}