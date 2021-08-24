package com.engineersk.flickrbrowser

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.engineersk.flickrbrowser.adapters.FlickrImageRecyclerViewAdapter
import com.engineersk.flickrbrowser.models.Photo

private const val TAG = "MainActivity"

class MainActivity : BaseActivity(), GetRawData.OnDownloadComplete,
    GetFlickrJSONData.OnDataAvailable, RecyclerItemClickListener.OnRecyclerClickListener {

    private lateinit var recyclerView: RecyclerView

    private val flickrImageRecyclerViewAdapter: FlickrImageRecyclerViewAdapter =
        FlickrImageRecyclerViewAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: called...")
        setContentView(R.layout.activity_main)
        activateToolbar(false)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(
                this, recyclerView,
                this
            )
        )
        recyclerView.adapter = flickrImageRecyclerViewAdapter

        val getRawData = GetRawData(this)

        val url = Uri.parse("https://www.flickr.com/services/feeds/photos_public.gne")
            .buildUpon()
            .appendQueryParameter("tagname", "oreo,android")
            .appendQueryParameter("tagmode", "ALL")
            .appendQueryParameter("lang", "en-us")
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

    override fun onItemClick(view: View, position: Int) {
        Log.d(TAG, "onItemClick: starts")
        Toast.makeText(
            this, "Normal tap at position $position",
            Toast.LENGTH_SHORT
        ).show()

    }

    override fun onItemLongClick(view: View, position: Int) {
        Log.d(TAG, "onItemLongClick: starts")
//        Toast.makeText(this, "Long tap at position $position", Toast.LENGTH_SHORT).show()
        val photo: Photo? = flickrImageRecyclerViewAdapter.getPhoto(position)
        if (photo != null) {
            val intent = Intent(this, PhotoDetailsActivity::class.java)
            intent.putExtra(PHOTO_TRANSFER, photo)
            startActivity(intent)
        }
    }


//    companion object{
//        private const val TAG = "MainActivity"
//    }
}