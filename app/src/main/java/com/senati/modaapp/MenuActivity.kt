package com.senati.modaapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.senati.modaapp.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nombre = intent.getStringExtra("nombre") ?: ""
        val rol = intent.getStringExtra("rol") ?: ""
        if (nombre.isNotEmpty() && rol.isNotEmpty()) {
            binding.tvBienvenida.text = getString(R.string.bienvenida, nombre, rol)
        } else {
            binding.tvBienvenida.text = getString(R.string.title_menu)
        }

        setupListeners()
    }

    private fun setupListeners() {
        binding.cardRopa.setOnClickListener {
            startActivity(Intent(this, RopaActivity::class.java))
        }

        binding.cardPedidos.setOnClickListener {
            Toast.makeText(this, "${getString(R.string.menu_pedidos)} - ${getString(R.string.msg_pantalla_en_desarrollo)}", Toast.LENGTH_SHORT).show()
        }

        binding.cardClientes.setOnClickListener {
            Toast.makeText(this, "${getString(R.string.menu_clientes)} - ${getString(R.string.msg_pantalla_en_desarrollo)}", Toast.LENGTH_SHORT).show()
        }

        binding.cardReportes.setOnClickListener {
            Toast.makeText(this, "${getString(R.string.menu_reportes)} - ${getString(R.string.msg_pantalla_en_desarrollo)}", Toast.LENGTH_SHORT).show()
        }

        binding.btnSalir.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}