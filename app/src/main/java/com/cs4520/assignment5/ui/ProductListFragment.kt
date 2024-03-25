package com.cs4520.assignment5.ui

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cs4520.assignment5.data.models.Product
import com.cs4520.assignment5.domain.ProductListViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProductListFragment(
    productListViewModel: ProductListViewModel = viewModel()
) {
    val context = LocalContext.current

    val isLoading = productListViewModel.isLoading.value

    val productListState = productListViewModel.productListState.value

    val errorMessageState = productListViewModel.errorMessageState.value

    if (isLoading) {
        LoadingProgressBarScreen()
    }
    
    productListState?.run {
        if (this.isEmpty()) {
            NoProductsAvailableScreen()
        } else {
            ProductListScreen(productListViewModel = productListViewModel)
        }
    }

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
fun ProductListScreen(
    productListViewModel: ProductListViewModel,
) {
    Column {
//        productListViewModel.productListLiveData.observe(LocalLifecycleOwner.current) {
//
//        }

        ProductList(
            modifier = Modifier.weight(1f),
            products = productListViewModel.productListState.value ?: emptyList()
        )
        PaginationComponent(
            page = productListViewModel.pageNumberState.value ?: 1,
            onPageNext = { productListViewModel.onNextPageClick() },
            onPageBack = { productListViewModel.onBackPageClick() }
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
        if (page > 1) {
            Button(onClick = onPageBack) {
                Text(text = "Back")
            }
        }
        Text(text = page.toString())
        Button(onClick = onPageNext) {
            Text(text = "Next")
        }
    }
}