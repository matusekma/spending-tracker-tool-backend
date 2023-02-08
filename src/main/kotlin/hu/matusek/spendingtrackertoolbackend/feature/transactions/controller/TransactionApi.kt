package hu.matusek.spendingtrackertoolbackend.feature.transactions.controller

import hu.matusek.spendingtrackertoolbackend.feature.transactions.dto.*
import jakarta.validation.Valid
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RequestMapping("/api/transactions")
interface TransactionApi {

    @GetMapping("/{id}")
    fun getTransaction(@PathVariable("id") id: Long): TransactionResponse

    @GetMapping
    fun getTransactions(
        @ParameterObject
        @PageableDefault(
            sort = ["paid"],
            direction = Sort.Direction.DESC,
            page = 0,
            size = 10,
        )
        pageable: Pageable,
        @ParameterObject
        transactionsFilter: TransactionsFilter
    ): Page<TransactionResponse>

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createTransaction(@Valid @RequestBody createTransactionRequest: CreateTransactionRequest): CreateTransactionResponse

    @PutMapping("/{id}")
    fun editTransaction(
        @PathVariable("id") id: Long,
        @Valid @RequestBody editTransactionRequest: EditTransactionRequest
    ): EditTransactionResponse

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTransaction(@PathVariable("id") id: Long)
}