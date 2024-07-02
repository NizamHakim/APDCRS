package com.example.finalproject_ppb.presentation.history

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.finalproject_ppb.R
import com.example.finalproject_ppb.ui.theme.colorNegative
import com.example.finalproject_ppb.ui.theme.colorPrimary
import com.example.finalproject_ppb.util.getActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryDetailScreen(
    viewModel: HistoryViewModel = hiltViewModel(),
    recordId: Int,
    navController: NavHostController
) {
    viewModel.getRecord(recordId)
    val record = viewModel.record
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
                ),
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.showDialog = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.DeleteForever,
                            contentDescription = "More Option",
                            tint = Color.White
                        )
                    }
                }
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
            if (record != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = record.record.recordName,
                        color = colorPrimary,
                        fontSize = 30.sp,
                        textDecoration = TextDecoration.Underline,
                        fontWeight = FontWeight.SemiBold
                    )
                    IconButton(
                        onClick = {
                            viewModel.showEditDialog = true
                            viewModel.updateUIName(record.record.recordName)
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.edit),
                            contentDescription = "Edit Icon",
                            modifier = Modifier.size(25.dp)
                        )
                    }
                }
                AsyncImage(
                    model = Uri.parse(record.record.recordImage),
                    contentDescription = "record image",
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .size(333.dp)
                        .clip(RoundedCornerShape(20.dp))
                )
                Text(
                    text = record.disease.diseaseName,
                    color = colorPrimary,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 20.dp)
                )
                Text(
                    text = record.disease.diseaseDescription,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 20.dp)
                )
                if (record.cure != null){
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
                            model = Uri.parse(record.cure.cureImage),
                            contentDescription = "cure image",
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(16.dp))
                        )
                        Column(
                            Modifier
                                .fillMaxHeight()
                                .padding(start = 20.dp)
                        ) {
                            Text(
                                text = record.cure.cureName,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = record.cure.cureDose,
                            )
                        }
                    }
                }
            }
        }
    }
    if (viewModel.showDialog) DeleteHistoryDialog(viewModel, navController)
    if (viewModel.showEditDialog) EditNameDialog(viewModel = viewModel)
}

@Composable
fun EditNameDialog(
    viewModel: HistoryViewModel
) {
    Dialog(
        onDismissRequest = {
            viewModel.showEditDialog = false
        }
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.wrapContentSize(),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(30.dp)
            ) {
                OutlinedTextField(
                    value = viewModel.newName,
                    onValueChange = {
                        viewModel.newName = it
                    },
                    label = { Text(text = "Nama") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    )
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp)
                        .height(50.dp)
                ) {
                    Button(
                        onClick = {
                            viewModel.showEditDialog = false
                            viewModel.updateRecord(viewModel.record!!.record.recordId, viewModel.newName)
                        },
                        shape = RoundedCornerShape(7.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorPrimary),
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Text(
                            text = "Update",
                            fontSize = 16.sp
                        )
                    }
                    OutlinedButton(
                        onClick = {
                            viewModel.showEditDialog = false
                        },
                        shape = RoundedCornerShape(7.dp),
                        border = BorderStroke(1.dp, Color.Gray),
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Text(
                            text = "Cancel",
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DeleteHistoryDialog(
    viewModel: HistoryViewModel,
    navController: NavHostController
){
    Dialog(
        onDismissRequest = {
            viewModel.showDialog = false
        }
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.wrapContentSize(),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(30.dp)
            ) {
                Text(
                    text = "Are you sure you want to delete ${viewModel.record!!.record.recordName}?",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "note: this process cannot be undone.",
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 10.dp)
                )
                Button(
                    onClick = {
                        viewModel.deleteRecord(viewModel.record!!.record)
                        viewModel.showDialog = false
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = colorNegative),
                    shape = RoundedCornerShape(7.dp),
                    modifier = Modifier
                        .width(280.dp)
                        .padding(top = 20.dp)
                        .height(50.dp)
                ) {
                    Text(
                        text = "Delete",
                        fontSize = 18.sp
                    )
                }
                OutlinedButton(
                    onClick = {
                        viewModel.showDialog = false
                    },
                    shape = RoundedCornerShape(7.dp),
                    border = BorderStroke(1.dp, Color.Gray),
                    modifier = Modifier
                        .width(280.dp)
                        .padding(top = 10.dp)
                        .height(50.dp)
                ) {
                    Text(
                        text = "Cancel",
                        fontSize = 18.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}