package com.senati.modaapp.model

data class StockItem(val modelo: String, val talla: String, val color: String, val cantidad: Int)

data class ClienteConPedidos(
    val nombres: String,
    val apellidos: String,
    val telefono: String,
    val pedidos: Int
)