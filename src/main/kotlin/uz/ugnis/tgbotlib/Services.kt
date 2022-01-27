package uz.ugnis.tgbotlib

import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

interface UpdateHandler {
    fun handle(update: Update, absSender: AbsSender)
}

interface UpdateHandlersAdapter {
    fun callbackHandlerAdapter(update: Update, sender: AbsSender)
    fun messageHandlerAdapter(update: Update, sender: AbsSender)
}

interface CallbackHandler {
    val callbackHandle: (_callback: CallbackQuery, _sender: AbsSender) -> Unit
    fun callbackTypes(): List<String>
}

interface MessageHandler {
    val messageHandler: (_message: Message, _sender: AbsSender) -> Unit
    fun steps(): List<String>
}

interface Authenticate {
    fun authenticate(update: Update)
}

interface ChatService {
    fun findByChatId(chatId: Long): Chat
}

