package edu.pe.cibertec.proyecto_gestordetareas.ui.tarea

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import edu.pe.cibertec.proyecto_gestordetareas.entity.Tarea

@Composable
fun TareaItem(
    tarea: Tarea,
    onClick: () -> Unit,
    onCompletadoChange: (Tarea) -> Unit,
    prioridadColor: Color = Color.Gray
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = prioridadColor.copy(alpha = 0.2f), // color de fondo suave
            contentColor = MaterialTheme.colorScheme.onSurface // para que el texto sea legible
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = tarea.completado,
                onCheckedChange = {
                    val tareaActualizada = tarea.copy(completado = it)
                    onCompletadoChange(tareaActualizada)
                }
            )

            Spacer(Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    tarea.titulo,
                    style = if (tarea.completado)
                        MaterialTheme.typography.titleMedium.copy(textDecoration = TextDecoration.LineThrough)
                    else
                        MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    tarea.descripcion,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "📅 ${tarea.fecha_creacion}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
