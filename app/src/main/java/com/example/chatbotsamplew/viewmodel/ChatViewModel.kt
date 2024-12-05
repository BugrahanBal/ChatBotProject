package com.example.chatbotsamplew.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatbotsamplew.model.Message
import kotlinx.coroutines.launch
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI

class ChatViewModel : ViewModel() {
    private val _messages = MutableLiveData<List<Message>>(emptyList())
    val messages: LiveData<List<Message>> get() = _messages

    private lateinit var webSocketClient: WebSocketClient

    fun connectWebSocket(serverUri: String) {
        val uri = URI(serverUri)
        webSocketClient = object : WebSocketClient(uri) {
            override fun onOpen(handshakedata: ServerHandshake?) {
                addMessage("Connected to server!", false)
            }

            override fun onMessage(message: String?) {
                message?.let { addMessage(it, false) }
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                addMessage("Connection closed: $reason", false)
            }

            override fun onError(ex: Exception?) {
                addMessage("Error: ${ex?.message}", false)
            }
        }
        webSocketClient.connect()
    }

    fun sendMessage(text: String) {
        if (::webSocketClient.isInitialized && webSocketClient.isOpen) {
            webSocketClient.send(text)
            addMessage(text, true)
        } else {
            addMessage("WebSocket is not connected", false)
        }
    }

    private fun addMessage(text: String, isFromUser: Boolean) {
        viewModelScope.launch {
            val currentMessages = _messages.value ?: emptyList()
            _messages.value = currentMessages + Message(text, isFromUser)
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (::webSocketClient.isInitialized) {
            webSocketClient.close()
        }
    }
}