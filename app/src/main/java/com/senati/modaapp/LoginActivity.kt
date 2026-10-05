package com.senati.modaapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.senati.modaapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnIngresar.setOnClickListener { validarLogin() }

        binding.btnVerCatalogo.setOnClickListener {
            startActivity(Intent(this, CatalogoActivity::class.java))
        }
    }

    private fun validarLogin() {
        val usuario = binding.etUsuario.text.toString().trim()
        val clave = binding.etClave.text.toString()

        binding.tilUsuario.error = null
        binding.tilClave.error = null

        var valido = true
        if (usuario.isEmpty()) {
            binding.tilUsuario.error = getString(R.string.error_usuario)
            valido = false
        }
        if (clave.isEmpty()) {
            binding.tilClave.error = getString(R.string.error_clave)
            valido = false
        }
        if (!valido) return

        if (usuario == "admin" && clave == "1234") {
            startActivity(Intent(this, MenuActivity::class.java))
            finish() // el botón "atrás" ya no regresa al login
        } else {
            Toast.makeText(this, R.string.error_credenciales, Toast.LENGTH_SHORT).show()
        }
    }
}