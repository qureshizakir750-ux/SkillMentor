package com.example.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

enum class StepState { COMPLETED, CURRENT, LOCKED, GOAL }

data class RoadmapStep(
    val title: String,
    val subtitle: String,
    val state: StepState
)

@Composable
fun CareerRoadmap(steps: List<RoadmapStep>, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Your Target Journey",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            steps.forEachIndexed { index, step ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    // Node + Line column
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.width(40.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(
                                    when (step.state) {
                                        StepState.COMPLETED -> MaterialTheme.colorScheme.primary
                                        StepState.CURRENT -> MaterialTheme.colorScheme.secondaryContainer
                                        StepState.LOCKED -> MaterialTheme.colorScheme.surface
                                        StepState.GOAL -> MaterialTheme.colorScheme.tertiary
                                    }
                                )
                                .border(
                                    width = 2.dp,
                                    color = if (step.state == StepState.CURRENT) MaterialTheme.colorScheme.primary else Color.Transparent,
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            val iconColor = when (step.state) {
                                StepState.COMPLETED -> MaterialTheme.colorScheme.onPrimary
                                StepState.CURRENT -> MaterialTheme.colorScheme.primary
                                StepState.LOCKED -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                                StepState.GOAL -> MaterialTheme.colorScheme.onTertiary
                            }
                            Icon(
                                imageVector = when (step.state) {
                                    StepState.COMPLETED -> Icons.Default.Check
                                    StepState.CURRENT -> Icons.Default.PlayArrow
                                    StepState.LOCKED -> Icons.Default.Lock
                                    StepState.GOAL -> Icons.Default.Flag
                                },
                                contentDescription = null,
                                tint = iconColor,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        if (index < steps.size - 1) {
                            val lineColor = if (step.state == StepState.COMPLETED) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                            Canvas(modifier = Modifier
                                .width(2.dp)
                                .height(56.dp)) {
                                if (step.state == StepState.COMPLETED) {
                                    drawLine(
                                        color = lineColor,
                                        start = androidx.compose.ui.geometry.Offset(size.width / 2, 0f),
                                        end = androidx.compose.ui.geometry.Offset(size.width / 2, size.height),
                                        strokeWidth = 2.dp.toPx()
                                    )
                                } else {
                                    drawLine(
                                        color = lineColor,
                                        start = androidx.compose.ui.geometry.Offset(size.width / 2, 0f),
                                        end = androidx.compose.ui.geometry.Offset(size.width / 2, size.height),
                                        strokeWidth = 2.dp.toPx(),
                                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                                    )
                                }
                            }
                        }
                    }

                    // Text column
                    Column(
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .weight(1f)
                            .padding(bottom = if (index < steps.size - 1) 32.dp else 0.dp)
                    ) {
                        Text(
                            text = step.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = if (step.state == StepState.CURRENT) FontWeight.Bold else FontWeight.Medium,
                            color = if (step.state == StepState.LOCKED) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f) else MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = step.subtitle,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = if (step.state == StepState.LOCKED) 0.6f else 1f)
                        )
                    }
                }
            }
        }
    }
}
