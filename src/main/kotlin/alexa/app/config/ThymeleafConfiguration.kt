package alexa.app.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import org.thymeleaf.spring5.SpringTemplateEngine

import org.thymeleaf.templatemode.TemplateMode

import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import java.nio.charset.StandardCharsets


@Configuration
class ThymeleafConfiguration {

    @Bean
    fun springTemplateEngine(): SpringTemplateEngine? {
        val templateEngine = SpringTemplateEngine()
        templateEngine.addTemplateResolver(htmlTemplateResolver())
        return templateEngine
    }

    @Bean
    fun htmlTemplateResolver(): ClassLoaderTemplateResolver? {
        val emailTemplateResolver = ClassLoaderTemplateResolver()
        emailTemplateResolver.prefix = "/templates/"
        emailTemplateResolver.suffix = ".html"
        emailTemplateResolver.templateMode = TemplateMode.HTML
        emailTemplateResolver.characterEncoding = StandardCharsets.UTF_8.name()
        return emailTemplateResolver
    }
}