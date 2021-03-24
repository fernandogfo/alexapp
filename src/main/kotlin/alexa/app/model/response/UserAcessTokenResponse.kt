package alexa.app.model.response

class UserAcessTokenResponse (

    val access_token: String,
    val expires_in: Int,
    val refresh_token: String,
    val scope: List<String>,
    val token_type: String

        )