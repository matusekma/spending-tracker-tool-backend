package hu.matusek.spendingtrackertoolbackend.error

import jakarta.persistence.EntityNotFoundException
import org.springframework.core.env.Environment
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.io.PrintWriter
import java.io.StringWriter


@ControllerAdvice
class ExceptionHandlingControllerAdvice(environment: Environment) {

    val isDevelopmentProfile = environment.activeProfiles.any { listOf("dev", "test").contains(it) }

    @ExceptionHandler(
        EntityNotFoundException::class,
        EmptyResultDataAccessException::class
    )
    fun handleEntityNotFoundException(e: Exception): ResponseEntity<ErrorResponse> =
        createErrorResponse(HttpStatus.NOT_FOUND, "Entity not found!", e)


    @ExceptionHandler(
        MethodArgumentNotValidException::class
    )
    fun handleInvalidRequestErrorException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> =
        createErrorResponse(HttpStatus.BAD_REQUEST, "Invalid request", e, e.fieldErrors)

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> =
        createErrorResponse(HttpStatus.BAD_REQUEST, "Invalid request", e)

    @ExceptionHandler(
        HttpRequestMethodNotSupportedException::class
    )
    fun handleHttpRequestMethodNotSupportedException(e: Exception): ResponseEntity<ErrorResponse> =
        createErrorResponse(HttpStatus.METHOD_NOT_ALLOWED, "Method not allowed!", e)

    @ExceptionHandler(
        Exception::class
    )
    fun handleInternalServerErrorException(e: Exception): ResponseEntity<ErrorResponse> =
        createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error happened!", e)


    private fun createErrorResponse(
        httpStatus: HttpStatus,
        message: String,
        e: Exception,
        data: Any? = null
    ): ResponseEntity<ErrorResponse> {
        val stackTrace = if (isDevelopmentProfile) {
            val sw = StringWriter()
            val pw = PrintWriter(sw)
            e.printStackTrace(pw)
            sw.toString()
        } else {
            null
        }
        return ResponseEntity(ErrorResponse(httpStatus, message, stackTrace, data), httpStatus)
    }


}