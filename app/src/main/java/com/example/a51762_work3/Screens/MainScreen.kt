package com.example.a51762_work3.Screens

import android.util.Log
import com.example.a51762_work3.R
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a51762_work3.Model.Brand
import com.example.a51762_work3.Viewmodel.MainViewModel
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.example.a51762_work3.Dialogs.FilterBrandsDialog
import com.example.a51762_work3.Model.Model
import com.example.a51762_work3.Model.ModelInfo
import com.example.a51762_work3.Model.Year
import com.example.a51762_work3.ui.theme.PurpleGrey40
import com.example.a51762_work3.ui.theme.greenSmall
import com.example.a51762_work3.ui.theme.greenSmaller
import com.example.a51762_work3.ui.theme.greenSuperSmaller
import com.example.a51762_work3.ui.theme.mainColor
import com.example.a51762_work3.ui.theme.secondaryColor
import java.text.Normalizer

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val mainViewModel: MainViewModel = viewModel()
    val brandstate by mainViewModel.brandsState
    val modelstate by mainViewModel.modelsState
    val yearstate by mainViewModel.yearsState
    val modelinfostate by mainViewModel.modelsInfoState
    val screenStack = mainViewModel.screenStack // can have 5 values("",brand,model,year,modelInfo)
    val currentScreen = screenStack.lastOrNull() ?: ""
    var showDialog by remember { mutableStateOf(false) }
    var filterLetter by remember {mutableStateOf("")}

    when (currentScreen) {
        "" -> {
            Column(
                modifier = Modifier.fillMaxSize().background(greenSuperSmaller),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                IconButton(
                    onClick = { mainViewModel.addScreen("brand") },
                    modifier = Modifier
                        .size(128.dp)
                        .background(
                            color = mainColor,
                            shape = RoundedCornerShape(8.dp)
                        )
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

        "brand" -> {
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
                                        onDismissRequest = { showDialog = false }
                                    )
                                }
                            }
                            val filterLetter = mainViewModel.filterLetter.value
                            BrandsScreen(
                                brands = brandstate.list,
                                onBrandClick = { brand ->
                                    mainViewModel.setSelectedBrand(brand)
                                    mainViewModel.fetchModels(brand.codigo)
                                    mainViewModel.addScreen("model")
                                },
                                filterLetter
                            )
                        }
                    }
                }
            }
        }

        "model" -> {
            Box(modifier = modifier.fillMaxSize().background(greenSuperSmaller)) {
                when {
                    modelstate.loading -> {
                        CircularProgressIndicator(modifier.align(Alignment.Center))
                    }

                    modelstate.error != null -> {
                        Text("ERROR OCCURRED", modifier = Modifier.align(Alignment.Center))
                    }

                    else -> {
                        val brand = mainViewModel.selectedBrand.value
                        Log.d("MainViewModel", brand.toString())
                        if(brand != null){
                            ModelsScreen(
                                brand = brand,
                                models = modelstate.list,
                                onModelClick = { selectedBrand, model ->
                                    mainViewModel.setSelectedModel(model)
                                    mainViewModel.fetchYears(selectedBrand.codigo, model.codigo)
                                    mainViewModel.addScreen("year")
                                }
                            )
                        }
                    }
                }
            }
        }

        "year" -> {
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
                                onModelClick = { selectedBrand, selectedModel, year ->
                                    mainViewModel.fetchModelsInfo(selectedBrand.codigo, model.codigo, year.codigo)
                                    mainViewModel.addScreen("modelinfo")
                                }
                            )
                        }
                    }
                }
            }
        }

        "modelinfo" -> {
            Box(modifier = modifier.fillMaxSize().background(greenSuperSmaller)) {
                when {
                    modelinfostate.loading -> {
                        CircularProgressIndicator(modifier.align(Alignment.Center))
                    }

                    modelinfostate.error != null -> {
                        Text("ERROR OCCURRED", modifier = Modifier.align(Alignment.Center))
                    }

                    else -> {
                        modelinfostate.info?.let { info ->
                            ModelInfoItem(modelinfo = info)
                        }
                    }
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

@Composable
fun ModelsScreen(brand: Brand, models: List<Model>, onModelClick: (Brand,Model) -> Unit){
    LazyColumn (modifier = Modifier.fillMaxSize()) {
        items(models) { model ->
            ModelItem(model = model, onClick = { onModelClick(brand,model) })
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

fun formatBrandNameForImage(name: String): String {
    val normalized = Normalizer.normalize(name, Normalizer.Form.NFD)
    val withoutAccents = normalized.replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
    return withoutAccents.lowercase()
        .replace("-", "")
        .replace("/", "")
        .replace(" ", "")
}
@Composable
fun ModelItem(model: Model, onClick: () -> Unit = {}){
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
                text = model.nome,
                color = Color.Black,
                fontWeight = Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp)
            )
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

@Composable
fun ModelInfoItem(modelinfo: ModelInfo){
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(greenSmall)
            .padding(16.dp)
    )
    {
        Text(buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Tipo de Veículo: ")
            }
            append(modelinfo.TipoVeiculo.toString())
        }, fontSize = 20.sp)
        Text(buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Valor: ")
            }
            append(modelinfo.Valor.toString())
        }, fontSize = 20.sp)
        Text(buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Marca: ")
            }
            append(modelinfo.Marca.toString())
        }, fontSize = 20.sp)
        Text(buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Modelo: ")
            }
            append(modelinfo.Modelo.toString())
        }, fontSize = 20.sp)
        Text(buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Ano: ")
            }
            append(modelinfo.AnoModelo.toString())
        }, fontSize = 20.sp)
        Text(buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Combustível: ")
            }
            append(modelinfo.Combustivel.toString())
        }, fontSize = 20.sp)
        Text(buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Sigla do Combustível: ")
            }
            append(modelinfo.SiglaCombustivel.toString())
        }, fontSize = 20.sp)
        Text(buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Mês de Referência: ")
            }
            append(modelinfo.MesReferencia.toString())
        }, fontSize = 20.sp)
    }
}