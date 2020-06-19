package com.dailydictionary.room.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dictionary_tbl")
class Dictionary {
    @PrimaryKey
    @NonNull
    var word: String? = null
    val meaning: String? = null
    val sentence: String? = null
}