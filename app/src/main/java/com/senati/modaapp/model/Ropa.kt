package com.senati.modaapp.model

data class Categoria(val id: Int, val nombre: String) {
    override fun toString() = nombre   // para que el Spinner muestre el nombre
}

data class Ropa(
    val id: Int = 0,
    val modelo: String,
    val idCategoria: Int,
    val categoria: String = "",
    val talla: String,
    val marca: String,
    val color: String,
    val precio: Double,
    val cantidad: Int,
    val foto: String
)