package edu.pe.cibertec.proyecto_gestordetareas.usecase

import edu.pe.cibertec.proyecto_gestordetareas.entity.Tarea

interface TareaUseCase {

    fun listarTareas(): List<Tarea>
    fun agregarTarea(tarea: Tarea): Long
    fun editarTarea(tarea: Tarea): Int
    fun obtenerTarea(idTarea: Int): Tarea?

}