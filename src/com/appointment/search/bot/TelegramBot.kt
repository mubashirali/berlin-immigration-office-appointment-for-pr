package com.appointment.search.bot

import java.io.BufferedInputStream
import java.io.IOException
import java.net.URL
import java.net.URLConnection

class TelegramBot {
    companion object {
        fun sendMsgToTelegram(currentURL: String) {
            var urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s"

            val apiToken = "5428839927:AAGo3Cr3x5pR0dtnsRYVVNDx3My-dAPCW_4"

            val chatId = "-829675342"
            urlString = String.format(urlString, apiToken, chatId, currentURL)
            try {
                val url = URL(urlString)
                val conn: URLConnection = url.openConnection()
                BufferedInputStream(conn.getInputStream())
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
