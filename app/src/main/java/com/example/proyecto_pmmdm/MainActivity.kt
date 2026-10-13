package com.example.proyecto_pmmdm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.PopupMenu
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_pmmdm.R

/** Pantalla A: listado de préstamos. */
class MainActivity : AppCompatActivity() {

    // Datos ficticios en memoria (sin base de datos todavía).
    private val prestamos = mutableListOf(
        Prestamo(1, "Taladro Bosch", "Marcos", "12/09/2026", "Con 3 brocas"),
        Prestamo(2, "Libro «Dune»", "Lucía", "20/09/2026", ""),
        Prestamo(3, "Chaqueta azul", "Pedro", "25/09/2026", "")
    )
    private var siguienteId = 4

    private lateinit var adapter: PrestamoAdapter
    private lateinit var lista: RecyclerView

    // Recibe el resultado del formulario (Activity Result API).
    private val lanzadorFormulario =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultado ->
            if (resultado.resultCode != RESULT_OK) return@registerForActivityResult // Cancelar / Atrás
            val d = resultado.data ?: return@registerForActivityResult
            val nuevo = Prestamo(
                id = siguienteId++,
                objeto = d.getStringExtra(Claves.OBJETO).orEmpty(),
                persona = d.getStringExtra(Claves.PERSONA).orEmpty(),
                fecha = d.getStringExtra(Claves.FECHA).orEmpty(),
                notas = d.getStringExtra(Claves.NOTAS).orEmpty()
            )
            prestamos.add(nuevo)
            adapter.notifyItemInserted(prestamos.size - 1)
            lista.scrollToPosition(prestamos.size - 1)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ajustarBordes(R.id.raiz)

        lista = findViewById(R.id.rvPrestamos)
        lista.layoutManager = LinearLayoutManager(this)
        adapter = PrestamoAdapter(prestamos) { abrirDetalle(it) }
        lista.adapter = adapter

        findViewById<Button>(R.id.btnNuevo).setOnClickListener {
            lanzadorFormulario.launch(Intent(this, FormularioActivity::class.java))
        }

        val btnOpciones = findViewById<Button>(R.id.btnOpciones)
        btnOpciones.setOnClickListener { vista ->
            PopupMenu(this, vista).apply {
                menuInflater.inflate(R.menu.menu_opciones, menu)
                setOnMenuItemClickListener { item ->
                    if (item.itemId == R.id.accion_ayuda) { mostrarAyuda(); true } else false
                }
                show()
            }
        }
    }

    private fun abrirDetalle(p: Prestamo) {
        val intent = Intent(this, DetalleActivity::class.java).apply {
            putExtra(Claves.ID, p.id)
            putExtra(Claves.OBJETO, p.objeto)
            putExtra(Claves.PERSONA, p.persona)
            putExtra(Claves.FECHA, p.fecha)
            putExtra(Claves.NOTAS, p.notas)
        }
        startActivity(intent)
    }

    private fun mostrarAyuda() {
        AlertDialog.Builder(this)
            .setTitle(R.string.ayuda_titulo)
            .setMessage(R.string.ayuda_texto)
            .setPositiveButton(R.string.ayuda_cerrar, null)
            .show()
    }
}
