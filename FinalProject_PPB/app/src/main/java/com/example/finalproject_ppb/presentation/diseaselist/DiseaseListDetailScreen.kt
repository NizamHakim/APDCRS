package com.example.finalproject_ppb.presentation.diseaselist

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.finalproject_ppb.ui.theme.colorPrimary
import com.example.finalproject_ppb.util.getActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiseaseListDetailScreen(
    viewModel: DiseaseListViewModel = hiltViewModel(),
    navController: NavHostController,
    diseaseId: Int
) {
    viewModel.getDisease(diseaseId)
    val disease = viewModel.disease
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (!navController.popBackStack()) context.getActivity()?.finish()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = colorPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 25.dp, vertical = 10.dp)
                .verticalScroll(scrollState)
        ) {
            if (disease != null) {
                AsyncImage(
                    model = Uri.parse(disease.disease.diseaseImage),
                    contentDescription = "record image",
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .size(333.dp)
                        .clip(RoundedCornerShape(20.dp))
                )
                Text(
                    text = disease.disease.diseaseName,
                    color = colorPrimary,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 20.dp)
                )
                Text(
                    text = disease.disease.diseaseDescription,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 20.dp)
                )
                Text(
                    text = "Rekomendasi Obat",
                    color = colorPrimary,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 20.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.padding(top = 20.dp)
                ) {
                    AsyncImage(
                        model = Uri.parse(disease.cure.cureImage),
                        contentDescription = "cure image",
                        modifier = Modifier.size(100.dp).clip(RoundedCornerShape(16.dp))
                    )
                    Column(
                        Modifier.fillMaxHeight().padding(start = 20.dp)
                    ) {
                        Text(
                            text = disease.cure.cureName,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = disease.cure.cureDose,
                        )
                    }
                }
            }
        }
    }
}