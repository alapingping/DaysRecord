package com.example.daysrecord.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.daysrecord.R
import com.example.daysrecord.open
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.home_fragment.*
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*

class HomeFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    companion object {
        val TAG = "HOME_FRAGMENT"
        fun newInstance() = HomeFragment()
        val MALE_TAG_FLAG = 1
        val FEMALE_TAG_FLAG = 2
    }

    private lateinit var viewModel: HomeViewModel
    private var imageClickListener: View.OnClickListener? = null
    private var nameClickListener: View.OnClickListener? = null

    private val defaultImageId = R.drawable.ic_baseline_person_circle_24
    private val defaultName = "未设置"
    private var startYear: Int? = null
    private var startMonth: Int? = null
    private var startDay: Int? = null
    private var male_image_path: String? = null
    private var female_image_path: String? = null

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
            // TODO("打开相册选择相片")
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, it?.tag.toString().toInt())
        }
        male_image_view.setOnClickListener(imageClickListener)
        female_image_view.setOnClickListener(imageClickListener)

        // 设置姓名内容
        nameClickListener = View.OnClickListener {
            // TODO("输入姓名")
            val code = it?.tag.toString().toInt()
            context?.let { it -> showNameInputDialog(it, code) }
        }
        male_name.setOnClickListener(nameClickListener)
        female_name.setOnClickListener(nameClickListener)
        // 检察读取存储权限
        checkPermission()
        // 设置toolbar透明
//        toolbar.background.alpha  = 0
        days.setOnClickListener {
            // TODO("设置日期")
            val now: Calendar = Calendar.getInstance(Locale.CHINA)
            val datePickerDialog = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show(requireFragmentManager(), "d")
        }
    }

    private fun loadDataFromSP() {
        activity?.getSharedPreferences("data", Context.MODE_PRIVATE)?.run {
            days.text = getString(R.string.days, calculateDaysDiff(LocalDate.of(
                this.getInt("year", LocalDate.now().year),
                this.getInt("monthOfYear", LocalDate.now().monthValue),
                this.getInt("dayOfMonth", LocalDate.now().dayOfMonth)
            ), LocalDate.now()))
            male_name.post {
                male_name.text = this.getString("male_name", defaultName)
            }
            female_name.post { female_name.text = this.getString("female_name", defaultName) }
            male_image_view.post {
                when(val maleImagePath = this.getString("male_image_path","")) {
                    "" -> male_image_view.setImageResource(defaultImageId)
                    else -> male_image_view.setImageURI(Uri.parse(maleImagePath))
                }
            }
            female_image_view.post {
                when (val femaleImagePath = this.getString("female_image_path", "")) {
                    "" -> female_image_view.setImageResource(defaultImageId)
                    else -> female_image_view.setImageURI(Uri.parse(femaleImagePath))
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.getSharedPreferences("data", Context.MODE_PRIVATE)?.open{
            putString("male_name", male_name.text.toString())
            putString("female_name", female_name.text.toString())
            male_image_path?.let { putString("male_image_path", it) }
            female_image_path?.let { putString("female_image_path", it) }
            startYear?.let { putInt("year", it) }
            startMonth?.let { putInt("monthOfYear", it) }
            startDay?.let { putInt("dayOfMonth", it) }
        }
    }

    private fun showNameInputDialog(context: Context, code: Int) {
        val editText: EditText = EditText(context)
        MaterialAlertDialogBuilder(context)
            .setTitle("请输入姓名")
            .setView(editText)
            .setNegativeButton("取消") { _, _->

            }
            .setPositiveButton("确定") { _, _ ->
                when (code) {
                    MALE_TAG_FLAG -> male_name.text = editText.text.toString()
                    FEMALE_TAG_FLAG -> female_name.text = editText.text.toString()
                }
            }
            .show()
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        // TODO("Not yet implemented")
        val startDate = LocalDate.of(year, monthOfYear + 1, dayOfMonth)
        val endDate = LocalDate.now()
        startYear = year
        startMonth = monthOfYear + 1
        startDay = dayOfMonth
        days.text = getString(R.string.days, calculateDaysDiff(startDate, endDate))
    }

    private fun calculateDaysDiff(startDate:LocalDate, endDate:LocalDate) = ChronoUnit.DAYS.between(startDate, endDate)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val uri = data?.data
        when(requestCode) {
            MALE_TAG_FLAG -> {
                male_image_view.setImageURI(uri)
                male_image_path = uri.toString()
            }
            FEMALE_TAG_FLAG -> {
                female_image_view.setImageURI(uri)
                female_image_path = uri.toString()
            }
        }
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf<String>(
                Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else {
            loadDataFromSP()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadDataFromSP()
        }
    }

}