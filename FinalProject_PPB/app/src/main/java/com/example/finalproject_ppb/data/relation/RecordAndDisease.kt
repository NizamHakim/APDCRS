package com.example.finalproject_ppb.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.finalproject_ppb.data.Disease
import com.example.finalproject_ppb.data.Record

data class RecordAndDisease(
    @Embedded val record: Record,
    @Relation(
        parentColumn = "diseaseId",
        entityColumn = "diseaseId"
    )
    val disease: Disease
)