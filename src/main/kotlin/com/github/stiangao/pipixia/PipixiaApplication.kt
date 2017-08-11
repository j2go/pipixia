package com.github.stiangao.pipixia

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class PipixiaApplication

fun main(args: Array<String>) {
    SpringApplication.run(PipixiaApplication::class.java, *args)
}
