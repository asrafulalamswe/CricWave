package com.mdasrafulalam.cricwave.model.playerswithteams

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mdasrafulalam.cricwave.converters.PositionTypeConverterPlayerWithTeams

@Entity(tableName = "players_with_team_table")
@TypeConverters(PositionTypeConverterPlayerWithTeams::class)
data class Data(
    var battingstyle: String?,
    var bowlingstyle: String?,
    var country_id: Int?,
    var dateofbirth: String?,
    var firstname: String?,
    var fullname: String?,
    var gender: String?,
    @PrimaryKey
    var id: Int,
    var image_path: String?,
    var lastname: String?,
    var position: Position?,
    var resource: String?,
    @Ignore
    var teams: List<Team>?,
    var updated_at: String?
){
    constructor():this(battingstyle = null,
     bowlingstyle= null,
     country_id= null,
     dateofbirth= null,
     firstname= null,
     fullname= null,
     gender= null,
     id= 0,
     image_path= null,
     lastname= null,
     position= null,
     resource= null,

     teams= null,
     updated_at= null
    )
}