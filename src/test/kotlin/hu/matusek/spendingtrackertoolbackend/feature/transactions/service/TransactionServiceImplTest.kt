package hu.matusek.spendingtrackertoolbackend.feature.transactions.service

import hu.matusek.spendingtrackertoolbackend.*
import hu.matusek.spendingtrackertoolbackend.domain.Category
import hu.matusek.spendingtrackertoolbackend.domain.Currency
import hu.matusek.spendingtrackertoolbackend.domain.Transaction
import hu.matusek.spendingtrackertoolbackend.feature.transactions.dto.EditTransactionRequest
import hu.matusek.spendingtrackertoolbackend.feature.transactions.dto.toTransaction
import hu.matusek.spendingtrackertoolbackend.repository.TransactionRepository
import jakarta.persistence.EntityNotFoundException
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.*

@SpringJUnitConfig
class TransactionServiceImplTest {

    @Mock
    lateinit var transactionRepository: TransactionRepository

    @InjectMocks
    lateinit var transactionService: TransactionServiceImpl

    @Nested
    inner class TestGetTransactionById {
        @Test
        fun `given a transaction exists, when getTransactionById is called, then it should return the transaction`() {
            val expectedTransaction = getTestTransaction()
            `when`(transactionRepository.findById(expectedTransaction.id!!)).thenReturn(Optional.of(expectedTransaction))

            val transactionResponse = transactionService.getTransactionById(expectedTransaction.id!!)

            assertTransactionResponse(expectedTransaction, transactionResponse)
        }

        @Test
        fun `given a transaction does not exist, when getTransactionById is called, then it should throw EntityNotFoundException`() {
            val testId = 1L
            `when`(transactionRepository.findById(testId)).thenReturn(Optional.empty())

            assertThrows<EntityNotFoundException> { transactionService.getTransactionById(testId) }
        }
    }

    @Nested
    inner class TestCreateTransaction {
        @Test
        fun `when createTransaction is called, then it should return the created transaction`() {
            val testId = 1L
            val createTransactionRequest = getTestCreateTransactionRequest()
            val savedTransaction = createTransactionRequest.toTransaction().apply { id = testId }
            `when`(transactionRepository.save(any(Transaction::class.java))).thenReturn(savedTransaction)

            val createTransactionResponse = transactionService.createTransaction(createTransactionRequest)

            assertCreateTransactionResponse(savedTransaction, createTransactionResponse)
        }
    }

    @Nested
    inner class TestEditTransaction {
        @Test
        fun `given a transaction exists, when editTransaction is called, then it should return the edited transaction`() {
            val transaction = getTestTransaction()
            `when`(transactionRepository.findById(transaction.id!!)).thenReturn(Optional.of(transaction))
            val editTransactionRequest = EditTransactionRequest(
                summary = "Edited summary",
                category = Category.ENTERTAINMENT,
                sum = BigDecimal.valueOf(1.56),
                currency = Currency.USD,
                paid = OffsetDateTime.now()
            )

            val editTransactionResponse = transactionService.editTransaction(transaction.id!!, editTransactionRequest)

            val editedTransaction = Transaction(
                transaction.id,
                editTransactionRequest.summary!!,
                editTransactionRequest.category!!,
                editTransactionRequest.sum!!,
                editTransactionRequest.currency!!,
                editTransactionRequest.paid!!
            )
            assertEditTransactionResponse(editedTransaction, editTransactionResponse)
        }

        @Test
        fun `given a transaction does not exist, when editTransaction is called, then it should throw EntityNotFoundException`() {
            val testId = 1L
            `when`(transactionRepository.findById(testId)).thenReturn(Optional.empty())
            val editTransactionRequest = EditTransactionRequest(
                summary = "Edited summary",
                category = Category.ENTERTAINMENT,
                sum = BigDecimal.valueOf(1.56),
                currency = Currency.USD,
                paid = OffsetDateTime.now()
            )

            assertThrows<EntityNotFoundException> { transactionService.editTransaction(testId, editTransactionRequest) }
        }
    }

    @Nested
    inner class TestDeleteTransactionById {
        @Test
        fun `given the transaction does not exist, when deleteTransactionById is called, then it should not call deleteById`() {
            val testId = 1L

            transactionService.deleteTransactionById(testId)

            verify(transactionRepository, never()).deleteById(testId)
        }

        @Test
        fun `given the transaction exists, when deleteTransactionById is called, then it should call deleteById`() {
            val testId = 1L
            `when`(transactionRepository.existsById(testId)).thenReturn(true)

            transactionService.deleteTransactionById(testId)

            verify(transactionRepository).deleteById(testId)
        }
    }

}