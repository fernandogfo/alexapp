package alexa.app.config

import com.github.philippheuer.credentialmanager.CredentialManager
import com.github.philippheuer.credentialmanager.CredentialManagerBuilder
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential
import com.github.philippheuer.credentialmanager.identityprovider.TwitchIdentityProvider
import com.github.twitch4j.chat.TwitchChat
import com.github.twitch4j.chat.TwitchChatBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BotConfig {

    @Value("\${twitch.clientId}")
    private lateinit var clientId: String

    @Value("\${twitch.clientSecret}")
    private lateinit var clientSecret: String

    @Value("\${twitch.acessToken}")
    private lateinit var acessToken: String

    @Value("\${twitch.redirectUrl}")
    private lateinit var redirectUrl: String

    @Value("\${twitch.app.permission.url}")
    private lateinit var twitchAppAuthUrl: String

    @Value("\${jwt.secret}")
    private lateinit var jwtSecret: String

    @Bean
    fun credentialBuilder(): CredentialManager {
            return CredentialManagerBuilder.builder().build().also {
                it.registerIdentityProvider(TwitchIdentityProvider(
                    clientId, clientSecret, redirectUrl))
            }
    }

    @Bean
    fun twitchChat(): TwitchChat {
        return TwitchChatBuilder.builder()
            .withCredentialManager(credentialBuilder())
            .withChatAccount(OAuth2Credential("twitch", acessToken))
            .build()
    }

    fun getClientId(): String {
        return clientId
    }

    fun getClientSecret(): String {
        return clientSecret
    }

    fun getRedirectUrl(): String {
        return redirectUrl
    }

    fun getTwitchAppUrlAuth(): String {
        return twitchAppAuthUrl
    }

    fun getJwtSecret(): String{
        return jwtSecret
    }

}