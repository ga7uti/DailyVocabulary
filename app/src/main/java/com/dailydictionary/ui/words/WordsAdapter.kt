package com.dailydictionary.ui.words

import android.app.Dialog
import android.content.Context
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.dailydictionary.R
import com.dailydictionary.data.entity.Dictionary
import com.dailydictionary.utils.Utils
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
        holder.itemView.findViewById<ImageView>(R.id.icWordFragmentMenu)
            .setOnClickListener(View.OnClickListener {
                showPopupMenu(it, mFilteredWords[position], position)
            })

        holder.itemView.setOnClickListener {
            showAlertDialog(mContext, mFilteredWords[position])
        }
    }

    private fun showPopupMenu(view: View, word: Dictionary, position: Int) {
        val popupMenu = PopupMenu(mContext, view)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_edit -> {
                    listener.edit(word)
                    true
                }
                R.id.action_delete -> {
                    listener.delete(word)
                    removeData(position)
                    true
                }
                else -> false
            }
        }

        popupMenu.inflate(R.menu.adapter_menu)
        popupMenu.show()
    }

    private fun showAlertDialog(mContext: Context, word: Dictionary) {
        val dialog = Dialog(mContext)
        dialog.setContentView(R.layout.layout_alert_word)

        //setting dialog window size
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        );

        //textView
        dialog.findViewById<TextView>(R.id.dialog_tv_title).text = Utils.capitalize(word.word)
        dialog.findViewById<TextView>(R.id.dialog_tv_meaning).text = word.meaning
        dialog.findViewById<TextView>(R.id.dialog_tv_sentence).text = Utils.capitalize(word.sentence)

        dialog.show()
    }

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dictionary: Dictionary) {
            itemView.findViewById<TextView>(R.id.tvWordFragmentWord).text =
                Utils.capitalize(dictionary.word.trim())
            itemView.findViewById<TextView>(R.id.tvWordFragmentMeaning).text =
                itemView.context.getString(R.string.meaning_with_quotes, dictionary.meaning?.trim())
        }
    }

    fun setAllWords(words: List<Dictionary>) {
        mAllWords = words;
        mFilteredWords = words.toMutableList()
        notifyDataSetChanged()
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