package com.engineersk.flickrbrowser

import android.app.SearchManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.preference.Preference

private const val TAG = "SearchActivity"

class SearchActivity : BaseActivity() {

    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        activateToolbar(true)
        Log.d(TAG, "onCreate: ends")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.d(TAG, "onCreateOptionsMenu: starts...")
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.action_search).actionView as SearchView?
        val searchableInfo = searchManager.getSearchableInfo(componentName)
        searchView?.setSearchableInfo(searchableInfo)
        searchView?.isIconified = false
        searchView?.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            /**
             * Called when the user submits the query. This could be due to a key press on the
             * keyboard or due to pressing a submit button.
             * The listener can override the standard behavior by returning true
             * to indicate that it has handled the submit request. Otherwise return false to
             * let the SearchView handle the submission by launching any associated intent.
             *
             * @param query the query text that is to be submitted
             *
             * @return true if the query has been handled by the listener, false to let the
             * SearchView perform the default action.
             */
            override fun onQueryTextSubmit(query: String): Boolean {
                val preference = getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE)
                preference.edit().putString(FLICKR_QUERY, query).apply()
                searchView?.clearFocus()
                finish()
                return true
            }

            /**
             * Called when the query text is changed by the user.
             *
             * @param newText the new content of the query text field.
             *
             * @return false if the SearchView should perform the default action of showing any
             * suggestions if available, true if the action was handled by the listener.
             */
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        searchView?.setOnCloseListener {
            finish()
            false
        }
        Log.d(TAG, "onCreateOptionsMenu: $componentName")
        Log.d(TAG, "onCreateOptionsMenu: ${searchView?.queryHint}")
        Log.d(TAG, "onCreateOptionsMenu: $searchableInfo")
        return true
    }
}