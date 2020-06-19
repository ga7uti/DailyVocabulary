package com.dailydictionary.db

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dailydictionary.db.dao.DictionaryDao
import com.dailydictionary.db.entity.Dictionary


@Database(entities = [Dictionary::class], version = 1, exportSchema = false)
abstract class DictionaryRoomDatabase : RoomDatabase() {
    abstract fun dictionaryDao(): DictionaryDao

    companion object {
        @Volatile
        private var INSTANCE: DictionaryRoomDatabase? = null
        fun getDatabaseInstance(context: Context): DictionaryRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val sRoomDatabaseCallback: Callback = object : Callback() {
                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        INSTANCE?.let { PopulateDbAsync(it).execute() }
                    }
                }

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DictionaryRoomDatabase::class.java,
                    "dictionary_db"
                ).addCallback(sRoomDatabaseCallback).build()
                INSTANCE = instance
                return instance
            }
        }
    }


    /**
     * Populate the database in the background.
     */
    private class PopulateDbAsync internal constructor(db: DictionaryRoomDatabase) :
        AsyncTask<Void?, Void?, Void?>() {
        private val mDao: DictionaryDao = db.dictionaryDao()
        override fun doInBackground(vararg p0: Void?): Void? {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created
            val dictionary = Dictionary("Kotlin", "Kotlin", "Kotlin")
            val dictionary1 = Dictionary("Java", "Kotlin", "Kotlin")
            val dictionary2 = Dictionary("Javascript", "Kotlin", "Kotlin")
            val dictionary3 = Dictionary("Typescript", "Kotlin", "Kotlin")
            val words =
                arrayOf(dictionary, dictionary1, dictionary2, dictionary3)
            mDao.deleteAll()
            for (element in words) {
                val word = Dictionary(element.word, element.meaning, element.sentence)
                mDao.insert(word)
            }
            return null
        }

    }

}