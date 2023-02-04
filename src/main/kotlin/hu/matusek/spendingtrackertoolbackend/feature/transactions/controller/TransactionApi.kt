package hu.matusek.spendingtrackertoolbackend.feature.transactions.controller

import hu.matusek.spendingtrackertoolbackend.feature.transactions.dto.TransactionResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RequestMapping("/transactions")
interface TransactionApi {

    @GetMapping("/{id}")
    fun getTransaction(@PathVariable("id") id: Long): TransactionResponse

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTransaction(@PathVariable("id") id: Long)
}