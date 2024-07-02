package com.example.finalproject_ppb.presentation.camera

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Yard
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.finalproject_ppb.navigation.ScreenDiseaseList
import com.example.finalproject_ppb.navigation.ScreenHistory
import com.example.finalproject_ppb.ui.theme.colorPrimary
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawer(
    navController: NavHostController
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color.White,
                drawerShape = RectangleShape,
                windowInsets = WindowInsets(top = 50.dp),
                modifier = Modifier.padding(end = 69.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.padding(start = 15.dp, bottom = 20.dp, end = 15.dp)
                ) {
                    AsyncImage(
                        model = Uri.parse("android.resource://com.example.finalproject_ppb//drawable/applogo"),
                        contentDescription = "App Logo",
                        modifier = Modifier
                            .size(80.dp)
                            .padding(end = 15.dp)
                    )
                    Text(
                        text = "Agricultural Plant Disease Cure Recommendation",
                        fontSize = 15.sp,
                        color = colorPrimary
                    )
                }
                Divider(color = Color.LightGray, modifier = Modifier.padding(bottom = 12.dp))
                NavigationDrawerItem(
                    label = {
                        Text(
                            text = "History",
                            fontSize = 20.sp,
                            color = colorPrimary
                        )
                    },
                    selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = "History Icon",
                            tint = colorPrimary,
                            modifier = Modifier.size(40.dp)
                        )
                    },
                    onClick = {
                        navController.navigate(ScreenHistory)
                        scope.launch {
                            drawerState.close()
                        }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = Color.White,
                        unselectedTextColor = colorPrimary,
                        unselectedIconColor = colorPrimary
                    ),
                    shape = RectangleShape
                )
                NavigationDrawerItem(
                    label = {
                        Text(
                            text = "Disease List",
                            fontSize = 20.sp,
                            color = colorPrimary
                        )
                    },
                    selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Yard,
                            contentDescription = "Disease List Icon",
                            tint = colorPrimary,
                            modifier = Modifier.size(40.dp)
                        )
                    },
                    onClick = {
                        navController.navigate(ScreenDiseaseList)
                        scope.launch {
                            drawerState.close()
                        }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = Color.White,
                        unselectedTextColor = colorPrimary,
                        unselectedIconColor = colorPrimary
                    ),
                    shape = RectangleShape
                )
            }
        }
    ) {
        CameraScreen(drawerState = drawerState, navController = navController)
    }
}