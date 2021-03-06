package alexa.app.config

import alexa.app.utils.JwtUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter


@Configuration
@EnableWebSecurity
class SecurityConfig @Autowired constructor(private val jwtUtil: JwtUtil) : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(HttpMethod.GET, "/").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/auth/alexa").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/privacy").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/term").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/auth/finish").permitAll()// GET requests don't need auth
                .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                .antMatchers(HttpMethod.GET, "/liveness").permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .addFilterBefore(JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter::class.java).authorizeRequests()
           }

    @Bean
    fun corsFilter(): FilterRegistrationBean<*> {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        with(config) {
            allowCredentials = true
            addAllowedOrigin("*")
            allowedMethods = arrayListOf("POST", "OPTIONS", "GET", "DELETE", "PUT")
            allowedHeaders = arrayListOf("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization")
        }
        source.registerCorsConfiguration("/**", config)
        val bean = FilterRegistrationBean(CorsFilter(source))
        bean.order = Ordered.HIGHEST_PRECEDENCE
        return bean
    }

    @Throws(Exception::class)
    override fun configure(web: WebSecurity) {
        web
            .ignoring()
            .antMatchers("/v1/auth/finish")
    }
}



