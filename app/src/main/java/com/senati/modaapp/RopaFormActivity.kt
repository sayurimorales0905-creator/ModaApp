package com.senati.modaapp

import android.database.sqlite.SQLiteConstraintException
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
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
    private var fotoOriginal: String? = null
    private var idEdicion = -1
    private val tallas = listOf("XS", "S", "M", "L", "XL")

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

        val categorias = dao.listarCategorias()
        binding.spCategoria.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categorias)
        binding.spTalla.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tallas)

        idEdicion = intent.getIntExtra("id", -1)
        if (idEdicion != -1) cargarParaEditar(categorias)

        binding.btnElegirFoto.setOnClickListener {
            elegirFoto.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }
        binding.btnGuardar.setOnClickListener { guardar() }
        binding.btnEliminar.setOnClickListener { confirmarEliminar() }
    }

    private fun cargarParaEditar(categorias: List<Categoria>) {
        val r = dao.obtener(idEdicion) ?: run { finish(); return }
        binding.etModelo.setText(r.modelo)
        binding.etMarca.setText(r.marca)
        binding.etColor.setText(r.color)
        binding.etPrecio.setText(r.precio.toString())
        binding.etCantidad.setText(r.cantidad.toString())
        binding.spCategoria.setSelection(
            categorias.indexOfFirst { it.id == r.idCategoria }.coerceAtLeast(0)
        )
        binding.spTalla.setSelection(tallas.indexOf(r.talla).coerceAtLeast(0))
        rutaFoto = r.foto
        fotoOriginal = r.foto
        binding.ivFoto.setImageBitmap(cargarFoto(r.foto))
        binding.btnGuardar.setText(R.string.btn_actualizar)
        binding.btnEliminar.visibility = View.VISIBLE
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
        val ropa = Ropa(
            id = if (idEdicion != -1) idEdicion else 0,
            modelo = modelo,
            idCategoria = categoria.id,
            talla = binding.spTalla.selectedItem as String,
            marca = marca,
            color = color,
            precio = precio!!,
            cantidad = cantidad!!,
            foto = rutaFoto!!
        )

        if (idEdicion == -1) {
            dao.insertar(ropa)
            Toast.makeText(this, R.string.msg_prenda_guardada, Toast.LENGTH_SHORT).show()
        } else {
            dao.actualizar(ropa)
            // si cambió la foto, borra la anterior
            fotoOriginal?.let { if (it != rutaFoto) File(it).delete() }
            Toast.makeText(this, R.string.msg_prenda_actualizada, Toast.LENGTH_SHORT).show()
        }
        finish()
    }

    private fun confirmarEliminar() {
        AlertDialog.Builder(this)
            .setTitle(R.string.titulo_eliminar)
            .setMessage(R.string.msg_confirmar_eliminar)
            .setPositiveButton(R.string.si) { _, _ -> eliminar() }
            .setNegativeButton(R.string.no, null)
            .show()
    }

    private fun eliminar() {
        try {
            dao.eliminar(idEdicion)
            fotoOriginal?.let { File(it).delete() }
            Toast.makeText(this, R.string.msg_prenda_eliminada, Toast.LENGTH_SHORT).show()
            finish()
        } catch (e: SQLiteConstraintException) {
            Toast.makeText(this, R.string.error_eliminar, Toast.LENGTH_LONG).show()
        }
    }
}