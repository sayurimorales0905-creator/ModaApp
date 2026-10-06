package com.senati.modaapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.senati.modaapp.data.PedidoAdminDao
import com.senati.modaapp.databinding.ActivityPedidosBinding

class PedidosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPedidosBinding
    private lateinit var dao: PedidoAdminDao
    private val adapter = PedidosAdapter(emptyList()) { p -> abrirDetalle(p.id) }
    private var estado = "PENDIENTE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPedidosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dao = PedidoAdminDao(this)
        binding.rvPedidos.layoutManager = LinearLayoutManager(this)
        binding.rvPedidos.adapter = adapter

        binding.chipPendientes.setOnClickListener { estado = "PENDIENTE"; cargar() }
        binding.chipAtendidos.setOnClickListener { estado = "ATENDIDO"; cargar() }
    }

    override fun onResume() {
        super.onResume()
        cargar()
    }

    private fun cargar() {
        val lista = dao.listarPorEstado(estado)
        adapter.actualizar(lista)
        binding.tvVacio.visibility = if (lista.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun abrirDetalle(id: Int) {
        val intent = Intent(this, DetallePedidoActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }
}