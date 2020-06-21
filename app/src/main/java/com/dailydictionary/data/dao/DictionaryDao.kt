package com.dailydictionary.data.dao

import androidx.room.*
import com.dailydictionary.data.entity.Dictionary

@Dao
interface DictionaryDao {

    @Query("SELECT * from dictionary_tbl ORDER BY word ASC")
    suspend fun getWords(): List<Dictionary>

    @Insert
    suspend fun insert(word: Dictionary)

    @Update
    suspend fun update(word: Dictionary)

    @Delete
    suspend fun delete(word: Dictionary)
}