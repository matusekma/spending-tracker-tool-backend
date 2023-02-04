package hu.matusek.spendingtrackertoolbackend

import hu.matusek.spendingtrackertoolbackend.domain.Category
import hu.matusek.spendingtrackertoolbackend.domain.Currency
import hu.matusek.spendingtrackertoolbackend.domain.Transaction
import hu.matusek.spendingtrackertoolbackend.feature.transactions.dto.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.provider.Arguments
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.stream.Stream

fun assertTransactionResponse(expectedTransaction: Transaction, transactionResponse: TransactionResponse) {
    expectedTransaction.run {
        Assertions.assertEquals(id, transactionResponse.id)
        Assertions.assertEquals(summary, transactionResponse.summary)
        Assertions.assertEquals(category, Category.valueOf(transactionResponse.category.uppercase()))
        Assertions.assertEquals(sum, transactionResponse.sum)
        Assertions.assertEquals(currency, transactionResponse.currency)
        Assertions.assertEquals(paid.toEpochSecond(), transactionResponse.paid.toEpochSecond())
    }
}

fun assertCreateTransactionResponse(
    expectedTransaction: Transaction,
    createTransactionResponse: CreateTransactionResponse
) {
    expectedTransaction.run {
        if (id != null) Assertions.assertEquals(id, createTransactionResponse.id)
        Assertions.assertEquals(summary, createTransactionResponse.summary)
        Assertions.assertEquals(category, Category.valueOf(createTransactionResponse.category.uppercase()))
        Assertions.assertEquals(sum, createTransactionResponse.sum)
        Assertions.assertEquals(currency, createTransactionResponse.currency)
        Assertions.assertEquals(paid.toEpochSecond(), createTransactionResponse.paid.toEpochSecond())
    }
}

fun assertEditTransactionResponse(
    editedTransaction: Transaction,
    editTransactionResponse: EditTransactionResponse
) {
    editedTransaction.run {
        Assertions.assertEquals(id, editTransactionResponse.id)
        Assertions.assertEquals(summary, editTransactionResponse.summary)
        Assertions.assertEquals(category, Category.valueOf(editTransactionResponse.category.uppercase()))
        Assertions.assertEquals(sum, editTransactionResponse.sum)
        Assertions.assertEquals(currency, editTransactionResponse.currency)
        Assertions.assertEquals(paid.toEpochSecond(), editTransactionResponse.paid.toEpochSecond())
    }
}

fun getTestOffsetDateTime() = OffsetDateTime.of(2000, 1, 1, 10, 0, 0, 0, ZoneOffset.UTC)
fun getTestTransactionWithoutId() =
    Transaction(
        null,
        "Test summary",
        Category.FOOD,
        BigDecimal.valueOf(1.5),
        Currency.HUF,
        getTestOffsetDateTime()
    )

fun getTestTransaction() = getTestTransactionWithoutId().apply { id = 1 }

fun getTestCreateTransactionRequest() =
    getTestTransaction().run {
        CreateTransactionRequest(
            summary,
            category,
            sum,
            currency,
            paid
        )
    }

fun Transaction.toTestCreateTransactionRequest() =
    CreateTransactionRequest(
        summary,
        category,
        sum,
        currency,
        paid
    )

fun getTestEditTransactionRequest() =
    EditTransactionRequest(
        summary = "Edited summary",
        category = Category.ENTERTAINMENT,
        sum = BigDecimal.valueOf(1.56),
        currency = Currency.USD,
        paid = getTestOffsetDateTime()
    )

fun getTestEditTransactionRequestsWithOneFieldAndEditedTransaction(): Stream<Arguments> =
    Stream.of(
        Arguments.of(
            EditTransactionRequest(summary = "Edited summary"),
            getTestTransaction().apply { summary = "Edited summary" }),
        Arguments.of(
            EditTransactionRequest(category = Category.HEALTHCARE),
            getTestTransaction().apply { category = Category.HEALTHCARE }),
        Arguments.of(
            EditTransactionRequest(sum = BigDecimal.valueOf(123.123)),
            getTestTransaction().apply { sum = BigDecimal.valueOf(123.123) }),
        Arguments.of(
            EditTransactionRequest(currency = Currency.EUR),
            getTestTransaction().apply { currency = Currency.EUR }),
        Arguments.of(
            EditTransactionRequest(paid = getTestOffsetDateTime().plusDays(10)),
            getTestTransaction().apply { paid = getTestOffsetDateTime().plusDays(10) }),
    )
