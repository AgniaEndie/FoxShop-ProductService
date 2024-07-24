package ru.agniaendie.productservice.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.agniaendie.productservice.dto.ProductDTO
import ru.agniaendie.productservice.exception.ProductCreationException
import ru.agniaendie.productservice.exception.ProductDeleteException
import ru.agniaendie.productservice.exception.ProductNotFoundException
import ru.agniaendie.productservice.exception.ProductUpdateException
import ru.agniaendie.productservice.logger
import ru.agniaendie.productservice.mapping.ProductMapper
import ru.agniaendie.productservice.repository.IProductRepository

@Service
class ProductService(@Autowired var repository: IProductRepository, @Autowired var mapper: ProductMapper) {

    fun getAllProduct(): List<ProductDTO> {
        val resultList = ArrayList<ProductDTO>()
        repository.findAll().forEach { resultList.add(mapper.fromProductDAO(it)) }
        return resultList
    }

    fun getProductByUuid(id: String): ProductDTO {
        val resultProduct = repository.findProductDAOByUuid(id)
        if (resultProduct != null) {
            return mapper.fromProductDAO(resultProduct)
        } else {
            throw ProductNotFoundException("Product with id: $id not found")
        }
    }

    fun createProduct(productDTO: ProductDTO): ProductDTO {
        try {
            val freshProduct = repository.save(mapper.fromProductDTO(productDTO))
            return mapper.fromProductDAO(freshProduct)
        } catch (e: Exception) {
            logger.error("Product creation error: ${e.message}")
            throw ProductCreationException("Product creation error: ${e.message}")
        }
    }

    fun updateProduct(productDTO: ProductDTO): ProductDTO {
        try {
            val updatedProduct = repository.save(mapper.fromProductDTO(productDTO))
            return mapper.fromProductDAO(updatedProduct)
        } catch (e: Exception) {
            logger.error("Product update error: ${e.message}")
            throw ProductUpdateException("Product update error: ${e.message}")
        }
    }

    fun deleteProduct(id: String) {
        try {
            repository.removeProductDAOByUuid(id)
        } catch (e: Exception) {
            logger.error("Product deletion error: ${e.message}")
            throw ProductDeleteException("Product deletion error: ${e.message}")
        }
    }


}