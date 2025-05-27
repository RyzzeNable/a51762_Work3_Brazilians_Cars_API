package com.example.a51762_work3.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a51762_work3.model.Brand
import com.example.a51762_work3.model.Model
import com.example.a51762_work3.model.Year
import com.example.a51762_work3.viewmodel.MainViewModel
import com.example.a51762_work3.ui.theme.greenSmall
import com.example.a51762_work3.ui.theme.greenSuperSmaller

@Composable
fun YearScreen(modifier: Modifier = Modifier, mainViewModel: MainViewModel, onYearSelected: (Brand, Model, Year) -> Unit){
    val yearstate by mainViewModel.yearsState

    Box(modifier = modifier.fillMaxSize().background(greenSuperSmaller)) {
        when {
            yearstate.loading -> {
                CircularProgressIndicator(modifier.align(Alignment.Center))
            }

            yearstate.error != null -> {
                Text("ERROR OCCURRED", modifier = Modifier.align(Alignment.Center))
            }

            else -> {
                val brand = mainViewModel.selectedBrand.value
                val model = mainViewModel.selectedModel.value
                if(brand != null && model != null){
                    YearsScreen(
                        brand = brand,
                        model = model,
                        years = yearstate.list,
                        onModelClick = { brand, model, year ->
                            onYearSelected(brand, model, year)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun YearsScreen(brand: Brand, model: Model, years: List<Year>, onModelClick: (Brand,Model,Year) -> Unit){
    LazyColumn (modifier = Modifier.fillMaxSize()) {
        items(years) { year ->
            YearItem(year = year, onClick = { onModelClick(brand,model,year) })
        }
    }
}

@Composable
fun YearItem(year: Year, onClick: () -> Unit = {}){
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
    )
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .background(greenSmall)
                .clickable { onClick() }
        ){
            Text(
                text = year.nome,
                color = Color.Black,
                fontWeight = Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp)
            )
        }
    }
}