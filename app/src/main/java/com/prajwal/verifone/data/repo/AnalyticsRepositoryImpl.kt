package com.prajwal.verifone.data.repo

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.RemoteException
import android.widget.Toast
import com.prajwal.analyticsservice.AnalyticsData
import com.prajwal.analyticsservice.IAnalyticsService
import com.prajwal.verifone.BuildConfig
import com.prajwal.verifone.domain.AnalyticsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

//Expected Deliverables
//3. Foreground client binder implementation.
class AnalyticsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AnalyticsRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getCurrentStats(): AnalyticsData? = withContext(Dispatchers.IO) {
        val intent = Intent().apply {
            setClassName(
                BuildConfig.ANALYTICS_PACKAGE,
                BuildConfig.ANALYTICS_SERVICE_CLASS
            )
        }

        try {
            suspendCancellableCoroutine { cont ->
                val connection = object : ServiceConnection {
                    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                        val aidl = IAnalyticsService.Stub.asInterface(service)
                        try {
                            val stats = aidl.currentStats
                            cont.resume(
                                AnalyticsData(
                                    batteryLevel = stats.batteryLevel,
                                    cpuUsage = stats.cpuUsage,
                                    memoryUsageMB = stats.memoryUsageMB
                                )
                            )
                        } catch (s: SecurityException) {
                            cont.resumeWithException(s)
                        } catch (e: RemoteException) {
                            cont.resumeWithException(e)
                        }
                    }

                    override fun onServiceDisconnected(name: ComponentName?) {
                        Handler(Looper.getMainLooper()).post {
                            Toast.makeText(context, "Analytics service disconnected unexpectedly", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
            }
        } catch (e: SecurityException) {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    "Permission denied to access Analytics Service",
                    Toast.LENGTH_LONG
                ).show()
            }
            null
        } catch (e: RemoteException) {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    "Failed to connect to Analytics Service",
                    Toast.LENGTH_LONG
                ).show()
            }
            null
        }
    }

}
