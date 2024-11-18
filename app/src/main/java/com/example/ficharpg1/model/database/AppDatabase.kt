package com.example.ficharpg1.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ficharpg1.model.dao.FichaDao
import com.example.ficharpg1.model.entity.Ficha

@Database(entities = [Ficha::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun fichaDao(): FichaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}