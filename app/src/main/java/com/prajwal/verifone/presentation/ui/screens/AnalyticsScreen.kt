package com.prajwal.verifone.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.prajwal.verifone.presentation.view_models.MainViewModel

@Composable
fun AnalyticsScreen(viewModel: MainViewModel = hiltViewModel()) {
    val statsState = viewModel.uiState.collectAsState()
    val stats = statsState.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        if (stats != null) {
            Card(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "System Stats",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = "🔋 Battery: ${stats.batteryLevel}%",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "🧠 CPU Usage: ${stats.cpuUsage}%",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "📦 Memory: ${stats.memoryUsageMB} MB",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        } else {
            CircularProgressIndicator()
        }
    }
}

