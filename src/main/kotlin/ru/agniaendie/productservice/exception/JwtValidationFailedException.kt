package ru.agniaendie.productservice.exception

class JwtValidationFailedException( override val message: String? ) : RuntimeException(message) {
}