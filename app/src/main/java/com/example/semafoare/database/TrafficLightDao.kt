package com.example.semafoare.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TrafficLightDao {
    @Query("SELECT * FROM trafficlight")
    fun getAll(): Flow<List<TrafficLight>>

    @Query("SELECT * FROM trafficlight where id = :id")
    fun get(id: Int): TrafficLight

    @Update
    fun update(trafficLight: TrafficLight)

    @Insert
    fun insert(trafficLight: TrafficLight)

    @Delete
    fun delete(trafficLight: TrafficLight)
}

@Dao
interface AlternativeDao{
    @Query("SELECT * FROM alternative")
    fun getAllAlternatives(): Flow<List<Alternative>>

    @Insert
    fun insert(alternative: Alternative)
}