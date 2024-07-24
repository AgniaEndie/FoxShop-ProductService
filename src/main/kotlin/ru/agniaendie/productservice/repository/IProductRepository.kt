package ru.agniaendie.productservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional
import ru.agniaendie.productservice.entity.ProductDAO

interface IProductRepository : JpaRepository<ProductDAO, String> {
    fun findProductDAOByUuid(uuid: String): ProductDAO?
    @Transactional
    fun removeProductDAOByUuid(uuid:String)
}