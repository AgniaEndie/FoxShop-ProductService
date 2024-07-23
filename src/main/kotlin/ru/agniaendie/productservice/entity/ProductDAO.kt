package ru.agniaendie.productservice.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class ProductDAO(
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id var uuid: String,
    var title: String,
    var description: String,
    var category: String,
    var count: Int,
    var price: Double,
    var priceSale: Double?,
    var isSaleActive: Boolean
)