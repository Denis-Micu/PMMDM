package com.example.proyecto_pmmdm

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_pmmdm.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.Locale

/** Pantalla C: formulario de alta con validación. */
class FormularioActivity : AppCompatActivity() {

    private lateinit var tilObjeto: TextInputLayout
    private lateinit var tilPersona: TextInputLayout
    private lateinit var tilFecha: TextInputLayout
    private lateinit var etObjeto: TextInputEditText
    private lateinit var etPersona: TextInputEditText
    private lateinit var etFecha: TextInputEditText
    private lateinit var etNotas: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_formulario)
        ajustarBordes(R.id.raiz)

        tilObjeto = findViewById(R.id.tilObjeto)
        tilPersona = findViewById(R.id.tilPersona)
        tilFecha = findViewById(R.id.tilFecha)
        etObjeto = findViewById(R.id.etObjeto)
        etPersona = findViewById(R.id.etPersona)
        etFecha = findViewById(R.id.etFecha)
        etNotas = findViewById(R.id.etNotas)

        findViewById<Button>(R.id.btnGuardar).setOnClickListener { guardar() }
        findViewById<Button>(R.id.btnCancelar).setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    private fun guardar() {
        // trim antes de validar. No se reescriben los campos: si hay error se conserva lo escrito.
        val objeto = etObjeto.text.toString().trim()
        val persona = etPersona.text.toString().trim()
        val fecha = etFecha.text.toString().trim()
        val notas = etNotas.text.toString().trim()

        tilObjeto.error = null
        tilPersona.error = null
        tilFecha.error = null

        var primerError: View? = null
        fun marcar(til: TextInputLayout, campo: View, mensaje: Int) {
            til.error = getString(mensaje)
            if (primerError == null) primerError = campo
        }

        when {
            objeto.isEmpty() -> marcar(tilObjeto, etObjeto, R.string.error_objeto_vacio)
            objeto.length < 2 -> marcar(tilObjeto, etObjeto, R.string.error_objeto_corto)
        }
        if (persona.isEmpty()) marcar(tilPersona, etPersona, R.string.error_persona_vacia)
        when {
            fecha.isEmpty() -> marcar(tilFecha, etFecha, R.string.error_fecha_vacia)
            !fechaValida(fecha) -> marcar(tilFecha, etFecha, R.string.error_fecha_formato)
        }

        if (primerError != null) {
            primerError?.requestFocus() // el foco va al primer campo incorrecto
            return
        }

        val resultado = Intent().apply {
            putExtra(Claves.OBJETO, objeto)
            putExtra(Claves.PERSONA, persona)
            putExtra(Claves.FECHA, fecha)
            putExtra(Claves.NOTAS, notas)
        }
        setResult(RESULT_OK, resultado)
        finish() // se cierra al instante: no se puede pulsar Guardar dos veces
    }

    private fun fechaValida(texto: String): Boolean {
        if (!Regex("""\d{2}/\d{2}/\d{4}""").matches(texto)) return false
        return try {
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).apply { isLenient = false }.parse(texto)
            true
        } catch (e: Exception) {
            false
        }
    }
}
