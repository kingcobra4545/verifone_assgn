package com.prajwal.verifone.domain

import com.prajwal.analyticsservice.AnalyticsData
import javax.inject.Inject

class GetAnalyticsStatsUseCase @Inject constructor(private val repository: AnalyticsRepository) {
    suspend operator fun invoke(): AnalyticsData? = repository.getCurrentStats()
}
