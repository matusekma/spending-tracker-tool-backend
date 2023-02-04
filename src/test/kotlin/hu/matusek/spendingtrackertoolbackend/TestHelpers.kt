package hu.matusek.spendingtrackertoolbackend

import hu.matusek.spendingtrackertoolbackend.domain.Category
import hu.matusek.spendingtrackertoolbackend.domain.Currency
import hu.matusek.spendingtrackertoolbackend.domain.Transaction
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

fun getTestTransaction() =
    Transaction(
        1,
        "Test summary",
        Category.FOOD,
        BigDecimal.valueOf(1.5),
        Currency.HUF,
        OffsetDateTime.now()
    )