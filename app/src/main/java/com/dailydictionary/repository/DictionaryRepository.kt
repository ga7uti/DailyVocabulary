package com.dailydictionary.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.dailydictionary.db.DictionaryRoomDatabase
import com.dailydictionary.db.dao.DictionaryDao
import com.dailydictionary.db.entity.Dictionary

class DictionaryRepository(application: Application) {

    private var mDictionaryDao: DictionaryDao =
        DictionaryRoomDatabase.getDatabaseInstance(application).dictionaryDao()
    private var mAllWords: LiveData<List<Dictionary>>

    init {
        mAllWords = mDictionaryDao.getAllWords()
    }

    fun getAllWords(): LiveData<List<Dictionary>> {
        return mAllWords
    }

    fun insert(word: Dictionary) {
        AsyncTask.execute {
            mDictionaryDao.insert(word)
        }
    }

    fun update(word: Dictionary) {
        AsyncTask.execute {
            mDictionaryDao.update(word)
        }
    }


    fun delete(word: Dictionary) {
        AsyncTask.execute {
            mDictionaryDao.delete(word)
        }
    }
}