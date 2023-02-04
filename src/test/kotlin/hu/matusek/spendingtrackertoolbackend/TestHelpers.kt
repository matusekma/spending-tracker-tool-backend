package hu.matusek.spendingtrackertoolbackend

import hu.matusek.spendingtrackertoolbackend.domain.Category
import hu.matusek.spendingtrackertoolbackend.domain.Currency
import hu.matusek.spendingtrackertoolbackend.domain.Transaction
import hu.matusek.spendingtrackertoolbackend.feature.transactions.dto.CreateTransactionRequest
import hu.matusek.spendingtrackertoolbackend.feature.transactions.dto.CreateTransactionResponse
import hu.matusek.spendingtrackertoolbackend.feature.transactions.dto.EditTransactionResponse
import hu.matusek.spendingtrackertoolbackend.feature.transactions.dto.TransactionResponse
import org.junit.jupiter.api.Assertions
import java.math.BigDecimal
import java.time.OffsetDateTime

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

fun getTestTransactionWithoutId() =
    Transaction(
        null,
        "Test summary",
        Category.FOOD,
        BigDecimal.valueOf(1.5),
        Currency.HUF,
        OffsetDateTime.now()
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
