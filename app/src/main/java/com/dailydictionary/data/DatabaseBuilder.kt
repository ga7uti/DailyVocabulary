package com.dailydictionary.data

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {

    private var INSTANCE: DictionaryDatabase? = null

    fun getInstance(context: Context): DictionaryDatabase {
        if (INSTANCE == null) {
            synchronized(DictionaryDatabase::class.java) {
                INSTANCE =
                    buildRoomDb(context)
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDb(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            DictionaryDatabase::class.java,
            "dictionary_db"
        ).build()
}
