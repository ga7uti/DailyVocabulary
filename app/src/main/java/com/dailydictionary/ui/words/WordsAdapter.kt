package com.dailydictionary.ui.words

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dailydictionary.R
import com.dailydictionary.data.entity.Dictionary
import java.util.*
import kotlin.collections.ArrayList


class WordsAdapter(private var listener: OnDialogClickListener) :
    RecyclerView.Adapter<WordsAdapter.WordViewHolder>(), Filterable {

    private var mAllWords: List<Dictionary> = ArrayList<Dictionary>()
    private var mFilteredWords: MutableList<Dictionary> = ArrayList<Dictionary>()
    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        mContext = parent.context
        return WordViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_words, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return mFilteredWords.size
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(mFilteredWords[position])
//        holder.itemView.setOnClickListener(View.OnClickListener {
//            showAlertDialog(mContext, mFilteredWords[position], position)
//        })

    }

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dictionary: Dictionary) {
            itemView.findViewById<TextView>(R.id.tvWordFragmentWord).text = dictionary.word;
            itemView.findViewById<TextView>(R.id.tvWordFragmentMeaning).text = dictionary.meaning;
            itemView.findViewById<TextView>(R.id.tvWordFragmentSentence).text = dictionary.sentence;
        }
    }

    fun setAllWords(words: List<Dictionary>) {
        mAllWords = words;
        mFilteredWords = words.toMutableList()
        notifyDataSetChanged()
    }

    private fun showAlertDialog(mContext: Context, word: Dictionary, position: Int) {
        val dialog = Dialog(mContext)
        dialog.setContentView(R.layout.layout_alert_word)

        //setting dialog window size
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        );

        //textView
        dialog.findViewById<TextView>(R.id.dialog_tv_title).text = word.word
        dialog.findViewById<TextView>(R.id.dialog_tv_meaning).text = word.meaning
        dialog.findViewById<TextView>(R.id.dialog_tv_sentence).text = word.sentence

        //button
        dialog.findViewById<Button>(R.id.dialog_delete_btn)
            .setOnClickListener(View.OnClickListener {
                listener.delete(word)
                removeData(position)
                dialog.dismiss()
            })
        dialog.findViewById<Button>(R.id.dialog_edit_btn).setOnClickListener(View.OnClickListener {
            listener.edit(word)
            dialog.dismiss()
        })
        dialog.show()
    }

    private fun removeData(position: Int) {
        mFilteredWords.removeAt(position)
        mAllWords = mFilteredWords
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return wordsFilter
    }

    private val wordsFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList: MutableList<Dictionary> = ArrayList()
            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(mAllWords)
            } else {
                val filterPattern: String = constraint.toString().toLowerCase(Locale.ROOT).trim()
                for (item in mAllWords) {
                    if (item.word.toLowerCase(Locale.ROOT).contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(constraint: CharSequence?, p1: FilterResults?) {
            mFilteredWords = p1?.values as ArrayList<Dictionary>
            notifyDataSetChanged()
        }
    }
}