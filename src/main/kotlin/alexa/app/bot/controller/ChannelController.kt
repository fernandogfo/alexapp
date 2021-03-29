package alexa.app.bot.controller

import alexa.app.bot.service.ChannelService
import alexa.app.service.AuthService
import alexa.app.utils.JwtUtil
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
    private val authService: AuthService,
    private val jwtUtil: JwtUtil
) {

    val logger: Logger = Logger.getLogger(ChannelController::class.java.name)

    @PostMapping("/clips")
    fun createClip(
        @RequestHeader("Authorization") jwt: String
    ): ResponseEntity<Unit> {

        logger.info("POST -> v1/channel/clips")
        logger.info("Criando clip para o usuário de email: ${jwtUtil.decode(jwt)}")
        return ResponseEntity.ok(channelService.createClip(authService.validateTokenByUserEmail(jwtUtil.decode(jwt))))
    }

    @PostMapping("/commercial")
    fun startCommercial(
        @RequestHeader("Authorization") jwt: String,
        @RequestHeader("seconds") seconds: Int?
    ): ResponseEntity<Unit> {

        logger.info("POST -> v1/channel/commercial")
        logger.info("Rodando comercial para o usuário de email: ${jwtUtil.decode(jwt)}")
        return ResponseEntity.ok(channelService.startCommercial(authService.validateTokenByUserEmail(jwtUtil.decode(jwt)), seconds))
    }
}