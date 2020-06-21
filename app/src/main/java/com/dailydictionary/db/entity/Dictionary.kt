package com.dailydictionary.db.entity

import androidx.annotation.NonNull
import androidx.room.*

@Entity(tableName = "dictionary_tbl")
data class Dictionary(
    @PrimaryKey @ColumnInfo(name = "word") var word: String,
    @ColumnInfo(name = "meaning") var meaning: String?,
    @ColumnInfo(name = "sentence") var sentence: String?
)
