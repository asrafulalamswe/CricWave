package com.mdasrafulalam.cricwave.model.standings


data class Data(
    var draw: Int?,
    var league_id: Int?,
    var legend_id: Int?,
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
)