package com.senati.modaapp

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.chip.Chip
import com.senati.modaapp.data.RopaDao
import com.senati.modaapp.databinding.ActivityCatalogoBinding
import com.senati.modaapp.model.Carrito
import com.senati.modaapp.model.Ropa

class CatalogoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCatalogoBinding
    private lateinit var dao: RopaDao
    private val adapter = CatalogoAdapter(emptyList()) { ropa -> pedirCantidad(ropa) }
    private var idCategoriaSel: Int? = null   // null = «Todas»

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatalogoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dao = RopaDao(this)
        binding.rvCatalogo.layoutManager = GridLayoutManager(this, 2)
        binding.rvCatalogo.adapter = adapter

        cargarChips()

        binding.fabCarrito.setOnClickListener {
            startActivity(Intent(this, CarritoActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        cargarPrendas()
        actualizarContador()
    }

    private fun cargarChips() {
        binding.chipCategorias.removeAllViews()
        agregarChip(getString(R.string.todas), null, true)
        dao.listarCategorias().forEach { agregarChip(it.nombre, it.id, false) }
    }

    private fun agregarChip(texto: String, idCategoria: Int?, marcado: Boolean) {
        val chip = Chip(this)
        chip.id = View.generateViewId()
        chip.text = texto
        chip.isCheckable = true
        chip.isChecked = marcado
        chip.setOnClickListener {
            idCategoriaSel = idCategoria
            cargarPrendas()
        }
        binding.chipCategorias.addView(chip)
    }

    private fun cargarPrendas() {
        val lista = dao.listarDisponibles(idCategoriaSel)
        adapter.actualizar(lista)
        binding.tvVacio.visibility = if (lista.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun actualizarContador() {
        binding.fabCarrito.text = getString(R.string.btn_carrito_n, Carrito.cantidadTotal())
    }

    // El cliente elige la cantidad sin superar el stock disponible
    private fun pedirCantidad(ropa: Ropa) {
        val disponible = ropa.cantidad - Carrito.cantidadDe(ropa.id)
        if (disponible <= 0) {
            Toast.makeText(this, R.string.msg_sin_stock_carrito, Toast.LENGTH_SHORT).show()
            return
        }

        val picker = NumberPicker(this).apply {
            minValue = 1
            maxValue = disponible
            value = 1
            wrapSelectorWheel = false
        }
        val contenedor = FrameLayout(this)
        contenedor.addView(
            picker,
            FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER
            )
        )

        AlertDialog.Builder(this)
            .setTitle(ropa.modelo)
            .setMessage(getString(R.string.disponible_fmt, disponible))
            .setView(contenedor)
            .setPositiveButton(R.string.btn_agregar) { _, _ ->
                Carrito.agregar(ropa, picker.value)
                actualizarContador()
                Toast.makeText(this, R.string.msg_agregada, Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton(R.string.no, null)
            .show()
    }
}