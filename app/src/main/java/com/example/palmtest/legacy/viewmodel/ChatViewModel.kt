package com.example.palmtest.legacy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.palmtest.legacy.data.Message

class ChatViewModel : ViewModel() {
    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> = _messages

    init {
        loadMessages()
    }

    private fun loadMessages() {
        _messages.value = emptyList()
    }

    fun sendMessage(text: String) {
        val currentMessages = _messages.value.orEmpty().toMutableList()
        currentMessages.add(Message(id = System.currentTimeMillis().toString(), text = text))
        _messages.value = currentMessages
    }
}