package hu.matusek.spendingtrackertoolbackend.feature.transactions.controller

import hu.matusek.spendingtrackertoolbackend.*
import hu.matusek.spendingtrackertoolbackend.domain.Category
import hu.matusek.spendingtrackertoolbackend.domain.Currency
import hu.matusek.spendingtrackertoolbackend.domain.Transaction
import hu.matusek.spendingtrackertoolbackend.feature.transactions.dto.*
import hu.matusek.spendingtrackertoolbackend.repository.TransactionRepository
import org.json.JSONObject
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.returnResult
import java.util.stream.Stream


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureWebTestClient
class TransactionControllerIntegrationTest {

    @Autowired
    private lateinit var transactionRepository: TransactionRepository

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @AfterEach
    fun afterEach() {
        transactionRepository.deleteAll()
    }

    @Nested
    inner class TestGetById {

        @Test
        fun `should return a transaction with the given id`() {
            val savedTransaction = transactionRepository.save(getTestTransactionWithoutId())

            val transactionResponse = webTestClient
                .get()
                .uri("/api/transactions/${savedTransaction.id}")
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
        fun `should return HTTP status Not Found when the transaction does not exist`() {
            webTestClient
                .get()
                .uri("/api/transactions/1")
                .exchange()
                .expectStatus()
                .isNotFound
        }
    }

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    inner class TestCreate {

        @Test
        fun `should return the saved transaction`() {
            val transaction = getTestTransactionWithoutId()
            val createTransactionRequest = transaction.toTestCreateTransactionRequest()

            val createTransactionResponse = webTestClient
                .post()
                .uri("/api/transactions")
                .bodyValue(createTransactionRequest)
                .exchange()
                .expectStatus()
                .isCreated
                .returnResult<CreateTransactionResponse>()
                .responseBody
                .blockFirst()

            assertCreateTransactionResponse(transaction, createTransactionResponse!!)
        }

        @Test
        fun `should accept lowercase category enum`() {
            val jsonTransaction = JSONObject().apply {
                put("summary", "Test")
                put("category", Category.FOOD.name.lowercase())
                put("sum", "12.0")
                put("currency", Currency.HUF.name)
                put("paid", getTestOffsetDateTime().toString())
            }

            webTestClient
                .post()
                .uri("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(jsonTransaction.toString())
                .exchange()
                .expectStatus()
                .isCreated
                .returnResult<CreateTransactionResponse>()
                .responseBody
                .blockFirst()
        }


        @ParameterizedTest
        @MethodSource("getInvalidTransactions")
        fun `should return HTTP status Bad Request for Transaction with invalid fields`(
            createTransactionRequest: CreateTransactionRequest
        ) {
            webTestClient
                .post()
                .uri("/api/transactions")
                .bodyValue(createTransactionRequest)
                .exchange()
                .expectStatus()
                .isBadRequest
        }

        private fun getInvalidTransactions(): Stream<CreateTransactionRequest> =
            Stream.of(
                getTestCreateTransactionRequest().copy(summary = ""),
            )

        @ParameterizedTest
        @MethodSource("getTransactionJsonsWithMissingFields")
        fun `should return HTTP status Bad Request for Transaction with missing fields`(
            createTransactionRequestJson: String
        ) {
            webTestClient
                .post()
                .uri("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(createTransactionRequestJson)
                .exchange()
                .expectStatus()
                .isBadRequest
        }

        private fun getTransactionJsonsWithMissingFields(): Stream<String> {
            val validObject = JSONObject().apply {
                put("summary", "Test")
                put("category", Category.FOOD.name)
                put("sum", "12.0")
                put("currency", Currency.HUF.name)
                put("paid", getTestOffsetDateTime().toString())
            }
            val emptyObject = JSONObject()
            val missingSummary = JSONObject(validObject.toString()).apply { remove("summary") }
            val missingCategory = JSONObject(validObject.toString()).apply { remove("category") }
            val missingSum = JSONObject(validObject.toString()).apply { remove("sum") }
            val missingCurrency = JSONObject(validObject.toString()).apply { remove("currency") }
            val missingPaid = JSONObject(validObject.toString()).apply { remove("paid") }
            return Stream.of(
                emptyObject,
                missingSummary,
                missingCategory,
                missingSum,
                missingCurrency,
                missingPaid
            ).map { it.toString() }
        }

    }

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    inner class TestEdit {

        @Test
        fun `should return the edited transaction`() {
            val savedTransaction = transactionRepository.save(getTestTransactionWithoutId())
            val editTransactionRequest = getTestEditTransactionRequest()

            val editTransactionResponse = webTestClient
                .put()
                .uri("/api/transactions/${savedTransaction.id}")
                .bodyValue(editTransactionRequest)
                .exchange()
                .expectStatus()
                .isOk
                .returnResult<EditTransactionResponse>()
                .responseBody
                .blockFirst()

            val editedTransaction = editTransactionRequest.run {
                Transaction(
                    savedTransaction.id,
                    summary!!,
                    category!!,
                    sum!!,
                    currency!!,
                    paid!!
                )
            }
            assertEditTransactionResponse(editedTransaction, editTransactionResponse!!)
        }

        @ParameterizedTest
        @MethodSource("getEditTransactionRequestsWithOneFieldAndEditedTransaction")
        fun `should only edit the specified field and return the edited transaction`(
            editTransactionRequest: EditTransactionRequest, editedTransaction: Transaction
        ) {
            val savedTransaction = transactionRepository.save(getTestTransactionWithoutId())
            editedTransaction.id = savedTransaction.id

            val editTransactionResponse = webTestClient
                .put()
                .uri("/api/transactions/${savedTransaction.id}")
                .bodyValue(editTransactionRequest)
                .exchange()
                .expectStatus()
                .isOk
                .returnResult<EditTransactionResponse>()
                .responseBody
                .blockFirst()

            assertEditTransactionResponse(editedTransaction, editTransactionResponse!!)
        }

        private fun getEditTransactionRequestsWithOneFieldAndEditedTransaction(): Stream<Arguments> =
            getTestEditTransactionRequestsWithOneFieldAndEditedTransaction()

        @Test
        fun `should return HTTP status Bad Request for transaction edit request with invalid summary`(
        ) {
            val editTransactionRequest = getTestEditTransactionRequest().copy(summary = "")

            webTestClient
                .post()
                .uri("/api/transactions")
                .bodyValue(editTransactionRequest)
                .exchange()
                .expectStatus()
                .isBadRequest
        }

    }

    @Nested
    inner class TestDeleteById {

        @Test
        fun `should return delete the transaction with the given id`() {
            val savedTransaction = transactionRepository.save(getTestTransactionWithoutId())

            webTestClient
                .delete()
                .uri("/api/transactions/${savedTransaction.id}")
                .exchange()
                .expectStatus()
                .isNoContent

            assertTrue(transactionRepository.findById(savedTransaction.id!!).isEmpty)
        }

        @Test
        fun `should be idempotent and not return error when the transaction does not exist`() {
            webTestClient
                .delete()
                .uri("/api/transactions/1")
                .exchange()
                .expectStatus()
                .isNoContent
        }
    }
}