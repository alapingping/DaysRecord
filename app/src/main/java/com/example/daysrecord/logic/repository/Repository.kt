package com.example.daysrecord.logic.repository

import androidx.lifecycle.LiveData
import com.example.daysrecord.DayRecordApplication
import com.example.daysrecord.Utils.Companion.dataConvert
import com.example.daysrecord.database.AppDatabase
import com.example.daysrecord.database.entity.Record
import com.example.daysrecord.logic.model.Message
import com.example.daysrecord.logic.model.Result
import com.example.daysrecord.logic.network.Client
import com.example.daysrecord.logic.network.Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object Repository {

    val recordDao = AppDatabase.getDatabase(DayRecordApplication.context).recordDao()

    fun getAllRecords(): LiveData<List<Record>> =
        recordDao.getAllRecords()

    fun getAllRecordsByTitle(title: String): LiveData<List<Record>> =
        recordDao.getAllRecordsByTitle(title)

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

    suspend fun getMessagesFromRemote() : List<Message>? {
        return Client.create(Service::class.java).getAllMessages().dataConvert()
    }

    suspend fun addNewMessage(message: Message): Int {
        return Client.create(Service::class.java).addMessages(message).code
    }

}