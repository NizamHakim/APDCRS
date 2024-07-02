package com.example.finalproject_ppb.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.finalproject_ppb.data.relation.DiseaseAndCure
import com.example.finalproject_ppb.data.relation.RecordAndDisease
import com.example.finalproject_ppb.data.relation.RecordDiseaseCure
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecord(record: Record)

    @Delete
    suspend fun deleteRecord(record: Record)

    @Query("UPDATE record SET recordName = :newName WHERE recordId = :recordId")
    suspend fun updateRecordName(recordId: Int, newName: String)

    @Query("SELECT * FROM disease")
    fun getAllDiseases(): Flow<List<Disease>>

    @Query("SELECT * FROM record")
    fun getAllRecords(): Flow<List<Record>>

    @Transaction
    @Query("""
        SELECT *
        FROM disease
        INNER JOIN cure
        ON disease.diseaseId = cure.diseaseId
    """)
    fun getAllDiseaseAndCure(): Flow<List<DiseaseAndCure>>

    @Transaction
    @Query("""
        SELECT * 
        FROM record
        INNER JOIN disease
        ON record.diseaseId = disease.diseaseId
    """)
    fun getAllRecordAndDisease(): Flow<List<RecordAndDisease>>

    @Transaction
    @Query("SELECT * FROM disease WHERE diseaseId = :diseaseId")
    suspend fun getDiseaseAndCureByDiseaseID(diseaseId: Int): DiseaseAndCure

    @Transaction
    @Query("SELECT * FROM record WHERE recordId = :recordId")
    suspend fun getRecordDiseaseCureByRecordID(recordId: Int): RecordDiseaseCure

    @Transaction
    @Query("SELECT * FROM record ORDER BY recordId DESC LIMIT 1")
    suspend fun getNewlyInsertedRecord(): Record
}