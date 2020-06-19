package com.dailydictionary.room.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dictionary_tbl")
class Dictionary {
    @PrimaryKey
    @NonNull
    private val word: String? = null
    private val meaning: String? = null
    private val sentence: String? = null
}