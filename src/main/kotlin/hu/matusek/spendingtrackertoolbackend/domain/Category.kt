package hu.matusek.spendingtrackertoolbackend.domain

import com.fasterxml.jackson.annotation.JsonValue

enum class Category {
    HOUSING,
    TRAVEL,
    FOOD,
    UTILITIES,
    INSURANCE,
    HEALTHCARE,
    FINANCIAL,
    LIFESTYLE,
    ENTERTAINMENT,
    CLOTHING,
    MISCELLANEOUS;

    @JsonValue
    fun toText() = name.lowercase()
}
