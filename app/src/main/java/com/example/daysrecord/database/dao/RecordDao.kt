package com.example.daysrecord.database.dao

import androidx.room.*
import com.example.daysrecord.database.entity.Record

@Dao
interface RecordDao {

    @Insert
    fun insertRecord(record: Record): Long

    @Query("select * from record")
    fun getAllRecords(): List<Record>

    @Delete
    fun deleteRecord(record: Record)

    @Update
    fun updateRecord(record: Record)

}