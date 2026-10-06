package com.senati.modaapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.senati.modaapp.data.ClienteDao
import com.senati.modaapp.data.PedidoDao
import com.senati.modaapp.databinding.ActivityPedidoBinding
import com.senati.modaapp.model.Carrito
import com.senati.modaapp.model.Cliente
import java.util.Locale

class PedidoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPedidoBinding
    private lateinit var clienteDao: ClienteDao
    private lateinit var pedidoDao: PedidoDao
    private var cliente: Cliente? = null
    private var esNuevo = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPedidoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clienteDao = ClienteDao(this)
        pedidoDao = PedidoDao(this)

        binding.tvResumen.text = getString(R.string.resumen_total, Carrito.total())

        binding.btnContinuar.setOnClickListener { continuar() }
        binding.btnConfirmar.setOnClickListener { confirmar() }
    }

    private fun continuar() {
        if (esNuevo) {
            registrarCliente()
            return
        }

        val telefono = binding.etTelefono.text.toString().trim()
        binding.tilTelefono.error = null
        if (telefono.length != 9 || !telefono.all { it.isDigit() }) {
            binding.tilTelefono.error = getString(R.string.error_telefono)
            return
        }

        val existente = clienteDao.buscarPorTelefono(telefono)
        if (existente != null) {
            cliente = existente
            mostrarConfirmacion()
        } else {
            esNuevo = true
            binding.etTelefono.isEnabled = false
            binding.layoutNuevo.visibility = View.VISIBLE
            binding.btnContinuar.setText(R.string.btn_registrar)
            Toast.makeText(this, R.string.msg_cliente_nuevo, Toast.LENGTH_SHORT).show()
        }
    }

    private fun registrarCliente() {
        val nombres = binding.etNombres.text.toString().trim()
        val apellidos = binding.etApellidos.text.toString().trim()

        binding.tilNombres.error = null
        binding.tilApellidos.error = null

        var valido = true
        if (nombres.isEmpty()) {
            binding.tilNombres.error = getString(R.string.error_nombres)
            valido = false
        }
        if (apellidos.isEmpty()) {
            binding.tilApellidos.error = getString(R.string.error_apellidos)
            valido = false
        }
        if (!valido) return

        val telefono = binding.etTelefono.text.toString().trim()
        val id = clienteDao.insertar(Cliente(telefono = telefono, nombres = nombres, apellidos = apellidos))
        if (id == -1L) {
            Toast.makeText(this, R.string.msg_error_cliente, Toast.LENGTH_SHORT).show()
            return
        }

        cliente = Cliente(id.toInt(), telefono, nombres, apellidos)
        esNuevo = false
        mostrarConfirmacion()
    }

    private fun mostrarConfirmacion() {
        val c = cliente ?: return
        binding.etTelefono.isEnabled = false
        binding.layoutNuevo.visibility = View.GONE
        binding.btnContinuar.visibility = View.GONE
        binding.tvHola.text = getString(R.string.hola_fmt, c.nombres)
        binding.tvHola.visibility = View.VISIBLE
        binding.btnConfirmar.visibility = View.VISIBLE
    }

    private fun confirmar() {
        val c = cliente ?: return
        if (Carrito.items.isEmpty()) {
            finish()
            return
        }

        val items = Carrito.items.toList()
        val idPedido = pedidoDao.registrar(c.id, items)

        val detalle = items.joinToString("\n") {
            "- ${it.ropa.modelo} (talla ${it.ropa.talla}, ${it.ropa.color}) x${it.cantidad}"
        }
        val total = "%.2f".format(Locale.US, Carrito.total())

        val msgCliente = buildString {
            appendLine("Hola ${c.nombres}, tu pedido #$idPedido en Boutique Moda Urbana fue registrado.")
            appendLine(detalle)
            appendLine("Total: S/ $total")
            append("Estado: PENDIENTE")
        }
        val msgTienda = buildString {
            appendLine("Nuevo pedido #$idPedido")
            appendLine("Cliente: ${c.nombres} ${c.apellidos}")
            appendLine("Teléfono: ${c.telefono}")
            appendLine(detalle)
            appendLine("Total: S/ $total")
            append("Estado: PENDIENTE")
        }

        Carrito.vaciar()

        val intent = Intent(this, PedidoListoActivity::class.java)
        intent.putExtra("idPedido", idPedido)
        intent.putExtra("telCliente", c.telefono)
        intent.putExtra("msgCliente", msgCliente)
        intent.putExtra("msgTienda", msgTienda)
        startActivity(intent)
        finish()
    }
}