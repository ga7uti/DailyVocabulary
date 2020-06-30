package com.dailydictionary.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dailydictionary.R
import com.dailydictionary.pref.UserPref
import com.dailydictionary.ui.words.OnPassFilterQuery
import com.dailydictionary.utils.AlertUtils
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.app_bar_main.*

class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var onPassFilterQuery: OnPassFilterQuery
    private lateinit var navController: NavController
    private lateinit var toolbar: Toolbar
    private var backPressedTime: Long = 0

    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.wordsFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //change theme
        val scChangeTheme = navView.menu
            .findItem(R.id.nav_theme).actionView as Switch

        scChangeTheme.isChecked = UserPref(this).darkMode == 1

        scChangeTheme.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                UserPref(this).darkMode = 0
                drawerLayout.close()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                UserPref(this).darkMode = 1
                drawerLayout.close()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        this.menu = menu
        // Associate searchable configuration with the SearchView
        val searchItem = menu.findItem(R.id.action_search)
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
            if (navDestination.id != R.id.wordsFragment) {
                menu?.findItem(R.id.action_search)?.isVisible = false
                toolbarLogoLayout.visibility = View.GONE
            } else {
                menu?.findItem(R.id.action_search)?.isVisible = true
                toolbarLogoLayout.visibility = View.VISIBLE
            }
        }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(listener)
    }

    override fun onPause() {
        super.onPause()
        navController.removeOnDestinationChangedListener(listener)
    }

    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            AlertUtils.showToast(this, R.string.press_again_to_exit)
        }
        backPressedTime = System.currentTimeMillis();
    }
}