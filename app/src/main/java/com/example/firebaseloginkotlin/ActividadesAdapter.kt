package com.example.firebaseloginkotlin

// ActividadesAdapter.kt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ActividadesAdapter(private val actividades: List<String>) :
    RecyclerView.Adapter<ActividadesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // En lugar de referenciar a actividadTextView, referenciamos directamente al TextView dentro de CalendarActivity.xml
        val actividadTextView: TextView = itemView.findViewById(R.id.actividadTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_calendar, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.actividadTextView.text = actividades[position]
    }

    override fun getItemCount(): Int {
        return actividades.size
    }
}
