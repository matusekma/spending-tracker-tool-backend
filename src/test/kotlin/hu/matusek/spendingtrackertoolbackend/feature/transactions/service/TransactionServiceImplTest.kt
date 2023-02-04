package hu.matusek.spendingtrackertoolbackend.feature.transactions.service

import hu.matusek.spendingtrackertoolbackend.assertTransactionResponse
import hu.matusek.spendingtrackertoolbackend.getTestTransaction
import hu.matusek.spendingtrackertoolbackend.repository.TransactionRepository
import jakarta.persistence.EntityNotFoundException
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
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
            val testId = 1L
            val expectedTransaction = getTestTransaction()
            `when`(transactionRepository.findById(testId)).thenReturn(Optional.of(expectedTransaction))

            val transactionResponse = transactionService.getTransactionById(testId)

            assertTransactionResponse(expectedTransaction, transactionResponse)
        }

        @Test
        fun `given a transaction does not exist, when getTransactionById is called, then it should throw EntityNotFoundException`() {
            val testId = 1L
            `when`(transactionRepository.findById(testId)).thenReturn(Optional.empty())

            assertThrows<EntityNotFoundException> { transactionService.getTransactionById(testId) }
        }
    }

}