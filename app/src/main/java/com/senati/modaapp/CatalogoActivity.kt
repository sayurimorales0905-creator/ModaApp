package com.senati.modaapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.chip.Chip
import com.senati.modaapp.data.RopaDao
import com.senati.modaapp.databinding.ActivityCatalogoBinding

class CatalogoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCatalogoBinding
    private lateinit var dao: RopaDao
    private val adapter = CatalogoAdapter(emptyList())
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
}