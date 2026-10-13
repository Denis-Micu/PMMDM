package com.example.proyecto_pmmdm

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_pmmdm.R

/** Pantalla B: detalle del préstamo pulsado. */
class DetalleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle)
        ajustarBordes(R.id.raiz)

        val notas = intent.getStringExtra(Claves.NOTAS).orEmpty()
        findViewById<TextView>(R.id.tvObjeto).text = intent.getStringExtra(Claves.OBJETO).orEmpty()
        findViewById<TextView>(R.id.tvPersona).text = intent.getStringExtra(Claves.PERSONA).orEmpty()
        findViewById<TextView>(R.id.tvFecha).text = intent.getStringExtra(Claves.FECHA).orEmpty()
        findViewById<TextView>(R.id.tvNotas).text =
            if (notas.isBlank()) getString(R.string.sin_notas) else notas

        // Volver cierra el detalle; Atrás del sistema hace lo mismo por defecto.
        findViewById<Button>(R.id.btnVolver).setOnClickListener { finish() }
    }
}
