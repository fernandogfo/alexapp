package alexa.app.bot.service

import alexa.app.config.BotConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ChatService @Autowired constructor(
    private val botConfig: BotConfig
) {

    fun sendMessage(channel: String?, message: String?) {
        if (!botConfig.twitchChat().isChannelJoined(channel)) {
            botConfig.twitchChat().joinChannel(channel)
        }
        botConfig.twitchChat().sendMessage(channel, message)
    }

}