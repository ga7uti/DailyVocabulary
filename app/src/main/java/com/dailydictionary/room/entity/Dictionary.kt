package com.dailydictionary.room.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dictionary_tbl")
class Dictionary(word: String?, meaning: String?, sentence: String?) {
    @PrimaryKey
    @NonNull
    var word: String? = null
    var meaning: String? = null
    var sentence: String? = null

    init {
        this.word = word
        this.sentence = sentence
        this.meaning = meaning
    }
}