package com.engineersk.flickrbrowser

import android.os.AsyncTask
import android.util.Log
import com.engineersk.flickrbrowser.models.Photo
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception

private const val TAG = "GetFlickrJSONData"

class GetFlickrJSONData(private val listener: OnDataAvailable):
    AsyncTask<String, Void, ArrayList<Photo>>() {

    interface OnDataAvailable{
        fun dataAvailable(data: ArrayList<Photo>)
        fun onError(exception: Exception)
    }

    override fun doInBackground(vararg params: String): ArrayList<Photo> {
        Log.d(TAG, "doInBackground: starts...")
        val photoList = ArrayList<Photo>()
        try{
            val jsonData = JSONObject(params[0])
            val itemsArray = jsonData.getJSONArray("items")

            for(i in 0 until itemsArray.length()){
                val jsonPhoto = itemsArray.getJSONObject(i)
                val title = jsonPhoto.getString("title")
                val author = jsonPhoto.getString("author")
                val authorId = jsonPhoto.getString("author_id")
                val tags = jsonPhoto.getString("tags")

                val media = jsonPhoto.getJSONObject("media")
                val photoUrl = media.getString("m")
                val link = photoUrl.replaceFirst("_m.jpg","_b.jpg")

                val photoObject = Photo(title, author, authorId, link, tags, photoUrl)
                Log.d(TAG, "doInBackground: $photoObject")
                photoList.add(photoObject)
            }
        }catch (e: JSONException){
            e.printStackTrace()
            Log.e(TAG, "doInBackground: JSON error data... ${e.message}" )
            listener.onError(e)
        }
        return photoList
    }

    override fun onPostExecute(result: ArrayList<Photo>) {
        Log.d(TAG, "onPostExecute: starts...")
        super.onPostExecute(result)
        listener.dataAvailable(result)
        Log.d(TAG, "onPostExecute: ends...")
    }
}