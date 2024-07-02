package com.example.finalproject_ppb.data

import android.util.Log
import com.example.finalproject_ppb.data.api.ClassifierApi
import com.example.finalproject_ppb.data.relation.DiseaseAndCure
import com.example.finalproject_ppb.data.relation.RecordAndDisease
import com.example.finalproject_ppb.data.relation.RecordDiseaseCure
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.File

class AppRepositoryImpl(
    private val dao: AppDao,
    private val classifierApi: ClassifierApi
): AppRepository {

    override suspend fun insertRecord(record: Record) {
        dao.insertRecord(record)
    }

    override suspend fun deleteRecord(record: Record) {
        dao.deleteRecord(record)
    }

    override suspend fun updateRecordName(recordId: Int, newName: String) {
        dao.updateRecordName(recordId, newName)
    }

    override fun getAllDiseases(): Flow<List<Disease>> {
        return dao.getAllDiseases()
    }

    override fun getAllRecords(): Flow<List<Record>> {
        return dao.getAllRecords()
    }

    override fun getAllDiseaseAndCure(): Flow<List<DiseaseAndCure>> {
        return dao.getAllDiseaseAndCure()
    }

    override fun getAllRecordAndDisease(): Flow<List<RecordAndDisease>> {
        return dao.getAllRecordAndDisease()
    }

    override suspend fun getDiseaseAndCureByDiseaseID(diseaseId: Int): DiseaseAndCure {
        return dao.getDiseaseAndCureByDiseaseID(diseaseId)
    }

    override suspend fun getRecordDiseaseCureByRecordID(recordId: Int): RecordDiseaseCure {
        return dao.getRecordDiseaseCureByRecordID(recordId)
    }

    override suspend fun uploadImage(file: File): Int? {
        return try {
            val response = classifierApi.uploadImage(
                image = MultipartBody.Part.createFormData(
                    "image",
                    file.name,
                    file.asRequestBody()
                )
            )
            response.body()?.result
        } catch (e: Exception){
            e.printStackTrace()
            Log.d("FAILED_TO_SEND", "Error")
            -1
        }
    }

    override suspend fun getNewlyInsertedRecord(): Record {
        return dao.getNewlyInsertedRecord()
    }
}