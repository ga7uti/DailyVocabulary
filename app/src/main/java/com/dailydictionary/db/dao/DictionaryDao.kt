package com.dailydictionary.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dailydictionary.db.entity.Dictionary

@Dao
interface DictionaryDao {

    @Insert
    fun insert(word: Dictionary)

    @Update
    fun update(word: Dictionary)

    @Query("DELETE FROM dictionary_tbl")
    fun deleteAll()

    @Query("SELECT * from dictionary_tbl ORDER BY word ASC")
    fun getAllWords():LiveData<List<Dictionary>>

}