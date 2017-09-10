package com.github.stiangao.pipixia.domain

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import javax.persistence.Entity

/**
 * Created by shitiangao on 2017/8/11.
 */
@Entity
class Restaurant : BaseEntity() {

    var poiId: Long = -1
    var name: String = ""

    var districtId: Long = -1

    var ggCoordLat: Double = 0.0
    var ggCoordLng: Double = 0.0

    var bCoordLat: Double = 0.0
    var bCoordLng: Double = 0.0

    var imageUrl: String = ""
    var imageUrl2: String = ""

    var averagePrice: Int = 0
    var currencyUnit: String = "RMB"
    var commentScore: Double = 1.0

    var commentCount: Int = 0

    var distanceNum: Int = 0

    var tagIds = ""

    var cuisineIds = ""

    var canBook: Boolean = false
    var haveProduct: Boolean = false
    var isPromotion: Boolean = false
    var recommendType: Int = 1

    var feature: String = ""
    var shiMeiLinType: Int = 0
    var shiMeiLinName: String = ""
    var shiMeiLinChainTypeName: String = ""

    var haveHotelProduct: Boolean = false

    var landmarkName: String = ""
    var landmarkDistance: String = ""

    var restaurantInfoId: Long = -1
}

@Repository
interface RestaurantRepository : PagingAndSortingRepository<Restaurant, Long>