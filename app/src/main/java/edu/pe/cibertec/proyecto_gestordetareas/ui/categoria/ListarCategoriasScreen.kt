package edu.pe.cibertec.proyecto_gestordetareas.ui.categoria

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.pe.cibertec.proyecto_gestordetareas.entity.Categoria
import edu.pe.cibertec.proyecto_gestordetareas.model.CategoriaModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListarCategoriasScreen(navController: NavHostController) {
    val context = LocalContext.current
    val categoriaModel = remember { CategoriaModel(context) }
    var categorias by remember { mutableStateOf(listOf<Categoria>()) }

    // Cargar categorías desde SQLite
    LaunchedEffect(Unit) {
        categorias = categoriaModel.listarCategorias()
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Categorías") }) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("crear_categoria") }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar categoría")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(12.dp)
        ) {
            if (categorias.isEmpty()) {
                Text("No hay categorías registradas.", modifier = Modifier.padding(8.dp))
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(categorias) { categoria ->
                        CategoriaRow(
                            categoria = categoria,
                            onClick = {
                                navController.navigate("editar_categoria/${categoria.id_categoria}")
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
private fun CategoriaRow(categoria: Categoria, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = categoria.denominacion, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = categoria.descripcion, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
