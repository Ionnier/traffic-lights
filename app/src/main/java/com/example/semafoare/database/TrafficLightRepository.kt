package com.example.semafoare.database

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class TrafficLightRepository(private val trafficLightDao: TrafficLightDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allData: Flow<List<TrafficLight>> = trafficLightDao.getAll()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(trafficLight: TrafficLight) {
        trafficLightDao.insert(trafficLight)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(trafficLight: TrafficLight) {
        trafficLightDao.delete(trafficLight)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(trafficLight: TrafficLight) {
        trafficLightDao.update(trafficLight)
    }
}
