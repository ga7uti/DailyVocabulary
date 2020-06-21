package com.dailydictionary.data.dao

import androidx.room.*
import com.dailydictionary.data.entity.Dictionary

@Dao
interface DictionaryDao {

    @Query("SELECT * from dictionary_tbl ORDER BY word ASC")
    fun getWords(): List<Dictionary>

    @Insert
    fun insert(word: Dictionary)

    @Update
    fun update(word: Dictionary)

    @Delete
    fun delete(word: Dictionary)
}