package com.github.stiangao.pipixia.web

/**
 * Created by shitiangao on 2017/9/21.
 */
class ResultBean<T>(val code: Int, val msg: String, val data: T) {

    fun of(data: T): ResultBean<*> {
        return ResultBean(State.SUCCESS.value, "", data)
    }

    enum class State constructor(val value: Int) {
        SUCCESS(1), FAIL(0), ERROR(-1)
    }
}
