package com.github.stiangao.pipixia.domain

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Index
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.persistence.Version

/**
 * Created by shitiangao on 2017/8/28.
 */
@Entity
//@Table(indexes = arrayOf(
//        Index(columnList = "restaurant_id,dish_id", name = "idx_restaurant_dish", unique = true)
//))
class RestaurantDish: BaseEntity() {

    var restaurantId: Long = -1

    var dishId: Long = -1

    var imageId = 0
    var imageUrl = ""
    var title = ""
    var description = ""
    var isRecommend = false
    var recommendCount = 0
}

@Repository
interface RestaurantDishRepository : CrudRepository<RestaurantDish, Long>