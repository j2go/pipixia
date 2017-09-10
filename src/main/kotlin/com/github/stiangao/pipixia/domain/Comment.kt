package com.github.stiangao.pipixia.domain

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.Entity

/**
 * Created by shitiangao on 2017/8/29.
 */
@Entity
class Comment : BaseEntity() {
    var restaurantId: Long = -1

    var keyWords = ""
    var resourceId: Long = 0
    var resourceType = 0
    var isFromTravel = false

    var businessId: Long = 0
    var businessType = 0

    var userId: Long = -1

    var totalStar = 0
    var touristTyep = 0
    var playYser = 1970
    var playMonth = 1
    var playDay = 1

    var Content = ""

    var usefulCount = 0
    //？？
    var userfulWhether = false

    var voteUsers = ""

    var isRecommend = false
    var isPicked = false
    var ip = ""
    var platForm = 1
    var sourceType = 1
    var parentCommentId: Long = 0

    var publishTime = Date()
    var publishStatus = 0

    var auditor = ""
    var auditorTime = Date()

    var commentImageIds = ""
}

@Entity
class CommentImage : BaseEntity() {

    var uploadTime = Date()
    var photoPath = ""
    var thnumbnailUrl = ""
    var width = 0
    var height = 0
    var baseUrl = ""
}

@Repository
interface CommentRepository : PagingAndSortingRepository<Comment, Long>

@Repository
interface CommentImageRepository : CrudRepository<CommentImage, Long>