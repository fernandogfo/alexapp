package alexa.app.bot.service

import alexa.app.client.twitchBot.ChannelClient
import alexa.app.config.BotConfig
import alexa.app.config.TwitchUrls
import alexa.app.exception.NotFoundException
import alexa.app.model.User
import alexa.app.model.response.CreateClipResponse
import alexa.app.utils.StringToBearer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ChannelService @Autowired constructor(
    private val channelClient: ChannelClient,
    private val botConfig: BotConfig,
    private val chatService: ChatService,
    private val twitchUrls: TwitchUrls
) {


    fun createClip(user: User): CreateClipResponse {

        return try {

         channelClient.createClip(
            user.twitchUserId,
            StringToBearer.insertBearer(user.twitchUserAccessToken),
            botConfig.getClientId()
        ).also {
            chatService.sendMessage(user.twitchUserLogin, twitchUrls.twitchClipUrl(it.data[0].id))
        }}catch (e: NotFoundException){

            throw NotFoundException("Não foi possível criar um clipe, você está fazendo live no momento ?")
        }
    }

}