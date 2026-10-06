package com.senati.modaapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.senati.modaapp.databinding.ItemPedidoBinding
import com.senati.modaapp.model.PedidoResumen

class PedidosAdapter(
    private var items: List<PedidoResumen>,
    private val onClick: (PedidoResumen) -> Unit
) : RecyclerView.Adapter<PedidosAdapter.VH>() {

    class VH(val b: ItemPedidoBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemPedidoBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(h: VH, position: Int) {
        val p = items[position]
        val ctx = h.itemView.context
        h.b.tvNumero.text = ctx.getString(R.string.pedido_num, p.id)
        h.b.tvCliente.text = p.cliente
        h.b.tvFecha.text = p.fecha
        h.b.tvTotal.text = ctx.getString(R.string.subtotal_fmt, p.total)
        h.itemView.setOnClickListener { onClick(p) }
    }

    fun actualizar(nuevos: List<PedidoResumen>) {
        items = nuevos
        notifyDataSetChanged()
    }
}