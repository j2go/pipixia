package com.github.stiangao.pipixia.web

import com.github.stiangao.pipixia.domain.Brand
import com.github.stiangao.pipixia.domain.BrandRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

/**
 * Created by ttgg on 2017/9/11.
 */
@RestController
class BrandController(@Autowired val brandRepository: BrandRepository) {

    @GetMapping("/brands")
    fun getRestaurants(@PageableDefault page: Pageable): Page<Brand> {
        return brandRepository.findAll(page)
    }

    @GetMapping("/brands/{id}")
    fun getRestaurant(@PathVariable id: Long): Brand? {
        val result = brandRepository.findById(id)
        return if (result.isPresent) result.get() else null
    }

    @PostMapping("/brands")
    fun createBrand(@RequestParam name: String): Brand {
        val brand = Brand()
        brand.name = name
        return brandRepository.save(brand)
    }

    @PutMapping("/brands/{id}")
    fun updateBrand(@RequestParam name: String, @PathVariable id: Long): Brand {

        val optionalBrand = brandRepository.findById(id)
        if (optionalBrand.isPresent) {
            val brand = optionalBrand.get()
            brand.name = name
            return brandRepository.save(brand)
        }
        return Brand()
    }

}