package com.dailydictionary.ui.addNewWord

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dailydictionary.data.DatabaseHelper
import com.dailydictionary.data.entity.Dictionary
import kotlinx.coroutines.launch

class AddNewWordViewModel(private val dbHelper: DatabaseHelper) : ViewModel() {

    fun insertWord(word: Dictionary) {
        viewModelScope.launch {
            dbHelper.insert(word)
        }
    }

    fun updateWord(word: Dictionary) {
        viewModelScope.launch {
            dbHelper.update(word)
        }
    }
}