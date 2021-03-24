package alexa.app.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@EntityScan(basePackages = ["alexa.app.model"])
@PropertySource(
    ignoreResourceNotFound = true,
    value = ["classpath:application.properties", "classpath:application-prod.properties"]
)
class AppConfig 