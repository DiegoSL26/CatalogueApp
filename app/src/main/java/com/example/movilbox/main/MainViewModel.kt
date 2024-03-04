package com.example.movilbox.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movilbox.data.ProductEntity
import com.example.movilbox.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _filters = MutableLiveData(false)
    val filters : LiveData<Boolean> = _filters

    private val _selection = MutableLiveData("Rating")
    val selection : LiveData<String> = _selection

    private val _products = MutableLiveData<List<ProductEntity>>()
    val products : LiveData<List<ProductEntity>> = _products

    private val _selectedProduct = MutableLiveData<ProductEntity>()
    val selectedProduct : LiveData<ProductEntity> = _selectedProduct

    init {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.getNewProducts()
            _products.postValue(productRepository.getAllProducts(_selection.value!!))
        }
    }

    fun onChangeSelection(newSelection: String) {
        _selection.value = newSelection
        viewModelScope.launch(Dispatchers.IO) {
            _products.postValue(productRepository.getAllProducts(newSelection))
        }
    }

    fun onChangeSelectedProduct (product : ProductEntity) {
        _selectedProduct.postValue(product)
        println(_selectedProduct.value)
    }

    fun onToggleFiltersPanelButton() {
        _filters.value = !_filters.value!!
    }
}