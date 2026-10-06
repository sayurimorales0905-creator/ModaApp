package com.senati.modaapp.data

import android.content.Context
import com.senati.modaapp.model.LineaPedido
import com.senati.modaapp.model.PedidoResumen
import com.senati.modaapp.util.ahora

class PedidoAdminDao(context: Context) {

    private val helper = DBHelper(context)

    companion object {
        private const val SELECT_PEDIDO =
            """SELECT p.id, c.nombres || ' ' || c.apellidos, c.telefono, p.fecha, p.total, p.estado
               FROM pedido p INNER JOIN cliente c ON c.id = p.id_cliente"""
    }

    fun listarPorEstado(estado: String): List<PedidoResumen> =
        consultar("$SELECT_PEDIDO WHERE p.estado = ? ORDER BY p.id DESC", arrayOf(estado))

    fun obtener(id: Int): PedidoResumen? =
        consultar("$SELECT_PEDIDO WHERE p.id = ?", arrayOf(id.toString())).firstOrNull()

    private fun consultar(sql: String, args: Array<String>): List<PedidoResumen> {
        val lista = mutableListOf<PedidoResumen>()
        helper.readableDatabase.rawQuery(sql, args).use { c ->
            while (c.moveToNext()) {
                lista.add(
                    PedidoResumen(
                        c.getInt(0), c.getString(1), c.getString(2),
                        c.getString(3), c.getDouble(4), c.getString(5)
                    )
                )
            }
        }
        return lista
    }

    fun detalle(idPedido: Int): List<LineaPedido> {
        val lista = mutableListOf<LineaPedido>()
        helper.readableDatabase.rawQuery(
            """SELECT d.id_ropa, r.modelo, r.talla, r.color, d.cantidad, r.foto, d.subtotal
               FROM detalle_pedido d INNER JOIN ropa r ON r.id = d.id_ropa
               WHERE d.id_pedido = ?""",
            arrayOf(idPedido.toString())
        ).use { c ->
            while (c.moveToNext()) {
                lista.add(
                    LineaPedido(
                        c.getInt(0), c.getString(1), c.getString(2) ?: "",
                        c.getString(3) ?: "", c.getInt(4), c.getString(5) ?: "", c.getDouble(6)
                    )
                )
            }
        }
        return lista
    }

    /**
     * Marca el pedido como ATENDIDO y descuenta el stock en una sola transacción.
     * Devuelve null si todo salió bien, o el modelo de la prenda sin stock suficiente.
     */
    fun atender(idPedido: Int): String? {
        val pedido = obtener(idPedido) ?: return null
        if (pedido.estado != "PENDIENTE") return null

        val lineas = detalle(idPedido)
        val db = helper.writableDatabase
        db.beginTransaction()
        try {
            for (l in lineas) {
                db.rawQuery(
                    "SELECT cantidad FROM ropa WHERE id = ?", arrayOf(l.idRopa.toString())
                ).use { c ->
                    val stock = if (c.moveToFirst()) c.getInt(0) else 0
                    if (stock < l.cantidad) return l.modelo   // sin setTransactionSuccessful: se revierte
                }
            }
            for (l in lineas) {
                db.execSQL(
                    "UPDATE ropa SET cantidad = cantidad - ? WHERE id = ?",
                    arrayOf<Any>(l.cantidad, l.idRopa)
                )
            }
            db.execSQL(
                "UPDATE pedido SET estado = 'ATENDIDO', fecha_atencion = ? WHERE id = ?",
                arrayOf<Any>(ahora(), idPedido)
            )
            db.setTransactionSuccessful()
            return null
        } finally {
            db.endTransaction()
        }
    }
}