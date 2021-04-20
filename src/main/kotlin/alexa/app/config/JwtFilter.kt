package alexa.app.config

import alexa.app.exception.NotFoundException
import alexa.app.utils.JwtUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtFilter @Autowired constructor(private val jwtUtil: JwtUtil) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val pathsToPass = arrayOf("/v1/auth/alexa", "/v1/privacy", "/v1/term", "/v1/auth/finish", "", "/")

        pathsToPass.forEach {
            if (it == request.requestURI) {
                filterChain.doFilter(request, response)
                return
            }
        }


        if (!jwtUtil.verify(request.getHeader("Authorization").toString())) {
            logger.info("JWT: ${request.getHeader("Authorization")}")
            return throw NotFoundException("Acesso Negado!")
        }

        SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(
            jwtUtil.decode(request.getHeader("Authorization").toString()), null, Collections.emptyList())
        filterChain.doFilter(request, response)

    }
}