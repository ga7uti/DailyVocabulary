package com.dailydictionary.ui.words

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dailydictionary.R
import com.dailydictionary.room.entity.Dictionary

class WordsAdapter() :
    RecyclerView.Adapter<WordsAdapter.WordViewHolder>() {

    private var mAllWords: List<Dictionary> = ArrayList<Dictionary>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_words, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return mAllWords.size
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(mAllWords[position])
    }

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dictionary: Dictionary) {
            itemView.findViewById<TextView>(R.id.textView_words).text = dictionary.word;
        }
    }

    fun setAllWords(words: List<Dictionary>) {
        mAllWords = words;
        notifyDataSetChanged()
    }

}