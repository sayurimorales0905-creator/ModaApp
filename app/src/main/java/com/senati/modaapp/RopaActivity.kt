package com.senati.modaapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.senati.modaapp.data.RopaDao
import com.senati.modaapp.databinding.ActivityRopaBinding

class RopaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRopaBinding
    private lateinit var dao: RopaDao
    private val adapter = RopaAdapter(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRopaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dao = RopaDao(this)
        binding.rvRopa.layoutManager = LinearLayoutManager(this)
        binding.rvRopa.adapter = adapter

        binding.fabAgregar.setOnClickListener {
            startActivity(Intent(this, RopaFormActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.actualizar(dao.listar())   // refresca al volver del formulario
    }
}