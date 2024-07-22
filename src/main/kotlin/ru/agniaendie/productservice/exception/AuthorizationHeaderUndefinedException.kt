package ru.agniaendie.productservice.exception

class AuthorizationHeaderUndefinedException(override val message: String?) : RuntimeException(message) {
}