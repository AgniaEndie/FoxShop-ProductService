package ru.agniaendie.productservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.agniaendie.productservice.entity.ProductDAO

interface IProductRepository : JpaRepository<ProductDAO, String> {
    fun findProductDAOByUuid(uuid: String): ProductDAO?
    fun removeProductDAOByUuid(uuid:String)
}