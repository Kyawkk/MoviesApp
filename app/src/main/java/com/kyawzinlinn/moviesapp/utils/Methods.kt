package com.kyawzinlinn.moviesapp.utils

import android.content.Context
import android.view.View
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