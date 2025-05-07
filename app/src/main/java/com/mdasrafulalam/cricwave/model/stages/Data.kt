package com.mdasrafulalam.cricwave.model.stages

import androidx.room.Entity
import androidx.room.PrimaryKey


data class Data(
    val code: String?,
    val id: Int,
    val league_id: Int,
    val name: String?,
    val resource: String?,
    val season_id: Int,
    val standings: Boolean,
    val type: String?,
    val updated_at: String?
)