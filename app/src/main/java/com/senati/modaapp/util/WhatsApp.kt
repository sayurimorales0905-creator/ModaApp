package com.senati.modaapp.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.senati.modaapp.R

fun abrirWhatsApp(context: Context, telefono: String, mensaje: String) {
    val uri = Uri.parse("https://wa.me/51$telefono?text=" + Uri.encode(mensaje))
    for (paquete in listOf("com.whatsapp", "com.whatsapp.w4b")) {
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, uri).setPackage(paquete))
            return
        } catch (e: ActivityNotFoundException) {
            // prueba con el siguiente paquete
        }
    }
    Toast.makeText(context, R.string.error_whatsapp, Toast.LENGTH_LONG).show()
}