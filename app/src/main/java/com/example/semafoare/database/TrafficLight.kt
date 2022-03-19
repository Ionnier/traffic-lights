package com.example.semafoare.database

import androidx.room.*
import java.io.Serializable
import java.util.*

@Entity
data class TrafficLight(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name="id") var trafficLightId: Int?,
    @ColumnInfo(name = "latitude") var latitude: Double?,
    @ColumnInfo(name = "longitude") var longitude: Double?,
    @Embedded var alternative: Alternative?,
    @ColumnInfo(name = "red_color") var redColor: Boolean,
    @ColumnInfo(name = "created_time", defaultValue = "CURRENT_TIMESTAMP") var createdTime: Date?
):Serializable
