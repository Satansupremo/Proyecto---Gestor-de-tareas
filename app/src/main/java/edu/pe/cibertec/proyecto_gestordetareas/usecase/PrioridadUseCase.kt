package edu.pe.cibertec.proyecto_gestordetareas.usecase

import edu.pe.cibertec.proyecto_gestordetareas.entity.Prioridad

interface PrioridadUseCase {
    fun listarPrioridades(): List<Prioridad>
    fun agregarPrioridad(prioridad: Prioridad): Long
    fun editarPrioridad(prioridad: Prioridad): Int
    fun obtenerPrioridad(idPrioridad: Int): Prioridad?
}