package com.example.daysrecord.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.daysrecord.database.dao.RecordDao
import com.example.daysrecord.database.entity.Record

@Database(version = 2, entities = [Record::class])
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
                .addMigrations(MIGRATION1_2)
                .build().apply { instance = this }
        }

        val MIGRATION1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE record ADD COLUMN marked INTEGER NOT NULL DEFAULT 0")
            }

        }
    }

}