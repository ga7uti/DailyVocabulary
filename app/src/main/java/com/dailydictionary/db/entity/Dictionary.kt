package com.dailydictionary.db.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dictionary_tbl")
class Dictionary(): Parcelable{
    @PrimaryKey
    @NonNull
    var word: String? = null
    var meaning: String? = null
    var sentence: String? = null

    constructor(parcel: Parcel) : this() {
        word = parcel.readString()
        meaning = parcel.readString()
        sentence = parcel.readString()
    }

    constructor(word: String?, meaning: String?, sentence: String?) : this() {
        this.word = word
        this.sentence = sentence
        this.meaning = meaning
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(word)
        parcel.writeString(meaning)
        parcel.writeString(sentence)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Dictionary> {
        override fun createFromParcel(parcel: Parcel): Dictionary {
            return Dictionary(parcel)
        }

        override fun newArray(size: Int): Array<Dictionary?> {
            return arrayOfNulls(size)
        }
    }
}