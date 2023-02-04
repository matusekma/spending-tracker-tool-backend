package hu.matusek.spendingtrackertoolbackend.feature.transactions.controller

import hu.matusek.spendingtrackertoolbackend.assertTransactionResponse
import hu.matusek.spendingtrackertoolbackend.feature.transactions.dto.TransactionResponse
import hu.matusek.spendingtrackertoolbackend.getTestTransaction
import hu.matusek.spendingtrackertoolbackend.repository.TransactionRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.returnResult


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class TransactionControllerIntegrationTest {

    @Autowired
    private lateinit var transactionRepository: TransactionRepository

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Nested
    inner class TestGetById {

        @AfterEach
        fun afterEach() {
            transactionRepository.deleteAll()
        }

        @Test
        fun `the GET by id endpoint should return a transaction with the given id`() {
            val savedTransaction = transactionRepository.save(getTestTransaction())

            val transactionResponse = webTestClient
                .get()
                .uri("/transactions/${savedTransaction.id}")
                .exchange()
                .expectStatus()
                .isOk
                .returnResult<TransactionResponse>()
                .responseBody
                .blockFirst()

            assertNotNull(transactionResponse)
            assertTransactionResponse(savedTransaction, transactionResponse!!)
        }

        @Test
        fun `the GET by id endpoint should return HTTP status Not Found when the transaction does not exist`() {
            webTestClient
                .get()
                .uri("/transactions/1")
                .exchange()
                .expectStatus()
                .isNotFound
        }
    }

    @Nested
    inner class TestDeleteById {

        @AfterEach
        fun afterEach() {
            transactionRepository.deleteAll()
        }

        @Test
        fun `the DELETE by id endpoint should return delete the transaction with the given id`() {
            val savedTransaction = transactionRepository.save(getTestTransaction())

            webTestClient
                .delete()
                .uri("/transactions/${savedTransaction.id}")
                .exchange()
                .expectStatus()
                .isNoContent

            assertTrue(transactionRepository.findById(savedTransaction.id!!).isEmpty)
        }

        @Test
        fun `the DELETE by id endpoint should be idempotent and not return error when the transaction does not exist`() {
            webTestClient
                .delete()
                .uri("/transactions/1")
                .exchange()
                .expectStatus()
                .isNoContent
        }
    }
}