package com.dailydictionary.ui.words

import androidx.lifecycle.*
import com.dailydictionary.data.DatabaseHelper
import com.dailydictionary.data.entity.Dictionary
import com.dailydictionary.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WordsViewModel(private val dbHelper: DatabaseHelper) : ViewModel() {


    fun getWords(): LiveData<List<Dictionary>> {
        return dbHelper.getWords()
    }

    fun deleteWord(word: Dictionary) {
        viewModelScope.launch {
            dbHelper.delete(word)
        }
    }
}