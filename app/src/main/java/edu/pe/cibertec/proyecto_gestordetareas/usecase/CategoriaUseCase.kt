package edu.pe.cibertec.proyecto_gestordetareas.usecase

import edu.pe.cibertec.proyecto_gestordetareas.entity.Categoria

interface CategoriaUseCase {
    fun listarCategorias(): List<Categoria>
    fun agregarCategoria(categoria: Categoria): Long
    fun editarCategoria(categoria: Categoria): Int
    fun obtenerCategoria(idCategoria: Int): Categoria?
}