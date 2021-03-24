package alexa.app.controller

import alexa.app.config.BotConfig
import alexa.app.model.request.AuthEmailAndCode
import alexa.app.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model

import org.springframework.web.bind.annotation.RequestParam

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping


@Controller
class FrontController @Autowired constructor(
    private val authService: AuthService,
    private val botConfig: BotConfig
) {

    @GetMapping
    fun index(
        @RequestParam(name = "name", required = false, defaultValue = "World") name: String?,
        model: Model
    ): String? {
        model.addAttribute("name", name)
        return "index"
    }

    @GetMapping("v1/auth/alexa")
    fun getUserEmail(
        @RequestParam(name = "code", required = true) code: String?,
        @RequestParam(name = "scope", required = true) scope: String?,
        model: Model
    ): String {

        model.addAttribute("code", code)
        return "auth"
    }

    @PostMapping("v1/auth/finish")
    fun auth(
        @ModelAttribute("authAlexaAndCode") authEmailAndCode: AuthEmailAndCode,
        model: Model
    ): String? {

        authService.registerUser(authEmailAndCode.code ?: "", authEmailAndCode.userEmail ?: "")
        model.addAttribute("link", botConfig.getRedirectUrl())

        return "finish"
    }

}