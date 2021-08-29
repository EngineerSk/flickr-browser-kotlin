package com.engineersk.flickrbrowser

import android.os.Bundle
import android.widget.ImageView
import com.engineersk.flickrbrowser.models.Photo
import com.google.android.material.textview.MaterialTextView
import com.squareup.picasso.Picasso

class PhotoDetailsActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_details)
        activateToolbar(true)

//        val photo = intent.getSerializableExtra(PHOTO_TRANSFER) as Photo
        val photo = intent.getParcelableExtra<Photo>(PHOTO_TRANSFER)
        val photoTitle = findViewById<MaterialTextView>(R.id.photo_title)
        photoTitle.text = photo?.title
        val photoAuthor = findViewById<MaterialTextView>(R.id.photo_author)
        photoAuthor.text = photo?.author
        val photoTags = findViewById<MaterialTextView>(R.id.photo_tags)
        photoTags.text = photo?.tags
        val photoImage = findViewById<ImageView>(R.id.photo_image)
        Picasso.get()
            .load(photo?.link)
            .error(R.drawable.ic_image_24)
            .placeholder(R.drawable.ic_image_24)
            .into(photoImage)
    }
}