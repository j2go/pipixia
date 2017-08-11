package com.github.stiangao.pipixia.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by shitiangao on 2017/8/11.
 */
@RestController
class IndexController {

    @GetMapping("/")
    fun index(): String {
        return "0.1.0"
    }


}

