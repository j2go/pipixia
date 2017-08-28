package com.github.stiangao.pipixia.domain

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Version

/**
 * 评委专家
 * Created by shitiangao on 2017/8/28.
 */
@Entity
class Expert {
    @Id
    var id: Long = -1

    @Version
    var version = 0

    var name = ""
    var intro = ""
    var identity = ""
    var imgUrl = ""
}

@Repository
interface ExpertRepository : CrudRepository<Expert, Long>