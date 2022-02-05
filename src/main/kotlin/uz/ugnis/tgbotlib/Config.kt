package uz.ugnis.tgbotlib

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.meta.api.objects.Update


@Configuration
class TelegramBotAutoConfiguration(
    @Value("\${bot.token}")
    private var token: String,
    @Value("\${bot.username}")
    private var username: String,
    private val chatService: ChatService,
    private val messageHandlers: List<MessageHandler>,
    private val callbackHandlers: List<CallbackHandler>,
) {

    @Bean
    fun updateHandlersAdapter(): UpdateHandlersAdapter {
        return UpdateHandlerAdapterImpl(messageHandlers, callbackHandlers, chatService)
    }

    @Bean
    fun updateHandler(): UpdateHandler {
        return UpdateHandlerImpl(updateHandlersAdapter())
    }

    @Bean
    @ConditionalOnMissingBean(Authenticate::class)
    fun authenticate(): Authenticate {
        return object : Authenticate {
            override fun authenticate(update: Update) {

            }
        }
    }

    @Bean
    fun runner(): MainBot {
        return MainBot(updateHandler(), authenticate(), token, username)
    }

}
