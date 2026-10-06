package com.senati.modaapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.senati.modaapp.data.ReporteDao
import com.senati.modaapp.databinding.ActivityReportesBinding

class ReportesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReportesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = ReporteDao(this)
        val (cantidad, monto) = dao.atendidosDelMes()
        binding.tvAtendidos.text = getString(R.string.reporte_atendidos, cantidad)
        binding.tvMonto.text = getString(R.string.reporte_monto, monto)

        binding.rvStock.layoutManager = LinearLayoutManager(this)
        binding.rvStock.adapter = StockAdapter(dao.stockPorPrenda())
    }
}