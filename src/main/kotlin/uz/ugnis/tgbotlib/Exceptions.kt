package uz.ugnis.tgbotlib

import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Message

sealed class CustomExceptions(_message: String) : RuntimeException(_message) {
    abstract fun errorType(): ErrorType
}

abstract class CallbackHandleException(private val callbackQuery: CallbackQuery, _message: String) :
    CustomExceptions(_message) {
    fun handle() {
        catch(callbackQuery, message!!)
    }

    override fun errorType() = ErrorType.CALLBACK_ERROR

    abstract fun catch(callbackQuery: CallbackQuery, message: String)
}

abstract class MassageHandleException(private val telegramMessage: Message, _message: String) :
    CustomExceptions(_message) {
    fun handle() {
        catch(telegramMessage, message!!)
    }

    override fun errorType() = ErrorType.MESSAGE_ERROR

    abstract fun catch(telegramMessage: Message, message: String)
}

