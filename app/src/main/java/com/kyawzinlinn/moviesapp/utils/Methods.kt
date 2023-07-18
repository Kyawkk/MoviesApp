package com.kyawzinlinn.moviesapp.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.transition.TransitionManager
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate
import java.util.Calendar

fun calculateAge(dateOfBirth: String): String{
    val dateOfYear = dateOfBirth.split("-").get(0).toInt()
    val currentYear = LocalDate.now().year

    return "${currentYear - dateOfYear} Years"
}

fun showSnackBar(view: View,message: String, listener: View.OnClickListener){
    val snackbar = Snackbar.make(view,message,Snackbar.LENGTH_SHORT)
    snackbar.setAction("Ok", listener)
    snackbar.show()
}

fun setUpLayoutTransition(viewGroup: ViewGroup){
    TransitionManager.beginDelayedTransition(viewGroup)
}

fun openGitHub(context: Context){
    val webpage = Uri.parse(GITHUB_URL)
    val intent = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_BROWSER)
    intent.data = webpage
    try {
        context.startActivity(intent)
    }catch (e: Exception){
        Toast.makeText(context,"App is not found to open the link!",Toast.LENGTH_SHORT).show()
    }
}

