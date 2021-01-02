package com.example.daysrecord.ui.addrecord

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.widget.DatePicker
import com.example.daysrecord.*
import kotlinx.android.synthetic.main.activity_add_record.*
import java.util.*

class RecordAddActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_record)
        time_input.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this, 0, DatePickerDialog.OnDateSetListener {
                    datePicker: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                run {
                    time_input.text = Editable.Factory.getInstance().newEditable("${year}.${month}.${dayOfMonth}")
                }
            }, Calendar.getInstance(Locale.CHINA).get(Calendar.YEAR)
            , Calendar.getInstance(Locale.CHINA).get(Calendar.MONTH)
            , Calendar.getInstance(Locale.CHINA).get(Calendar.DAY_OF_MONTH))
            datePickerDialog.show()
        }

        val time = time_input.text.toString()
        val title = title_input.text.toString()
        val content = event_input.text.toString()


        TODO("通知更新ui")

    }

    companion object {
        @JvmStatic
        fun start(context: Context) =
            startActivity<RecordAddActivity>(
                context
            ) {}
    }
}