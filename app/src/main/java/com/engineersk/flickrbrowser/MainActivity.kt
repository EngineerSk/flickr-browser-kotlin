package com.engineersk.flickrbrowser

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.engineersk.flickrbrowser.adapters.FlickrImageRecyclerViewAdapter
import com.engineersk.flickrbrowser.models.Photo

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), GetRawData.OnDownloadComplete,
    GetFlickrJSONData.OnDataAvailable {

    private lateinit var recyclerView: RecyclerView
    private lateinit var toolbar: Toolbar

    private val flickrImageRecyclerViewAdapter:FlickrImageRecyclerViewAdapter = FlickrImageRecyclerViewAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: called...")
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = flickrImageRecyclerViewAdapter

        val getRawData = GetRawData(this)

        val url = Uri.parse("https://www.flickr.com/services/feeds/photos_public.gne")
            .buildUpon()
            .appendQueryParameter("tagname", "oreo,android")
            .appendQueryParameter("tagmode", "ALL")
            .appendQueryParameter("lang","en-us")
            .appendQueryParameter("format", "json")
            .appendQueryParameter("nojsoncallback", "1")
            .build().toString()
        getRawData.execute(url)
    }

    override fun downloadComplete(data: String, status: DownloadStatus) {
        if (status == DownloadStatus.OK) {
            Log.d(TAG, "onStatusDownloadComplete called: data is $data")
            val getFlickrJSONData = GetFlickrJSONData(this)
            getFlickrJSONData.execute(data)
        } else {
            Log.d(
                TAG,
                "onStatusDownloadComplete failed with status $status: Error message is $data"
            )
        }
    }

    override fun dataAvailable(data: ArrayList<Photo>) {
        Log.d(TAG, "dataAvailable: starts $data")
        flickrImageRecyclerViewAdapter.loadNewData(data)
    }

    override fun onError(exception: Exception) {
        Log.e(TAG, "onError: ${exception.message}")
    }


//    companion object{
//        private const val TAG = "MainActivity"
//    }
}