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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cs4520.assignment5.data.models.Product
import com.cs4520.assignment5.domain.ProductListViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProductListFragment(
    productListViewModel: ProductListViewModel = viewModel()
) {
    val isLoading = productListViewModel.isLoading.value
    val context = LocalContext.current
    var progressBar: Boolean? = null
    var productList: Boolean? = null
    var noProductText: Boolean? = null

    productListViewModel.isLoading.observe(LocalLifecycleOwner.current) {
        if (it) {
            progressBar = LoadingProgressBarScreen()
        } else {
            progressBar = null
        }
    }

    productListViewModel.productListLiveData.observe(LocalLifecycleOwner.current) {
        if (it == null) {
            noProductText = null
            productList = null
        } else {
            if (it.isEmpty()) {
                noProductText = NoProductsAvailableScreen()
                productList = null
            } else {
                productList = ProductListScreen(
                    productListViewModel = productListViewModel,
                )
                noProductText = null
            }
        }
    }

    productListViewModel.errorMessageLiveData.observe(LocalLifecycleOwner.current) {
        if (it != null) {
            Toast.makeText(
                context,
                it,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    productListViewModel.pageNumberLiveData.observe(LocalLifecycleOwner.current) {
        productListViewModel.fetchProducts(it)
    }

//    if (productListViewModel.isLoading.value == true) {
//        LoadingProgressBarScreen()
//    } else if(productListViewModel.productListLiveData.value?.isEmpty() == true) {
//        NoProductsAvailableScreen()
//    }

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
            products = productListViewModel.productListLiveData.value ?: emptyList()
        )
        PaginationComponent(
            page = productListViewModel.pageNumberLiveData.value ?: 1,
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