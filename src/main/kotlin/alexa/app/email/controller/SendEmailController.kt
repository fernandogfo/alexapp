package alexa.app.email.controller

import alexa.app.controller.AuthController
import alexa.app.email.service.SendEmailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.logging.Logger

@RestController
@RequestMapping("v1/email")
class SendEmailController @Autowired constructor(private val sendEmailService: SendEmailService) {
    val logger: Logger = Logger.getLogger(SendEmailController::class.java.name)

    @PostMapping
    fun send(
        @RequestHeader("to") to: String,
        @RequestHeader("topic") topic: String, @RequestHeader("userName") userName: String
    ) {

        logger.info("POST -> v1/email")
        logger.info("Enviando email para usuário: ${to} de nome: ${userName}")
        return sendEmailService.generateEmailAndSend(to, topic, userName)
    }
}