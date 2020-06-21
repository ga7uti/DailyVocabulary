package com.dailydictionary.db

import com.dailydictionary.db.DatabaseHelper
import com.dailydictionary.db.DictionaryDatabase
import com.dailydictionary.db.entity.Dictionary

class DatabaseHelperImpl(private val dictionaryDatabase: DictionaryDatabase) :
    DatabaseHelper {
    override suspend fun getWords(): List<Dictionary> =
        dictionaryDatabase.dictionaryDao().getWords()

    override suspend fun insert(word: Dictionary) =
        dictionaryDatabase.dictionaryDao().insert(word)

    override suspend fun update(word: Dictionary) =
        dictionaryDatabase.dictionaryDao().update(word)

    override suspend fun delete(word: Dictionary) =
        dictionaryDatabase.dictionaryDao().delete(word)
}