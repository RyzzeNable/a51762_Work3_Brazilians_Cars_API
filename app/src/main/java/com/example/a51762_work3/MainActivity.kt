package com.example.a51762_work3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.getValue
import com.example.a51762_work3.viewmodel.MainViewModel
import com.example.a51762_work3.components.TopBar
import com.example.a51762_work3.ui.theme.A51762_Work3Theme
import com.example.a51762_work3.ui.theme.greenSuperSmaller
import androidx.navigation.compose.rememberNavController
import com.example.a51762_work3.navigation.BrandsApp
import com.example.a51762_work3.navigation.Screens

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            val showBackArrow = currentRoute != Screens.Main.route
            A51762_Work3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = greenSuperSmaller
                ) {
                    val mainViewModel: MainViewModel = viewModel()
                    Scaffold (
                        topBar = {
                            TopBar(
                                title = "Cars Brands",
                                showBackArrow = showBackArrow,
                                onBackClick = {
                                    navController.navigateUp()
                                    mainViewModel.removeScreen()
                                }
                            )
                        }
                    ){ innerPadding ->
                        BrandsApp(
                            modifier = Modifier.padding(innerPadding),
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}