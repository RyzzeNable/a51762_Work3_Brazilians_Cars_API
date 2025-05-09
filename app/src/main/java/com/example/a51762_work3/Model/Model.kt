package com.example.a51762_work3.Model

data class Model(
    val codigo: Int,
    val nome: String,
)

data class ModelsResponse(val modelos: List<Model>)