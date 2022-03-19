package com.example.semafoare

import android.app.Application
import com.example.semafoare.database.AppDatabase
import com.example.semafoare.database.TrafficLightRepository
import timber.log.Timber

class TrafficLightApplication: Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { TrafficLightRepository(database.trafficLightDao(), database.alternativeDao()) }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

}