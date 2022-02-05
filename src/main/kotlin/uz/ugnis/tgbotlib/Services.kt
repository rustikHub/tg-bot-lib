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
    fun callbackHandle(callback: CallbackQuery, sender: AbsSender)
    fun callbackTypes(): List<String>
}

interface MessageHandler {
    fun messageHandle(message: Message, sender: AbsSender)
    fun steps(): List<String>
}

interface Authenticate {
    fun authenticate(update: Update)
}

interface ChatService {
    fun findByChatId(chatId: Long): Chat?
    fun save(chat: Chat): Chat
}

