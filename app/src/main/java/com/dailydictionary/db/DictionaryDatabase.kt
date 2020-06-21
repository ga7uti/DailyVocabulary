package com.dailydictionary.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dailydictionary.db.dao.DictionaryDao
import com.dailydictionary.db.entity.Dictionary


@Database(entities = [Dictionary::class], version = 1)
abstract class DictionaryDatabase : RoomDatabase() {

    abstract fun dictionaryDao(): DictionaryDao
}