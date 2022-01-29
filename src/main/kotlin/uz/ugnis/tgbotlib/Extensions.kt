package uz.ugnis.tgbotlib

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands
import org.telegram.telegrambots.meta.api.methods.pinnedmessages.PinChatMessage
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction
import org.telegram.telegrambots.meta.api.methods.send.SendDocument
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.methods.updatingmessages.*
import org.telegram.telegrambots.meta.bots.AbsSender
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import java.io.File

fun AbsSender.sendAction(chatId: String) {
    val sendAction = SendChatAction(chatId, "typing")
    try {
        this.execute(sendAction)
    } catch (e: Exception) {
        println("Could not send action to $chatId. Error Message: ${e.message}")
    }
}

fun AbsSender.pinMessage(chatId: String?, messageId: Int?) {
    try {
        val pinMessage = PinChatMessage(chatId!!, messageId!!)
        this.execute(pinMessage)
    } catch (e: Exception) {
        println("Could not pin message from chat $chatId, message $messageId")
    }
}

fun AbsSender.deleteMessage(messageId: Int, chatId: String) {
    val result = DeleteMessage()
    result.chatId = chatId
    result.messageId = messageId
    try {
        this.executeAsync(result)
    } catch (e: TelegramApiException) {
        println("Error deleting message")
    }
}

fun AbsSender.removeInlineMarkup(messageId: Int, chatId: String) {
    val result = EditMessageReplyMarkup()
    result.chatId = chatId
    result.messageId = messageId

    result.replyMarkup = InlineKeyboardMarkupBuilder()
        .build()

    try {
        this.executeAsync(result)
    } catch (e: TelegramApiException) {
        println("Error editing message")
    }
}

fun AbsSender.deleteMessage(result: DeleteMessage) {
    try {
        this.executeAsync(result)
    } catch (e: TelegramApiException) {
        println("Error deleting message")
    }
}

fun AbsSender.deleteInlineKeyboard(messageId: Int, chatId: String) {
    val editKeyboard = EditMessageReplyMarkup()
    editKeyboard.messageId = messageId
    editKeyboard.chatId = chatId
    editKeyboard.replyMarkup = InlineKeyboardMarkupBuilder.emptyInlineMarkup()
    try {
        this.execute(editKeyboard)
    } catch (e: TelegramApiException) {
        //TODO: Delete qilinmadi
    }
}

fun AbsSender.setCommands() {
    val setMyCommands = SetMyCommands()
}

fun AbsSender.editMessage(editMessageMedia: EditMessageMedia) {
    try {
        this.execute(editMessageMedia)
    } catch (e: TelegramApiException) {
        println("Couldn't edit message media. messageId = ${editMessageMedia.messageId}, chatId = ${editMessageMedia.chatId}. Error Message: ${e.message}")
    }
}

fun AbsSender.editMessage(editMessageMedia: EditMessageMedia, file: File?) {
    try {
        this.execute(editMessageMedia)
    } catch (e: TelegramApiException) {
        println("Couldn't edit message media. messageId = ${editMessageMedia.messageId}, chatId = ${editMessageMedia.chatId}. Error Message: ${e.message}")
    } finally {
        file?.delete()
    }
}

fun AbsSender.editMessage(editMessage: EditMessageText) {
    try {
        this.execute(editMessage)
    } catch (e: TelegramApiException) {
        println("Couldn't edit message. messageId = ${editMessage.messageId}, chatId = ${editMessage.chatId}. Error Message: ${e.message}")
    }
}

fun AbsSender.editMessageReplyMarkup(editMessageReplyMarkup: EditMessageReplyMarkup) {
    try {
        this.execute(editMessageReplyMarkup)
    } catch (e: TelegramApiException) {
        println("Couldn't edit message. messageId = ${editMessageReplyMarkup.messageId}, chatId = ${editMessageReplyMarkup.chatId}. Error Message: ${e.message}")
    }
}

fun AbsSender.editMessageCaption(editMessageCaption: EditMessageCaption) {
    try {
        this.execute(editMessageCaption)
    } catch (e: TelegramApiException) {
        println("Couldn't edit message caption. messageId = ${editMessageCaption.messageId}, chatId = ${editMessageCaption.chatId}. Error Message: ${e.message}")
    }
}

fun AbsSender.sendMessage(send: SendMessage): Int? {
    return this.execute(send).messageId
}

fun AbsSender.sendDocument(sendDocument: SendDocument): Int? {
    return execute(sendDocument).messageId
}

fun AbsSender.sendPhoto(sendPhoto: SendPhoto): Int? {
    return execute(sendPhoto).messageId
}

fun AbsSender.answerCallBackQuery(answerCallbackQuery: AnswerCallbackQuery) {
    try {
        this.execute(answerCallbackQuery)
    } catch (e: TelegramApiException) {
        println(e.message)
    }
}