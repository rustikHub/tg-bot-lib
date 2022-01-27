package uz.ugnis.tgbotlib

import org.springframework.http.ResponseEntity
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update

sealed class CustomExceptions(_message: String) : RuntimeException(_message) {
    abstract fun errorType(): ErrorType
}

abstract class CallbackHandleException(private val callbackQuery: CallbackQuery, _message: String) :
    CustomExceptions(_message) {
    fun handle(): ResponseEntity<Any> {
        catch(callbackQuery, message!!)
        return ResponseEntity.badRequest().body(message)
    }

    override fun errorType() = ErrorType.CALLBACK_ERROR

    abstract fun catch(callbackQuery: CallbackQuery, message: String)
}

abstract class MassageHandleException(private val telegramMessage: Message, _message: String) :
    CustomExceptions(_message) {
    fun handle(): ResponseEntity<Any> {
        catch(telegramMessage, message!!)
        return ResponseEntity.badRequest().body(message)
    }

    override fun errorType() = ErrorType.MESSAGE_ERROR

    abstract fun catch(telegramMessage: Message, message: String)
}

abstract class AnyException(private val update: Update, _message: String) :
    CustomExceptions(_message) {
    fun handle(): ResponseEntity<Any> {
        catch(update, message!!)
        return ResponseEntity.badRequest().body(message)
    }

    override fun errorType() = ErrorType.ANY_ERROR

    abstract fun catch(update: Update, message: String)
}
