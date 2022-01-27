package uz.ugnis.tgbotlib

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@Component
class UpdateHandlerAdapterImpl(
    private val handlersFactory: HandlersFactory,
    private val chatService: ChatService
) : UpdateHandlersAdapter {

    override fun callbackHandlerAdapter(update: Update, sender: AbsSender) {
        val callback = update.callbackQuery
        val callbackDataType = callback.data.split("#").first()
        handlersFactory.handleCallback(callbackDataType, update.callbackQuery, sender)
    }

    override fun messageHandlerAdapter(update: Update, sender: AbsSender) {
        val chatId = update.run {
            when {
                hasInlineQuery() -> inlineQuery.from.id
                hasChosenInlineQuery() -> chosenInlineQuery.from.id
                hasCallbackQuery() -> callbackQuery.from.id
                hasPreCheckoutQuery() -> preCheckoutQuery.from.id
                else -> message.from.id
            }
        }
        val chat = chatService.findByChatId(chatId)
        val steps = handlersFactory.messageHandlerSteps.first {
            it.contains(chat.step)
        }
        handlersFactory.handleMessage(steps.first(), update.message, sender)
    }

}