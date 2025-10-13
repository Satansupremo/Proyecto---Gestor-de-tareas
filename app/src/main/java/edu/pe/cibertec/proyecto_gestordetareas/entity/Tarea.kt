package edu.pe.cibertec.proyecto_gestordetareas.entity

import android.icu.util.LocaleData

data class Tarea(
    val id_tarea: Int = 0,
    val titulo: String,
    val descripcion: String,
    val fecha_creacion: String,
    val completado: Boolean = false,
    val id_categoria: Int = 0,
    val id_prioridad: Int = 0
)