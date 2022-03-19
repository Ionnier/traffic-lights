package com.example.semafoare.database

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class TrafficLightRepository(private val trafficLightDao: TrafficLightDao, private val alternativeDao: AlternativeDao) {

    val allData: Flow<List<TrafficLight>> = trafficLightDao.getAll()
    val alternatives: Flow<List<Alternative>> = alternativeDao.getAllAlternatives()


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(trafficLight: TrafficLight) {
        trafficLightDao.insert(trafficLight)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAlternative(alternative: Alternative) {
        alternativeDao.insert(alternative)
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
