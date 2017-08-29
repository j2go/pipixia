package com.github.stiangao.pipixia.domain

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.Version

/**
 * Created by shitiangao on 2017/8/28.
 */
@Entity
class RestaurantInfo {
    @Id
    var id: Long = -1

    @Version
    var version = 0

    @OneToOne
    var restaurant = Restaurant()

    var enName = ""
    var address = ""
    var cAddress = ""

    @ManyToMany
    var labels: MutableList<Label> = mutableListOf()

    var bookTels = ""
    var facilityNames = ""
    var payMentCardNames = ""

    var coverImageUrls = ""

    var description = ""
    var openTime = ""
    var webSite = ""
    var imageCount = 0
    var bookingCount = 0

    var specialPromoteCode = 0

    @ManyToOne
    var brand: Brand? = null
    @ManyToOne
    var expert: Expert? = null

    var recommendReason = ""

    //不知道这是啥字段
    var resideDistrictId = 0

}

@Repository
interface RestaurantInfoRepository : CrudRepository<RestaurantInfo, Long>