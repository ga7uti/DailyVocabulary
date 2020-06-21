package com.dailydictionary.ui.words

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.dailydictionary.R
import com.dailydictionary.ViewModelFactory
import com.dailydictionary.data.DatabaseBuilder
import com.dailydictionary.data.DatabaseHelperImpl
import com.dailydictionary.data.entity.Dictionary
import com.dailydictionary.data.model.DictionaryModel
import com.dailydictionary.ui.activity.MainActivity
import com.dailydictionary.utils.AlertUtils
import com.dailydictionary.utils.Status
import kotlinx.android.synthetic.main.words_fragment.*

class WordsFragment : Fragment(), OnDialogClickListener, OnPassFilterQuery {

    companion object {
        fun newInstance() = WordsFragment()
    }

    private lateinit var viewModel: WordsViewModel
    private val adapter = WordsAdapter(this)
    private lateinit var mView: View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.words_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mView = view
        setupViewModel()
        setupObserver()
        setupUI()

    }

    private fun setupUI() {
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(context)
        fab_add.setOnClickListener(View.OnClickListener {
            Navigation.findNavController(mView).navigate(R.id.addWordFragment)
        })

    }

    private fun setupObserver() {
        viewModel.getWords().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { words -> adapter.setAllWords(words) }
                    recyclerview.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerview.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    context?.let { it1 ->
                        it.message?.let { it2 ->
                            AlertUtils.showToast(
                                it1,
                                it2
                            )
                        }
                    };
                }
            }
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

    override fun edit(word: Dictionary) {
        val newWord = DictionaryModel(word.word!!, word.meaning!!, word.sentence!!)
        val bundle = Bundle()
        bundle.putParcelable("word_data", newWord)
        bundle.putBoolean("edit", true)
        Navigation.findNavController(mView).navigate(R.id.addWordFragment, bundle)

    }

    override fun delete(word: Dictionary) {
        viewModel.deleteWord(word)
        adapter.removeData()
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        (activity as MainActivity).onPassFilterQuery = this

    }

    override fun passQuery(query: String) {
        adapter.filter.filter(query)
    }
}