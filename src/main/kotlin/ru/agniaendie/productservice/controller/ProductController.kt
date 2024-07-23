package ru.agniaendie.productservice.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.*
import ru.agniaendie.productservice.dto.ProductDTO
import ru.agniaendie.productservice.service.ProductService

@RestController
@RequestMapping("/api/product")
class ProductController(@Autowired val productService: ProductService) {

    @GetMapping("/all")
    fun getAllProduct() : List<ProductDTO>{
        return productService.getAllProduct()
    }

    @GetMapping("/get/{id}")
    @Cacheable(key = "#id", value = ["ProductDTO"])
    fun getProductByUuid(@PathVariable("id") id: String) : ProductDTO {
        return productService.getProductByUuid(id)
    }


    @PostMapping("/create")
    fun createProduct(@RequestBody productDTO: ProductDTO) : ProductDTO {
        return productService.createProduct(productDTO)
    }

    @PutMapping("/update")
    @CacheEvict(key = "#productDTO.uuid", value = ["ProductDTO"])
    fun updateProduct(@RequestBody productDTO: ProductDTO) : ProductDTO {
        return productService.updateProduct(productDTO)
    }

    @DeleteMapping("/delete/{id}")
    @CacheEvict(key = "#id", value = ["ProductDTO"])
    fun deleteProduct(@PathVariable("id") id: String) {
        return productService.deleteProduct(id)
    }
}