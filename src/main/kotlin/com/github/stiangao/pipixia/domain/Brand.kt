package com.github.stiangao.pipixia.domain

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import javax.persistence.Entity

/**
 * Created by shitiangao on 2017/8/28.
 */
@Entity
class Brand : BaseEntity() {

    var name = ""
    var restCount = 0
}

@Repository
interface BrandRepository : PagingAndSortingRepository<Brand, Long>