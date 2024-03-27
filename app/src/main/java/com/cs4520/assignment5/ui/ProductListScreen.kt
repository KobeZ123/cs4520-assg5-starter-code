package com.cs4520.assignment5.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cs4520.assignment5.data.models.Product
import com.cs4520.assignment5.domain.ProductListViewModel

@Composable
fun ProductListScreen(
    productListViewModel: ProductListViewModel = viewModel()
) {
    val context = LocalContext.current

    val errorMessageState = productListViewModel.errorMessageState.value

    ProductListPage(productListViewModel = productListViewModel)

    if (errorMessageState.isNullOrEmpty().not()) {
        Toast.makeText(
            context,
            errorMessageState,
            Toast.LENGTH_LONG
        ).show()
    }
}

/**
 * loading progress bar screen component
 */
@Composable
fun LoadingProgressBarScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth().background(Color.White),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

/**
 * no products available screen component
 */
@Composable 
fun NoProductsAvailableScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxWidth().background(Color.White),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "No products available")
    }
}

/**
 * product list screen component
 */
@Composable
fun ProductListPage(
    productListViewModel: ProductListViewModel,
) {
    val isLoading = productListViewModel.isLoading.value

    val productListState = productListViewModel.productListState.value

    Column (
        modifier = Modifier.background(Color.White)
    ){
        if (isLoading) {
            LoadingProgressBarScreen(
                modifier = Modifier.weight(0.9f)
            )
        }

        productListState?.run {
            if (this.isEmpty()) {
                NoProductsAvailableScreen(
                    modifier = Modifier.weight(0.9f)
                )
            } else {
                ProductList(
                    modifier = Modifier.weight(0.9f),
                    products = productListViewModel.productListState.value ?: emptyList()
                )
            }
        }

        Box(
            modifier = Modifier
                .weight(0.1f)
                .fillMaxWidth()
        ) {
            PaginationComponent(
                page = productListViewModel.pageNumberState.value,
                onPageNext = { productListViewModel.onNextPageClick() },
                onPageBack = { productListViewModel.onBackPageClick() }
            )
        }
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
            ProductCard(product = product)
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
            .padding(vertical = 8.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier.width(96.dp),
            contentAlignment = Alignment.Center
        ) {
            if (page > 1) {
                Button(onClick = onPageBack) {
                    Text(text = "Back")
                }
            }
        }
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = page.toString()
        )
        Box(
            modifier = Modifier.width(96.dp),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = onPageNext) {
                Text(text = "Next")
            }
        }
    }
}
