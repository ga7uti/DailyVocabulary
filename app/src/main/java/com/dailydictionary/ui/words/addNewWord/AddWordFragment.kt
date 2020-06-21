package com.dailydictionary.ui.words.addNewWord

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.dailydictionary.R
import com.dailydictionary.ViewModelFactory
import com.dailydictionary.data.DatabaseBuilder
import com.dailydictionary.data.DatabaseHelperImpl
import com.dailydictionary.data.entity.Dictionary
import com.dailydictionary.model.DictionaryModel
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
    private var mWord: DictionaryModel? = null
    private var isEditable: Boolean = false
    private lateinit var mView: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mWord = arguments?.getParcelable("word_data")
        isEditable = arguments?.getBoolean("edit") ?: false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_word_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mView = view
        setUI(view)
        setupViewModel()

        mWord?.let { setData(it) }
    }

    private fun setUI(view: View) {
        mEtWord = view.findViewById(R.id.et_add_word_fragment_word)
        mEtMeaning = view.findViewById(R.id.et_add_word_fragment_meaning)
        mEtSentence = view.findViewById(R.id.et_add_word_fragment_sentence)

        view.findViewById<Button>(R.id.btn_add_fragment_save)
            .setOnClickListener(View.OnClickListener {
                val word = validate()
                if (!isEditable)
                    word?.let { it1 -> insertWord(it1) }
                else
                    word?.let { it1 -> updateWord(it1) }
            })
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                DatabaseHelperImpl(DatabaseBuilder.getInstance(requireContext().applicationContext))
            )
        ).get(WordsViewModel::class.java)
    }

    private fun setData(word: DictionaryModel) {
        mEtWord.setText(word.word)
        mEtMeaning.setText(word.meaning)
        mEtSentence.setText(word.sentence)
    }

    private fun insertWord(word: Dictionary) {
        viewModel.insertWord(word)
        val navigation = Navigation.findNavController(mView)
        Utils.hideKeyBoard(activity)
        navigation.popBackStack(R.id.wordsFragment, true)
        navigation.navigate(R.id.wordsFragment)
    }

    private fun updateWord(word: Dictionary) {
        viewModel.updateWord(word)
        val navigation = Navigation.findNavController(mView)
        Utils.hideKeyBoard(activity)
        navigation.popBackStack(R.id.wordsFragment, true)
        navigation.navigate(R.id.wordsFragment)
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
