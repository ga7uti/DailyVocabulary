package com.dailydictionary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dailydictionary.data.DatabaseHelper
import com.dailydictionary.ui.addNewWord.AddNewWordViewModel
import com.dailydictionary.ui.words.WordsViewModel


class ViewModelFactory(private val dbHelper: DatabaseHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordsViewModel::class.java)) {
            return WordsViewModel(dbHelper) as T
        }
        if (modelClass.isAssignableFrom(AddNewWordViewModel::class.java)) {
            return AddNewWordViewModel(dbHelper) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }


}