package com.example.daysrecord.ui.events

import android.content.Context
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.daysrecord.BaseActivity
import com.example.daysrecord.R
import com.example.daysrecord.Utils
import com.example.daysrecord.database.entity.Record
import com.example.daysrecord.startActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_record_detail.*


class RecordDetailActivity : BaseActivity() {

    private lateinit var record: Record
    private var marked = false
    private var isEditing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_detail)
        record = intent.getParcelableExtra("record")!!

        record_title.text = SpannableStringBuilder(record.title)
        record_content.text = SpannableStringBuilder(record.content)
        record_time.text = Utils.getTransformedTime(record.time)

        val onFocusChangeListener: View.OnFocusChangeListener = View.OnFocusChangeListener {
                v, hasFocus ->
            if (v is EditText && isEditing) return@OnFocusChangeListener
            else if (v is EditText && !isEditing && hasFocus) {
                // 焦点变到EditText上
                isEditing = true
                toolbar.menu.apply{
                    findItem(R.id.option_more_options).isVisible = false
                    findItem(R.id.option_done).isVisible = true
                    findItem(R.id.option_delete).isVisible = false
                }
            }
            // 焦点不在EditText上
            else {
                isEditing = false
                toolbar.menu.apply{
                    findItem(R.id.option_more_options).isVisible = true
                    findItem(R.id.option_done).isVisible = false
                    findItem(R.id.option_delete).isVisible = true
                }
            }
        }
        record_title.onFocusChangeListener = onFocusChangeListener
        record_content.onFocusChangeListener = onFocusChangeListener

        // 工具栏设置
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        toolbar.inflateMenu(R.menu.menu_record_detail)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.option_delete -> {
                MaterialAlertDialogBuilder(this)
                    .setMessage(resources.getString(R.string.confirm_message))
                    .setNegativeButton(resources.getString(R.string.cancel)) { dialog, which ->
                        // Respond to negative button press
                        dialog.cancel()
                    }
                    .setPositiveButton(resources.getString(R.string.delete)) { dialog, which ->
                        // Respond to positive button press
                        // TODO: 删除此纪录
                    }
                    .show()
            }
            R.id.option_done -> {
                val manager: InputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(
                    currentFocus?.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
                isEditing = false
                if (record_title.hasFocus()) record_title.clearFocus()
                if (record_content.hasFocus()) record_content.clearFocus()
            }
            R.id.option_mark -> {
                marked = !marked
                if (marked) {
                    toolbar.menu.findItem(R.id.option_mark).setIcon(R.drawable.ic_baseline_mark_24)
                } else {
                    toolbar.menu.findItem(R.id.option_mark)
                        .setIcon(R.drawable.ic_baseline_unmark_24)
                }
            }
            R.id.option_more_options -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_record_detail, menu)
        menu?.findItem(R.id.option_done)?.isVisible = false
        return true
    }

    companion object {
        @JvmStatic
        fun start(context: Context, record: Record) {
            startActivity<RecordDetailActivity>(
                context
            ) {
                putExtra("record", record)
            }
        }

    }

}