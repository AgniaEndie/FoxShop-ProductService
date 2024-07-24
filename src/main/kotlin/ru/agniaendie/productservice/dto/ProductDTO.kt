package ru.agniaendie.productservice.dto

import java.io.Serializable

data class ProductDTO (
    var uuid:String?,
    var title:String,
    var description:String,
    var category: String,
    var count:Int,
    var price: Double,
    var priceSale: Double?,
    var isSaleActive: Boolean
): Serializable