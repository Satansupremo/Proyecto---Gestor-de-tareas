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
                color INTEGER
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

        // Crear las tablas
        db.execSQL(createTableCategoria)
        db.execSQL(createTablePrioridad)
        db.execSQL(createTableTarea)

        // ==============================
        // INSERTAR DATOS INICIALES
        // ==============

        // CATEGORÍAS
        db.execSQL("""
            INSERT INTO categoria (denominacion, descripcion) VALUES
            ('Trabajo', 'Tareas relacionadas con el entorno laboral'),
            ('Estudio', 'Tareas académicas o de aprendizaje'),
            ('Hogar', 'Actividades domésticas y del día a día'),
            ('Personal', 'Objetivos y metas personales')
        """.trimIndent())

        // PRIORIDADES (colores en formato entero ARGB)
        db.execSQL("""
            INSERT INTO prioridad (denominacion, descripcion, color) VALUES
            ('Alta', 'Tareas urgentes o críticas', ${0xFFFF0000}),  -- Rojo
            ('Media', 'Tareas importantes pero no urgentes', ${0xFFFFFF00}),  -- Amarillo
            ('Baja', 'Tareas que pueden esperar', ${0xFF00FF00}),  -- Verde
            ('Ninguna', 'Sin prioridad definida', ${0xFFFFFFFF})  -- Blanco
        """.trimIndent())

        // TAREAS DE EJEMPLO
        db.execSQL("""
            INSERT INTO tarea (titulo, descripcion, fecha_creacion, completado, id_categoria, id_prioridad) VALUES
            ('Preparar informe mensual', 'Revisar y enviar reporte de ventas', '2025-10-15', 0, 1, 1),
            ('Estudiar Kotlin', 'Repasar clases de Android Studio', '2025-10-14', 0, 2, 2),
            ('Lavar la ropa', 'Ropa acumulada del fin de semana', '2025-10-13', 1, 3, 3),
            ('Leer libro de productividad', 'Terminar el capítulo 3', '2025-10-12', 0, 4, 4)
        """.trimIndent())
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
