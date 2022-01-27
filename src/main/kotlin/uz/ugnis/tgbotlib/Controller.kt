package uz.ugnis.tgbotlib

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(CustomExceptions::class)
    fun handleYIMException(exception: CustomExceptions) = when (exception.errorType()) {
        ErrorType.CALLBACK_ERROR -> (exception as CallbackHandleException).handle()
        ErrorType.MESSAGE_ERROR -> (exception as MassageHandleException).handle()
        ErrorType.ANY_ERROR -> (exception as AnyException).handle()
    }
}