package com.example.finalproject_ppb.presentation.history

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject_ppb.data.AppRepository
import com.example.finalproject_ppb.data.Record
import com.example.finalproject_ppb.data.relation.RecordDiseaseCure
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: AppRepository
): ViewModel() {
    val records = repository.getAllRecordAndDisease()

    var record by mutableStateOf<RecordDiseaseCure?>(null)
        private set

    var showDialog by mutableStateOf(false)

    var showEditDialog by mutableStateOf(false)

    var newName by mutableStateOf("")

    fun updateUIName(it: String){
        newName = it
    }

    fun updateRecord(recordId: Int, newName: String){
        viewModelScope.launch {
            repository.updateRecordName(recordId, newName)
            record = repository.getRecordDiseaseCureByRecordID(recordId)
        }
    }

    fun getRecord(recordId: Int){
        viewModelScope.launch {
            record = repository.getRecordDiseaseCureByRecordID(recordId)
        }
    }

    fun deleteRecord(delRecord: Record){
        viewModelScope.launch {
            repository.deleteRecord(delRecord)
            record = null
        }
    }
}