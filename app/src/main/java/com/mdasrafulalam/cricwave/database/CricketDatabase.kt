package com.mdasrafulalam.cricwave.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mdasrafulalam.cricwave.converters.PositionTypeConverterPlayerWithTeams
import com.mdasrafulalam.cricwave.converters.RecentFormTypeConverter
import com.mdasrafulalam.cricwave.dao.CricketDao
import com.mdasrafulalam.cricwave.model.fixturesfilteringdateincludingruns.Data
import com.mdasrafulalam.cricwave.model.fixturesfilteringdateincludingruns.Run
import com.mdasrafulalam.cricwave.model.stages.Series
import com.mdasrafulalam.cricwave.model.standings.AllStandings

@Database(entities = [Data::class,
    com.mdasrafulalam.cricwave.model.countries.Data::class,
    com.mdasrafulalam.cricwave.model.leagues.Data::class,
    com.mdasrafulalam.cricwave.model.players.Data::class,
    com.mdasrafulalam.cricwave.model.scores.Data::class,
    com.mdasrafulalam.cricwave.model.seasons.Data::class,
    Series::class,
    com.mdasrafulalam.cricwave.model.officials.Data::class,
    com.mdasrafulalam.cricwave.model.teams.Data::class,
    com.mdasrafulalam.cricwave.model.venues.Data::class,
    AllStandings::class,
    Run::class,
    com.mdasrafulalam.cricwave.model.playerswithteams.Data::class] , version = 1, exportSchema = false)
@TypeConverters(PositionTypeConverterPlayerWithTeams::class,
    RecentFormTypeConverter::class)
abstract class CricketDatabase: RoomDatabase() {
    abstract fun getCricketDao(): CricketDao
    companion object {
        private var db: CricketDatabase? = null
        fun getDB(context: Context): CricketDatabase {
            if (db == null) {
                db = Room.databaseBuilder(
                    context.applicationContext,
                    CricketDatabase::class.java,
                    "cricket_db"
                ).build()
                return db!!
            }
            return db!!
        }
    }
}