package com.example.daysrecord.ui.message

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import com.example.daysrecord.BaseActivity
import com.example.daysrecord.R
import com.example.daysrecord.startActivity
import com.example.daysrecord.ui.message.MessageFragment.Companion.NEW_MESSAGE_ACTIVITY_REQUEST_CODE
import kotlinx.android.synthetic.main.activity_new_message.*

class NewMessageActivity : BaseActivity() {

    private val author: String = "name"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)

        new_message_content.addTextChangedListener(onTextChanged = {
            s,_,_,_ ->
            run {
                publish_button.isEnabled = s?.length != 0
            }
        })
        publish_button.isEnabled = false
        publish_button.setOnClickListener {
            val intent = Intent().apply {
                putExtra("messageAuthor", author)
                putExtra("messageContent", new_message_content.text.toString())
            }
            setResult(NEW_MESSAGE_ACTIVITY_REQUEST_CODE, intent)
            finish()
        }

    }


    companion object {
        @JvmStatic
        fun start(context: Context) {
            startActivity<NewMessageActivity>(
                context
            ) {

            }
        }
    }

}