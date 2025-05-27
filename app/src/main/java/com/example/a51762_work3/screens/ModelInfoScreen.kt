package com.example.a51762_work3.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a51762_work3.model.ModelInfo
import com.example.a51762_work3.viewmodel.MainViewModel
import com.example.a51762_work3.ui.theme.greenSmall
import com.example.a51762_work3.ui.theme.greenSuperSmaller
import com.example.a51762_work3.screens.formatBrandNameForImage

@Composable
fun ModelInfoScreen(modifier: Modifier = Modifier, mainViewModel: MainViewModel){
    val modelinfostate by mainViewModel.modelsInfoState

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

@Composable
fun ModelInfoItem(modelinfo: ModelInfo){
    val context = LocalContext.current
    val imageName = formatBrandNameForImage(modelinfo.Marca.toString())
    val imageResId = remember(imageName) {
        context.resources.getIdentifier(imageName, "drawable", context.packageName)
    }
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
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            if (imageResId != 0) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = "Brand Logo",
                    modifier = Modifier
                        .size(150.dp)
                        .padding(8.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}