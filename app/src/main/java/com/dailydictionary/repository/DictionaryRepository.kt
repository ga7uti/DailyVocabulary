package com.dailydictionary.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.dailydictionary.room.DictionaryRoomDatabase
import com.dailydictionary.room.dao.DictionaryDao
import com.dailydictionary.room.entity.Dictionary

abstract class DictionaryRepository(private var application: Application) {

    private var mDictionaryDao: DictionaryDao =
        DictionaryRoomDatabase.getDatabaseInstance(application).dictionaryDao()
    private var mAllWords: LiveData<List<Dictionary>>

    init {
        mAllWords = mDictionaryDao.getAllWords()
    }

    fun getAllWords(): LiveData<List<Dictionary>> {
        return mAllWords
    }

    fun insert(dictionary: Dictionary) {
        InsertAsyncTask(mDictionaryDao)
    }

    class InsertAsyncTask(private var dictionaryDao: DictionaryDao) :
        AsyncTask<Dictionary, Unit, Unit>() {
        override fun doInBackground(vararg p0: Dictionary?) {
            p0[0]?.let { dictionaryDao.insert(it) };
        }
    }


}