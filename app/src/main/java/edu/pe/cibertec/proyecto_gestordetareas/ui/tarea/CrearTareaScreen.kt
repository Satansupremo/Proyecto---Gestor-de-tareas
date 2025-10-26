package edu.pe.cibertec.proyecto_gestordetareas.ui.tarea

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.pe.cibertec.proyecto_gestordetareas.entity.Tarea
import edu.pe.cibertec.proyecto_gestordetareas.model.CategoriaModel
import edu.pe.cibertec.proyecto_gestordetareas.model.PrioridadModel
import edu.pe.cibertec.proyecto_gestordetareas.model.TareaModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearTareaScreen(navController: NavHostController, idTarea: Int? = null) {
    val context = LocalContext.current
    val tareaModel = remember { TareaModel(context) }
    val categoriaModel = remember { CategoriaModel(context) }
    val prioridadModel = remember { PrioridadModel(context) }

    // Cargar datos base
    val categorias = remember { categoriaModel.listarCategorias() }
    val prioridades = remember { prioridadModel.listarPrioridades() }

    // Estados del formulario
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var categoriaSeleccionada by remember { mutableStateOf(categorias.firstOrNull()) }
    var prioridadSeleccionada by remember { mutableStateOf(prioridades.firstOrNull()) }

    // Estados de los menús desplegables
    var expandedCategoria by remember { mutableStateOf(false) }
    var expandedPrioridad by remember { mutableStateOf(false) }

    // Si idTarea no es nulo, estamos editando
    LaunchedEffect(idTarea) {
        if (idTarea != null) {
            val tarea = tareaModel.obtenerTarea(idTarea)
            tarea?.let {
                titulo = it.titulo
                descripcion = it.descripcion
                categoriaSeleccionada = categorias.find { c -> c.id_categoria == it.id_categoria }
                prioridadSeleccionada = prioridades.find { p -> p.id_prioridad == it.id_prioridad }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (idTarea == null) "Nueva Tarea" else "Editar Tarea")
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            // --- Selección de Categoría ---
            ExposedDropdownMenuBox(
                expanded = expandedCategoria,
                onExpandedChange = { expandedCategoria = !expandedCategoria }
            ) {
                OutlinedTextField(
                    value = categoriaSeleccionada?.denominacion ?: "",
                    onValueChange = {},
                    label = { Text("Categoría") },
                    readOnly = true,
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expandedCategoria,
                    onDismissRequest = { expandedCategoria = false }
                ) {
                    categorias.forEach { categoria ->
                        DropdownMenuItem(
                            text = { Text(categoria.denominacion) },
                            onClick = {
                                categoriaSeleccionada = categoria
                                expandedCategoria = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            // --- Selección de Prioridad ---
            ExposedDropdownMenuBox(
                expanded = expandedPrioridad,
                onExpandedChange = { expandedPrioridad = !expandedPrioridad }
            ) {
                OutlinedTextField(
                    value = prioridadSeleccionada?.denominacion ?: "",
                    onValueChange = {},
                    label = { Text("Prioridad") },
                    readOnly = true,
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expandedPrioridad,
                    onDismissRequest = { expandedPrioridad = false }
                ) {
                    prioridades.forEach { prioridad ->
                        DropdownMenuItem(
                            text = { Text(prioridad.denominacion) },
                            onClick = {
                                prioridadSeleccionada = prioridad
                                expandedPrioridad = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = {
                    navController.popBackStack()
                }) {
                    Text("Cancelar")
                }

                Button(onClick = {
                    val fecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

                    if (idTarea == null) {
                        // Crear nueva tarea
                        val nuevaTarea = Tarea(
                            titulo = titulo,
                            descripcion = descripcion,
                            fecha_creacion = fecha,
                            id_categoria = categoriaSeleccionada?.id_categoria ?: 0,
                            id_prioridad = prioridadSeleccionada?.id_prioridad ?: 0
                        )
                        tareaModel.agregarTarea(nuevaTarea)
                    } else {
                        // Actualizar tarea existente
                        val tareaActualizada = Tarea(
                            id_tarea = idTarea,
                            titulo = titulo,
                            descripcion = descripcion,
                            fecha_creacion = fecha,
                            id_categoria = categoriaSeleccionada?.id_categoria ?: 0,
                            id_prioridad = prioridadSeleccionada?.id_prioridad ?: 0
                        )
                        tareaModel.editarTarea(tareaActualizada)
                    }

                    navController.navigate("listar_tareas") {
                        popUpTo("listar_tareas") { inclusive = true }
                    }
                }) {
                    Text(if (idTarea == null) "Guardar" else "Actualizar")
                }
            }
        }
    }
}
