package com.senati.modaapp.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory

fun cargarFoto(ruta: String, maxLado: Int = 600): Bitmap? {
    val medidas = BitmapFactory.Options().apply { inJustDecodeBounds = true }
    BitmapFactory.decodeFile(ruta, medidas)
    var muestreo = 1
    while (medidas.outWidth / muestreo > maxLado || medidas.outHeight / muestreo > maxLado) muestreo *= 2
    return BitmapFactory.decodeFile(ruta, BitmapFactory.Options().apply { inSampleSize = muestreo })
}