package ru.agniaendie.productservice.mapping

import org.springframework.stereotype.Component
import ru.agniaendie.productservice.dto.ProductDTO
import ru.agniaendie.productservice.entity.ProductDAO

@Component
class ProductMapper {
    fun fromProductDAO(productDAO: ProductDAO): ProductDTO {
        return ProductDTO(
            productDAO.uuid,
            productDAO.title,
            productDAO.description,
            productDAO.category,
            productDAO.count,
            productDAO.price,
            productDAO.priceSale,
            productDAO.isSaleActive
        )
    }

    fun fromProductDTO(productDTO: ProductDTO): ProductDAO {
        return ProductDAO(
            productDTO.uuid,
            productDTO.title,
            productDTO.description,
            productDTO.category,
            productDTO.count,
            productDTO.price,
            productDTO.priceSale,
            productDTO.isSaleActive
        )
    }
}