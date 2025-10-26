package edu.pe.cibertec.proyecto_gestordetareas.ui.estadisticas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.pe.cibertec.proyecto_gestordetareas.model.TareaModel

@Composable
fun EstadisticasScreen(navController: NavHostController) {
    val context = LocalContext.current
    val tareaModel = remember { TareaModel(context) }

    var total by remember { mutableStateOf(0) }
    var completadas by remember { mutableStateOf(0) }

    // Cargar estadísticas desde SQLite
    LaunchedEffect(Unit) {
        val tareas = tareaModel.listarTareas()
        total = tareas.size
        completadas = tareas.count { it.completado }
    }

    val porcentaje = if (total > 0) completadas * 100 / total else 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text("📊 Estadísticas", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        Text("Total de tareas: $total")
        Text("Completadas: $completadas")
        Text("Pendientes: ${total - completadas}")
        Spacer(Modifier.height(16.dp))
        Text("Progreso: $porcentaje%")
        Spacer(Modifier.height(12.dp))
        LinearProgressIndicator(
            progress = porcentaje / 100f,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { navController.navigate("listartareas") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("⬅ Volver a Lista de Tareas")
        }
    }
}
