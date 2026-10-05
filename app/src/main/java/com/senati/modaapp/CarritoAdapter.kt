package com.senati.modaapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.senati.modaapp.databinding.ItemCarritoBinding
import com.senati.modaapp.model.ItemCarrito
import com.senati.modaapp.util.cargarFoto

class CarritoAdapter(
    private val items: List<ItemCarrito>,
    private val onLongClick: (ItemCarrito) -> Unit
) : RecyclerView.Adapter<CarritoAdapter.VH>() {

    class VH(val b: ItemCarritoBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemCarritoBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(h: VH, position: Int) {
        val item = items[position]
        val ctx = h.itemView.context
        h.b.tvModelo.text = item.ropa.modelo
        h.b.tvDetalle.text = ctx.getString(
            R.string.item_carrito_detalle, item.ropa.talla, item.cantidad, item.ropa.precio
        )
        h.b.tvSubtotal.text = ctx.getString(R.string.subtotal_fmt, item.subtotal)
        h.b.ivFoto.setImageBitmap(cargarFoto(item.ropa.foto, 200))
        h.itemView.setOnLongClickListener {
            onLongClick(item)
            true
        }
    }
}