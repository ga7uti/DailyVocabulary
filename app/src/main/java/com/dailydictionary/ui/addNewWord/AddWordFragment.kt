package com.dailydictionary.ui.addNewWord

import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.dailydictionary.R
import com.dailydictionary.ViewModelFactory
import com.dailydictionary.data.DatabaseBuilder
import com.dailydictionary.data.DatabaseHelperImpl
import com.dailydictionary.data.entity.Dictionary
import com.dailydictionary.data.model.DictionaryModel
import com.dailydictionary.utils.AlertUtils
import com.dailydictionary.utils.Utils
import kotlinx.android.synthetic.main.add_word_fragment.*

class AddWordFragment : Fragment() {

    companion object {
        fun newInstance() = AddWordFragment()
    }

    private lateinit var viewModel: AddNewWordViewModel
    private var mWord: DictionaryModel? = null
    private var isEditable: Boolean = false
    private lateinit var navigation: NavController

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
        setupViewModel()
        mWord?.let { setData(it) }

        //setting navigation
        navigation = Navigation.findNavController(view)

        //add btn click listener
        view.findViewById<Button>(R.id.btn_add_fragment_save)
            .setOnClickListener {
                val word = validate()
                if (!isEditable)
                    word?.let { it1 ->
                        viewModel.insertWord(it1)
                    }
                else
                    word?.let { it1 ->
                        viewModel.updateWord(it1)
                    }

                Utils.hideKeyBoard(activity)
                navigation.popBackStack(R.id.wordsFragment, true)
                navigation.navigate(R.id.wordsFragment)
            }

    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                DatabaseHelperImpl(DatabaseBuilder.getInstance(requireContext().applicationContext))
            )
        ).get(AddNewWordViewModel::class.java)
    }

    private fun setData(word: DictionaryModel) {
        etAddWordFragmentWord.inputType = InputType.TYPE_NULL
        context?.let { ContextCompat.getColor(it,R.color.colorSecondaryText) }?.let {
            etAddWordFragmentWord.setTextColor(
                it
            )
        }
        etAddWordFragmentWord.setOnClickListener {
            context?.let { it1 -> AlertUtils.showToast(it1, R.string.cannot_edit) }
        }
        etAddWordFragmentWord.setText(word.word)
        etAddWordFragmentMeaning.setText(word.meaning)
        etAddWordFragmentSentence.setText(word.sentence)
    }

    private fun validate(): Dictionary? {
        if (TextUtils.isEmpty(etAddWordFragmentWord.text)) {
            context?.let { AlertUtils.showToast(it, R.string.hint_word) }
            etAddWordFragmentWord.requestFocus()
            return null
        }
        return getDetails()
    }

    private fun getDetails(): Dictionary {
        return Dictionary(
            etAddWordFragmentWord.text.toString(),
            etAddWordFragmentMeaning.text.toString(),
            etAddWordFragmentSentence.text.toString()
        )
    }
}
