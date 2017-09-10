package com.github.stiangao.pipixia.domain

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.persistence.Entity

/**
 * Created by shitiangao on 2017/8/28.
 */
@Entity
class RestaurantInfo : BaseEntity() {
    var restaurant_id: Long = -1

    var enName = ""
    var address = ""
    var cAddress = ""

    var labels = ""

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

    var brandId: Long = -1

    var expertId: Long = -1

    var recommendReason = ""

    //不知道这是啥字段
    var resideDistrictId = 0

}

@Repository
interface RestaurantInfoRepository : CrudRepository<RestaurantInfo, Long>