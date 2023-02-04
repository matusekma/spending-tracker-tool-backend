package hu.matusek.spendingtrackertoolbackend.error

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus
import java.time.OffsetDateTime

class ErrorResponse(
    httpStatus: HttpStatus,
    val message: String,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val stackTrace: String? = null,
    val data: Any? = null,
) {

    val status: Int

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    val timestamp: OffsetDateTime

    init {
        status = httpStatus.value()
        timestamp = OffsetDateTime.now()
    }

}