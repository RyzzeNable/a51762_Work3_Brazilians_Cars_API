package com.example.a51762_work3.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.example.a51762_work3.ui.theme.greenSuperSmaller
import com.example.a51762_work3.ui.theme.mainColor

@Composable
fun MainScreen(modifier: Modifier = Modifier, onStart: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(greenSuperSmaller),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IconButton(
            onClick = onStart,
            modifier = Modifier
                .size(128.dp)
                .background(color = mainColor, shape = RoundedCornerShape(8.dp))
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play",
                tint = Color.White,
                modifier = Modifier.fillMaxSize(0.6f)
            )
        }
    }
}



