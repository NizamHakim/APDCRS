package com.example.finalproject_ppb.presentation.camera

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject_ppb.data.AppRepository
import com.example.finalproject_ppb.data.Record
import com.example.finalproject_ppb.data.relation.RecordDiseaseCure
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val repository: AppRepository
): ViewModel() {

    var showDialog by mutableStateOf(false)

    var errorDialog by mutableStateOf(false)

    var capturedBitmap by mutableStateOf<Bitmap?>(null)

    var newlyInsertedRecord by mutableStateOf<Record?>(null)

    var sending by mutableStateOf(false)

    fun onPhotoTaken(imageBitmap: Bitmap){
        capturedBitmap = imageBitmap
    }

    fun sendSelectedImage(file: File, callback: (Boolean) -> Unit){
        viewModelScope.launch {
            val response = repository.uploadImage(file)
            val datetime = file.name.removeSuffix(".jpg").replace('_', ':')
            if (response != -1){
                val record = Record(0, "Untitled", datetime, file.toURI().toString(), response!!)
                repository.insertRecord(record)
                newlyInsertedRecord = repository.getNewlyInsertedRecord()
                callback(true)
            }else{
                Log.e("FAILURE", "IMAGE NOT SENT")
                callback(false)
            }
        }
    }
}