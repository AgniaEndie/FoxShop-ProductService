package ru.agniaendie.productservice.entity

import jakarta.persistence.*

@Entity
@Table(name = "Product")
data class ProductDAO(
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id var uuid: String,
    var title: String,
    var description: String,
    var category: String,
    var count: Int,
    var price: Double,
    var priceSale: Double?,
    @Column(name = "is_sale_active")
    var isSaleActive: Boolean
)
