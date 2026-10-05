package com.senati.modaapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.senati.modaapp.databinding.ItemRopaBinding
import com.senati.modaapp.model.Ropa
import com.senati.modaapp.util.cargarFoto

class RopaAdapter(private var items: List<Ropa>) : RecyclerView.Adapter<RopaAdapter.VH>() {

    class VH(val b: ItemRopaBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemRopaBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(h: VH, position: Int) {
        val r = items[position]
        val ctx = h.itemView.context
        h.b.tvModelo.text = r.modelo
        h.b.tvDetalle.text = ctx.getString(R.string.ropa_detalle, r.talla, r.color, r.marca)
        h.b.tvStock.text = ctx.getString(R.string.ropa_stock, r.cantidad, r.precio)
        h.b.ivFoto.setImageBitmap(cargarFoto(r.foto, 200))
    }

    fun actualizar(nuevos: List<Ropa>) {
        items = nuevos
        notifyDataSetChanged()
    }
}