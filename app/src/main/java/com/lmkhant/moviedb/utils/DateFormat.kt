package com.lmkhant.moviedb.utils

import java.text.SimpleDateFormat

fun formatDate(inputDate: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd")
    val outputFormat = SimpleDateFormat("MMM dd, yyyy")

    val date = inputFormat.parse(inputDate)
    return outputFormat.format(date)
}