package com.dailydictionary.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dailydictionary.R
import com.dailydictionary.room.entity.Dictionary


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerview)
        val adapter = WordsAdapter(getWordsList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun getWordsList(): ArrayList<Dictionary> {
        val mAllWords: ArrayList<Dictionary> = ArrayList<Dictionary>()
        val list: MutableList<Dictionary> = mutableListOf<Dictionary>()
        val dictionary = Dictionary()
        dictionary.word = "Hello Kotlin"
        list.add(dictionary)
        mAllWords.addAll(list)
        return mAllWords
    }
}