package com.dailydictionary.ui.words

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dailydictionary.R
import com.dailydictionary.db.entity.Dictionary

class WordsAdapter(private var listener: OnDialogClickListener) :
    RecyclerView.Adapter<WordsAdapter.WordViewHolder>() {

    private var mAllWords: List<Dictionary> = ArrayList<Dictionary>()
    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        mContext = parent.context
        return WordViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_words, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return mAllWords.size
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(mAllWords[position])
        holder.itemView.setOnClickListener(View.OnClickListener {
            showAlertDialog(mContext, mAllWords[position])
        })

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

    fun removeData() {
        notifyDataSetChanged()
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
        dialog.findViewById<TextView>(R.id.dialog_tv_title).text = word.word
        dialog.findViewById<TextView>(R.id.dialog_tv_meaning).text = word.meaning
        dialog.findViewById<TextView>(R.id.dialog_tv_sentence).text = word.sentence

        //button
        dialog.findViewById<Button>(R.id.dialog_delete_btn)
            .setOnClickListener(View.OnClickListener {
                listener.delete(word)
                dialog.dismiss()
            })
        dialog.findViewById<Button>(R.id.dialog_edit_btn).setOnClickListener(View.OnClickListener {
            listener.edit(word)
            dialog.dismiss()
        })
        dialog.show()
    }

}