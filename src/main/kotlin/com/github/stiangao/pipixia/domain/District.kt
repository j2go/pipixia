package com.github.stiangao.pipixia.domain

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Version

/**
 * Created by shitiangao on 2017/8/22.
 */
@Entity
class District {

    @Id
    var id: Long = -1

    @Version
    var version = 0

    var name: String = ""
    var enName: String = ""
    var inChina: Boolean = true
    var isOversea: Boolean = false

}

@Repository
interface DistrictRepository : CrudRepository<District, Long>