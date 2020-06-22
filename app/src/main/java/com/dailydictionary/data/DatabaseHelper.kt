package com.dailydictionary.data

import androidx.lifecycle.LiveData
import com.dailydictionary.data.entity.Dictionary

interface DatabaseHelper {

     fun getWords(): LiveData<List<Dictionary>>

    suspend fun insert(word: Dictionary)

    suspend fun update(word: Dictionary)

    suspend fun delete(word: Dictionary)
}