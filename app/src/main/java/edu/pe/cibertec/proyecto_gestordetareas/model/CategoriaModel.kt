package edu.pe.cibertec.proyecto_gestordetareas.model

import android.content.Context
import edu.pe.cibertec.proyecto_gestordetareas.entity.Categoria
import edu.pe.cibertec.proyecto_gestordetareas.repository.CategoriaRepository
import edu.pe.cibertec.proyecto_gestordetareas.usecase.CategoriaUseCase

class CategoriaModel(context: Context) : CategoriaUseCase {
    private val categoriaRepository = CategoriaRepository(context)

    override fun listarCategorias(): List<Categoria> {
        return categoriaRepository.listarCategorias()
    }

    override fun agregarCategoria(categoria: Categoria): Long {
        return categoriaRepository.agregarCategoria(categoria)
    }

    override fun editarCategoria(categoria: Categoria): Int {
        return categoriaRepository.editarCategoria(categoria)
    }

    override fun obtenerCategoria(idCategoria: Int): Categoria? {
        return categoriaRepository.obtenerCategoria(idCategoria)
    }
}
