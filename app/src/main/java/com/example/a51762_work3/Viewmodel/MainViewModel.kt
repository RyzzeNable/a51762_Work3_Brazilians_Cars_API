package com.example.a51762_work3.Viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a51762_work3.API.carService
import com.example.a51762_work3.Model.Brand
import com.example.a51762_work3.Model.Model
import com.example.a51762_work3.Model.ModelInfo
import com.example.a51762_work3.Model.Year
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel : ViewModel() {

    private val _brandState = mutableStateOf(BrandState())
    val brandsState: State<BrandState> = _brandState

    private val _modelState = mutableStateOf(ModelState())
    val modelsState: State<ModelState> = _modelState

    private val _yearState = mutableStateOf(YearState())
    val yearsState: State<YearState> = _yearState

    private val _modelInfoState = mutableStateOf(ModelInfoState())
    val modelsInfoState: State<ModelInfoState> = _modelInfoState

    private val _screenStack = mutableStateListOf<String>()
    val screenStack: List<String> get() = _screenStack

    private val _selectedBrand = mutableStateOf<Brand?>(null)
    val selectedBrand: State<Brand?> = _selectedBrand

    private val _selectedModel = mutableStateOf<Model?>(null)
    val selectedModel: State<Model?> = _selectedModel

    private val _filterLetter = mutableStateOf("")
    val filterLetter: State<String> = _filterLetter

    fun setSelectedBrand(brand: Brand) {
        _selectedBrand.value = brand
    }

    fun setSelectedModel(model: Model) {
        _selectedModel.value = model
    }

    fun setFilterLetter(letter: String){
        _filterLetter.value = letter
    }

    init {
        fetchBrands()
    }

    public fun fetchBrands(){
        viewModelScope.launch {
            try {
                Log.d("MainViewModel", "Fetching brands...")
                val response = carService.getBrands()
                Log.d("MainViewModel", "Got brands: $response")
                _brandState.value = _brandState.value.copy(
                    list = response,
                    loading = false,
                    error = null
                )
            }catch (e: Exception){
                Log.e("MainViewModel", "Error: ${e.message}", e)
                _brandState.value = _brandState.value.copy(
                    loading = false,
                    error = "Error fetching Brands ${e.message}"
                )
            }
        }
    }

    public fun fetchModels(brandId: Int){
        viewModelScope.launch {
            try {
                Log.d("MainViewModel", "Fetching models...")
                val response = carService.getModels(brandId)
                Log.d("MainViewModel", "Got models: ${response.modelos.size}")
                _modelState.value = _modelState.value.copy(
                    list = response.modelos,
                    loading = false,
                    error = null
                )
            }catch (e: Exception){
                _modelState.value = _modelState.value.copy(
                    loading = false,
                    error = "Error fetching Models ${e.message}"
                )
            }
        }
    }

    public fun fetchYears(brandId: Int, modelId: Int){
        viewModelScope.launch {
            try {
                Log.d("MainViewModel", "Fetching years...")
                val response = carService.getYears(brandId, modelId)
                Log.d("MainViewModel", "Got years: ${response.size}")
                _yearState.value = _yearState.value.copy(
                    list = response,
                    loading = false,
                    error = null
                )
            }catch (e: Exception){
                _yearState.value = _yearState.value.copy(
                    loading = false,
                    error = "Error fetching Years ${e.message}"
                )
            }
        }
    }

    public fun fetchModelsInfo(brandId: Int, modelId: Int, yearId: String){
        viewModelScope.launch {
            try {
                Log.d("MainViewModel", "Fetching Models Info...")
                val response = carService.getModelsInfo(brandId, modelId, yearId)
                Log.d("MainViewModel", response.toString())
                _modelInfoState.value = _modelInfoState.value.copy(
                    info = response,
                    loading = false,
                    error = null
                )
            }catch (e: Exception){
                _modelInfoState.value = _modelInfoState.value.copy(
                    loading = false,
                    error = "Error fetching Model Info ${e.message}"
                )
            }
        }
    }

    public fun addScreen(screen: String) {
        _screenStack.add(screen)
    }

    public fun removeScreen() {
        if (_screenStack.size > 1) {
            _screenStack.removeAt(_screenStack.lastIndex)
        }
    }

    data class BrandState(
        val loading: Boolean = true,
        val list: List<Brand> = emptyList(),
        val error: String? = null
    )

    data class ModelState(
        val loading: Boolean = true,
        val list: List<Model> = emptyList(),
        val error: String? = null
    )

    data class YearState(
        val loading: Boolean = true,
        val list: List<Year> = emptyList(),
        val error: String? = null
    )

    data class ModelInfoState(
        val loading: Boolean = true,
        val info: ModelInfo? = null,
        val error: String? = null
    )
}