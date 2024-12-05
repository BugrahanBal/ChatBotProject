package com.example.chatbotsamplew.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatbotsamplew.R
import com.example.chatbotsamplew.model.Message

class ChatAdapter(private val messages: List<Message>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userMessage: TextView = itemView.findViewById(R.id.userMessage)
        val botMessage: TextView = itemView.findViewById(R.id.botMessage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val message = messages[position]
        if (message.isFromUser) {
            holder.userMessage.text = message.text
            holder.userMessage.visibility = View.VISIBLE
            holder.botMessage.visibility = View.GONE
        } else {
            holder.botMessage.text = message.text
            holder.botMessage.visibility = View.VISIBLE
            holder.userMessage.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = messages.size
}