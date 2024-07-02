package com.example.finalproject_ppb

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.finalproject_ppb.navigation.Navigation
import com.example.finalproject_ppb.presentation.camera.CameraScreen
import com.example.finalproject_ppb.presentation.camera.NavigationDrawer
import com.example.finalproject_ppb.presentation.diseaselist.DiseaseListScreen
import com.example.finalproject_ppb.presentation.history.HistoryDetailScreen
import com.example.finalproject_ppb.ui.theme.FinalProject_PPBTheme
import dagger.hilt.android.AndroidEntryPoint
import java.io.InputStream

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (!hasRequiredPermissions()){
            ActivityCompat.requestPermissions(this, CAMERAX_PERMISSIONS, 0)
        }
        setContent {
            FinalProject_PPBTheme {
                MainApp()
            }
        }
    }

    private fun hasRequiredPermissions(): Boolean{
        val status = CAMERAX_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(applicationContext, it) == PackageManager.PERMISSION_GRANTED
        }
        return status
    }

    companion object{
        private val CAMERAX_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA
        )
    }
}

@Composable
fun MainApp() {
//    val navController = rememberNavController()
//    HistoryDetailScreen(recordId = 0, navController = navController)
    Navigation()
}