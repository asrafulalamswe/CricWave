package com.mdasrafulalam.cricwave.model.officials

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "official_table")
data class Data(
    val country_id: Int?,
    val dateofbirth: String?,
    val firstname: String?,
    val fullname: String?,
    val gender: String?,
    @PrimaryKey
    val id: Int?,
    val lastname: String?,
    val resource: String?,
    val updated_at: String?
)