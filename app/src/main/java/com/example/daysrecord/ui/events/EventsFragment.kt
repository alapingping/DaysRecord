package com.example.daysrecord.ui.events

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.daysrecord.R
import com.example.daysrecord.Utils
import com.example.daysrecord.database.entity.Record
import kotlinx.android.synthetic.main.events_fragment.*

private const val ADD_RECORD_CODE = 1

class EventsFragment : Fragment() {

    companion object {
        fun newInstance() = EventsFragment()
    }

    private val viewModel by lazy {
        ViewModelProvider(this).get(EventsViewModel::class.java)
    }

    private var records = ArrayList<Record>()
    private lateinit var adapter: RecordAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.events_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.records.observe(requireActivity()) {
            records.clear()
            records.addAll(it)
            adapter.notifyDataSetChanged()
        }
        updateRecords()

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recycler.layoutManager = layoutManager
        adapter = RecordAdapter(requireActivity(), records)
        recycler.adapter = adapter
        swipeRefresh.setOnRefreshListener {
            // 更新数据
            updateRecords()
//            Toast.makeText(requireContext(), "刷新数据", Toast.LENGTH_LONG).show()
            swipeRefresh.isRefreshing = false
        }
        floatButton.setOnClickListener {
            // 新建Record
            val intent = Intent(requireContext(), RecordDetailActivity::class.java)
            intent.putExtra("record", Record(title = "", content = "", time = Utils.getDefaultTime()))
            startActivityForResult(intent, ADD_RECORD_CODE)
        }

    }

    fun updateRecords() = viewModel.getAllRecords()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_RECORD_CODE) {
            // 更新界面
            updateRecords()
        }
    }



}