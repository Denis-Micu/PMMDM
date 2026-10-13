package com.example.proyecto_pmmdm

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/** Evita que barras del sistema y teclado tapen el contenido (la vista raíz debe tener el id dado). */
fun AppCompatActivity.ajustarBordes(idRaiz: Int) {
    val raiz = findViewById<android.view.View>(idRaiz)
    ViewCompat.setOnApplyWindowInsetsListener(raiz) { v, insets ->
        val b = insets.getInsets(
            WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime()
        )
        v.setPadding(b.left, b.top, b.right, b.bottom)
        insets
    }
}
