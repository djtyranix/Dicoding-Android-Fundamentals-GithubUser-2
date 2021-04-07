package com.nixstudio.githubuser2

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.nixstudio.githubuser2.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    private var mMainFragment = MainFragment()
    var doubleBackToExitOnce : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                    .replace(R.id.container, mMainFragment)
//                    .commitNow()
//        }

        mMainFragment = getForegroundFragment() as MainFragment

    }

    fun getForegroundFragment(): Fragment? {
        var navHostFragment = supportFragmentManager.findFragmentByTag("container_fragment")
        return when (navHostFragment) {
            null -> null
            else -> navHostFragment.childFragmentManager.fragments.get(0)
        }
    }

    override fun onBackPressed() {
        if (doubleBackToExitOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitOnce = true

        Toast.makeText(this, "Press again to exit.", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed({
            kotlin.run { doubleBackToExitOnce = false }
        }, 2000)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                mMainFragment.showLoading(false)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                mMainFragment.searchUser(query)
                return true
            }
        })

        searchView.setOnCloseListener(object : SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                mMainFragment.showLoading(false)
                return true
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting_menu -> {
                mMainFragment.view?.findNavController()?.navigate(R.id.action_mainFragment_to_settingsActivity)
                return true
            }
            else -> return true
        }
    }
}