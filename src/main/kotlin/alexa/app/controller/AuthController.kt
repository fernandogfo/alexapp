package alexa.app.controller


import alexa.app.service.AuthService
import alexa.app.utils.JwtUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.logging.Logger


@RestController
@RequestMapping("v1/auth")
class AuthController @Autowired constructor(private val authService: AuthService,
                                            private val jwtUtil: JwtUtil) {
    val logger: Logger = Logger.getLogger(AuthController::class.java.name)

    @GetMapping
    fun getAuth(@RequestParam("code") code: String, @RequestParam("Authorization") jwt: String): String {
        logger.info("GET -> v1/auth")
        logger.info("Registrando usuário de code: ${code} e alexa id: ${jwtUtil.decode(jwt)}")
        return authService.registerUser(code, jwtUtil.decode(jwt)).toString()
    }

}