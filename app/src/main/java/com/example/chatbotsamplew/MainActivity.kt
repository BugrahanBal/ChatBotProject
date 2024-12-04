package com.example.chatbotsamplew
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.app.Activity
import okhttp3.*

class MainActivity : Activity(){

    private lateinit var client: OkHttpClient
    private lateinit var webSocket: WebSocket
    private lateinit var chatContainer: LinearLayout
    private lateinit var messageInput: EditText
    private lateinit var sendButton: Button

    private val websocketUrl = "wss://echo.websocket.org" // WebSocket URL
    private val tag = "ChatBot"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chatContainer = findViewById(R.id.chatContainer)
        messageInput = findViewById(R.id.messageInput)
        sendButton = findViewById(R.id.sendButton)

        client = OkHttpClient()
        initWebSocket()

        sendButton.setOnClickListener {
            val message = messageInput.text.toString().trim()
            if (message.isNotEmpty()) {
                sendMessage(message)
                messageInput.text.clear()
            }
        }
    }

    private fun initWebSocket() {
        val request = Request.Builder().url(websocketUrl).build()
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.d(tag, "WebSocket bağlantısı açıldı")
                runOnUiThread { addMessageToChat("Sistem: Bağlantı kuruldu.", true) }
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                Log.d(tag, "Gelen mesaj: $text")
                runOnUiThread { addMessageToChat("Bot: $text", true) }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Log.e(tag, "WebSocket hatası: ${t.message}")
                runOnUiThread { addMessageToChat("Sistem: Bağlantı hatası oluştu.", true) }
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                Log.d(tag, "Bağlantı kapanıyor: $reason")
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                Log.d(tag, "Bağlantı kapandı: $reason")
            }
        })
    }

    private fun sendMessage(message: String) {
        addMessageToChat("Sen: $message", false)
        webSocket.send(message)
    }

    private fun addMessageToChat(message: String, isBotMessage: Boolean) {
        val textView = TextView(this).apply {
            text = message
            setPadding(16, 16, 16, 16)
            textSize = 16f
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = if (isBotMessage) Gravity.START else Gravity.END
            }
            setBackgroundResource(if (isBotMessage) android.R.color.holo_blue_light else android.R.color.holo_green_light)
        }

        chatContainer.addView(textView)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::webSocket.isInitialized) {
            webSocket.close(1000, "Uygulama kapatıldı")
        }
        client.dispatcher.executorService.shutdown()
    }
}