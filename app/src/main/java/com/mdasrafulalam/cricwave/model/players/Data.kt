package com.mdasrafulalam.cricwave.model.players

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "players_table")
data class Data(
    var battingstyle: String?,
    var bowlingstyle: String?,
    @Ignore
    val career: List<Career>?,
    var country_id: Int?,
    var dateofbirth: String?,
    var firstname: String?,
    var fullname: String?,
    var gender: String?,
    @PrimaryKey
    var id: Int,
    var image_path: String?,
    var lastname: String?,
    @Ignore
    var position: Position?,
    var resource: String?,
    var updated_at: String?
):java.io.Serializable{
    constructor(): this (
         battingstyle = null,
         bowlingstyle= null,
         career = null,
         country_id= null,
         dateofbirth= null,
         firstname= null,
         fullname= null,
         gender= null,
         id = 0,
         image_path= null,
         lastname= null,
         position= null,
         resource= null,
         updated_at= null
    )
}