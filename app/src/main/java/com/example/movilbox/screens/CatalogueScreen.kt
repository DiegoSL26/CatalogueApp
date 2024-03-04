package com.example.movilbox.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movilbox.data.ProductEntity
import com.example.movilbox.main.MainViewModel
import com.example.movilbox.navigation.AppScreens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel(), navController: NavController) {
    val products by viewModel.products.observeAsState(arrayListOf())
    val filtersSwitch by viewModel.filters.observeAsState(false)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TopBar()
        MainText(text = "Productos", fontSize = 32)
        if(filtersSwitch) {
            FiltersPanel()
        }
        ProductsList(navController, products = products)
    }
}

@Composable
fun FiltersPanel(viewModel: MainViewModel = hiltViewModel()) {

    val filtersOptions = listOf("Rating", "Precio", "Descuento", "Categoria", "Stock", "Marca")
    val currentSelection by viewModel.selection.observeAsState("Rating")

    Card(
        shape = RoundedCornerShape(10),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        modifier = Modifier
            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier
            .background(Color(0xffededed))
            .padding(vertical = 24.dp, horizontal = 12.dp)) {
            Column(modifier =  Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
                MainText(text = "Ordenar por", fontSize = 24)
            }
            RadioButtonGroup(options = filtersOptions, selection = currentSelection, onItemClick = { viewModel.onChangeSelection(it) })
        }
    }
}

@Composable
fun RadioButtonGroup(
    options: List<String>,
    selection: String,
    onItemClick: ((String) -> Unit)
) {
    RadioButtonWithLabel(label = "Rating", selected = selection == options[0], onItemClick = { onItemClick(it) })
    RadioButtonWithLabel(label = "Precio", selected = selection == options[1], onItemClick = { onItemClick(it) })
    RadioButtonWithLabel(label = "Descuento", selected = selection == options[2], onItemClick = { onItemClick(it) })
    RadioButtonWithLabel(label = "Categoria", selected = selection == options[3], onItemClick = { onItemClick(it) })
    RadioButtonWithLabel(label = "Stock", selected = selection == options[4], onItemClick = { onItemClick(it) })
    RadioButtonWithLabel(label = "Marca", selected = selection == options[5], onItemClick = { onItemClick(it) })
}

@Composable
fun RadioButtonWithLabel(
    label: String,
    selected: Boolean,
    onItemClick: ((String) -> Unit)
) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        RadioButton(selected = selected, onClick = { onItemClick(label) })
        SecondaryText(text = label, fontSize = 16)
    }
}

@Composable
fun MainText(text: String, fontSize: Int) {
    Text(
        textAlign = TextAlign.Center,
        text = text,
        style = TextStyle(
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Bold
        )
    )
}

@Composable
fun SecondaryText(text: String, fontSize: Int) {
    Text(
        textAlign = TextAlign.Center,
        text = text,
        style = TextStyle(
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Gray
        )
    )
}

@Composable
fun RightComponent(viewModel: MainViewModel = hiltViewModel()) {
    Row {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Filled.Search, "Search")
        }
        Spacer(modifier = Modifier.width(20.dp))
        IconButton(onClick = { viewModel.onToggleFiltersPanelButton() }) {
            Icon(Icons.Filled.Tune, "Filters")
        }
    }
}

@Composable
fun TopBar() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(all = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Filled.KeyboardArrowLeft, "Back")
        }
        RightComponent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCard(viewModel: MainViewModel = hiltViewModel(), navController: NavController, product: ProductEntity) {
    Card(
        shape = RoundedCornerShape(10),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        modifier = Modifier
            .height(200.dp)
            .padding(4.dp),
        onClick = {
            navController.navigate(route = AppScreens.ProductScreen.route)
            viewModel.onChangeSelectedProduct(product)
        }
    ) {
        if(product.stock != 0) {
            Column (
                modifier = Modifier
                    .background(Color(0xffededed))
                    .padding(20.dp)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .height(70.dp)
                        .width(700.dp),
                    model = product.thumbnail,
                    contentDescription = "Product Thumbnail",
                    contentScale = ContentScale.Crop
                )
                Column (verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                    .fillMaxHeight()
                    .padding(top = 16.dp)) {
                    MainText(text = product.title, fontSize = 12)
                    SecondaryText(text = product.brand, fontSize = 11)
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        MainText(text = "$${product.price}.00", fontSize = 11)
                        MainText(text = "Rating: ${product.rating}", fontSize = 11)
                    }
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    textAlign = TextAlign.Center,
                    text = "No hay Stock",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        drawStyle = Stroke(
                            miter = 10f,
                            width = 10f,
                            join = StrokeJoin.Round
                        )
                    )
                )
                Text(
                    textAlign = TextAlign.Center,
                    text = "No hay Stock",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red,
                    )
                )
                Column (
                    modifier = Modifier
                        .background(Color(0xffededed))
                        .padding(20.dp)
                        .zIndex(-1f)
                        .blur(2.dp)
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .height(70.dp)
                            .width(700.dp),
                        model = product.thumbnail,
                        contentDescription = "Product Thumbnail",
                        contentScale = ContentScale.Crop
                    )
                    Column (verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                        .fillMaxHeight()
                        .padding(top = 16.dp)) {
                        MainText(text = product.title, fontSize = 12)
                        SecondaryText(text = product.brand, fontSize = 11)
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            MainText(text = "$${product.price}.00", fontSize = 11)
                            MainText(text = "Rating: ${product.rating}", fontSize = 11)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductsList(navController: NavController, products: List<ProductEntity>) {
    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)) {
        val productCount = products.size
        items(productCount) {index ->
            ProductCard(navController = navController, product = products[index])
        }
    }
}