package ru.agniaendie.productservice.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import ru.agniaendie.productservice.exception.AuthorizationHeaderUndefinedException
import ru.agniaendie.productservice.service.JwtService

@Component
class JwtFilter(@Autowired val jwtService: JwtService) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val header = request.getHeader("Authorization")
        if (header != null && header.startsWith("Bearer ")) {

            val token = request.getHeader(HttpHeaders.AUTHORIZATION).substringAfter("Bearer ")
            if (jwtService.validateToken(token)) {
                val claims = jwtService.extractAllClaims(token)
                val authenticatedUser = UsernamePasswordAuthenticationToken(
                    claims["sub"],
                    claims["role"],
                    listOf(SimpleGrantedAuthority(claims["role"] as String)),
                )
                val ctx = SecurityContextHolder.getContext()
                ctx.authentication = authenticatedUser
                SecurityContextHolder.setContext(ctx)
                if (SecurityContextHolder.getContext().authentication == null) {
                    throw AuthorizationHeaderUndefinedException("Failed-F")
                }
            } else {
                throw AuthorizationHeaderUndefinedException("Failed-M")
            }
        }
        filterChain.doFilter(request, response)
    }


}