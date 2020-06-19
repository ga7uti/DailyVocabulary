package com.dailydictionary.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dailydictionary.room.entity.Dictionary

interface DictionaryDao {

    @Insert
    fun insert(word: Dictionary)

    @Update
    fun update(word: Dictionary)

    @Delete
    fun delete(id: String)

    @Query("DELETE FROM dictionary_tbl")
    fun deleteAll()

    @Query("SELECT * from dictionary_tbl ORDER BY word ASC")
    fun getAllWords():LiveData<List<Dictionary>>

}