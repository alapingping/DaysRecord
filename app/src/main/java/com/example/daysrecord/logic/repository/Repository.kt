package com.example.daysrecord.logic.repository

import androidx.lifecycle.LiveData
import com.example.daysrecord.DayRecordApplication
import com.example.daysrecord.database.AppDatabase
import com.example.daysrecord.database.entity.Record

object Repository {

    val recordDao = AppDatabase.getDatabase(DayRecordApplication.context).recordDao()

    fun getAllRecords(): LiveData<List<Record>> {
        return recordDao.getAllRecords()
    }

    fun insertRecord(record: Record) {
        recordDao.insertRecord(record)
    }

    fun deleteRecord(record: Record) {
        recordDao.deleteRecord(record)
    }

    fun deleteRecordById(id: Long) {
        recordDao.deleteRecordById(id)
    }

    fun updateRecord(record: Record) {
        recordDao.updateRecord(record)
    }

    fun updateRecordById(id: Long, title:String, content:String, time:String) {
        recordDao.updateRecordById(id, title, content, time)
    }

}