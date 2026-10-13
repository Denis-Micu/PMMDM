package com.example.proyecto_pmmdm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/** Convierte cada Prestamo de la lista en una fila (item_prestamo.xml). */
class PrestamoAdapter(
    private val datos: List<Prestamo>,
    private val alPulsar: (Prestamo) -> Unit
) : RecyclerView.Adapter<PrestamoAdapter.FilaHolder>() {

    class FilaHolder(vista: View) : RecyclerView.ViewHolder(vista) {
        val titulo: TextView = vista.findViewById(R.id.tvFilaObjeto)
        val subtitulo: TextView = vista.findViewById(R.id.tvFilaPersonaFecha)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilaHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_prestamo, parent, false)
        return FilaHolder(vista)
    }

    override fun onBindViewHolder(holder: FilaHolder, position: Int) {
        val p = datos[position]
        holder.titulo.text = p.objeto
        holder.subtitulo.text = holder.itemView.context
            .getString(R.string.fila_persona_fecha, p.persona, p.fecha)
        holder.itemView.setOnClickListener { alPulsar(p) }
    }

    override fun getItemCount() = datos.size
}
