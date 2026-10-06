package com.senati.modaapp.data

import android.content.Context
import com.senati.modaapp.model.ClienteConPedidos
import com.senati.modaapp.model.StockItem

class ReporteDao(context: Context) {

    private val helper = DBHelper(context)

    /** Pedidos atendidos y monto vendido en el mes actual */
    fun atendidosDelMes(): Pair<Int, Double> {
        helper.readableDatabase.rawQuery(
            """SELECT COUNT(*), COALESCE(SUM(total), 0)
               FROM pedido
               WHERE estado = 'ATENDIDO'
                 AND substr(fecha_atencion, 1, 7) = strftime('%Y-%m', 'now', 'localtime')""",
            null
        ).use { c ->
            if (c.moveToFirst()) return Pair(c.getInt(0), c.getDouble(1))
        }
        return Pair(0, 0.0)
    }

    fun stockPorPrenda(): List<StockItem> {
        val lista = mutableListOf<StockItem>()
        helper.readableDatabase.rawQuery(
            "SELECT modelo, talla, color, cantidad FROM ropa ORDER BY cantidad ASC, modelo", null
        ).use { c ->
            while (c.moveToNext()) {
                lista.add(StockItem(c.getString(0), c.getString(1) ?: "", c.getString(2) ?: "", c.getInt(3)))
            }
        }
        return lista
    }

    fun clientesConPedidos(): List<ClienteConPedidos> {
        val lista = mutableListOf<ClienteConPedidos>()
        helper.readableDatabase.rawQuery(
            """SELECT c.nombres, c.apellidos, c.telefono, COUNT(p.id)
               FROM cliente c LEFT JOIN pedido p ON p.id_cliente = c.id
               GROUP BY c.id
               ORDER BY c.apellidos, c.nombres""",
            null
        ).use { c ->
            while (c.moveToNext()) {
                lista.add(ClienteConPedidos(c.getString(0), c.getString(1), c.getString(2), c.getInt(3)))
            }
        }
        return lista
    }
}