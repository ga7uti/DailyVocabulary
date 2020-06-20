package com.dailydictionary.ui.addNewWord

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dailydictionary.db.entity.Dictionary
import com.dailydictionary.repository.DictionaryRepository

class AddWordViewModel(application: Application) : AndroidViewModel(application){

    private val mDictionaryRepository: DictionaryRepository = DictionaryRepository(application)

    fun insert(word: Dictionary) {
        mDictionaryRepository.insert(word)
    }
}