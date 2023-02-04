package hu.matusek.spendingtrackertoolbackend.domain

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.OffsetDateTime


@Entity
@Table(name = "transactions")
class Transaction(
    id: Long? = null,
    var summary: String,
    @Enumerated(EnumType.STRING)
    var category: Category,
    var sum: BigDecimal,
    @Enumerated(EnumType.STRING)
    var currency: Currency,
    var paid: OffsetDateTime
) : BaseEntity(id)