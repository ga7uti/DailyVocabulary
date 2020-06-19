package com.dailydictionary.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dailydictionary.repository.DictionaryRepository
import com.dailydictionary.room.entity.Dictionary

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val mDictionaryRepository: DictionaryRepository = DictionaryRepository(application)
    val mAllWords: LiveData<List<Dictionary>>

    init {
        mAllWords = mDictionaryRepository.getAllWords()
    }

    fun insert(word: Dictionary) {
        mDictionaryRepository.insert(word)
    }
}