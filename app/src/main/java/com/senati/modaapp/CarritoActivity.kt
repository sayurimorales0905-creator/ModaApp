package com.senati.modaapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.senati.modaapp.databinding.ActivityCarritoBinding
import com.senati.modaapp.model.Carrito
import com.senati.modaapp.model.ItemCarrito

class CarritoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCarritoBinding
    private lateinit var adapter: CarritoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarritoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CarritoAdapter(Carrito.items) { item -> confirmarQuitar(item) }
        binding.rvCarrito.layoutManager = LinearLayoutManager(this)
        binding.rvCarrito.adapter = adapter

        binding.btnHacerPedido.setOnClickListener {
            startActivity(Intent(this, PedidoActivity::class.java))
        }
        actualizarVista()
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
        actualizarVista()
    }

    private fun confirmarQuitar(item: ItemCarrito) {
        AlertDialog.Builder(this)
            .setTitle(R.string.titulo_quitar)
            .setMessage(R.string.msg_confirmar_quitar)
            .setPositiveButton(R.string.si) { _, _ ->
                Carrito.quitar(item)
                adapter.notifyDataSetChanged()
                actualizarVista()
            }
            .setNegativeButton(R.string.no, null)
            .show()
    }

    private fun actualizarVista() {
        val vacio = Carrito.items.isEmpty()
        binding.tvVacio.visibility = if (vacio) View.VISIBLE else View.GONE
        binding.tvAyuda.visibility = if (vacio) View.GONE else View.VISIBLE
        binding.btnHacerPedido.isEnabled = !vacio
        binding.tvTotal.text = getString(R.string.total_fmt, Carrito.total())
    }
}