package com.senati.modaapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.senati.modaapp.data.DBHelper
import com.senati.modaapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("sesion", MODE_PRIVATE)
        val guardado = prefs.getString("nombre", null)
        if (guardado != null) {
            irAlMenu(guardado, prefs.getString("rol", "") ?: "")
            return
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnIngresar.setOnClickListener { validarLogin() }

        binding.btnVerCatalogo.setOnClickListener {
            startActivity(Intent(this, CatalogoActivity::class.java))
        }
    }

    private fun irAlMenu(nombre: String, rol: String) {
        val intent = Intent(this, MenuActivity::class.java)
        intent.putExtra("nombre", nombre)
        intent.putExtra("rol", rol)
        startActivity(intent)
        finish()
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

        val user = DBHelper(this).validarUsuario(usuario, clave)
        if (user != null) {
            getSharedPreferences("sesion", MODE_PRIVATE).edit()
                .putString("nombre", user.usuario)
                .putString("rol", user.rol)
                .apply()
            irAlMenu(user.usuario, user.rol)
        } else {
            Toast.makeText(this, R.string.error_credenciales, Toast.LENGTH_SHORT).show()
        }
    }
}