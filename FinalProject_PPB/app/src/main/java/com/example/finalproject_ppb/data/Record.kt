package com.example.finalproject_ppb.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "record")
data class Record(
    @PrimaryKey(autoGenerate = true)
    val recordId: Int,
    val recordName: String,
    val recordDate: String,
    val recordImage: String,
    val diseaseId: Int
)
