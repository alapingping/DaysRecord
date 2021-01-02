package com.example.daysrecord.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.example.daysrecord.BaseActivity
import com.example.daysrecord.R
import com.example.daysrecord.database.entity.Record
import com.example.daysrecord.startActivity
import kotlinx.android.synthetic.main.activity_record_detail.*

class RecordDetailActivity : BaseActivity() {

    private lateinit var record: Record
    private var position: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_detail)
        record = intent.getParcelableExtra("record")!!
        position = intent.getIntExtra("position", 0)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Glide.with(this).load(record.pictureId).into(recordImageView)
        collapsingToolbar.title = record.title
        recordContent.text = record.content.repeat(20)

//        _title.text = record.title
//        content.text = record.content
//        time.text = record.time
//        back.setOnClickListener {
//
//            val db = MyDatabaseHelper.getInstance(this).readableDatabase
//            db.delete("record", "time = ? and content = ?", arrayOf(record.title, record.content))
//
//            val msg = Message.obtain()
//            msg.what = 2
//            msg.arg1 = position
//            MainActivity.handler?.sendMessage(msg)
//
//            this.finish()
//        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        @JvmStatic
        fun start(context: Context, record: Record, position: Int) {
            startActivity<RecordDetailActivity>(
                context
            ) {
                putExtra("record", record)
                putExtra("position", position)
            }
        }

    }

}