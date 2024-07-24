package ru.agniaendie.productservice.handler

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import ru.agniaendie.productservice.dto.ErrorDTO
import ru.agniaendie.productservice.exception.ProductCreationException
import ru.agniaendie.productservice.exception.ProductDeleteException
import ru.agniaendie.productservice.exception.ProductNotFoundException
import ru.agniaendie.productservice.exception.ProductUpdateException

@ControllerAdvice
@Component
class ExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(ProductDeleteException::class)
    fun handleProductDeleteException(ex: ProductDeleteException, request: WebRequest): ResponseEntity<Any> {
        logger.error("Product delete exception : ${ex.message}")
        return ResponseEntity(ErrorDTO("Product delete exception"), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ProductCreationException::class)
    fun handleProductCreationException(ex: ProductCreationException, request: WebRequest): ResponseEntity<Any> {
        logger.error("Product creation exception : ${ex.message}")
        return ResponseEntity(ErrorDTO("Product creation exception"), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ProductNotFoundException::class)
    fun handleProductNotFoundException(ex: ProductNotFoundException, request: WebRequest): ResponseEntity<Any> {
        logger.error("Product not found exception : ${ex.message}")
        return ResponseEntity(ErrorDTO("Product not found exception"), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler
    fun handleProductUpdateException(ex: ProductUpdateException, request: WebRequest): ResponseEntity<Any> {
        logger.error("Product update exception : ${ex.message}")
        return ResponseEntity(ErrorDTO("Product update exception"), HttpStatus.BAD_REQUEST)
    }
}