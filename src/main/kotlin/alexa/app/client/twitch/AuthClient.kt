package alexa.app.client.twitch

import alexa.app.model.response.RefreshUserTokenResponse
import alexa.app.model.response.UserAcessTokenResponse
import alexa.app.model.response.UserTokenValidation

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

@FeignClient(name = "AuthClient", url = "\${twitch.auth.url}")
interface AuthClient {

    @PostMapping("/token")
    fun getUserAcessToken(
        @RequestParam("client_id") clientId: String,
        @RequestParam("client_secret") clientSecret: String,
        @RequestParam("code") code: String,
        @RequestParam("grant_type") grantType: String,
        @RequestParam("redirect_uri") redirectUri: String
    ): Optional<UserAcessTokenResponse>

    @GetMapping("/validate")
    fun validateUserAcessToken(@RequestHeader("Authorization") token: String): Optional<UserTokenValidation>

    @PostMapping("/token")
    fun refreshAcessToken(
        @RequestParam("grant_type") grantType: String?,
        @RequestParam("refresh_token") refreshToken: String?,
        @RequestParam("client_id") clientId: String?,
        @RequestParam("client_secret") clientSecret: String?
    ): Optional<RefreshUserTokenResponse>

}