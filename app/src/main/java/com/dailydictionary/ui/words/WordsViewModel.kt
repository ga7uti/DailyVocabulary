package com.dailydictionary.ui.words

import androidx.lifecycle.*
import com.dailydictionary.db.DatabaseHelper
import com.dailydictionary.db.entity.Dictionary
import com.dailydictionary.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WordsViewModel(private val dbHelper: DatabaseHelper) : ViewModel() {

    private val mWords = MutableLiveData<Resource<List<Dictionary>>>()

    init {
        fetchWords()
    }

    private fun fetchWords() {
        viewModelScope.launch(Dispatchers.IO) {
            mWords.postValue(Resource.loading(null))
            try {
                val words = dbHelper.getWords()
                if (words.isNotEmpty())
                    mWords.postValue(Resource.success(words))
                else
                    mWords.postValue(Resource.error("No words added", null))
            } catch (e: Exception) {
                mWords.postValue(Resource.error("Something went wrong", null))
            }
        }
    }

    fun getWords(): LiveData<Resource<List<Dictionary>>> {
        return mWords
    }

    fun insertWord(word: Dictionary) {
        viewModelScope.launch(Dispatchers.IO) {
            dbHelper.insert(word)
        }
    }

    fun updateWord(word: Dictionary) {
        viewModelScope.launch(Dispatchers.IO) {
            dbHelper.update(word)
        }
    }

    fun deleteWord(word: Dictionary) {
        viewModelScope.launch (Dispatchers.IO){
            dbHelper.delete(word)
        }
    }
}