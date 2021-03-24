package alexa.app.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class TwitchUrls {

    @Value("\${twitch.clips.url}")
    lateinit var twitchClipsUrl: String


    fun twitchClipUrl(clipId: String): String{
        return twitchClipsUrl+clipId
    }
}