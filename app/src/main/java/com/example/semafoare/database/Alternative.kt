package com.example.semafoare.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Alternative(
    @PrimaryKey(autoGenerate = true) val alternativeId: Long,
    val alternativeTitle: String
)
