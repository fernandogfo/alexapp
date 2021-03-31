package alexa.app.email.controller

import alexa.app.email.service.SendEmailService
import alexa.app.utils.JwtUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.logging.Logger

@RestController
@RequestMapping("v1/email")
class SendEmailController @Autowired constructor(
    private val sendEmailService: SendEmailService,
    private val jwtUtil: JwtUtil
) {
    val logger: Logger = Logger.getLogger(SendEmailController::class.java.name)

    @PostMapping
    fun send(
        @RequestHeader("Authorization") jwt: String,
        @RequestHeader("topic") topic: String, @RequestHeader("userName") userName: String
    ) {

        logger.info("POST -> v1/email")
        logger.info("Enviando email para usuário: ${jwtUtil.decode(jwt)} de nome: ${userName}")
        return sendEmailService.generateEmailAndSend(jwtUtil.decode(jwt), topic, userName)
    }
}