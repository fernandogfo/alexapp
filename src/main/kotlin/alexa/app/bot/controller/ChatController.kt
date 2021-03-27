package alexa.app.bot.controller


import alexa.app.bot.service.ChatService
import alexa.app.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.logging.Logger

@RestController
@RequestMapping("v1/chat")
class ChatController @Autowired constructor(
    private val chatService: ChatService,
    private val authService: AuthService
) {

    val logger: Logger = Logger.getLogger(ChatController::class.java.name)

    @PostMapping("/only-emotes")
    fun onlyEmotes(
        @RequestHeader("email") email: String,
        @RequestHeader("emotes") emote: Boolean
    ): ResponseEntity<Unit> {

        logger.info("POST -> v1/chat/only-emotes")
        logger.info("Apenas emotes no chat para o usuário de email: ${email}")
        return ResponseEntity.ok(chatService.onlyEmotes(authService.validateTokenByUserEmail(email), emote))
    }
}