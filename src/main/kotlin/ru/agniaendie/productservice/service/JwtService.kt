package ru.agniaendie.productservice.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.agniaendie.productservice.exception.JwtValidationFailedException
import ru.agniaendie.productservice.logger
import java.security.KeyFactory
import java.security.spec.X509EncodedKeySpec
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Service
class JwtService(
    @Value("\${jwt.secret.public}") private val publicKey: String,
    @Value("\${jwt.expiration.zoneId}") private val zoneId: String
) {

    fun validateToken(authToken: String): Boolean {
        try {
            val extractedClaims = extractAllClaims(authToken)
            val currentTime = LocalDateTime.now().atZone(ZoneId.of(zoneId)).toInstant()
            if(extractedClaims["exp"] is Long){
                //logger.error("true ${extractedClaims["exp"]} ${extractedClaims["exp"].toString().toLong() < currentTime.toEpochMilli()}")
                return extractedClaims["exp"].toString().toLong() < currentTime.toEpochMilli()
            }else{
                //logger.error("false ${extractedClaims["exp"]} ${extractedClaims["exp"].toString().toLong() > currentTime.toEpochMilli()}")
                return false
            }
        } catch (e: JwtValidationFailedException) {
            logger.error(e.message)
            return false
        }
    }

    fun preparePublicKey(): String {
        val clearedRawKey = publicKey.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "")
            .replace("\\s+".toRegex(), "")
        return clearedRawKey
    }

    fun extractAllClaims(token: String): Claims {
        try {
            val key = preparePublicKey()
            val factory = KeyFactory.getInstance("RSA")
            val publicKey = factory.generatePublic(X509EncodedKeySpec(Base64.getDecoder().decode(key.toByteArray())))

            return Jwts.parser().verifyWith(publicKey).build()
                .parseSignedClaims(token).payload
        } catch (e: IllegalArgumentException) {
            throw JwtValidationFailedException("Failed to verify token")
        }
    }

}