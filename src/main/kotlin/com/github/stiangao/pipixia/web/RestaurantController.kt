package com.github.stiangao.pipixia.web

import com.github.stiangao.pipixia.domain.Restaurant
import com.github.stiangao.pipixia.domain.RestaurantRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

/**
 * Created by ttgg on 2017/9/8.
 */
@RestController
class RestaurantController(@Autowired val restaurantRepo: RestaurantRepository) {

    @GetMapping("/restaurants")
    fun getRestaurants(@PageableDefault page: Pageable): Page<Restaurant> {
        return restaurantRepo.findAll(page)
    }

    @GetMapping("/restaurants/{id}")
    fun getRestaurant(@PathVariable id: Long): Restaurant? {
        val result = restaurantRepo.findById(id)
        return if (result.isPresent) result.get() else null
    }
}