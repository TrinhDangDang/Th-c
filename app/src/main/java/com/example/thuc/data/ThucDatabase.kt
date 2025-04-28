package com.example.thuc.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Alarm::class, Quote::class],
    version = 1,
    exportSchema = false
)
abstract class ThucDatabase : RoomDatabase() {

    abstract fun alarmDao(): AlarmDao

    abstract fun quoteDao(): QuoteDao


    companion object {
        @Volatile
        private var Instance: ThucDatabase? = null

        fun getDatabase(context: Context): ThucDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, ThucDatabase::class.java, "thuc_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
