package edu.pe.cibertec.proyecto_gestordetareas.ui.tarea

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import edu.pe.cibertec.proyecto_gestordetareas.entity.Tarea

@Composable
fun TareaItem(
    tarea: Tarea,
    onClick: () -> Unit,
    onCompletadoChange: (Tarea) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ✅ Checkbox para marcar completado
            Checkbox(
                checked = tarea.completado,
                onCheckedChange = { isChecked ->
                    val tareaActualizada = tarea.copy(completado = isChecked)
                    onCompletadoChange(tareaActualizada)
                }
            )

            Spacer(Modifier.width(8.dp))

            // 📝 Información de la tarea
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = tarea.titulo,
                    style = if (tarea.completado)
                        MaterialTheme.typography.titleMedium.copy(
                            textDecoration = TextDecoration.LineThrough
                        )
                    else
                        MaterialTheme.typography.titleMedium
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = tarea.descripcion,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "📅 ${tarea.fecha_creacion}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
