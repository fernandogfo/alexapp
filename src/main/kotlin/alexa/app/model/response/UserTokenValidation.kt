package alexa.app.model.response

class UserTokenValidation (
    val client_id: String,
    val login: String,
    val scopes: List<String>,
    val user_id: String,
    val expires_in: Int
        )