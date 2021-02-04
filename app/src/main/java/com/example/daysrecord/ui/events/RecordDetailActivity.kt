package com.example.daysrecord.ui.events

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import com.example.daysrecord.BaseActivity
import com.example.daysrecord.R
import com.example.daysrecord.Utils
import com.example.daysrecord.database.entity.Record
import com.example.daysrecord.logic.repository.Repository
import com.example.daysrecord.startActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_record_detail.*
import kotlinx.coroutines.launch
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope

private val MARK_MAP = listOf(false, true)

class RecordDetailActivity : BaseActivity() {

    private lateinit var record: Record
    private var marked = false
    private var isEditing = false
    private var deleted = false


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
                    findItem(R.id.option_mark).isVisible = false
                    if (record_title.text.isEmpty() && record_content.text.isEmpty()) {
                        findItem(R.id.option_done).isEnabled = false
                        findItem(R.id.option_done).icon.mutate()
                        // 初始化图标颜色
                        findItem(R.id.option_done).icon.setColorFilter(resources.getColor(R.color.unEnabled), PorterDuff.Mode.SRC_ATOP)
                    }
                }

            }
            // 焦点不在EditText上
            else {
                isEditing = false
                toolbar.menu.apply{
                    findItem(R.id.option_more_options).isVisible = true
                    findItem(R.id.option_done).isVisible = false
                    findItem(R.id.option_delete).isVisible = true
                    findItem(R.id.option_mark).isVisible = true
                }
            }
        }

        record_title.onFocusChangeListener = onFocusChangeListener
        record_content.onFocusChangeListener = onFocusChangeListener

        val action: (
            text: CharSequence?,
            start: Int,
            before: Int,
            count: Int
        ) -> Unit = { _, _, _, _ ->
            if (record_title.text.isEmpty() && record_content.text.isEmpty()) {
                toolbar.menu.apply {
                    findItem(R.id.option_done).isEnabled = false
                    findItem(R.id.option_done).icon.mutate()
                    findItem(R.id.option_done).icon.setColorFilter(resources.getColor(R.color.unEnabled), PorterDuff.Mode.SRC_ATOP)
                }
            } else {
                toolbar.menu.apply {
                    findItem(R.id.option_done).isEnabled = true
                    findItem(R.id.option_done).icon.mutate()
                    findItem(R.id.option_done).icon.setColorFilter(resources.getColor(R.color.enabled), PorterDuff.Mode.SRC_ATOP)
                }
            }
        }
        record_title.doOnTextChanged(action)
        record_content.doOnTextChanged(action)


        // 工具栏设置
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener {
            if (record_title.text.isEmpty() && record_content.text.isEmpty())
                deleted = true
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
                        dialog.cancel()
                    }
                    .setPositiveButton(resources.getString(R.string.delete)) { dialog, which ->
                        // 删除此纪录
                        deleted = true
                        finish()
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
        when (record.marked) {
            1 -> {
                menu?.findItem(R.id.option_mark)?.setIcon(R.drawable.ic_baseline_mark_24)
                marked = true
            }
        }
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

    override fun onDestroy() {
        super.onDestroy()
        if (!deleted && record.title == record_title.text.toString()
            && record.content == record_content.text.toString()
            && MARK_MAP[record.marked] == marked)
                // 数据未做改动，直接返回
                return
        if (record.id == 0L) {
            if (deleted) return
            // 新建Record
            val record = Record(
                title = record_title.text.toString(),
                content = record_content.text.toString(),
                time = Utils.getDefaultTime(),
                marked = MARK_MAP.indexOf(marked))
            // 插入Record
            GlobalScope.launch(Dispatchers.IO) {
                Repository.insertRecord(record)
            }
        } else {
            // 原有Record做了变更
            if (deleted) {
                // 进行删除操作
                GlobalScope.launch(Dispatchers.IO) { Repository.deleteRecordById(record.id) }
            } else {
                // 进行更新操作
                GlobalScope.launch(Dispatchers.IO) {
//                Repository.updateRecordById(
//                    record.id,
//                    record_title.text.toString(),
//                    record_content.text.toString(),
//                    Utils.getDefaultTime(),
//                    )
                    val recordCopy = record.copy(
                        record.id,
                        record_title.text.toString(),
                        record_content.text.toString(),
                        Utils.getDefaultTime(),
                        MARK_MAP.indexOf(marked)
                    )
                    Repository.updateRecord(recordCopy)
                }
            }
        }
        setResult(1)
    }
}