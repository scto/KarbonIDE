package com.rk.xededitor.ui.screens.project.view


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.rk.xededitor.R
import org.robok.engine.core.components.compose.preferences.base.PreferenceGroup
import org.robok.engine.core.components.compose.preferences.base.PreferenceLayout
import org.robok.engine.core.components.compose.preferences.base.PreferenceTemplate
import java.io.File


@Composable
fun ProjectView(modifier: Modifier = Modifier) {
    val projectDir = File(LocalContext.current.filesDir.parentFile, "projects")
    if (!projectDir.exists()) {
        projectDir.mkdirs()
    }
    
    // Scaffold to structure the layout
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    // Action for FAB click, e.g., create a new project
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                },
                text = {
                    Text(text = "Create Project")
                }
            )
        }
    ) { innerPadding ->
        PreferenceLayout(
            label = stringResource(R.string.app_name),
            backArrowVisible = true,
            modifier = Modifier.padding(innerPadding)
        ) {
            val projects = projectDir.listFiles() ?: emptyArray()
            if (projects.isEmpty()) {
                for (i in 0 until 8) {
                    File(projectDir, "project$i").mkdir()
                }
                // NoContentScreen(label = "No Projects")
            }
            PreferenceGroup(heading = "projects") {
                projects.forEach { project ->
                    if (project.isProject()) {
                        ProjectRow(project = project)
                    }
                }
            }
        }
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun File.isProject(): Boolean {
    return isDirectory // and listFiles() != null
}

@Composable
fun ProjectRow(modifier: Modifier = Modifier, project: File, onClick: () -> Unit = {}) {
    val icon = File(project, "icon.png")
    
    PreferenceTemplate(
        title = { Text(text = project.name, style = MaterialTheme.typography.titleMedium) },
        description = { Text(text = project.path, style = MaterialTheme.typography.titleSmall) },
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onClick() }),
        startWidget = {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                AsyncImage(
                    model = if (icon.exists()) {
                        icon.toUri()
                    } else {
                        R.drawable.android_green
                    },
                    contentDescription = null,
                    modifier = Modifier
                        .size(45.dp)
                        .padding(4.dp),
                    contentScale = ContentScale.Crop,
                )
            }
        }
    )
}
