package alexa.app.email.service

import alexa.app.config.BotConfig
import alexa.app.model.mail.HtmlTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import org.thymeleaf.spring5.SpringTemplateEngine
import java.nio.charset.StandardCharsets
import org.springframework.mail.javamail.MimeMessageHelper
import org.thymeleaf.context.Context

import javax.mail.internet.MimeMessage

import alexa.app.model.mail.Mail

import java.util.HashMap

@Service
class SendEmailService @Autowired constructor(private val emailSender: JavaMailSender,
                                              private val templateEngine: SpringTemplateEngine,
                                              private val botConfig: BotConfig
                                                ) {

    fun generateEmailAndSend(to: String, topic:String, userName: String){
        val properties: MutableMap<String, Any> = HashMap()
        properties["link"] = botConfig.getTwitchAppUrlAuth()
        properties["userName"] = userName
        properties["userEmail"] = to

        val mail: Mail = Mail(
            from = "doutorlunatic@gmail.com",
            to = to,
            subject = topic,
            htmlTemplate = HtmlTemplate("email", properties)
        )
        sendEmail(mail)
    }

    fun sendEmail(mail: Mail) {
        val message: MimeMessage = emailSender.createMimeMessage()
        val helper = MimeMessageHelper(
            message,
            MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
            StandardCharsets.UTF_8.name()
        )
        val html = getHtmlContent(mail)
        helper.setTo(mail.to)
        helper.setFrom(mail.from)
        helper.setSubject(mail.subject)
        helper.setText(html, true)
        emailSender.send(message)
    }

    private fun getHtmlContent(mail: Mail): String {
        val context = Context()
        context.setVariables(mail.htmlTemplate.props)
        return templateEngine.process(mail.htmlTemplate.template, context)
    }

}