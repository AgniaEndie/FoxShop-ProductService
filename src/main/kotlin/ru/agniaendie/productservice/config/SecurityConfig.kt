package ru.agniaendie.productservice.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandlerImpl
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import ru.agniaendie.productservice.security.JwtFilter

@EnableWebSecurity
@Configuration
class SecurityConfig(@Autowired val jwtFilter: JwtFilter) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .cors { it.disable() }
            .authorizeHttpRequests { authorize ->
                authorize.requestMatchers("/api/product/all", "/api/product/get/**","/error").permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling { ex ->
                ex.accessDeniedHandler(AccessDeniedHandlerImpl())
            }.securityContext { securityContext -> securityContext.requireExplicitSave(false) }
            .sessionManagement { sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .build()
    }
}