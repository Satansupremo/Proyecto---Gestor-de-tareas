package edu.pe.cibertec.proyecto_gestordetareas.model

import android.content.Context
import edu.pe.cibertec.proyecto_gestordetareas.entity.Tarea
import edu.pe.cibertec.proyecto_gestordetareas.repository.TareaRepository
import edu.pe.cibertec.proyecto_gestordetareas.usecase.TareaUseCase

class TareaModel(context: Context) : TareaUseCase {

    private val tareaRepository = TareaRepository(context)

    override fun listarTareas(): List<Tarea> {
        return tareaRepository.listarTareas()
    }

    override fun agregarTarea(tarea: Tarea): Long {
        return tareaRepository.agregarTarea(tarea)
    }

    override fun editarTarea(tarea: Tarea): Int {
        return tareaRepository.editarTarea(tarea)
    }

    override fun obtenerTarea(idTarea: Int): Tarea? {
        return tareaRepository.obtenerTarea(idTarea)
    }
}