package com.prajwal.verifone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.prajwal.verifone.presentation.ui.screens.AnalyticsScreen
import com.prajwal.verifone.presentation.ui.theme.VerifoneTheme
import com.prajwal.verifone.presentation.view_models.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchStats()
        enableEdgeToEdge()
        setContent {
            VerifoneTheme {
                AnalyticsScreen()
            }
        }
    }
}
