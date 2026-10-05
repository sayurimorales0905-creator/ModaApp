package com.senati.modaapp.data

import android.content.ContentValues
import android.content.Context
import com.senati.modaapp.model.ItemCarrito
import com.senati.modaapp.util.ahora

class PedidoDao(context: Context) {

    private val helper = DBHelper(context)

    fun registrar(idCliente: Int, items: List<ItemCarrito>): Long {
        val db = helper.writableDatabase
        db.beginTransaction()
        try {
            val pedido = ContentValues().apply {
                put("id_cliente", idCliente)
                put("fecha", ahora())
                put("total", items.sumOf { it.subtotal })
                put("estado", "PENDIENTE")
            }
            val idPedido = db.insertOrThrow("pedido", null, pedido)

            for (item in items) {
                val detalle = ContentValues().apply {
                    put("id_pedido", idPedido)
                    put("id_ropa", item.ropa.id)
                    put("cantidad", item.cantidad)
                    put("precio_unit", item.ropa.precio)
                    put("subtotal", item.subtotal)
                }
                db.insertOrThrow("detalle_pedido", null, detalle)
            }

            db.setTransactionSuccessful()
            return idPedido
        } finally {
            db.endTransaction()
        }
    }
}