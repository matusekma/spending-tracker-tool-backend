package hu.matusek.spendingtrackertoolbackend.repository

import hu.matusek.spendingtrackertoolbackend.domain.Category
import hu.matusek.spendingtrackertoolbackend.domain.Currency
import hu.matusek.spendingtrackertoolbackend.domain.Transaction
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.time.OffsetDateTime

@Repository
interface TransactionRepository : CrudRepository<Transaction, Long> {

    @Query(
        "select t from Transaction t " +
                "where " +
                "(:category is null or t.category = :category) AND " +
                "(:currency is null or t.currency = :currency) AND " +
                "(cast(:paidFrom as timestamp) is null or t.paid >= :paidFrom) AND " +
                "(cast(:paidTo as timestamp) is null or t.paid <= :paidTo) AND " +
                "(:sumFrom is null or t.sum >= :sumFrom) AND " +
                "(:sumTo is null or t.sum <= :sumTo) AND " +
                "(:summary is null or :summary = '' or upper(t.summary) like upper(concat('%', :summary, '%')))"
    )
    fun findAllFilteredAndPaged(
        pageable: Pageable,
        category: Category?,
        currency: Currency?,
        paidFrom: OffsetDateTime?,
        paidTo: OffsetDateTime?,
        sumFrom: BigDecimal?,
        sumTo: BigDecimal?,
        summary: String?
    ): Page<Transaction>

}