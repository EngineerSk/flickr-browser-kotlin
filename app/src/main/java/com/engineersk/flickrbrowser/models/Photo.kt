package com.engineersk.flickrbrowser.models

import android.util.Log
import java.io.*
import kotlin.jvm.Throws

private const val TAG = "Photo"

class Photo(
    var title: String, var author: String, private var authorId: String,
    var link: String, var tags: String, var image: String) : Serializable {

    companion object{
        private const val serialVersionUID = 1L
    }
    override fun toString(): String {
        return "Photo(title=$title, author=$author, authorId=$authorId, link=$link, tags=$tags, image=$image)"
    }

    @Throws(IOException::class)
    private fun writeObject(outputStream: ObjectOutputStream){
        Log.d(TAG, "writeObject: called")
        outputStream.writeUTF(title)
        outputStream.writeUTF(author)
        outputStream.writeUTF(authorId)
        outputStream.writeUTF(link)
        outputStream.writeUTF(tags)
        outputStream.writeUTF(image)
    }

    @Throws(IOException::class, ClassNotFoundException::class)
    private fun readObject(inputStream: ObjectInputStream){
        title = inputStream.readUTF()
        author = inputStream.readUTF()
        authorId = inputStream.readUTF()
        link = inputStream.readUTF()
        tags = inputStream.readUTF()
        image = inputStream.readUTF()
    }

    @Throws(ObjectStreamException::class)
    private fun readObjectNoData(){
        Log.d(TAG, "readObjectNoData: called...")
    }

}








