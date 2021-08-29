package com.engineersk.flickrbrowser

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

private const val TAG = "BaseActivity"

internal const val FLICKR_QUERY = "FLICKR_QUERY"
internal const val PHOTO_TRANSFER = "PHOTO_TRANSFER"
internal const val SHARED_PREF_KEY = "shared_prefs"

open class BaseActivity : AppCompatActivity() {

    internal fun activateToolbar(enableHome: Boolean){
        Log.d(TAG, "activateToolbar: ")
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(enableHome)
    }
}