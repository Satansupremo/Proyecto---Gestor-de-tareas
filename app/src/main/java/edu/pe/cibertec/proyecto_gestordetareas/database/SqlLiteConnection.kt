package edu.pe.cibertec.proyecto_gestordetareas.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqlLiteConnection(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {

        // Tabla CATEGORIA
        val createTableCategoria = """
            CREATE TABLE categoria (
                id_categoria INTEGER PRIMARY KEY AUTOINCREMENT,
                denominacion TEXT NOT NULL,
                descripcion TEXT
            )
        """.trimIndent()

        // Tabla PRIORIDAD
        val createTablePrioridad = """
            CREATE TABLE prioridad (
                id_prioridad INTEGER PRIMARY KEY AUTOINCREMENT,
                denominacion TEXT NOT NULL,
                descripcion TEXT,
                color TEXT
            )
        """.trimIndent()

        // Tabla TAREA
        val createTableTarea = """
            CREATE TABLE tarea (
                id_tarea INTEGER PRIMARY KEY AUTOINCREMENT,
                titulo TEXT NOT NULL,
                descripcion TEXT,
                fecha_creacion TEXT,
                completado INTEGER DEFAULT 0,
                id_categoria INTEGER,
                id_prioridad INTEGER,
                FOREIGN KEY(id_categoria) REFERENCES categoria(id_categoria),
                FOREIGN KEY(id_prioridad) REFERENCES prioridad(id_prioridad)
            )
        """.trimIndent()

        // Ejecutar creación de tablas
        db.execSQL(createTableCategoria)
        db.execSQL(createTablePrioridad)
        db.execSQL(createTableTarea)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS tarea")
        db.execSQL("DROP TABLE IF EXISTS categoria")
        db.execSQL("DROP TABLE IF EXISTS prioridad")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "gestor_tareas.db"
        private const val DATABASE_VERSION = 1
    }
}
