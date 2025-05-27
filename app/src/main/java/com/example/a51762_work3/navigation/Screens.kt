package com.example.a51762_work3.navigation

sealed class Screens(val route: String) {
    object Main : Screens("main")
    object Brand : Screens("brand")
    object Model : Screens("model")
    object Year : Screens("year")
    object ModelInfo : Screens("modelinfo")
}