package com.senati.modaapp

import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.senati.modaapp.data.RopaDao
import com.senati.modaapp.databinding.ActivityRopaFormBinding
import com.senati.modaapp.model.Categoria
import com.senati.modaapp.model.Ropa
import com.senati.modaapp.util.cargarFoto
import java.io.File

class RopaFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRopaFormBinding
    private lateinit var dao: RopaDao
    private var rutaFoto: String? = null

    private val elegirFoto =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                val ruta = copiarFoto(uri)
                rutaFoto = ruta
                binding.ivFoto.setImageBitmap(cargarFoto(ruta))
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRopaFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dao = RopaDao(this)

        binding.spCategoria.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item, dao.listarCategorias()
        )
        binding.spTalla.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item,
            listOf("XS", "S", "M", "L", "XL")
        )

        binding.btnElegirFoto.setOnClickListener {
            elegirFoto.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }
        binding.btnGuardar.setOnClickListener { guardar() }
    }

    // Copia la imagen a la carpeta interna de la app y devuelve su ruta
    private fun copiarFoto(uri: Uri): String {
        val archivo = File(filesDir, "ropa_${System.currentTimeMillis()}.jpg")
        contentResolver.openInputStream(uri)?.use { entrada ->
            archivo.outputStream().use { salida -> entrada.copyTo(salida) }
        }
        return archivo.absolutePath
    }

    private fun guardar() {
        val modelo = binding.etModelo.text.toString().trim()
        val marca = binding.etMarca.text.toString().trim()
        val color = binding.etColor.text.toString().trim()
        val precio = binding.etPrecio.text.toString().toDoubleOrNull()
        val cantidad = binding.etCantidad.text.toString().toIntOrNull()

        binding.tilModelo.error = null
        binding.tilPrecio.error = null
        binding.tilCantidad.error = null

        var valido = true
        if (modelo.isEmpty()) {
            binding.tilModelo.error = getString(R.string.error_modelo)
            valido = false
        }
        if (precio == null || precio <= 0) {
            binding.tilPrecio.error = getString(R.string.error_precio)
            valido = false
        }
        if (cantidad == null || cantidad < 0) {
            binding.tilCantidad.error = getString(R.string.error_cantidad)
            valido = false
        }
        if (rutaFoto == null) {
            Toast.makeText(this, R.string.error_foto, Toast.LENGTH_SHORT).show()
            valido = false
        }
        if (!valido) return

        val categoria = binding.spCategoria.selectedItem as Categoria
        dao.insertar(
            Ropa(
                modelo = modelo,
                idCategoria = categoria.id,
                talla = binding.spTalla.selectedItem as String,
                marca = marca,
                color = color,
                precio = precio!!,
                cantidad = cantidad!!,
                foto = rutaFoto!!
            )
        )
        Toast.makeText(this, R.string.msg_prenda_guardada, Toast.LENGTH_SHORT).show()
        finish()
    }
}