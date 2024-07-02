package com.example.finalproject_ppb.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cure")
data class Cure(
    @PrimaryKey(autoGenerate = true)
    val cureId: Int,
    val cureName: String,
    val cureDose: String,
    val cureImage: String,
    val diseaseId: Int
)
