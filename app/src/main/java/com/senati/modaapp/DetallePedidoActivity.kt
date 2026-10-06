package com.senati.modaapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.senati.modaapp.data.PedidoAdminDao
import com.senati.modaapp.databinding.ActivityDetallePedidoBinding

class DetallePedidoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetallePedidoBinding
    private lateinit var dao: PedidoAdminDao
    private var idPedido = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallePedidoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dao = PedidoAdminDao(this)
        idPedido = intent.getIntExtra("id", 0)
        val pedido = dao.obtener(idPedido) ?: run { finish(); return }

        binding.tvNumero.text = getString(R.string.pedido_num, pedido.id)
        binding.tvCliente.text = getString(R.string.detalle_cliente, pedido.cliente)
        binding.tvTelefono.text = getString(R.string.detalle_telefono, pedido.telefono)
        binding.tvFecha.text = getString(R.string.detalle_fecha, pedido.fecha)
        binding.tvEstado.text = getString(R.string.detalle_estado, pedido.estado)
        binding.tvTotal.text = getString(R.string.total_fmt, pedido.total)

        binding.rvLineas.layoutManager = LinearLayoutManager(this)
        binding.rvLineas.adapter = LineaAdapter(dao.detalle(idPedido))

        if (pedido.estado != "PENDIENTE") binding.btnAtender.visibility = View.GONE
        binding.btnAtender.setOnClickListener { confirmarAtender() }
    }

    private fun confirmarAtender() {
        AlertDialog.Builder(this)
            .setTitle(R.string.titulo_atender)
            .setMessage(R.string.msg_confirmar_atender)
            .setPositiveButton(R.string.si) { _, _ -> atender() }
            .setNegativeButton(R.string.no, null)
            .show()
    }

    private fun atender() {
        val faltante = dao.atender(idPedido)
        if (faltante == null) {
            Toast.makeText(this, R.string.msg_pedido_atendido, Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(
                this, getString(R.string.error_stock_insuficiente, faltante), Toast.LENGTH_LONG
            ).show()
        }
    }
}