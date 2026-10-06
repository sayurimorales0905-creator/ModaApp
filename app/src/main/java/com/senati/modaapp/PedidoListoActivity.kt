package com.senati.modaapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.senati.modaapp.data.DBHelper
import com.senati.modaapp.databinding.ActivityPedidoListoBinding
import com.senati.modaapp.util.abrirWhatsApp

class PedidoListoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPedidoListoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPedidoListoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idPedido = intent.getLongExtra("idPedido", 0)
        val telCliente = intent.getStringExtra("telCliente") ?: ""
        val msgCliente = intent.getStringExtra("msgCliente") ?: ""
        val msgTienda = intent.getStringExtra("msgTienda") ?: ""

        binding.tvTitulo.text = getString(R.string.pedido_registrado, idPedido)
        binding.tvVistaPrevia.text = msgCliente

        binding.btnWhatsCliente.setOnClickListener {
            abrirWhatsApp(this, telCliente, msgCliente)
        }
        binding.btnWhatsTienda.setOnClickListener {
            abrirWhatsApp(this, DBHelper(this).telefonoAdmin(), msgTienda)
        }
        binding.btnVolver.setOnClickListener { irAlCatalogo() }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                irAlCatalogo()
            }
        })
    }

    private fun irAlCatalogo() {
        val intent = Intent(this, CatalogoActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
        finish()
    }
}