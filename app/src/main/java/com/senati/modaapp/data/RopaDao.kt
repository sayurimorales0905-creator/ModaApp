package com.senati.modaapp.data

import android.content.ContentValues
import android.content.Context
import com.senati.modaapp.model.Categoria
import com.senati.modaapp.model.Ropa

class RopaDao(context: Context) {

    private val helper = DBHelper(context)

    companion object {
        private const val SELECT_ROPA =
            """SELECT r.id, r.modelo, r.id_categoria, c.nombre, r.talla, r.marca,
               r.color, r.precio, r.cantidad, r.foto
               FROM ropa r INNER JOIN categoria c ON c.id = r.id_categoria"""
    }

    private fun valores(r: Ropa) = ContentValues().apply {
        put("modelo", r.modelo)
        put("id_categoria", r.idCategoria)
        put("talla", r.talla)
        put("marca", r.marca)
        put("color", r.color)
        put("precio", r.precio)
        put("cantidad", r.cantidad)
        put("foto", r.foto)
    }

    fun listarCategorias(): List<Categoria> {
        val lista = mutableListOf<Categoria>()
        helper.readableDatabase.rawQuery("SELECT id, nombre FROM categoria ORDER BY id", null).use { c ->
            while (c.moveToNext()) lista.add(Categoria(c.getInt(0), c.getString(1)))
        }
        return lista
    }

    fun insertar(r: Ropa): Long = helper.writableDatabase.insert("ropa", null, valores(r))

    fun actualizar(r: Ropa): Int =
        helper.writableDatabase.update("ropa", valores(r), "id = ?", arrayOf(r.id.toString()))

    fun eliminar(id: Int): Int =
        helper.writableDatabase.delete("ropa", "id = ?", arrayOf(id.toString()))

    fun obtener(id: Int): Ropa? =
        consultar("$SELECT_ROPA WHERE r.id = ?", arrayOf(id.toString())).firstOrNull()

    fun listar(filtro: String = ""): List<Ropa> {
        if (filtro.isBlank()) return consultar("$SELECT_ROPA ORDER BY r.id DESC", null)
        val like = "%${filtro.trim()}%"
        return consultar(
            "$SELECT_ROPA WHERE r.modelo LIKE ? OR r.marca LIKE ? OR r.color LIKE ? ORDER BY r.id DESC",
            arrayOf(like, like, like)
        )
    }

    fun listarDisponibles(idCategoria: Int?): List<Ropa> {
        return if (idCategoria == null) {
            consultar("$SELECT_ROPA WHERE r.cantidad > 0 ORDER BY r.id DESC", null)
        } else {
            consultar(
                "$SELECT_ROPA WHERE r.cantidad > 0 AND r.id_categoria = ? ORDER BY r.id DESC",
                arrayOf(idCategoria.toString())
            )
        }
    }

    private fun consultar(sql: String, args: Array<String>?): List<Ropa> {
        val lista = mutableListOf<Ropa>()
        helper.readableDatabase.rawQuery(sql, args).use { c ->
            while (c.moveToNext()) {
                lista.add(
                    Ropa(
                        id = c.getInt(0),
                        modelo = c.getString(1),
                        idCategoria = c.getInt(2),
                        categoria = c.getString(3),
                        talla = c.getString(4) ?: "",
                        marca = c.getString(5) ?: "",
                        color = c.getString(6) ?: "",
                        precio = c.getDouble(7),
                        cantidad = c.getInt(8),
                        foto = c.getString(9) ?: ""
                    )
                )
            }
        }
        return lista
    }
}