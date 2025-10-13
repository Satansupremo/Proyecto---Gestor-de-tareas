package edu.pe.cibertec.proyecto_gestordetareas.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import edu.pe.cibertec.proyecto_gestordetareas.database.SqlLiteConnection
import edu.pe.cibertec.proyecto_gestordetareas.entity.Categoria

class CategoriaRepository(context: Context) {

    private val dbHelper = SqlLiteConnection(context)

    // Listar todas las categorías
    fun listarCategorias(): List<Categoria> {
        val lista = mutableListOf<Categoria>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM categoria", null)

        if (cursor.moveToFirst()) {
            do {
                val categoria = Categoria(
                    id_categoria = cursor.getInt(cursor.getColumnIndexOrThrow("id_categoria")),
                    denominacion = cursor.getString(cursor.getColumnIndexOrThrow("denominacion")),
                    descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"))
                )
                lista.add(categoria)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return lista
    }

    // Agregar una nueva categoría
    fun agregarCategoria(categoria: Categoria): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues()
        values.put("denominacion", categoria.denominacion)
        values.put("descripcion", categoria.descripcion)

        val resultado = db.insert("categoria", null, values)
        db.close()
        return resultado
    }

    // Editar una categoría existente
    fun editarCategoria(categoria: Categoria): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues()
        values.put("denominacion", categoria.denominacion)
        values.put("descripcion", categoria.descripcion)

        val filasAfectadas = db.update(
            "categoria",
            values,
            "id_categoria = ?",
            arrayOf(categoria.id_categoria.toString())
        )

        db.close()
        return filasAfectadas
    }

    // Obtener una categoría por ID
    fun obtenerCategoria(idCategoria: Int): Categoria? {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM categoria WHERE id_categoria = ?",
            arrayOf(idCategoria.toString())
        )

        var categoria: Categoria? = null
        if (cursor.moveToFirst()) {
            categoria = Categoria(
                id_categoria = cursor.getInt(cursor.getColumnIndexOrThrow("id_categoria")),
                denominacion = cursor.getString(cursor.getColumnIndexOrThrow("denominacion")),
                descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"))
            )
        }

        cursor.close()
        db.close()
        return categoria
    }
}
