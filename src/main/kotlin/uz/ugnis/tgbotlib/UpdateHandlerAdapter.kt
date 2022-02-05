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
    private val allSteps: MutableMap<String, String> = mutableMapOf()
    private val allCallbacks: MutableMap<String, String> = mutableMapOf()

    init {
        callbackHandlers.forEach {
            callbackHandlersMap[it.callbackTypes()[0]] = it
            val first = it.callbackTypes().first()
            it.callbackTypes().forEach { type ->
                allCallbacks[type] = first
            }
        }
        messageHandlers.forEach {
            messageHandlersMap[it.steps()[0]] = it
            val first = it.steps().first()
            it.steps().forEach { step ->
                allSteps[step] = first
            }
        }
    }

    override fun callbackHandlerAdapter(update: Update, sender: AbsSender) {
        val callback = update.callbackQuery
        val callbackType = callback.data.split("#").first()
        try {
            val rootCallbackType = allCallbacks[callbackType]
            callbackHandlersMap[rootCallbackType]?.callbackHandle(callback, sender)
        } catch (e: CallbackHandleException) {
            e.handle()
        }
    }

    override fun messageHandlerAdapter(update: Update, sender: AbsSender) {
        val message = update.message
        val chatId = message.chatId
        val chat = chatService.findByChatId(chatId)
            ?: if (update.hasMessage() && message.hasText() && message.text.equals("/start")) {
                chatService.save(Chat(chatId, "/start"))
            } else {
                throw UserNotFoundException("Couldn't find user with id $chatId")
            }
        val step = allSteps[chat.step]
        try {
            messageHandlersMap[step]?.messageHandle(message, sender)
        } catch (e: MassageHandleException) {
            e.handle()
        }
    }
}