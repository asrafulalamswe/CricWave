package com.mdasrafulalam.cricwave.model.playersrankingwithdetails

data class BatterRankingWithDetail (
    var fullname: String?,
    var id: Int,
    var image_path: String?,
    var gender: String?,
    var country_id: Int?,
    var dateofbirth: String?,
    var ranking: Int?,
    var rating:Double
    )