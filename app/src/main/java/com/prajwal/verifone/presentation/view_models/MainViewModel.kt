package com.prajwal.verifone.presentation.view_models

import android.os.RemoteException
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prajwal.analyticsservice.AnalyticsData
import com.prajwal.verifone.domain.GetAnalyticsStatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAnalyticsStatsUseCase: GetAnalyticsStatsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<AnalyticsData?>(null)
    val uiState: StateFlow<AnalyticsData?> = _uiState.asStateFlow()

    fun fetchStats() {
        viewModelScope.launch {
            try {
                _uiState.value = getAnalyticsStatsUseCase.invoke()
            } catch (e: RemoteException) {
                Log.e("ViewModel", "RemoteException: ${e.message}", e)
            } catch (e: SecurityException) {
                Log.e("ViewModel", "SecurityException: ${e.message}", e)
            } catch (e: IllegalStateException) {
                Log.e("ViewModel", "IllegalStateException: ${e.message}", e)
            }
        }
    }
}
