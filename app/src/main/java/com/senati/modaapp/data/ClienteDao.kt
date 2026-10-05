package com.senati.modaapp.data

import android.content.ContentValues
import android.content.Context
import com.senati.modaapp.model.Cliente
import com.senati.modaapp.util.ahora

class ClienteDao(context: Context) {

    private val helper = DBHelper(context)

    fun buscarPorTelefono(telefono: String): Cliente? {
        helper.readableDatabase.rawQuery(
            "SELECT id, telefono, nombres, apellidos FROM cliente WHERE telefono = ?",
            arrayOf(telefono)
        ).use { c ->
            if (c.moveToFirst()) {
                return Cliente(c.getInt(0), c.getString(1), c.getString(2), c.getString(3))
            }
        }
        return null
    }

    fun insertar(c: Cliente): Long {
        val valores = ContentValues().apply {
            put("telefono", c.telefono)
            put("nombres", c.nombres)
            put("apellidos", c.apellidos)
            put("fecha_registro", ahora())
        }
        return helper.writableDatabase.insert("cliente", null, valores)
    }
}