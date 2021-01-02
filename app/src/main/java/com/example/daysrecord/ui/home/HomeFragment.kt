package com.example.daysrecord.ui.home

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import com.example.daysrecord.R
import com.example.daysrecord.open
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment() {

    companion object {
        val TAG = "HOME_FRAGMENT"
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private var imageClickListener: View.OnClickListener? = null
    private var nameClickListener: View.OnClickListener? = null

    private val defaultImageId = R.drawable.ic_baseline_person_circle_24
    private val defaultName = "未设置"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel

        // 设置背景透明度
        home_background.background.alpha = 100
        // 设置头像
        imageClickListener = View.OnClickListener {
            TODO("打开相册选择相片")
        }
        male_image_view.setOnClickListener(imageClickListener)
        female_image_view.setOnClickListener(imageClickListener)

        // 设置姓名内容
        nameClickListener = View.OnClickListener {
            TODO("输入姓名")
        }
        male_name.setOnClickListener(nameClickListener)
        female_name.setOnClickListener(nameClickListener)
        // 设置数据
        loadDataFromSP()
        // 设置toolbar透明
//        toolbar.background.alpha  = 0
    }

    private fun loadDataFromSP() {
        activity?.getSharedPreferences("data", Context.MODE_PRIVATE)?.run {
            val day = this.getInt("days", 0)
            days.text = "${day.toString()}天"
            male_name.text = this.getString("male_name", defaultName)
            female_name.text = this.getString("male_name", defaultName)
            male_image_view.setImageResource(this.getInt("male_image", defaultImageId))
            female_image_view.setImageResource(this.getInt("female_image", defaultImageId))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.getSharedPreferences("data", Context.MODE_PRIVATE)?.open{
            putInt("days", days.text.toString().toInt())
            putString("male_name", male_name.text.toString())
            putString("female_name", female_name.text.toString())
            putInt("male_image", male_image_view.id)
            putInt("female_image", female_image_view.id)
        }
    }

}