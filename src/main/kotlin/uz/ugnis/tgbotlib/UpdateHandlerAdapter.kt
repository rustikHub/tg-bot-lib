package uz.ugnis.tgbotlib

import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

class UpdateHandlerAdapterImpl(
    messageHandlers: List<MessageHandler>,
    callbackHandlers: List<CallbackHandler>,
    private val chatService: ChatService
) : UpdateHandlersAdapter {
    private val messageHandlersMap: MutableMap<String, MessageHandler> = HashMap()
    private val callbackHandlersMap: MutableMap<String, CallbackHandler> = HashMap()
    private val allSteps: MutableList<List<String>> = ArrayList()

    init {
        callbackHandlers.forEach {
            callbackHandlersMap[it.callbackTypes()[0]] = it
        }
        messageHandlers.forEach {
            messageHandlersMap[it.steps()[0]] = it
            allSteps.add(it.steps())
        }
    }

    override fun callbackHandlerAdapter(update: Update, sender: AbsSender) {
        val callback = update.callbackQuery
        val step = callback.data.split("#").first()
        try {
            callbackHandlersMap[step]?.callbackHandle?.invoke(callback, sender)
        } catch (e: CallbackHandleException) {
            e.handle()
        }
    }

    override fun messageHandlerAdapter(update: Update, sender: AbsSender) {
        val message = update.message
        val chatId = message.chatId
        val chat = chatService.findByChatId(chatId)
        val step = allSteps.find { it.contains(chat.step) }!!.first()
        try {
            messageHandlersMap[step]?.messageHandler?.invoke(message, sender)
        } catch (e: MassageHandleException) {
            e.handle()
        }
    }
}