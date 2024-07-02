package com.example.finalproject_ppb.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "disease")
data class Disease(
    @PrimaryKey(autoGenerate = false)
    val diseaseId: Int,
    val diseaseName: String,
    val diseaseDescription: String,
    val diseaseImage: String
)
