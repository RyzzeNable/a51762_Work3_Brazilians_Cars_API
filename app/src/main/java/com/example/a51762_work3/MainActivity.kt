package com.example.a51762_work3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a51762_work3.Screens.MainScreen
import com.example.a51762_work3.Viewmodel.MainViewModel
import com.example.a51762_work3.components.TopBar
import com.example.a51762_work3.ui.theme.A51762_Work3Theme
import com.example.a51762_work3.ui.theme.greenSuperSmaller

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
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
                                onBackClick = { mainViewModel.removeScreen() }
                            )
                        }
                    ){ innerPadding ->
                        MainScreen(modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}