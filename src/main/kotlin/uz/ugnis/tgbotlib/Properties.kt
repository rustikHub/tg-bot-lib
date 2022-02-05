package uz.ugnis.tgbotlib

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration


@Configuration
@ConfigurationProperties(prefix = "telegram")
@EnableConfigurationProperties
class TelegramConfig {
    var bots: List<Bot> = ArrayList()

    // Getter/Setter for gateways
    // ...
    class Bot {
        val token: String = ""
        val username: String = ""
    }
}