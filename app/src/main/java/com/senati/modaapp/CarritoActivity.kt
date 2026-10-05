package com.senati.modaapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CarritoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this, "${getString(R.string.btn_carrito)} - ${getString(R.string.msg_pantalla_en_desarrollo)}", Toast.LENGTH_SHORT).show()
        finish()
    }
}