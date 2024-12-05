package com.example.chatbotsamplew.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatbotsamplew.R
import com.example.chatbotsamplew.adapter.ChatAdapter
import com.example.chatbotsamplew.viewmodel.ChatViewModel

class ChatActivity : AppCompatActivity() {
    private val chatViewModel: ChatViewModel by viewModels()
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val sendButton: Button = findViewById(R.id.sendButton)
        val messageInput: EditText = findViewById(R.id.messageInput)

        chatAdapter = ChatAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = chatAdapter

        chatViewModel.messages.observe(this, Observer {
            chatAdapter = ChatAdapter(it)
            recyclerView.adapter = chatAdapter
            recyclerView.scrollToPosition(it.size - 1)
        })

        sendButton.setOnClickListener {
            val message = messageInput.text.toString()
            if (message.isNotEmpty()) {
                chatViewModel.sendMessage(message)
                messageInput.text.clear()
            }
        }

        chatViewModel.connectWebSocket("wss://echo.websocket.org")
    }
}
