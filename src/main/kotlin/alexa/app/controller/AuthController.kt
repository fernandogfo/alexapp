package alexa.app.controller


import alexa.app.bot.controller.ChannelController
import alexa.app.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.logging.Logger


@RestController
@RequestMapping("v1/auth")
class AuthController @Autowired constructor(private val authService: AuthService) {
    val logger: Logger = Logger.getLogger(AuthController::class.java.name)

    @GetMapping
    fun getAuth(@RequestParam("code") code: String, @RequestParam("userEmail") userEmail: String): String {
        logger.info("GET -> v1/auth")
        logger.info("Registrando usuário de code: ${code} e alexa id: ${userEmail}")
        return authService.registerUser(code, userEmail).toString()
    }

}