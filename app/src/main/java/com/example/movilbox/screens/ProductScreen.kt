package com.example.movilbox.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movilbox.main.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductScreen(viewModel: MainViewModel, navController: NavController) {

    val selectedProduct by viewModel.selectedProduct.observeAsState()
    
    selectedProduct?.let { product ->
        Scaffold (
            topBar = {
                TopAppBar({
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().height(50.dp)) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Filled.KeyboardArrowLeft, "Back")
                        }
                        Spacer(modifier = Modifier.width(36.dp))
                        MainText(text = "Detalle del producto", fontSize = 20)
                    }
                })
            }
        ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(all = 20.dp)
                .verticalScroll(rememberScrollState())) {


                AsyncImage(
                    modifier = Modifier
                        .padding(bottom = 32.dp)
                        .height(400.dp)
                        .fillMaxWidth(),
                    model = product.images.get(0),
                    contentDescription = "Product Thumbnail",
                    contentScale = ContentScale.Crop
                )
                Column {
                    MainText(text = product.title, fontSize = 32)
                    SecondaryText(text = product.brand, fontSize = 24)
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                        .padding(vertical = 22.dp)
                        .fillMaxWidth()) {
                        MainText(text = "$${product.price}.00", fontSize = 20)
                        MainText(text = "Descuento del ${product.discountPercentage}%", fontSize = 20)
                    }
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                        .padding(vertical = 22.dp)
                        .fillMaxWidth()) {
                        MainText(text = "Stock: ${product.stock} und.", fontSize = 20)
                        MainText(text = "Rating: ${product.rating} ⭐", fontSize = 20)
                    }
                    MainText(text = "Descripción", fontSize = 20)
                    Text(text = product.description)
                }
            }
        }

    } ?: run {
        Text(text = "No hay producto seleccionado")
    }
    
}