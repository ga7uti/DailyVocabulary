package com.dailydictionary.ui.words

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dailydictionary.R
import com.dailydictionary.db.entity.Dictionary
import com.dailydictionary.utils.AlertUtils
import com.google.android.material.floatingactionbutton.FloatingActionButton

class WordsFragment : Fragment(), OnDialogClickListener {

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(WordsViewModel::class.java)
        viewModel.mAllWords
            .observe(viewLifecycleOwner,
                Observer<List<Dictionary>> { words -> // Update the cached copy of the words in the adapter.
                    adapter.setAllWords(words)
                })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mView = view
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerview)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        view.findViewById<FloatingActionButton>(R.id.fab_add)
            .setOnClickListener(View.OnClickListener {
                Navigation.findNavController(mView).navigate(R.id.addWordFragment)
            })

    }

    override fun edit(word: Dictionary) {
        val bundle = Bundle()
        bundle.putParcelable("word_data", word)
        bundle.putBoolean("edit", true)
        Navigation.findNavController(mView).navigate(R.id.addWordFragment, bundle)

    }

    override fun delete(word: Dictionary) {
        viewModel.delete(word)
        adapter.removeData()
    }
}