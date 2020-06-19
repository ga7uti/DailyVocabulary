package com.dailydictionary.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dailydictionary.room.dao.DictionaryDao
import com.dailydictionary.room.entity.Dictionary

@Database(entities = [Dictionary::class], version = 1, exportSchema = false)
abstract class DictionaryRoomDatabase : RoomDatabase() {
    abstract fun dictionaryDao(): DictionaryDao

    companion object {
        @Volatile
        private var INSTANCE: DictionaryRoomDatabase? = null
        fun getDatabaseInstance(context: Context): DictionaryRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DictionaryRoomDatabase::class.java,
                    "dictionary_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}