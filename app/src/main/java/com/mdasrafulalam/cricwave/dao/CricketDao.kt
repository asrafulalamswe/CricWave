package com.mdasrafulalam.cricwave.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mdasrafulalam.cricwave.model.fixturesfilteringdateincludingruns.Data
import com.mdasrafulalam.cricwave.model.fixturesfilteringdateincludingruns.Run
import com.mdasrafulalam.cricwave.model.stages.Series
import com.mdasrafulalam.cricwave.model.standings.AllStandings

@Dao
interface CricketDao {

    @Update
    suspend fun updateFavouriteSeries(series: Series)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFixtures(fixtures: Data)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRuns(runs: Run)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPlayersWithTeam(player: com.mdasrafulalam.cricwave.model.playerswithteams.Data)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCountries(country:com.mdasrafulalam.cricwave.model.countries.Data)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addLeagues(country:com.mdasrafulalam.cricwave.model.leagues.Data)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPlayers(country:com.mdasrafulalam.cricwave.model.players.Data)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addScores(country:com.mdasrafulalam.cricwave.model.scores.Data)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSeason(country:com.mdasrafulalam.cricwave.model.seasons.Data)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addStages(series:Series)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStandings(standings: AllStandings)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTeams(country:com.mdasrafulalam.cricwave.model.teams.Data)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addVenues(country:com.mdasrafulalam.cricwave.model.venues.Data)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addOfficials(country:com.mdasrafulalam.cricwave.model.officials.Data)

    @Query("SELECT * from fixtures_table")
    fun getAllFixtures(): LiveData<List<Data>>

    @Query("SELECT * from runs_table")
    fun getAllRuns(): LiveData<List<Run>>


    @Query("SELECT * from players_with_team_table")
    fun getAllPlayersWithTeam(): LiveData<List<com.mdasrafulalam.cricwave.model.playerswithteams.Data>>

    @Query("SELECT * from country_table")
    fun getAllCountries(): LiveData<List<com.mdasrafulalam.cricwave.model.countries.Data>>

    @Query("SELECT * from players_table")
    fun getAllPlayers(): LiveData<List<com.mdasrafulalam.cricwave.model.players.Data>>

    @Query("SELECT * from score_table")
    fun getAllScores(): LiveData<List<com.mdasrafulalam.cricwave.model.scores.Data>>

    @Query("SELECT * from seasons_table")
    fun getAllSeasons(): LiveData<List<com.mdasrafulalam.cricwave.model.seasons.Data>>

    @Query("SELECT * from stages_table")
    fun getAllStages(): LiveData<List<Series>>

    @Query("SELECT * from standing_table")
    fun getAllStandingsBySeason():  LiveData<List<AllStandings>>

    @Query("SELECT * from teams_table")
    fun getAllTeams(): LiveData<List<com.mdasrafulalam.cricwave.model.teams.Data>>

    @Query("SELECT * from venues_table")
    fun getAllVenues(): LiveData<List<com.mdasrafulalam.cricwave.model.venues.Data>>

    @Query("SELECT * from official_table")
    fun getAllOfficials(): LiveData<List<com.mdasrafulalam.cricwave.model.officials.Data>>

    //get venu info by id
    @Query("SELECT * from teams_table where id = :id")
    fun getVenuInfoById(id:Int): LiveData<com.mdasrafulalam.cricwave.model.teams.Data>


    //updated 05.05.2025
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllFixtures(fixtures: List<Data>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllRuns(runs: List<Run>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllTeams(teams: List<com.mdasrafulalam.cricwave.model.teams.Data>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllVenues(venues: List<com.mdasrafulalam.cricwave.model.venues.Data>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllOfficials(officials: List<com.mdasrafulalam.cricwave.model.officials.Data>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllStandings(standings: List<com.mdasrafulalam.cricwave.model.standings.AllStandings>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllPlayersWithTeams(players: List<com.mdasrafulalam.cricwave.model.playerswithteams.Data>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllCountries(countries: List<com.mdasrafulalam.cricwave.model.countries.Data>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllLeagues(leagues: List<com.mdasrafulalam.cricwave.model.leagues.Data>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllPlayers(players: List<com.mdasrafulalam.cricwave.model.players.Data>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllScores(scores: List<com.mdasrafulalam.cricwave.model.scores.Data>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllSeasons(seasons: List<com.mdasrafulalam.cricwave.model.seasons.Data>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllStages(stages: List<Series>)



}