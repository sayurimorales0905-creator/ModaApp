package com.senati.modaapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.senati.modaapp.databinding.ItemClienteBinding
import com.senati.modaapp.model.ClienteConPedidos

class ClientesAdapter(private val items: List<ClienteConPedidos>) :
    RecyclerView.Adapter<ClientesAdapter.VH>() {

    class VH(val b: ItemClienteBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemClienteBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(h: VH, position: Int) {
        val c = items[position]
        h.b.tvNombre.text = "${c.nombres} ${c.apellidos}"
        h.b.tvTelefono.text = c.telefono
        h.b.tvPedidos.text = h.itemView.context.getString(R.string.cliente_pedidos, c.pedidos)
    }
}