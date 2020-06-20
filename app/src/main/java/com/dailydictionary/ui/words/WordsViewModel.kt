package com.dailydictionary.ui.words

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dailydictionary.repository.DictionaryRepository
import com.dailydictionary.db.entity.Dictionary

class WordsViewModel(application: Application) : AndroidViewModel(application) {
    private val mDictionaryRepository: DictionaryRepository = DictionaryRepository(application)
    val mAllWords: LiveData<List<Dictionary>>

    init {
        mAllWords = mDictionaryRepository.getAllWords()
    }

    fun insert(word: Dictionary) {
        mDictionaryRepository.insert(word)
    }

    fun update(word: Dictionary) {
        mDictionaryRepository.update(word)
    }
}