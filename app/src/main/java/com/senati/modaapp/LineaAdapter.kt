package com.senati.modaapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.senati.modaapp.databinding.ItemCarritoBinding
import com.senati.modaapp.model.LineaPedido
import com.senati.modaapp.util.cargarFoto

class LineaAdapter(private val items: List<LineaPedido>) :
    RecyclerView.Adapter<LineaAdapter.VH>() {

    class VH(val b: ItemCarritoBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemCarritoBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(h: VH, position: Int) {
        val l = items[position]
        val ctx = h.itemView.context
        h.b.tvModelo.text = l.modelo
        h.b.tvDetalle.text = ctx.getString(R.string.linea_detalle, l.talla, l.color, l.cantidad)
        h.b.tvSubtotal.text = ctx.getString(R.string.subtotal_fmt, l.subtotal)
        h.b.ivFoto.setImageBitmap(cargarFoto(l.foto, 200))
    }
}