package com.dailydictionary.data

import androidx.lifecycle.LiveData
import com.dailydictionary.data.entity.Dictionary

class DatabaseHelperImpl(private val dictionaryDatabase: DictionaryDatabase) :
    DatabaseHelper {
    override fun getWords(): LiveData<List<Dictionary>> =
        dictionaryDatabase.dictionaryDao().getWords()

    override suspend fun insert(word: Dictionary) =
        dictionaryDatabase.dictionaryDao().insert(word)

    override suspend fun update(word: Dictionary) =
        dictionaryDatabase.dictionaryDao().update(word)

    override suspend fun delete(word: Dictionary) =
        dictionaryDatabase.dictionaryDao().delete(word)
}