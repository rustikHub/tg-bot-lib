package uz.ugnis.tgbotlib

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class MainBot(
    private val updateHandler: UpdateHandler,
    private var authenticate: Authenticate,
    @Value("\${bot.token}")
    private var token: String,
    @Value("\${bot.name}")
    private var username: String,
) : TelegramLongPollingBot() {
    override fun getBotToken() = token

    override fun getBotUsername() = username

    override fun onUpdateReceived(update: Update) {
        authenticate.authenticate(update)
        updateHandler.handle(update, this)
    }
}