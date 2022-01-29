package uz.ugnis.tgbotlib

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException
import org.telegram.telegrambots.meta.generics.BotSession
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy


@Configuration
@ConditionalOnClass(TelegramBotsApi::class)
class TelegramBotAutoConfiguration(

    @Value("\${bot.token}")
    private var token: String,
    @Value("\${bot.username}")
    private var username: String,
    private val chatService: ChatService,
    private val messageHandlers: List<MessageHandler>,
    private val callbackHandlers: List<CallbackHandler>,
    private val pollingBots: List<TelegramLongPollingBot>
) {
    private val sessions = mutableListOf<BotSession>()

    @PostConstruct
    @Throws(TelegramApiRequestException::class)
    fun start() {
        val api = telegramBotsApi()
        pollingBots.forEach {
            try {
                sessions.add(api.registerBot(it))
            } catch (e: TelegramApiException) {
                println("Ulana olmadim, Token yoki username xato!")
            }
        }
    }

    @Bean
    @ConditionalOnMissingBean
    @Throws(TelegramApiRequestException::class)
    fun telegramBotsApi() = TelegramBotsApi(DefaultBotSession::class.java)

    @PreDestroy
    fun stop() {
        sessions.forEach {
            it.stop()
        }
    }

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
    fun runner(): MainBot? {
        return MainBot(updateHandler(), authenticate(), token, username)
    }

}
