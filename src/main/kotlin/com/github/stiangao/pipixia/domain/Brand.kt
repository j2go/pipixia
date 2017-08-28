package com.github.stiangao.pipixia.domain

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Version

/**
 * Created by shitiangao on 2017/8/28.
 */
@Entity
class Brand {
    @Id
    var id: Long = -1

    @Version
    var version = 0

    var name = ""
    var restCount = 0
}

@Repository
interface BrandRepository : CrudRepository<Brand, Long>