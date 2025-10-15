package edu.pe.cibertec.proyecto_gestordetareas.model

import android.content.Context
import edu.pe.cibertec.proyecto_gestordetareas.entity.Prioridad
import edu.pe.cibertec.proyecto_gestordetareas.repository.PrioridadRepository
import edu.pe.cibertec.proyecto_gestordetareas.usecase.PrioridadUseCase

class PrioridadModel(context: Context) : PrioridadUseCase {

    private val prioridadRepository = PrioridadRepository(context)

    override fun listarPrioridades(): List<Prioridad> {
        return prioridadRepository.listarPrioridades();
    }

    override fun agregarPrioridad(prioridad: Prioridad): Long {
        return prioridadRepository.agregarPrioridad(prioridad)
    }

    override fun editarPrioridad(prioridad: Prioridad): Int {
        return prioridadRepository.editarPrioridad(prioridad)
    }

    override fun obtenerPrioridad(idPrioridad: Int): Prioridad? {
        return prioridadRepository.obtenerPrioridad(idPrioridad)
    }
}