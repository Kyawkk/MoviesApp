package com.kyawzinlinn.moviesapp.utils

import java.time.LocalDate
import java.util.Calendar

fun calculateAge(dateOfBirth: String): String{
    val dateOfYear = dateOfBirth.split("-").get(0).toInt()
    val currentYear = LocalDate.now().year

    return "${currentYear - dateOfYear} Years"
}