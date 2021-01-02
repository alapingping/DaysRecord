package com.example.daysrecord.logic.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.daysrecord.DayRecordApplication
import com.example.daysrecord.database.AppDatabase
import com.example.daysrecord.database.entity.Record

object Repository {

    val recordDao = AppDatabase.getDatabase(DayRecordApplication.context).recordDao()

    fun getAllRecords(): LiveData<List<Record>> {
        val records = MutableLiveData<List<Record>>()
        records.value = recordDao.getAllRecords()
        return records
    }

    fun insertRecord(record: Record) {
        recordDao.insertRecord(record)
    }

}