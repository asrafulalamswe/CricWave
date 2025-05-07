package com.mdasrafulalam.cricwave.model.stages

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stages_table")
data class Series( var isBookMarked:Boolean = false,
                   val code: String?,
                   @PrimaryKey
                   val id: Int,
                   val league_id: Int,
                   val name: String?,
                   val resource: String?,
                   val season_id: Int,
                   val standings: Boolean,
                   val type: String?,
                   val updated_at: String?)
