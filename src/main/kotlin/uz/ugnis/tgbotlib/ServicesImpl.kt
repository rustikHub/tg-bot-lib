package uz.ugnis.tgbotlib

import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

class UpdateHandlerImpl(
    private val updateHandlerAdapter: UpdateHandlersAdapter
) : UpdateHandler {
    override fun handle(update: Update, absSender: AbsSender) {
        when {
            update.hasMessage() -> {
                updateHandlerAdapter.messageHandlerAdapter(update, absSender)
            }
            update.hasCallbackQuery() -> {
                updateHandlerAdapter.callbackHandlerAdapter(update, absSender)
            }
        }
    }
}