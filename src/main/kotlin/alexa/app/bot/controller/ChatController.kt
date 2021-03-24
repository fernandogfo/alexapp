package alexa.app.bot.controller


import alexa.app.bot.service.ChatService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.logging.Logger

@RestController
@RequestMapping("v1/chat")
class ChatController @Autowired constructor(private val chatService: ChatService) {

    val logger: Logger = Logger.getLogger(ChatController::class.java.name)

}