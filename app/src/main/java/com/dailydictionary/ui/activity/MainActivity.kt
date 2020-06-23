package com.dailydictionary.ui.activity

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.dailydictionary.R
import com.dailydictionary.ui.words.OnPassFilterQuery
import com.dailydictionary.ui.words.WordsFragment
import com.dailydictionary.utils.AlertUtils


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var onPassFilterQuery: OnPassFilterQuery
    private lateinit var navController: NavController
    private lateinit var navDestination: NavDestination
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appBarConfiguration = AppBarConfiguration.Builder(
            R.id.wordsFragment
        ).build()

        navController = Navigation.findNavController(this, R.id.fragment_container);
        navDestination = navController.currentDestination!!
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    override fun onSupportNavigateUp(): Boolean {
        return (NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        this.menu = menu
        // Associate searchable configuration with the SearchView
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                onPassFilterQuery.passQuery(newText)
                return false
            }
        })
        return true
    }

    private val listener =
        NavController.OnDestinationChangedListener { _, navDestination, _ ->
            menu?.findItem(R.id.action_search)?.isVisible = navDestination.id != R.id.addWordFragment
        }

    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(listener)
    }

    override fun onPause() {
        super.onPause()
        navController.removeOnDestinationChangedListener(listener)
    }
}