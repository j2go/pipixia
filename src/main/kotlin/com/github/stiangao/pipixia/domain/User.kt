package com.github.stiangao.pipixia.domain

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.persistence.Entity
import javax.persistence.Id

/**
 * Created by shitiangao on 2017/8/29.
 */
@Entity
class User : BaseEntity() {

    var uid: Long = 0
    var clientAuth = ""

    var nick = ""
    var imageSrc = ""
    var homeUrl = ""
    var districtName = ""

    var mdealInfoIds = ""

    var vipLevel = 0
    // ??
    var gender = 0

    var friendCount = 0
    var followCount = 0
    var commentCount = 0
}

@Entity
class MedalInfo {
    @Id
    var id: Long = -1

    var name = ""
    var level = 0
    var type = 0
    var iconUrl = ""
    var isGrowUp = false
}

@Repository
interface UserRepository : CrudRepository<User, Long>

@Repository
interface MedalInfoRepository : CrudRepository<MedalInfo, Long>