package com.cs4520.assignment5.data.models

/**
 * This interface represents a Product
 * @property name product name
 * @property date product date
 * @property price product price
 * @property type product type
 */
sealed class Product(
    val name: String,
    val date: String?,
    val price: Double,
    val type: String
) {
    /**
     * This interface represents a Food product
     * @property name food name
     * @property date food date
     * @property price food price
     * @property type food type
     */
    data class FoodProduct(
        val foodName: String,
        val foodDate: String?,
        val foodPrice: Double,
    ): Product(foodName, foodDate, foodPrice, "Food")

    /**
     * This interface represents a Equipment product
     * @property name equipment name
     * @property date equipment date
     * @property price equipment price
     * @property type equipment type
     */
    data class EquipmentProduct(
        val equipmentName: String,
        val equipmentDate: String?,
        val equipmentPrice: Double,
    ): Product(equipmentName, equipmentDate, equipmentPrice, "Equipment")
}
