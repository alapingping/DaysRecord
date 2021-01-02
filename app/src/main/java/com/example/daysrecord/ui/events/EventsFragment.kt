package com.example.daysrecord.ui.events

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.example.daysrecord.R
import com.example.daysrecord.database.entity.Record
import com.example.daysrecord.ui.addrecord.RecordAddActivity
import kotlinx.android.synthetic.main.events_fragment.*

class EventsFragment : Fragment() {

    companion object {
        fun newInstance() = EventsFragment()
    }

    private val viewModel by lazy {
        ViewModelProvider(this).get(EventsViewModel::class.java)
    }

    private val records = ArrayList<Record>()
    lateinit var adapter: RecordAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initRecords()

        viewModel.records.observe(requireActivity()) {records ->
            adapter.notifyDataSetChanged()
        }

        val layoutManager = GridLayoutManager(context, 1)
        recycler.layoutManager = layoutManager
        adapter = RecordAdapter(requireContext(), records)
        recycler.adapter = adapter
        swipeRefresh.setOnRefreshListener {
            // 更新数据
//            initRecords()
//            adapter.notifyDataSetChanged()
            Toast.makeText(requireContext(), "refresh", Toast.LENGTH_LONG).show()
            swipeRefresh.isRefreshing = false
        }
        floatButton.setOnClickListener {
            // 启动添加Activity
            RecordAddActivity.start(requireContext())
        }

        return inflater.inflate(R.layout.events_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

    fun initRecords() {

        records.run {
            for (i in 1..7) {
                add(Record("第${i}天", "无事"))
            }
        }
    }

}