package hu.matusek.spendingtrackertoolbackend.feature.transactions.controller

import hu.matusek.spendingtrackertoolbackend.feature.transactions.dto.TransactionResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/transactions")
interface TransactionApi {

    @GetMapping("/{id}")
    fun getTransaction(@PathVariable("id") id: Long): TransactionResponse

}