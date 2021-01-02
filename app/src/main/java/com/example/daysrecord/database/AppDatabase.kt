package com.example.daysrecord.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.daysrecord.database.dao.RecordDao
import com.example.daysrecord.database.entity.Record

@Database(version = 1, entities = [Record::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun recordDao(): RecordDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): AppDatabase {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(context.applicationContext,
                    AppDatabase::class.java, "app_database")
                .build().apply { instance = this }
        }
    }

}