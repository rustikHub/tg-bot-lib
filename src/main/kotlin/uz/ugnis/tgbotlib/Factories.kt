package uz.ugnis.tgbotlib

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.bots.AbsSender

@Service
class HandlersFactory(messageHandlers: List<MessageHandler>, callbackHandlers: List<CallbackHandler>) {
    private val messageHandlerFactory: MutableMap<String, (message: Message, sender: AbsSender) -> Unit> =
        mutableMapOf()
    private val callbackHandlerFactory: MutableMap<String, (callback: CallbackQuery, sender: AbsSender) -> Unit> =
        mutableMapOf()
    internal val messageHandlerSteps: MutableList<List<String>> = mutableListOf()

    init {
        messageHandlers.forEach {
            messageHandlerFactory[it.steps().first()] = it.messageHandler
            messageHandlerSteps.add(it.steps())
        }
        callbackHandlers.forEach {
            callbackHandlerFactory[it.callbackTypes().first()] = it.callbackHandle
        }
    }

    fun handleMessage(step: String, message: Message, sender: AbsSender) {
        messageHandlerFactory[step]!!.invoke(message, sender)
    }

    fun handleCallback(callbackDataType: String, callbackQuery: CallbackQuery, sender: AbsSender) {
        callbackHandlerFactory[callbackDataType]!!.invoke(callbackQuery, sender)
    }
}