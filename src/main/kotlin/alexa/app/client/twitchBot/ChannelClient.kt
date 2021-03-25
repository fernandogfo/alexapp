package alexa.app.client.twitchBot

import alexa.app.model.request.StartCommercialBody
import alexa.app.model.response.CreateClipResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "ChannelClient", url = "\${twitch.helix}")
interface ChannelClient {

    @PostMapping("/clips")
    fun createClip(
        @RequestParam("broadcaster_id") broadcaster_id: String?,
        @RequestHeader("Authorization") Authorization: String?,
        @RequestHeader("client-id") clientId: String?
    ): CreateClipResponse

    @PostMapping("/channels/commercial")
    fun startCommercial(
        @RequestBody startCommercialBody: StartCommercialBody,
        @RequestHeader("Authorization") Authorization: String?,
        @RequestHeader("client-id") clientId: String?
    )

}