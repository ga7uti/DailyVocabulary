package com.dailydictionary.model

import android.os.Parcel
import android.os.Parcelable

class DictionaryModel() : Parcelable {

    var word: String? = null
    var meaning: String? = null
    var sentence: String? = null

    constructor(parcel: Parcel) : this() {
        word = parcel.readString()
        meaning = parcel.readString()
        sentence = parcel.readString()
    }

    constructor(word: String, meaning: String, sentence: String) : this() {
        this.word = word
        this.meaning = meaning
        this.sentence = sentence
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(word)
        parcel.writeString(meaning)
        parcel.writeString(sentence)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DictionaryModel> {
        override fun createFromParcel(parcel: Parcel): DictionaryModel {
            return DictionaryModel(parcel)
        }

        override fun newArray(size: Int): Array<DictionaryModel?> {
            return arrayOfNulls(size)
        }
    }
}