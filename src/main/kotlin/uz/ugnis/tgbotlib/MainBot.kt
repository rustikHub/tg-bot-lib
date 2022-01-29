package uz.ugnis.tgbotlib

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update


class MainBot(
    private val updateHandler: UpdateHandler,
    private var authenticate: Authenticate,
    private var token: String,
    private var username: String,
) : TelegramLongPollingBot() {
    override fun getBotToken() = token

    override fun getBotUsername() = username

    override fun onUpdateReceived(update: Update) {
        authenticate.authenticate(update)
        updateHandler.handle(update, this)
    }
}