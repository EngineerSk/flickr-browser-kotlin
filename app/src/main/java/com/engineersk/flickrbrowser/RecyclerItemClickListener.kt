package com.engineersk.flickrbrowser

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "RecyclerItemClickListen"

class RecyclerItemClickListener(
    context: Context, recyclerView: RecyclerView,
    private val listener: OnRecyclerClickListener) : RecyclerView.SimpleOnItemTouchListener() {

    interface OnRecyclerClickListener {
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }

    private val gestureDetector =
        GestureDetectorCompat(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                Log.d(TAG, "onSingleTapUp: starts...")
                val childView = e?.let { recyclerView.findChildViewUnder(it.x, e.y) }
                Log.d(TAG, "onSingleTapUp: calling listener.onItemClick!")
                listener.onItemClick(childView!!, recyclerView.getChildAdapterPosition(childView))
                return super.onSingleTapUp(e)
            }

            override fun onLongPress(e: MotionEvent) {
                Log.d(TAG, "onLongPress: starts...")
                val childView = recyclerView.findChildViewUnder(e.x,e.y)
                Log.d(TAG, "onLongPress: calling listener.onItemLongClick!")
                listener.onItemLongClick(childView!!, recyclerView.getChildAdapterPosition(childView))
//            super.onLongPress(e)
            }
        })


    override fun onInterceptTouchEvent(recyclerView: RecyclerView, e: MotionEvent): Boolean {
        Log.d(TAG, "onInterceptTouchEvent: Motion event $e")
        val result = gestureDetector.onTouchEvent(e)
        Log.d(TAG, "onInterceptTouchEvent: returning $result")
//        return true
        return super.onInterceptTouchEvent(recyclerView, e)

    }
}