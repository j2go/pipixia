package com.github.stiangao.pipixia.domain

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.Entity
import javax.persistence.Index
import javax.persistence.Table

/**
 * Created by ttgg on 2017/8/22.
 */
@Entity
@Table(indexes = arrayOf(
        Index(columnList = "name", name = "idx_name", unique = true)
))
class Tag : BaseEntity() {
    var name: String = ""
}

@Repository
interface TagRepository : CrudRepository<Tag, Long> {
    fun findByName(name: String): Optional<Tag>
}