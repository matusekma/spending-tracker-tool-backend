package hu.matusek.spendingtrackertoolbackend.feature.transactions.controller

import hu.matusek.spendingtrackertoolbackend.feature.transactions.dto.CreateTransactionRequest
import hu.matusek.spendingtrackertoolbackend.feature.transactions.dto.CreateTransactionResponse
import hu.matusek.spendingtrackertoolbackend.feature.transactions.dto.TransactionResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RequestMapping("/transactions")
interface TransactionApi {

    @GetMapping("/{id}")
    fun getTransaction(@PathVariable("id") id: Long): TransactionResponse

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createTransaction(@Valid @RequestBody createTransactionRequest: CreateTransactionRequest): CreateTransactionResponse

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTransaction(@PathVariable("id") id: Long)
}