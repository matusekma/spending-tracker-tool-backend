package hu.matusek.spendingtrackertoolbackend.domain

import jakarta.persistence.*
import java.time.OffsetDateTime


@MappedSuperclass
abstract class BaseEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
) {

    var createdAt: OffsetDateTime? = null

    var updatedAt: OffsetDateTime? = null

    @PrePersist
    fun onPrePersist() {
        val now = OffsetDateTime.now()
        createdAt = now
        updatedAt = now
    }

    @PreUpdate
    fun onPreUpdate() {
        updatedAt = OffsetDateTime.now()
    }
}