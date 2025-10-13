package edu.pe.cibertec.proyecto_gestordetareas.entity

import androidx.compose.ui.graphics.Color

data class Prioridad(
    val id_prioridad: Int = 0,
    val denominacion: String,
    val descripcion: String,
    val color: Color
)