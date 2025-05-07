package com.mdasrafulalam.cricwave.model.standings

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mdasrafulalam.cricwave.converters.RecentFormTypeConverter

@Entity(tableName = "standing_table")
@TypeConverters(RecentFormTypeConverter::class)
data class AllStandings (
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,
    var draw: Int?,
    var league_id: Int?,
    var lost: Int?,
    var netto_run_rate: Double?,
    var noresult: Int?,
    var overs_against: Double?,
    var overs_for: Double?,
    var played: Int?,
    var points: Int?,
    var position: Int,
    var recent_form: List<String>?,
    var resource: String?,
    var runs_against: Int?,
    var runs_for: Int?,
    var season_id: Int?,
    var stage_id: Int?,
    var team_id: Int?,
    var updated_at: String?,
    var won: Int?
        ){
    constructor(): this (
        id = 0,
        draw = null,
        league_id= null,
        lost= null,
        netto_run_rate= null,
        noresult= null,
        overs_against= null,
        overs_for= null,
        played= null,
        points= null,
        position=0 ,
        recent_form= null,
        resource= null,
        runs_against= null,
        runs_for= null,
        season_id= null,
        stage_id= null,
        team_id= null,
        updated_at= null,
        won= null)
}