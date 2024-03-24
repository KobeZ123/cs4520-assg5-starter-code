package com.cs4520.assignment5.util.mappers

import com.cs4520.assignment5.data.models.Product

/**
 * Maps the dataset to a list of [Product]
 */
fun List<List<Any?>>.mapToProductList(): List<Product> {
    return this.map{
        if (it.size == 4
            && it[0] is String
            && it[1] is String
            && it[2] is String?
            && it[3] is Int) {
            when (it[1]) {
                "Equipment" -> {
                    Product.EquipmentProduct(
                        equipmentName = it[0] as String,
                        equipmentDate = it[2] as String?,
                        equipmentPrice = it[3] as Double,
                    )

                }
                "Food" ->
                    Product.FoodProduct(
                        foodName = it[0] as String,
                        foodDate = it[2] as String?,
                        foodPrice = it[3] as Double,
                    )
                else ->
                    throw IllegalStateException("The dataset has unsupported product type!")
            }
        } else {
            throw IllegalStateException("The dataset is improperly formatted!")
        }
    }
}
