package com.example.daysrecord

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.widget.Toast

inline fun <reified T> startActivity(context: Context, block: Intent.() -> Unit) {
    val intent = Intent(context, T::class.java)
    intent.block()
    context.startActivity(intent)
}

fun String.showToast(time: Int = Toast.LENGTH_LONG) {
    Toast.makeText(DayRecordApplication.context, this, time).show()
}

fun SharedPreferences.open(block: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    editor.block()
    editor.apply()
}