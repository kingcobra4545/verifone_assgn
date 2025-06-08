package com.prajwal.verifone.domain

import com.prajwal.analyticsservice.AnalyticsData

interface AnalyticsRepository {
    suspend fun getCurrentStats(): AnalyticsData?
}