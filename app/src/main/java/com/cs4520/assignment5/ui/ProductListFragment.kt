package com.cs4520.assignment5.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cs4520.assignment5.data.models.Product

@Composable
fun ProductListFragment() {
    LoadingProgressBarScreen()
}

/**
 * loading progress bar screen component
 */
@Composable
fun LoadingProgressBarScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

/**
 * no products available screen component
 */
@Composable 
fun NoProductsAvailableScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "No products available")
    }
}

/**
 * product list screen component
 */
@Composable
fun ProductListScreen() {
    Column {
        ProductList(
            modifier = Modifier.weight(1f),
            products = emptyList()
        )
        PaginationComponent(
            page = 0,
            onPageNext = { /*TODO*/ },
            onPageBack = { /*TODO*/ }
        )
    }
}

/**
 * product list component that displays the products
 */
@Composable
fun ProductList(
    modifier: Modifier = Modifier,
    products: List<Product>
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(products) { product ->
            ProductCardFragment(product = product)
        }
    }
}

/**
 * pagination component that changes the page
 */
@Composable
fun PaginationComponent(
    page: Int,
    onPageNext: () -> Unit,
    onPageBack: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Button(onClick = onPageBack) {
            Text(text = "Back")
        }
        Text(text = page.toString())
        Button(onClick = onPageNext) {
            Text(text = "Next")
        }
    }
}