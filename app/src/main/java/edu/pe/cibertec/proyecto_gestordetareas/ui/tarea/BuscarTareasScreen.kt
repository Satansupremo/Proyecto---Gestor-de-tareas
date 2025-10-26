package edu.pe.cibertec.proyecto_gestordetareas.ui.tarea

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.pe.cibertec.proyecto_gestordetareas.entity.Tarea
import edu.pe.cibertec.proyecto_gestordetareas.model.TareaModel

@Composable
fun BuscarTareasScreen(navController: NavHostController) {
    val context = LocalContext.current
    val tareaModel = remember { TareaModel(context) }

    var query by remember { mutableStateOf("") }
    var tareas by remember { mutableStateOf(emptyList<Tarea>()) }

    // Carga inicial de tareas desde la base de datos
    LaunchedEffect(Unit) {
        tareas = tareaModel.listarTareas()
    }

    // Filtrar tareas según el texto ingresado
    val filtradas = tareas.filter {
        it.titulo.contains(query, ignoreCase = true) ||
                it.descripcion.contains(query, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Buscar tarea...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        LazyColumn {
            items(filtradas) { tarea ->
                TareaItem(
                    tarea = tarea,
                    onClick = {
                        navController.navigate("detalle_tarea/${tarea.id_tarea}")
                    },
                    onCompletadoChange = { tareaActualizada ->
                        // Actualizar el estado completado
                        tareaModel.editarTarea(tareaActualizada)
                        // Actualizar la lista filtrada
                        tareas = tareaModel.listarTareas()
                    }
                )
            }
        }
    }
}
