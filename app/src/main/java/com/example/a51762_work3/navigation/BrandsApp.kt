package com.example.a51762_work3.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.a51762_work3.screens.BrandScreen
import com.example.a51762_work3.screens.MainScreen
import com.example.a51762_work3.screens.ModelInfoScreen
import com.example.a51762_work3.screens.ModelScreen
import com.example.a51762_work3.screens.YearScreen
import com.example.a51762_work3.viewmodel.MainViewModel

@Composable
fun BrandsApp(modifier: Modifier = Modifier, navController: NavHostController) {
    val mainViewModel: MainViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screens.Main.route) {
        composable(Screens.Main.route) {
            MainScreen(
                modifier = modifier,
                onStart = { navController.navigate(Screens.Brand.route) })
        }
        composable(Screens.Brand.route) {
            BrandScreen(
                modifier = modifier,
                mainViewModel = mainViewModel,
                onBrandSelected = {
                    mainViewModel.setSelectedBrand(it)
                    mainViewModel.fetchModels(it.codigo)
                    navController.navigate(Screens.Model.route)
                }
            )
        }
        composable(Screens.Model.route) {
            ModelScreen(
                modifier = modifier,
                mainViewModel = mainViewModel,
                onModelSelected = { brand, model ->
                    mainViewModel.setSelectedModel(model)
                    mainViewModel.fetchYears(brand.codigo, model.codigo)
                    navController.navigate(Screens.Year.route)
                }
            )
        }
        composable(Screens.Year.route) {
            YearScreen(
                modifier = modifier,
                mainViewModel = mainViewModel,
                onYearSelected = { brand, model, year ->
                    mainViewModel.fetchModelsInfo(brand.codigo, model.codigo, year.codigo)
                    navController.navigate(Screens.ModelInfo.route)
                }
            )
        }
        composable(Screens.ModelInfo.route) {
            ModelInfoScreen(modifier = modifier, mainViewModel = mainViewModel)
        }
    }
}