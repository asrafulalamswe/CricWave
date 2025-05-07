package com.mdasrafulalam.cricwave.converters

import androidx.room.TypeConverter
import com.mdasrafulalam.cricwave.model.playerswithteams.Position
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class PositionTypeConverterPlayerWithTeams {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val adapter: JsonAdapter<Position> =
        moshi.adapter(Position::class.java)

    @TypeConverter
    fun fromJson(json: String): Position? {
        return adapter.fromJson(json)
    }

    @TypeConverter
    fun toJson(position: Position?): String {
        return adapter.toJson(position)
    }
}

