package com.example.a51762_work3.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a51762_work3.Viewmodel.MainViewModel
import com.example.a51762_work3.ui.theme.mainColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String = "Cars Brands", onBackClick: (() -> Unit)? = null) {
    val mainViewModel: MainViewModel = viewModel()
    val screenStack = mainViewModel.screenStack
    CenterAlignedTopAppBar(
        modifier = Modifier.padding(bottom = 0.dp),
        title = {
            Text(
                title,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(color = Color.White)
            )
        },
        navigationIcon = {
            if (onBackClick != null && screenStack.size > 1) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = mainColor
        ),
    )
}