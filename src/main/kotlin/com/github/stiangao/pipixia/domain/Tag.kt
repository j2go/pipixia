package com.github.stiangao.pipixia.domain

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.*

/**
 * Created by ttgg on 2017/8/22.
 */
@Entity
@Table(indexes = arrayOf(
        Index(columnList = "name", name = "idx_name", unique = true)
))
class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = -1

    @Version
    var version = 0
    var name: String = ""
}

@Repository
interface TagRepository : CrudRepository<Tag, Long> {
    fun findByName(name: String) : Optional<Tag>
}