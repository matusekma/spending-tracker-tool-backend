package hu.matusek.spendingtrackertoolbackend.repository

import hu.matusek.spendingtrackertoolbackend.domain.Transaction
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository: CrudRepository<Transaction, Long>