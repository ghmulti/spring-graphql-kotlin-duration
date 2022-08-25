package com.sample.demo

import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours

@Controller
class SampleController {

    @QueryMapping
    fun test() : List<Sample> = listOf(
        Sample(10.days), Sample(1.hours)
    )

}
