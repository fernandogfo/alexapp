package alexa.app.utils

import alexa.app.config.BotConfig
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.Claim
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*
import javax.servlet.http.HttpServletRequest

@Service
class JwtUtil @Autowired constructor(private val botConfig: BotConfig) {
    val email = "email"
    fun verify(jwt: String?): Boolean {
        return try {
            val algorithm = Algorithm.HMAC256(botConfig.getJwtSecret())
            val jwtVerifier = JWT.require(algorithm).build()
            jwtVerifier.verify(jwt)
            true
        } catch (e: Exception) {
            false
        }
    }
    fun decode(jwt:String): String {
        val algorithm: Algorithm = Algorithm.HMAC256(botConfig.getJwtSecret())
        val jwtVerifier = JWT.require(algorithm).build()
        val decodedJWT: DecodedJWT = jwtVerifier.verify(jwt)
        val claims: Map<String, Claim> = decodedJWT.claims
        val claim = claims[email]!!.asString()
        return claim.toString()
    }


}