package com.github.stiangao.pipixia.domain

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.persistence.Entity

/**
 * Created by shitiangao on 2017/8/22.
 */
@Entity
class District : BaseEntity() {
    var name: String = ""
    var enName: String = ""
    var inChina: Boolean = true
    var isOversea: Boolean = false

}

@Repository
interface DistrictRepository : CrudRepository<District, Long>