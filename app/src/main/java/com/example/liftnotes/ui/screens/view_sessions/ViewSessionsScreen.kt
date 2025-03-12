package com.example.liftnotes.ui.screens.view_sessions

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.liftnotes.R
import com.example.liftnotes.model.CurrentSession
import com.example.liftnotes.ui.common.CompletionTracker
import com.example.liftnotes.ui.common.FloatingAddButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewSessionsScreen(
    navController: NavController,
    viewModel: ViewSessionsViewModel = viewModel(factory = ViewSessionsViewModel.provideFactory())
) {
    val view = LocalView.current
    val currentSessions by viewModel.currentSessions.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                title = {
                    Text("Top app bar")
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Bottom app bar",
                )
            }
        },
        floatingActionButton = {
            FloatingAddButton(onClick = {})
        },

    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(currentSessions) {
                SessionCard(it)
            }
        }
    }
}

@Composable
private fun SessionCard(
    currentSession: CurrentSession
) {
    val session = currentSession.session
    Card (
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 8.dp)
        ) {
            Image(
                painter = painterResource(session.imageId),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                modifier = Modifier
                    .padding(8.dp)
                    .size(32.dp)
            )
            Column(
                Modifier.weight(0.6f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = session.name,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium
                )
                session.description?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            CompletionTracker(
                currentSession.completionDays,
                Modifier.weight(0.4f)
            )
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_reorder),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(8.dp)
                    .size(32.dp)
            )
        }
    }
}