package com.github.stiangao.pipixia.web

import com.github.stiangao.pipixia.service.SpiderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by shitiangao on 2017/8/11.
 */
@RestController
class IndexController(@Autowired val spider: SpiderService) {

    @GetMapping("/")
    fun index(): String {
        return "0.1.0"
    }

    @GetMapping("/one")
    fun one(): String {
        return spider.getOne()
    }

}

