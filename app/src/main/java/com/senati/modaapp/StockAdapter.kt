package com.senati.modaapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.senati.modaapp.databinding.ItemStockBinding
import com.senati.modaapp.model.StockItem

class StockAdapter(private val items: List<StockItem>) :
    RecyclerView.Adapter<StockAdapter.VH>() {

    class VH(val b: ItemStockBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemStockBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(h: VH, position: Int) {
        val s = items[position]
        val ctx = h.itemView.context
        val bajo = s.cantidad <= 3
        h.b.tvNombre.text = ctx.getString(R.string.stock_item, s.modelo, s.talla, s.color)
        h.b.tvCantidad.text = ctx.getString(R.string.stock_cant, s.cantidad)
        h.b.tvAlerta.visibility = if (bajo) View.VISIBLE else View.GONE
        // Las prendas con 3 o menos unidades quedan resaltadas con borde rojo
        h.b.root.strokeColor = Color.parseColor("#C62828")
        h.b.root.strokeWidth = if (bajo) 6 else 0
    }
}