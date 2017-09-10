package com.github.stiangao.pipixia.domain

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.persistence.Entity

/**
 * Created by shitiangao on 2017/8/22.
 */
@Entity
class Dish : BaseEntity() {
    var name: String = ""
    var cuisineId: Long = -1
    var type = ""
}

@Repository
interface DishRepository : CrudRepository<Dish, Long>