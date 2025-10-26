package edu.pe.cibertec.proyecto_gestordetareas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.pe.cibertec.proyecto_gestordetareas.ui.categoria.ListarCategoriasScreen
import edu.pe.cibertec.proyecto_gestordetareas.ui.estadisticas.EstadisticasScreen
import edu.pe.cibertec.proyecto_gestordetareas.ui.prioridad.ListarPrioridadesScreen
import edu.pe.cibertec.proyecto_gestordetareas.ui.tarea.BuscarTareasScreen
import edu.pe.cibertec.proyecto_gestordetareas.ui.tarea.CrearTareaScreen
import edu.pe.cibertec.proyecto_gestordetareas.ui.tarea.ListarTareasScreen
import edu.pe.cibertec.proyecto_gestordetareas.ui.theme.ProyectoGestorDeTareasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProyectoGestorDeTareasTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "listar_tareas",
        modifier = modifier
    ) {
        composable("listar_tareas") { ListarTareasScreen(navController) }
        composable("crear_tarea") { CrearTareaScreen(navController) }

        // ⚠️ ESTA RUTA ES LA QUE FALTABA
        composable("editar_tarea/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toInt() ?: 0
            CrearTareaScreen(navController, idTarea = id)
        }

        composable("buscar_tareas") { BuscarTareasScreen(navController) }
        composable("estadisticas") { EstadisticasScreen(navController) }
        composable("listar_categorias") { ListarCategoriasScreen(navController) }
        composable("listar_prioridades") { ListarPrioridadesScreen(navController) }
    }
}
