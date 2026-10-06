package com.senati.modaapp.model

data class PedidoResumen(
    val id: Int,
    val cliente: String,
    val telefono: String,
    val fecha: String,
    val total: Double,
    val estado: String
)

data class LineaPedido(
    val idRopa: Int,
    val modelo: String,
    val talla: String,
    val color: String,
    val cantidad: Int,
    val foto: String,
    val subtotal: Double
)