package hu.matusek.spendingtrackertoolbackend.domain.converter

import hu.matusek.spendingtrackertoolbackend.domain.Category
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component


@Component
class CategoryConverter : Converter<String, Category> {
    override fun convert(categoryString: String): Category? =
        if (categoryString.isBlank()) null else Category.valueOf(categoryString.uppercase())

}