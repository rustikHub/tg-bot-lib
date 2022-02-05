package uz.ugnis.tgbotlib

import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

sealed class CustomExceptions(_message: String) : RuntimeException(_message) {
    abstract fun errorType(): ErrorType
}

abstract class CallbackHandleException(
    val absSender: AbsSender,
    val callbackQuery: CallbackQuery,
    _message: String
) : CustomExceptions(_message) {
    abstract fun handle()

    override fun errorType() = ErrorType.CALLBACK_ERROR
}

abstract class MassageHandleException(
    val absSender: AbsSender,
    val telegramMessage: Message,
    _message: String
) : CustomExceptions(_message) {
    abstract fun handle()

    override fun errorType() = ErrorType.MESSAGE_ERROR
}

abstract class AnyException(val absSender: AbsSender, val update: Update, _message: String) :
    CustomExceptions(_message) {
    abstract fun handle()

    override fun errorType() = ErrorType.MESSAGE_ERROR
}

class UserNotFoundException(message: String) : RuntimeException(message)

