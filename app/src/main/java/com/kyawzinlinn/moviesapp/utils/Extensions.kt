package com.kyawzinlinn.moviesapp.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.widget.Toast
import androidx.transition.TransitionManager

fun Activity.playYouTubeVideo(videoId: String){
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$videoId"))
    intent.setPackage("com.google.android.youtube")
    try {
        startActivity(intent)
    }catch (e: Exception){
        showToast("YouTube App is not found!")
        startActivity(Intent.createChooser(intent, "Open With"))
    }
}

fun Activity.showToast(message: String){
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}

fun ViewGroup.setUpLayoutTransition(){
    TransitionManager.beginDelayedTransition(this)
}