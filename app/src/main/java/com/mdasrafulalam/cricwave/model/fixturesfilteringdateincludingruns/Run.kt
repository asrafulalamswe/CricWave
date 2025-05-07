package com.mdasrafulalam.cricwave.model.fixturesfilteringdateincludingruns

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "runs_table")
data class Run(
    val fixture_id: Int?,
    @PrimaryKey
    val id: Int?,
    val inning: Int?,
    val overs: Double?,
    val pp1: String?,
    val pp2: String?,
    val pp3: String?,
    val resource: String?,
    val score: Int?,
    val team_id: Int?,
    val updated_at: String?,
    val wickets: Int?
)