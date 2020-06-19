package com.dailydictionary.ui.words

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dailydictionary.R
import com.dailydictionary.room.entity.Dictionary

class WordsFragment : Fragment() {

    companion object {
        fun newInstance() = WordsFragment()
    }

    private lateinit var viewModel: WordsViewModel
    private val adapter = WordsAdapter()


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
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerview)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

    }
}