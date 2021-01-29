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
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.daysrecord.R
import com.example.daysrecord.Utils
import com.example.daysrecord.database.entity.Record
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
        val view:View? = inflater.inflate(R.layout.events_fragment, container, false)


        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecords()

        viewModel.records.observe(requireActivity()) {records ->
            adapter.notifyDataSetChanged()
        }

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recycler.layoutManager = layoutManager
        adapter = RecordAdapter(requireActivity(), records)
        recycler.adapter = adapter
        swipeRefresh.setOnRefreshListener {
            // 更新数据
//            initRecords()
//            adapter.notifyDataSetChanged()
            Toast.makeText(requireContext(), "刷新数据", Toast.LENGTH_LONG).show()
            swipeRefresh.isRefreshing = false
        }
        floatButton.setOnClickListener {
            // 新建Record
            RecordDetailActivity.start(requireContext(), Record("", "", Utils.getDefaultTime()))
        }

    }

    fun initRecords() {
        records.run {
            for (i in 1..2) {
                add(Record("第${i}天", "无事", "2020-01-28 20:30"))
            }
            for (i in 3..4) {
                add(Record("第${i}天", "无事", "2021-01-18 20:30"))
            }
            for (i in 5..6) {
                add(Record("第${i}天", "无事", "2021-01-28 20:30"))
            }
        }
    }

}