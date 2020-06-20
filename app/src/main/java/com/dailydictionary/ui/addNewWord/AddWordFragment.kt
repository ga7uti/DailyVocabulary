package com.dailydictionary.ui.addNewWord

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.Navigation
import com.dailydictionary.R
import com.dailydictionary.db.entity.Dictionary
import com.dailydictionary.ui.words.WordsViewModel
import com.dailydictionary.utils.AlertUtils
import com.dailydictionary.utils.Utils

class AddWordFragment : Fragment() {

    companion object {
        fun newInstance() = AddWordFragment()
    }

    private lateinit var viewModel: WordsViewModel
    private lateinit var mEtWord: EditText
    private lateinit var mEtMeaning: EditText
    private lateinit var mEtSentence: EditText
    private var mWord: Dictionary? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_word_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(WordsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUI(view)
    }

    private fun setUI(view: View) {
        mEtWord = view.findViewById(R.id.et_add_word_fragment_word)
        mEtMeaning = view.findViewById(R.id.et_add_word_fragment_meaning)
        mEtSentence = view.findViewById(R.id.et_add_word_fragment_sentence)

        view.findViewById<Button>(R.id.btn_add_fragment_save)
            .setOnClickListener(View.OnClickListener {
                mWord = validate()
                if (mWord != null) {
                    viewModel.insert(mWord!!)
                    val navigation = Navigation.findNavController(view)
                    Utils.hideKeyBoard(activity)
                    navigation.popBackStack(R.id.wordsFragment, true)
                    navigation.navigate(R.id.wordsFragment)
                }
            })
    }

    private fun validate(): Dictionary? {
        if (TextUtils.isEmpty(mEtWord.text)) {
            context?.let { AlertUtils.showToast(it, R.string.hint_word) }
            mEtWord.requestFocus()
            return null
        } else if (TextUtils.isEmpty(mEtMeaning.text)) {
            context?.let { AlertUtils.showToast(it, R.string.hint_meaning) }
            mEtMeaning.requestFocus()
            return null
        }
        return getDetails()
    }

    private fun getDetails(): Dictionary {
        return Dictionary(
            mEtWord.text.toString(),
            mEtMeaning.text.toString(),
            mEtSentence.text.toString()
        )
    }
}
