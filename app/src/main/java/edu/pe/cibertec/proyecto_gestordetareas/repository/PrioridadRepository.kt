package edu.pe.cibertec.proyecto_gestordetareas.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import edu.pe.cibertec.proyecto_gestordetareas.database.SqlLiteConnection
import edu.pe.cibertec.proyecto_gestordetareas.entity.Prioridad
import androidx.compose.ui.graphics.Color

class PrioridadRepository(context: Context) {

    private val dbHelper = SqlLiteConnection(context)

    // Listar todas las prioridades
    fun listarPrioridades(): List<Prioridad> {
        val lista = mutableListOf<Prioridad>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM prioridad", null)

        if (cursor.moveToFirst()) {
            do {
                val prioridad = Prioridad(
                    id_prioridad = cursor.getInt(cursor.getColumnIndexOrThrow("id_prioridad")),
                    denominacion = cursor.getString(cursor.getColumnIndexOrThrow("denominacion")),
                    descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion")),
                    // Guardamos el color como un número entero (ARGB) y lo convertimos a Color
                    color = Color(cursor.getInt(cursor.getColumnIndexOrThrow("color")))
                )
                lista.add(prioridad)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return lista
    }

    // Agregar una nueva prioridad
    fun agregarPrioridad(prioridad: Prioridad): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("denominacion", prioridad.denominacion)
            put("descripcion", prioridad.descripcion)
            // Convertimos el color a entero (ARGB)
            put("color", prioridad.color.value.toLong().toInt())
        }

        val resultado = db.insert("prioridad", null, values)
        db.close()
        return resultado
    }

    // Editar una prioridad existente
    fun editarPrioridad(prioridad: Prioridad): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("denominacion", prioridad.denominacion)
            put("descripcion", prioridad.descripcion)
            put("color", prioridad.color.value.toLong().toInt())
        }

        val filasAfectadas = db.update(
            "prioridad",
            values,
            "id_prioridad = ?",
            arrayOf(prioridad.id_prioridad.toString())
        )

        db.close()
        return filasAfectadas
    }

    // Obtener una prioridad por ID
    fun obtenerPrioridad(idPrioridad: Int): Prioridad? {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM prioridad WHERE id_prioridad = ?",
            arrayOf(idPrioridad.toString())
        )

        var prioridad: Prioridad? = null
        if (cursor.moveToFirst()) {
            prioridad = Prioridad(
                id_prioridad = cursor.getInt(cursor.getColumnIndexOrThrow("id_prioridad")),
                denominacion = cursor.getString(cursor.getColumnIndexOrThrow("denominacion")),
                descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion")),
                color = Color(cursor.getInt(cursor.getColumnIndexOrThrow("color")))
            )
        }

        cursor.close()
        db.close()
        return prioridad
    }
}
