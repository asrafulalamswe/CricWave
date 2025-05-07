package com.mdasrafulalam.cricwave.converters

import androidx.room.TypeConverter

class RecentFormTypeConverter {
    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toList(value: String): List<String> {
        return value.split(",")
    }
}
