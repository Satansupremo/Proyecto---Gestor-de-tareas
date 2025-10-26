package edu.pe.cibertec.proyecto_gestordetareas.ui.tarea

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.pe.cibertec.proyecto_gestordetareas.entity.Tarea
import edu.pe.cibertec.proyecto_gestordetareas.model.PrioridadModel
import edu.pe.cibertec.proyecto_gestordetareas.model.TareaModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListarTareasScreen(navController: NavHostController) {
    val context = LocalContext.current
    val tareaModel = remember { TareaModel(context) }
    val prioridadModel = remember { PrioridadModel(context) }

    var tareas by remember { mutableStateOf(emptyList<Tarea>()) }
    val prioridades = remember { prioridadModel.listarPrioridades() }

    // Filtros
    var query by remember { mutableStateOf("") }
    var prioridadSeleccionada by remember { mutableStateOf<String?>(null) }
    var expandedPrioridad by remember { mutableStateOf(false) }

    var mostrarCompletadas by remember { mutableStateOf(true) }
    var mostrarNoCompletadas by remember { mutableStateOf(true) }

    // Cargar todas las tareas al iniciar
    LaunchedEffect(Unit) {
        tareas = tareaModel.listarTareas()
    }

    // Filtrar tareas por texto, prioridad y estado completado
    val tareasFiltradas = tareas.filter { tarea ->
        val coincideTexto = tarea.titulo.contains(query, ignoreCase = true) ||
                tarea.descripcion.contains(query, ignoreCase = true)
        val coincidePrioridad = prioridadSeleccionada?.let { sel ->
            val prioridadTarea = prioridades.find { it.id_prioridad == tarea.id_prioridad }?.denominacion
            prioridadTarea == sel
        } ?: true
        val coincideCompletado = (tarea.completado && mostrarCompletadas) ||
                (!tarea.completado && mostrarNoCompletadas)
        coincideTexto && coincidePrioridad && coincideCompletado
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Mis tareas") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("crear_tarea") }) {
                Text("+")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(12.dp)
        ) {
            // Input de búsqueda
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Buscar tarea") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            // Dropdown de prioridades
            ExposedDropdownMenuBox(
                expanded = expandedPrioridad,
                onExpandedChange = { expandedPrioridad = !expandedPrioridad }
            ) {
                OutlinedTextField(
                    value = prioridadSeleccionada ?: "Todas",
                    onValueChange = {},
                    label = { Text("Filtrar por prioridad") },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expandedPrioridad,
                    onDismissRequest = { expandedPrioridad = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Todas") },
                        onClick = {
                            prioridadSeleccionada = null
                            expandedPrioridad = false
                        }
                    )
                    prioridades.forEach { prioridad ->
                        DropdownMenuItem(
                            text = { Text(prioridad.denominacion) },
                            onClick = {
                                prioridadSeleccionada = prioridad.denominacion
                                expandedPrioridad = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            // Checkbox para completadas / no completadas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = mostrarCompletadas,
                        onCheckedChange = { mostrarCompletadas = it }
                    )
                    Text("Completadas")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = mostrarNoCompletadas,
                        onCheckedChange = { mostrarNoCompletadas = it }
                    )
                    Text("No completadas")
                }
            }

            Spacer(Modifier.height(12.dp))

            // Lista de tareas filtradas
            LazyColumn {
                items(tareasFiltradas) { tarea ->
                    // Buscar color de la prioridad correspondiente
                    val colorPrioridad = prioridades.find { it.id_prioridad == tarea.id_prioridad }?.color ?: MaterialTheme.colorScheme.surfaceVariant

                    TareaItem(
                        tarea = tarea,
                        onClick = { navController.navigate("editar_tarea/${tarea.id_tarea}") },
                        onCompletadoChange = { tareaActualizada ->
                            tareaModel.editarTarea(tareaActualizada)
                            tareas = tareaModel.listarTareas()
                        },
                        prioridadColor = colorPrioridad // <-- pasamos el color
                    )
                }
            }
        }
    }
}
