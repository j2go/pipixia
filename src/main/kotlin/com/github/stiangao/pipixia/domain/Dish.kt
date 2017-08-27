package com.github.stiangao.pipixia.domain

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.persistence.*

/**
 * Created by shitiangao on 2017/8/22.
 */
@Entity
class Dish {
    @Id
    var id: Long = -1

    @Version
    var version = 0

    var name: String = ""
    var type: Int = 0
}

@Repository
interface DishRepository : CrudRepository<Dish, Long>