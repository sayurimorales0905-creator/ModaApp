package com.senati.modaapp.model

data class ItemCarrito(val ropa: Ropa, var cantidad: Int) {
    val subtotal: Double get() = ropa.precio * cantidad
}

object Carrito {
    val items = mutableListOf<ItemCarrito>()

    fun cantidadDe(idRopa: Int): Int = items.find { it.ropa.id == idRopa }?.cantidad ?: 0

    fun agregar(ropa: Ropa, cantidad: Int) {
        val existente = items.find { it.ropa.id == ropa.id }
        if (existente != null) existente.cantidad += cantidad
        else items.add(ItemCarrito(ropa, cantidad))
    }

    fun quitar(item: ItemCarrito) { items.remove(item) }
    fun total(): Double = items.sumOf { it.subtotal }
    fun cantidadTotal(): Int = items.sumOf { it.cantidad }
    fun vaciar() = items.clear()
}