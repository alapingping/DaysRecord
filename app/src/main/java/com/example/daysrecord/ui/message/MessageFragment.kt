package com.example.daysrecord.ui.message

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daysrecord.R
import com.example.daysrecord.Utils
import com.example.daysrecord.Utils.Companion.getDefaultTime
import com.example.daysrecord.Utils.Companion.showToast
import com.example.daysrecord.logic.model.Message
import kotlinx.android.synthetic.main.fragment_message.*

/**
 * A simple [Fragment] subclass.
 * Use the [MessageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

private const val DEFAULT_USERNAME = "默认"

class MessageFragment : Fragment() {

    private val messageAdapter by lazy {
        MessageAdapter(requireContext(), messages);
    }
    private val messages = ArrayList<Message?>()

    private val viewModel by lazy {
        ViewModelProvider(this).get(MessageViewModel::class.java)
    }

    private val cache = ArrayList<Message?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.list.observe(viewLifecycleOwner) {
            messages.clear()
            messages.addAll(it)
            messageAdapter.submitList(it)
        }
        viewModel.code.observe(viewLifecycleOwner) {
            if (it == 200) {
                messages.addAll(cache)
                messageAdapter.submitList(messages)
                cache.clear()
            }
        }

        val layoutManager = LinearLayoutManager(requireContext())
        message_recycler.layoutManager = layoutManager
        message_recycler.adapter = messageAdapter
        if (Utils.isNetworkAvailable(requireContext())) {
            viewModel.getMessagesFromRemote()
        } else {
            "当前网络不可用，请检查网络状况".showToast()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    fun startNewMessageActivity() =
        startActivityForResult(Intent(requireActivity(), NewMessageActivity::class.java), NEW_MESSAGE_ACTIVITY_REQUEST_CODE)


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (NEW_MESSAGE_ACTIVITY_REQUEST_CODE == requestCode) {
            val message = data?.let {
                val messageAuthor = it.getStringExtra("messageAuthor")
                val messageContent = it.getStringExtra("messageContent")
                val messageTime = getDefaultTime()
                Message(
                    author = messageAuthor!!,
                    content = messageContent!!,
                    time = messageTime
                )
            }
            if (Utils.isNetworkAvailable(requireContext())) {
                viewModel.addNewMessage(message!!)
            } else {
                "当前网络不可用，请检查网络状况".showToast()
            }
            cache.add(message)
        }
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

        const val NEW_MESSAGE_ACTIVITY_REQUEST_CODE = 1

    }
}