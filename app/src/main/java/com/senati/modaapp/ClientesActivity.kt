package com.senati.modaapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.senati.modaapp.data.ReporteDao
import com.senati.modaapp.databinding.ActivityClientesBinding

class ClientesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClientesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvClientes.layoutManager = LinearLayoutManager(this)
        binding.rvClientes.adapter = ClientesAdapter(ReporteDao(this).clientesConPedidos())
    }
}