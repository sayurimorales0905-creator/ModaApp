package com.senati.modaapp.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun ahora(): String =
    SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())