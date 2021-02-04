package com.example.daysrecord.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.daysrecord.database.entity.Record

@Dao
interface RecordDao {

    @Insert
    fun insertRecord(record: Record): Long

    @Query("select * from record")
    fun getAllRecords(): LiveData<List<Record>>

    @Delete
    fun deleteRecord(record: Record)

    @Update
    fun updateRecord(record: Record)

    @Query("delete from record where id = :id")
    fun deleteRecordById(id: Long)

    @Query("update record set title = :title and content = :content and time = :time where id = :id")
    fun updateRecordById(id: Long, title: String, content: String, time: String)

}