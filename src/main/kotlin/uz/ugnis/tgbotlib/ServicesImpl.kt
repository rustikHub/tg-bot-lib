package uz.ugnis.tgbotlib

import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender
import uz.ugnis.tgbotlib.Authenticate
import uz.ugnis.tgbotlib.UpdateHandler
import uz.ugnis.tgbotlib.UpdateHandlersAdapter

@Component
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

class DefaultAuthenticateImpl : Authenticate {
    override fun authenticate(update: Update) {
        println("\t\tNo Auth")
    }
}

class DefaultChatService : ChatService {
    override fun findByChatId(chatId: Long): Chat {
        return Chat(565656,"S")
    }

}