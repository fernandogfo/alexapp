package alexa.app.bot.controller

import alexa.app.bot.service.ChannelService
import alexa.app.service.AuthService
import feign.Headers
import org.hibernate.bytecode.BytecodeLogger.LOGGER
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.logging.Logger

@RestController
@RequestMapping("v1/channel")
class ChannelController @Autowired constructor(
    private val channelService: ChannelService,
    private val authService: AuthService
) {

    val logger: Logger = Logger.getLogger(ChannelController::class.java.name)

    @PostMapping("/clips")
    fun createClip(
        @RequestHeader("email") email: String
    ): ResponseEntity<Unit> {

        logger.info("POST -> v1/channel/clips")
        logger.info("Criando clip para o usuário de email: ${email}")
        return ResponseEntity.ok(channelService.createClip(authService.validateTokenByUserEmail(email)))
    }

    @PostMapping("/commercial")
    fun startCommercial(
        @RequestHeader("email") email: String,
        @RequestHeader("seconds") seconds: Int?
    ): ResponseEntity<Unit> {

        logger.info("POST -> v1/channel/commercial")
        logger.info("Rodando comercial para o usuário de email: ${email}")
        return ResponseEntity.ok(channelService.startCommercial(authService.validateTokenByUserEmail(email), seconds))
    }
}