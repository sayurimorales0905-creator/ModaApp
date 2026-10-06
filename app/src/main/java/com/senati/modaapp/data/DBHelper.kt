package com.senati.modaapp.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.senati.modaapp.model.Usuario

class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        const val DB_NAME = "modaapp.db"
        const val DB_VERSION = 3

        // Número que recibe los WhatsApp de pedidos (9 dígitos, sin +51)
        const val TELEFONO_TIENDA = "957785535"
    }

    override fun onConfigure(db: SQLiteDatabase) {
        super.onConfigure(db)
        db.setForeignKeyConstraintsEnabled(true)
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """CREATE TABLE usuario (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                usuario TEXT UNIQUE NOT NULL,
                clave TEXT NOT NULL,
                rol TEXT NOT NULL,
                telefono TEXT)"""
        )
        db.execSQL("INSERT INTO usuario (usuario, clave, rol, telefono) VALUES ('admin', '1234', 'ADMIN', '957785535')")

        db.execSQL("CREATE TABLE categoria (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT UNIQUE NOT NULL)")
        listOf("Polos", "Pantalones", "Vestidos", "Casacas", "Zapatillas").forEach {
            db.execSQL("INSERT INTO categoria (nombre) VALUES (?)", arrayOf(it))
        }

        db.execSQL(
            """CREATE TABLE ropa (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                modelo TEXT NOT NULL,
                id_categoria INTEGER NOT NULL,
                talla TEXT,
                marca TEXT,
                color TEXT,
                precio REAL CHECK(precio > 0),
                cantidad INTEGER CHECK(cantidad >= 0),
                foto TEXT,
                FOREIGN KEY (id_categoria) REFERENCES categoria(id))"""
        )

        crearTablasPedido(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) crearTablasPedido(db)
        if (oldVersion < 3) {
            db.execSQL("UPDATE usuario SET telefono = ? WHERE rol = 'ADMIN'", arrayOf(TELEFONO_TIENDA))
        }
    }

    private fun crearTablasPedido(db: SQLiteDatabase) {
        db.execSQL(
            """CREATE TABLE cliente (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                telefono TEXT UNIQUE NOT NULL,
                nombres TEXT NOT NULL,
                apellidos TEXT NOT NULL,
                fecha_registro TEXT NOT NULL)"""
        )
        db.execSQL(
            """CREATE TABLE pedido (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                id_cliente INTEGER NOT NULL,
                fecha TEXT NOT NULL,
                total REAL NOT NULL,
                estado TEXT NOT NULL DEFAULT 'PENDIENTE',
                fecha_atencion TEXT,
                FOREIGN KEY (id_cliente) REFERENCES cliente(id))"""
        )
        db.execSQL(
            """CREATE TABLE detalle_pedido (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                id_pedido INTEGER NOT NULL,
                id_ropa INTEGER NOT NULL,
                cantidad INTEGER NOT NULL CHECK(cantidad > 0),
                precio_unit REAL NOT NULL,
                subtotal REAL NOT NULL,
                FOREIGN KEY (id_pedido) REFERENCES pedido(id) ON DELETE CASCADE,
                FOREIGN KEY (id_ropa) REFERENCES ropa(id))"""
        )
    }

    fun telefonoAdmin(): String {
        readableDatabase.rawQuery(
            "SELECT telefono FROM usuario WHERE rol = 'ADMIN' LIMIT 1", null
        ).use { c ->
            if (c.moveToFirst()) return c.getString(0) ?: ""
        }
        return ""
    }

    fun validarUsuario(usuario: String, clave: String): Usuario? {
        readableDatabase.rawQuery(
            "SELECT id, usuario, rol FROM usuario WHERE usuario = ? AND clave = ?",
            arrayOf(usuario, clave)
        ).use { c ->
            if (c.moveToFirst()) return Usuario(c.getInt(0), c.getString(1), c.getString(2))
        }
        return null
    }
}