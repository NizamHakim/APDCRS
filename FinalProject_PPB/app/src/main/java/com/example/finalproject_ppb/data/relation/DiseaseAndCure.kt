package com.example.finalproject_ppb.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.finalproject_ppb.data.Cure
import com.example.finalproject_ppb.data.Disease

data class DiseaseAndCure(
    @Embedded val disease: Disease,
    @Relation(
        parentColumn = "diseaseId",
        entityColumn = "diseaseId"
    )
    val cure: Cure
)
