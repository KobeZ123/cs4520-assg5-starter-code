package com.cs4520.assignment5.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cs4520.assignment5.data.models.Product
import com.cs4520.assignment5.R


/**
 * product card component
 */
@Composable
fun ProductCard(
    product: Product,
) {
    val painter: Painter = if (product is Product.EquipmentProduct) {
        painterResource(id = R.drawable.equipment)
    } else {
        painterResource(id = R.drawable.food)
    }

    val color: Color = if (product is Product.EquipmentProduct) {
        Color(0xFFE06666)
    } else {
        Color(0xFFFFD965)
    }

    Row(
        modifier = Modifier.fillMaxWidth().background(color)
    ) {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painter,
                contentDescription = product.name + " image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(96.dp)
                    .height(96.dp),
            )
        }


        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.height(128.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = product.name)
            if (product.date != null) {
                Text(text = product.date)
            }
            Text(text = String.format("$%.2f", product.price))
        }
    }
}

@Preview
@Composable
fun ProductCardPreview() {
    MaterialTheme {
        ProductCard(
            product = Product.EquipmentProduct(
                equipmentName = "Hammer",
                equipmentDate = "01-01-2024",
                equipmentPrice = 5.5
            )
        )
    }
}