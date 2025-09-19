package com.example.palmtest.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.palmtest.legacy.data.Message
import com.example.palmtest.legacy.viewmodel.ChatViewModel
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.inOrder
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ChatViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var messagesObserver: Observer<List<Message>>

    private lateinit var viewModel: ChatViewModel

    @Before
    fun setup() {
        viewModel = ChatViewModel()
        viewModel.messages.observeForever(messagesObserver)
    }

    @Test
    fun `should initialize with empty messages list`() {
        verify(messagesObserver).onChanged(emptyList())
    }

    @Test
    fun `should add message when sendMessage is called`() {
        val messageText = "Hello World"

        val inOrder = inOrder(messagesObserver)

        // Initial state
        inOrder.verify(messagesObserver).onChanged(emptyList())

        // Action
        viewModel.sendMessage(messageText)

        // Verification
        val captor = argumentCaptor<List<Message>>()
        inOrder.verify(messagesObserver).onChanged(captor.capture())
        val capturedValue = captor.firstValue
        assert(capturedValue.size == 1)
        assert(capturedValue[0].text == messageText)
    }
}