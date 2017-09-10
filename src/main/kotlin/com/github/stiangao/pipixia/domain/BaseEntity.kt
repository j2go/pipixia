package com.github.stiangao.pipixia.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*

/**
 * Created by ttgg on 2017/9/10.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BaseEntity {
    @Id
    @GeneratedValue
    var id: Long = -1

    @Version
    var version = 0

    @CreatedDate
    var createDate = Date()

    @LastModifiedDate
    var lastModifyDate = Date()

    var sourceId: Long = -1
}