package alexa.app.service

import alexa.app.client.twitch.AuthClient
import alexa.app.config.BotConfig
import alexa.app.exception.BadRequestException
import alexa.app.exception.NotFoundException
import alexa.app.model.User
import alexa.app.repository.UserRepository
import alexa.app.utils.StringToBearer
import com.apollographql.apollo.Logger
import org.hibernate.bytecode.BytecodeLogger.LOGGER
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import sun.rmi.runtime.Log

@Service
class AuthService @Autowired constructor(
    private val userRepository: UserRepository,
    private val authClient: AuthClient,
    private val botConfig: BotConfig
) {

    fun registerUser(code: String, userEmail: String) {

        val tokenResponse = authClient.getUserAcessToken(
            botConfig.getClientId(), botConfig.getClientSecret(),
            code, "authorization_code", botConfig.getRedirectUrl()
        ).orElseThrow { BadRequestException("Algo deu errado!") }

        val isAuth = authClient.validateUserAcessToken("Bearer ${tokenResponse.access_token}")
            .orElseThrow { BadRequestException("Algo deu errado!") }

        val user = userRepository.findByTwitchUserId(isAuth.user_id)

        if (user.isPresent) {
            user.get().userEmail = userEmail
            user.get().twitchUserLogin = isAuth.login
            user.get().twitchUserAccessToken = tokenResponse.access_token
            user.get().twitchUserRefreshAccessToken = tokenResponse.refresh_token
            userRepository.save(user.get())

        } else {
            userRepository.save(User().apply {
                this.userEmail = userEmail
                twitchUserId = isAuth.user_id
                twitchUserLogin = isAuth.login
                twitchUserAccessToken = tokenResponse.access_token
                twitchUserRefreshAccessToken = tokenResponse.refresh_token
            })
        }

    }

    fun validateTokenByUserEmail(userEmail: String): User {
        val user = userRepository.findByUserEmail(userEmail)
            .orElseThrow { NotFoundException("Esse usuário não está cadastrado!") }

        return try {
            authClient.validateUserAcessToken(StringToBearer.insertBearer(user.twitchUserAccessToken))
            user
        } catch (e: BadRequestException) {
            LOGGER.info("Acess Token expirado ou inválido, tentando revalidar: ${e}")
            return refreshAcessToken(user)
        }

    }

    fun refreshAcessToken(user: User): User {
        LOGGER.info("Revalidando token do usuário: ${user.userEmail}")
        val newToken = authClient.refreshAcessToken(
            "refresh_token", user.twitchUserRefreshAccessToken, botConfig.getClientId(),
            botConfig.getClientSecret()
        ).orElseThrow { BadRequestException("Ocorreu um erro ao revalidar token!") }

        val userToSave = userRepository.findById(user.id!!).orElseThrow { NotFoundException("Usuário não encontrado!") }

        userToSave.twitchUserAccessToken = newToken.accessToken
        userToSave.twitchUserRefreshAccessToken = newToken.refreshToken
        return userRepository.save(userToSave)
    }
}