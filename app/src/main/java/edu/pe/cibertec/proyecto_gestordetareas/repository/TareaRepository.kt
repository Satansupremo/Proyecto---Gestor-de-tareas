package edu.pe.cibertec.proyecto_gestordetareas.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import edu.pe.cibertec.proyecto_gestordetareas.database.SqlLiteConnection
import edu.pe.cibertec.proyecto_gestordetareas.entity.Tarea

class TareaRepository(context: Context) {

    private val dbHelper = SqlLiteConnection(context)

    // Listar todas las tareas
    fun listarTareas(): List<Tarea> {
        val lista = mutableListOf<Tarea>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM tarea", null)

        if (cursor.moveToFirst()) {
            do {
                val tarea = Tarea(
                    id_tarea = cursor.getInt(cursor.getColumnIndexOrThrow("id_tarea")),
                    titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo")),
                    descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion")),
                    fecha_creacion = cursor.getString(cursor.getColumnIndexOrThrow("fecha_creacion")),
                    completado = cursor.getInt(cursor.getColumnIndexOrThrow("completado")) == 1,
                    id_categoria = cursor.getInt(cursor.getColumnIndexOrThrow("id_categoria")),
                    id_prioridad = cursor.getInt(cursor.getColumnIndexOrThrow("id_prioridad"))
                )
                lista.add(tarea)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return lista
    }

    // Agregar una nueva tarea
    fun agregarTarea(tarea: Tarea): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("titulo", tarea.titulo)
            put("descripcion", tarea.descripcion)
            put("fecha_creacion", tarea.fecha_creacion)
            put("completado", if (tarea.completado) 1 else 0)
            put("id_categoria", tarea.id_categoria)
            put("id_prioridad", tarea.id_prioridad)
        }

        val resultado = db.insert("tarea", null, values)
        db.close()
        return resultado
    }

    // Editar una tarea existente
    fun editarTarea(tarea: Tarea): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("titulo", tarea.titulo)
            put("descripcion", tarea.descripcion)
            put("fecha_creacion", tarea.fecha_creacion)
            put("completado", if (tarea.completado) 1 else 0)
            put("id_categoria", tarea.id_categoria)
            put("id_prioridad", tarea.id_prioridad)
        }

        val filasAfectadas = db.update(
            "tarea",
            values,
            "id_tarea = ?",
            arrayOf(tarea.id_tarea.toString())
        )

        db.close()
        return filasAfectadas
    }

    // Obtener una tarea por ID
    fun obtenerTarea(idTarea: Int): Tarea? {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM tarea WHERE id_tarea = ?",
            arrayOf(idTarea.toString())
        )

        var tarea: Tarea? = null
        if (cursor.moveToFirst()) {
            tarea = Tarea(
                id_tarea = cursor.getInt(cursor.getColumnIndexOrThrow("id_tarea")),
                titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo")),
                descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion")),
                fecha_creacion = cursor.getString(cursor.getColumnIndexOrThrow("fecha_creacion")),
                completado = cursor.getInt(cursor.getColumnIndexOrThrow("completado")) == 1,
                id_categoria = cursor.getInt(cursor.getColumnIndexOrThrow("id_categoria")),
                id_prioridad = cursor.getInt(cursor.getColumnIndexOrThrow("id_prioridad"))
            )
        }

        cursor.close()
        db.close()
        return tarea
    }
}
