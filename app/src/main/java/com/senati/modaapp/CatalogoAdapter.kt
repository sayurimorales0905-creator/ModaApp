package com.senati.modaapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.senati.modaapp.databinding.ItemCatalogoBinding
import com.senati.modaapp.model.Ropa
import com.senati.modaapp.util.cargarFoto

class CatalogoAdapter(private var items: List<Ropa>) :
    RecyclerView.Adapter<CatalogoAdapter.VH>() {

    class VH(val b: ItemCatalogoBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemCatalogoBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(h: VH, position: Int) {
        val r = items[position]
        val ctx = h.itemView.context
        h.b.tvModelo.text = r.modelo
        h.b.tvTalla.text = ctx.getString(R.string.talla_fmt, r.talla)
        h.b.tvPrecio.text = ctx.getString(R.string.precio_fmt, r.precio)
        h.b.ivFoto.setImageBitmap(cargarFoto(r.foto, 400))
    }

    fun actualizar(nuevos: List<Ropa>) {
        items = nuevos
        notifyDataSetChanged()
    }
}