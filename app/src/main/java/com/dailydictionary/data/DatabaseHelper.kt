package com.dailydictionary.data

import com.dailydictionary.data.entity.Dictionary

interface DatabaseHelper {

    suspend fun getWords(): List<Dictionary>

    suspend fun insert(word: Dictionary)

    suspend fun update(word: Dictionary)

    suspend fun delete(word: Dictionary)
}