package edu.pe.cibertec.proyecto_gestordetareas.ui.prioridad

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.pe.cibertec.proyecto_gestordetareas.entity.Prioridad
import edu.pe.cibertec.proyecto_gestordetareas.model.PrioridadModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListarPrioridadesScreen(navController: NavHostController) {
    val context = LocalContext.current
    val prioridadModel = remember { PrioridadModel(context) }
    var prioridades by remember { mutableStateOf(listOf<Prioridad>()) }

    // Cargar prioridades desde SQLite
    LaunchedEffect(Unit) {
        prioridades = prioridadModel.listarPrioridades()
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Prioridades") }) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("crear_prioridad") }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar prioridad")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(12.dp)
        ) {
            if (prioridades.isEmpty()) {
                Text("No hay prioridades registradas.", modifier = Modifier.padding(8.dp))
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(prioridades) { prioridad ->
                        PrioridadRow(
                            prioridad = prioridad,
                            onClick = {
                                navController.navigate("editar_prioridad/${prioridad.id_prioridad}")
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { navController.navigate("listartareas") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("⬅ Volver")
            }
        }
    }
}

@Composable
private fun PrioridadRow(prioridad: Prioridad, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Círculo que muestra el color de la prioridad
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(prioridad.color)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = prioridad.denominacion, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = prioridad.descripcion, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
