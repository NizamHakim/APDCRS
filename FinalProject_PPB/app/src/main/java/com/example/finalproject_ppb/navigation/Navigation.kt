package com.example.finalproject_ppb.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.finalproject_ppb.presentation.camera.NavigationDrawer
import com.example.finalproject_ppb.presentation.diseaselist.DiseaseListDetailScreen
import com.example.finalproject_ppb.presentation.diseaselist.DiseaseListScreen
import com.example.finalproject_ppb.presentation.history.HistoryDetailScreen
import com.example.finalproject_ppb.presentation.history.HistoryScreen
import kotlinx.serialization.Serializable

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ScreenCamera) {
        composable<ScreenCamera>{
            NavigationDrawer(navController = navController)
        }
        composable<ScreenDiseaseList> {
            DiseaseListScreen(navController = navController)
        }
        composable<ScreenHistory> { 
            HistoryScreen(navController = navController)
        }
        composable<ScreenHistoryDetails> {
            val recordId = it.toRoute<ScreenHistoryDetails>().recordId
            HistoryDetailScreen(recordId = recordId, navController = navController)
        }
        composable<ScreenDiseaseListDetails> {
            val diseaseId = it.toRoute<ScreenDiseaseListDetails>().diseaseId
            DiseaseListDetailScreen(navController = navController, diseaseId = diseaseId)
        }
    }
}

@Serializable
object ScreenCamera

@Serializable
object ScreenDiseaseList

@Serializable
object ScreenHistory

@Serializable
data class ScreenHistoryDetails(
    val recordId: Int
)

@Serializable
data class ScreenDiseaseListDetails(
    val diseaseId: Int
)