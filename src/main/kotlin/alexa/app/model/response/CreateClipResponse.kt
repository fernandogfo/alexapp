package alexa.app.model.response

class CreateClipResponse {
    val data: List<responseDataClip> = emptyList()
}
class responseDataClip (
    val id: String,
    val edit_url: String
)