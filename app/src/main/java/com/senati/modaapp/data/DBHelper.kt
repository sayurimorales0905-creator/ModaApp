package com.senati.modaapp.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.senati.modaapp.model.Usuario

class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        const val DB_NAME = "modaapp.db"
        const val DB_VERSION = 1
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
        db.execSQL("INSERT INTO usuario (usuario, clave, rol, telefono) VALUES ('admin', '1234', 'ADMIN', '999999999')")

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
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Se llena en el Sprint 3 (DB_VERSION = 2)
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