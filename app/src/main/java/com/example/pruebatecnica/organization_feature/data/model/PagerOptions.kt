package com.example.pruebatecnica.organization_feature.data.model

data class PagerOptions(
    var previous :Boolean=false,
    var first:String="1",
    var second:String="2",
    var third:String="3",
    val next:Boolean=false,
    var currentPage: Int = 1
)
