package com.example.a51762_work3.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a51762_work3.dialogs.FilterBrandsDialog
import com.example.a51762_work3.model.Brand
import com.example.a51762_work3.viewmodel.MainViewModel
import com.example.a51762_work3.ui.theme.greenSmall
import com.example.a51762_work3.ui.theme.greenSmaller
import com.example.a51762_work3.ui.theme.greenSuperSmaller
import java.text.Normalizer

@Composable
fun BrandScreen(modifier: Modifier = Modifier, mainViewModel: MainViewModel, onBrandSelected: (Brand) -> Unit){
    val brandstate by mainViewModel.brandsState
    var showDialog by remember { mutableStateOf(false) }
    val filterLetter by mainViewModel.filterLetter

    Box(modifier = modifier.fillMaxSize().background(greenSuperSmaller)) {
        when {
            brandstate.loading -> {
                CircularProgressIndicator(modifier.align(Alignment.Center))
            }

            brandstate.error != null -> {
                Text("ERROR OCCURRED", modifier = Modifier.align(Alignment.Center))
            }

            else -> {
                Column(modifier = Modifier.fillMaxSize()) {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        FilledIconButton(
                            onClick = { showDialog = true },
                            modifier = Modifier
                                .fillMaxWidth(),
                            shape = RectangleShape,
                            colors = IconButtonDefaults.filledIconButtonColors(containerColor = greenSmaller)
                        ) {
                            Row (
                            ){
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Filter"
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Filter", fontWeight = FontWeight.Bold)
                            }
                        }
                        if(showDialog){
                            FilterBrandsDialog(
                                onDismissRequest = { showDialog = false },
                                mainViewModel = mainViewModel
                            )
                        }
                    }
                    val filterLetter = mainViewModel.filterLetter.value
                    BrandsScreen(
                        brands = brandstate.list,
                        onBrandClick = onBrandSelected,
                        filterLetter
                    )
                }
            }
        }
    }
}

@Composable
fun BrandsScreen(brands: List<Brand>, onBrandClick: (Brand) -> Unit, filterLetter: String) {
    val filteredBrands = if (filterLetter.isNotBlank()) {
        brands.filter { it.nome.startsWith(filterLetter, ignoreCase = true) }
    } else {
        brands
    }
    LazyColumn (modifier = Modifier.fillMaxSize()) {
        items(filteredBrands) { brand ->
            BrandItem(brand = brand, onClick = { onBrandClick(brand) })
        }
    }
}

fun formatBrandNameForImage(name: String): String {
    val normalized = Normalizer.normalize(name, Normalizer.Form.NFD)
    val withoutAccents = normalized.replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
    return withoutAccents.lowercase()
        .replace("-", "")
        .replace("/", "")
        .replace(" ", "")
}

@Composable
fun BrandItem(brand: Brand, onClick: () -> Unit = {}) {
    val context = LocalContext.current
    val imageName = formatBrandNameForImage(brand.nome)
    val imageResId = remember(imageName) {
        context.resources.getIdentifier(imageName, "drawable", context.packageName)
    }
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
                text = brand.nome,
                color = Color.Black,
                fontWeight = Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            if (imageResId != 0) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = "Brand Logo",
                    modifier = Modifier
                        .size(78.dp)
                        .padding(8.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}