package alexa.app.model.mail



class Mail (



    val from: String,
    val to: String,
    val subject: String,
    val htmlTemplate: HtmlTemplate,
)
class HtmlTemplate(val template: String, val props: Map<String, Any>)