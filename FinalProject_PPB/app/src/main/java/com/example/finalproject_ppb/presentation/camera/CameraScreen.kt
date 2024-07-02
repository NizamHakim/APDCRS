package com.example.finalproject_ppb.presentation.camera

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.TorchState
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.example.finalproject_ppb.R
import com.example.finalproject_ppb.navigation.ScreenHistoryDetails
import com.example.finalproject_ppb.ui.theme.colorPrimary
import com.example.finalproject_ppb.util.bitmapToFile
import com.example.finalproject_ppb.util.centerCrop
import com.example.finalproject_ppb.util.contentUriToBitmap
import kotlinx.coroutines.launch


@Composable
fun CameraScreen(
    drawerState: DrawerState,
    navController: NavHostController,
    viewModel: CameraViewModel = hiltViewModel()
){
    val context = LocalContext.current
    val cameraController = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_CAPTURE)
        }
    }

    // TorchState
    val torchState = remember { mutableIntStateOf(TorchState.OFF) }
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver{ _, event ->
            if (event == Lifecycle.Event.ON_RESUME){
                torchState.intValue = TorchState.OFF
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    // Drawer State
    val scope = rememberCoroutineScope()

    // Photo Picker
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { contentUri ->
            if (contentUri != null){
                viewModel.capturedBitmap = contentUriToBitmap(context, contentUri)
                viewModel.showDialog = true
            }
        }
    )

    // Screen
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CameraPreview(controller = cameraController)
        Image(
            painter = painterResource(id = R.drawable.viewfinder),
            contentDescription = "viewfinder",
            colorFilter = ColorFilter.tint(colorPrimary),
            modifier = Modifier
                .size(250.dp)
                .align(Alignment.Center)
                .offset(y = (-80).dp)
        )
        IconButton(
            onClick = {
                scope.launch {
                    drawerState.open()
                }
            },
            modifier = Modifier.offset(10.dp, 30.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "side menu",
                tint = Color.White
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 70.dp)
        ) {
            IconButton(
                onClick = {
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.browsegallery),
                    contentDescription = "browseGallery",
                    modifier = Modifier.size(30.dp),
                    tint = Color.White
                )
            }
            OutlinedButton(
                onClick = {
                    scope.launch {
                        takePhoto(context, cameraController){
                            viewModel.onPhotoTaken(it)
                            viewModel.showDialog = true
                        }
                    }
                },
                shape = CircleShape,
                modifier = Modifier.size(70.dp),
                border = BorderStroke(3.dp, colorPrimary),
                contentPadding = PaddingValues(0.dp)
            ) {
                Canvas(
                    modifier = Modifier.size(60.dp)
                ) {
                    drawCircle(
                        color = colorPrimary,
                    )
                }
            }
            IconButton(
                onClick = {
                    if (cameraController.torchState.value == TorchState.OFF){
                        cameraController.enableTorch(true)
                        torchState.intValue = TorchState.ON
                    }else{
                        cameraController.enableTorch(false)
                        torchState.intValue = TorchState.OFF
                    }
                }
            ) {
                if (torchState.intValue == TorchState.OFF){
                    Icon(
                        painter = painterResource(id = R.drawable.flashon),
                        contentDescription = "flash on",
                        modifier = Modifier.size(30.dp),
                        tint = Color.White
                    )
                }else{
                    Icon(
                        painter = painterResource(id = R.drawable.flashoff),
                        contentDescription = "flash off",
                        modifier = Modifier.size(30.dp),
                        tint = Color.White
                    )
                }
            }
        }
        if (viewModel.showDialog) ImageCapturedDialog(viewModel = viewModel, navController = navController)
        if (viewModel.sending){
            Surface(
                color = Color.Black.copy(0.6F),
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(50.dp),
                        color = Color.White,
                    )
                    Text(
                        text = "Please Wait...",
                        color = Color.White,
                        modifier = Modifier.padding(top = 15.dp)
                    )
                }
            }
        }
        if (viewModel.errorDialog) ErrorDialog(viewModel = viewModel)
    }
}

@Composable
fun ImageCapturedDialog(
    viewModel: CameraViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(20.dp)
            ) {
                viewModel.capturedBitmap?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = "Capture Image",
                        modifier = Modifier
                            .size(280.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                }
                Button(
                    onClick = {
                        viewModel.capturedBitmap?.let {
                            viewModel.showDialog = false
                            val file = bitmapToFile(it, context)
                            viewModel.sending = true
                            viewModel.sendSelectedImage(file){ success ->
                                viewModel.sending = false
                                if (success){
                                    viewModel.newlyInsertedRecord?.let {record ->
                                        navController.navigate(ScreenHistoryDetails(recordId = record.recordId))
                                    }
                                } else {
                                    viewModel.errorDialog = true
                                }
                            }
                        }
                    },
                    shape = RoundedCornerShape(7.dp),
                    modifier = Modifier
                        .width(280.dp)
                        .height(60.dp)
                        .padding(top = 10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorPrimary)
                ) {
                    Text(
                        text = "Process Image",
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
                        .height(50.dp)
                        .padding(top = 5.dp)
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

@Composable
fun ErrorDialog(
    viewModel: CameraViewModel
) {
    Dialog(
        onDismissRequest = {
            viewModel.errorDialog = false
        }
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.wrapContentSize(),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(20.dp)
            ) {
                Text(text = "Sorry object not recognized.")
                Text(text = "Please try again.")
                Button(
                    onClick = {
                        viewModel.errorDialog = false
                    },
                    shape = RoundedCornerShape(7.dp),
                    modifier = Modifier
                        .width(280.dp)
                        .height(60.dp)
                        .padding(top = 10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorPrimary)
                ) {
                    Text(
                        text = "OK",
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

private fun takePhoto(
    context: Context,
    cameraController: LifecycleCameraController,
    onPhotoTaken: (Bitmap) -> Unit
){
    cameraController.takePicture(
        ContextCompat.getMainExecutor(context),
        object: OnImageCapturedCallback(){
            override fun onCaptureSuccess(image: ImageProxy) {
                super.onCaptureSuccess(image)

                val matrix = Matrix().apply {
                    postRotate(image.imageInfo.rotationDegrees.toFloat())
                }
                val rotatedBitmap = Bitmap.createBitmap(
                    image.toBitmap(),
                    0,
                    0,
                    image.width,
                    image.height,
                    matrix,
                    true
                )
                onPhotoTaken(rotatedBitmap.centerCrop())
                image.close()
            }

            override fun onError(exception: ImageCaptureException) {
                super.onError(exception)
                Log.e("CAMERA CAPTURE ERROR", "Failed taking photo")
            }
        }
    )
}