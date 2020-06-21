package com.dailydictionary.db

import com.dailydictionary.db.entity.Dictionary

interface DatabaseHelper {

    suspend fun getWords(): List<Dictionary>

    suspend fun insert(word: Dictionary)

    suspend fun update(word: Dictionary)

    suspend fun delete(word: Dictionary)
}