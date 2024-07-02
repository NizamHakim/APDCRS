package com.example.finalproject_ppb.data

import com.example.finalproject_ppb.data.relation.DiseaseAndCure
import com.example.finalproject_ppb.data.relation.RecordAndDisease
import com.example.finalproject_ppb.data.relation.RecordDiseaseCure
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import java.io.File

interface AppRepository {

    suspend fun insertRecord(record: Record)

    suspend fun deleteRecord(record: Record)

    suspend fun updateRecordName(recordId: Int, newName: String)

    fun getAllDiseases(): Flow<List<Disease>>

    fun getAllRecords(): Flow<List<Record>>

    fun getAllDiseaseAndCure(): Flow<List<DiseaseAndCure>>

    fun getAllRecordAndDisease(): Flow<List<RecordAndDisease>>

    suspend fun getDiseaseAndCureByDiseaseID(diseaseId: Int): DiseaseAndCure

    suspend fun getRecordDiseaseCureByRecordID(recordId: Int): RecordDiseaseCure

    suspend fun uploadImage(file: File): Int?

    suspend fun getNewlyInsertedRecord(): Record
}