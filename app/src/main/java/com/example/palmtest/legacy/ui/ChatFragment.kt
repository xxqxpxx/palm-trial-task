package com.example.palmtest.legacy.ui

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.palmtest.legacy.viewmodel.ChatViewModel

class ChatFragment : Fragment() {
    private val viewModel: ChatViewModel by viewModels()
    private var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Create a simple RecyclerView programmatically for testing
        return RecyclerView(requireContext()).apply {
            layoutManager = LinearLayoutManager(requireContext())
            recyclerView = this
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // FIXED: Use viewLifecycleOwner instead of requireActivity()
        // This ensures the observer is tied to the fragment's view lifecycle
        viewModel.messages.observe(viewLifecycleOwner) { msgs ->
            recyclerView?.adapter = MessagesAdapter(msgs)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerView = null
    }
}
