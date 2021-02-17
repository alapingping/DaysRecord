package com.example.daysrecord.ui.message

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daysrecord.R
import com.example.daysrecord.logic.model.Comment
import com.example.daysrecord.logic.model.Message
import com.example.daysrecord.logic.model.Result
import com.example.daysrecord.logic.network.Client
import com.example.daysrecord.logic.network.Service
import com.example.daysrecord.ui.events.EventsViewModel
import kotlinx.android.synthetic.main.fragment_message.*
import kotlinx.android.synthetic.main.item_message.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 * Use the [MessageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MessageFragment : Fragment() {

    private val messageAdapter by lazy {
        MessageAdapter(requireContext(), messages);
    }
    private val messages = ArrayList<Message>()

    private val viewModel by lazy {
        ViewModelProvider(this).get(MessageViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.list.observe(viewLifecycleOwner) {
            messageAdapter.submitList(it)
        }

        val layoutManager = LinearLayoutManager(requireContext())
        message_recycler.layoutManager = layoutManager
        message_recycler.adapter = messageAdapter
        viewModel.getMessagesFromRemote()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment MessageFragment.
         */
        @JvmStatic
        fun newInstance() =
            MessageFragment()
    }
}