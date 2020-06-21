package com.dailydictionary.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dailydictionary.data.dao.DictionaryDao
import com.dailydictionary.data.entity.Dictionary


@Database(entities = [Dictionary::class], version = 1,exportSchema = false)
abstract class DictionaryDatabase : RoomDatabase() {

    abstract fun dictionaryDao(): DictionaryDao
}