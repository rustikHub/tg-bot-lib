package uz.ugnis.tgbotlib

import org.springframework.context.annotation.Import

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@Import(TelegramBotAutoConfiguration::class)
annotation class EnableTelegramBotAutoConfiguration
