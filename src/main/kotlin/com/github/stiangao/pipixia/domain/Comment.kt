package com.github.stiangao.pipixia.domain

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Version

/**
 * Created by shitiangao on 2017/8/29.
 */
@Entity
class Comment {
    @Id
    var id: Long = -1

    @Version
    var version = 0

    @ManyToOne
    var restaurant = Restaurant()

    var keyWords = ""
    var resourceId: Long = 0
    var resourceType = 0
    var isFromTravel = false

    var businessId: Long = 0
    var businessType = 0

    @ManyToOne
    var User = User()

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

    @OneToMany
    var images: MutableList<CommentImage> = mutableListOf()
}

@Entity
class CommentImage {
    @Id
    var id: Long = -1

    @Version
    var version = 0

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