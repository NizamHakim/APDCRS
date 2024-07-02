package com.example.finalproject_ppb.presentation.diseaselist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject_ppb.data.AppRepository
import com.example.finalproject_ppb.data.relation.DiseaseAndCure
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiseaseListViewModel @Inject constructor(
    private val repository: AppRepository
): ViewModel(){
    val diseaseAndCure = repository.getAllDiseaseAndCure()
    var disease by mutableStateOf<DiseaseAndCure?>(null)
        private set

    fun getDisease(diseaseId: Int){
        viewModelScope.launch {
            disease = repository.getDiseaseAndCureByDiseaseID(diseaseId)
        }
    }
}