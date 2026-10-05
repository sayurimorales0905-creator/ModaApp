package com.senati.modaapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.senati.modaapp.databinding.ActivityCatalogoBinding

class CatalogoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCatalogoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatalogoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnCarrito.setOnClickListener {
            Toast.makeText(this, "${getString(R.string.btn_carrito)} - ${getString(R.string.msg_pantalla_en_desarrollo)}", Toast.LENGTH_SHORT).show()
        }
    }
}