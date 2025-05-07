package com.mdasrafulalam.cricwave.repository

import androidx.lifecycle.LiveData
import com.mdasrafulalam.cricwave.dao.CricketDao
import com.mdasrafulalam.cricwave.model.fixturesfilteringdateincludingruns.Data
import com.mdasrafulalam.cricwave.model.fixturesfilteringdateincludingruns.Run
import com.mdasrafulalam.cricwave.model.stages.Series
import com.mdasrafulalam.cricwave.model.standings.AllStandings

class CricketRepository(private val dao:CricketDao) {

    suspend fun addFixtures(fixtures: Data) = dao.addFixtures(fixtures)
    suspend fun addRuns(runs: Run) = dao.addRuns(runs)
    suspend fun addPlayersWithTeam(players: com.mdasrafulalam.cricwave.model.playerswithteams.Data) = dao.addPlayersWithTeam(players)
    suspend fun updateFavouriteSeries(stages: Series) = dao.updateFavouriteSeries(stages)
    suspend fun addCountries(country:com.mdasrafulalam.cricwave.model.countries.Data) = dao.addCountries(country)
    suspend fun addLeagues(leagues:com.mdasrafulalam.cricwave.model.leagues.Data) = dao.addLeagues(leagues)
    suspend fun addPlayers(players:com.mdasrafulalam.cricwave.model.players.Data) = dao.addPlayers(players)
    suspend fun addScores(scores:com.mdasrafulalam.cricwave.model.scores.Data) = dao.addScores(scores)
    suspend fun addSeasons(seasons:com.mdasrafulalam.cricwave.model.seasons.Data) = dao.addSeason(seasons)
    suspend fun addStages(series:Series) = dao.addStages(series)
    suspend fun addTeams(teams:com.mdasrafulalam.cricwave.model.teams.Data) = dao.addTeams(teams)
    suspend fun addStandinsBySeason(standings:AllStandings) = dao.addStandings(standings)
    suspend fun addVenues(venues:com.mdasrafulalam.cricwave.model.venues.Data) = dao.addVenues(venues)
    suspend fun addOfficials(officials:com.mdasrafulalam.cricwave.model.officials.Data) = dao.addOfficials(officials)
    suspend fun insertAllFixtures(fixtures: List<Data>) = dao.insertAllFixtures(fixtures)
    suspend fun insertAllRuns(runs: List<Run>) = dao.insertAllRuns(runs)
    suspend fun insertAllTeams(teams: List<com.mdasrafulalam.cricwave.model.teams.Data>) = dao.insertAllTeams(teams)

    suspend fun insertAllVenues(venues: List<com.mdasrafulalam.cricwave.model.venues.Data>) = dao.insertAllVenues(venues)

    suspend fun insertAllOfficials(officials: List<com.mdasrafulalam.cricwave.model.officials.Data>) = dao.insertAllOfficials(officials)

    suspend fun insertAllStandings(standings: List<com.mdasrafulalam.cricwave.model.standings.AllStandings>) = dao.insertAllStandings(standings)

    suspend fun insertAllPlayersWithTeams(players: List<com.mdasrafulalam.cricwave.model.playerswithteams.Data>) = dao.insertAllPlayersWithTeams(players)

    suspend fun insertAllCountries(countries: List<com.mdasrafulalam.cricwave.model.countries.Data>) = dao.insertAllCountries(countries)

    suspend fun insertAllLeagues(leagues: List<com.mdasrafulalam.cricwave.model.leagues.Data>) = dao.insertAllLeagues(leagues)

    suspend fun insertAllPlayers(players: List<com.mdasrafulalam.cricwave.model.players.Data>) = dao.insertAllPlayers(players)

    suspend fun insertAllScores(scores: List<com.mdasrafulalam.cricwave.model.scores.Data>) = dao.insertAllScores(scores)

    suspend fun insertAllSeasons(seasons: List<com.mdasrafulalam.cricwave.model.seasons.Data>) = dao.insertAllSeasons(seasons)

    suspend fun insertAllStages(stages: List<com.mdasrafulalam.cricwave.model.stages.Series>) = dao.insertAllStages(stages)



    fun getAllFixtures() : LiveData<List<Data>> =  dao.getAllFixtures()
    fun getAllRuns() : LiveData<List<Run>> =  dao.getAllRuns()
    fun getAllPlayersWithTeam() : LiveData<List<com.mdasrafulalam.cricwave.model.playerswithteams.Data>> =  dao.getAllPlayersWithTeam()
    fun getAllCountries() : LiveData<List<com.mdasrafulalam.cricwave.model.countries.Data>> =  dao.getAllCountries()
    fun getAllPlayers() : LiveData<List<com.mdasrafulalam.cricwave.model.players.Data>> =  dao.getAllPlayers()
    fun getAllScores() : LiveData<List<com.mdasrafulalam.cricwave.model.scores.Data>> =  dao.getAllScores()
    fun getAllSeasons() : LiveData<List<com.mdasrafulalam.cricwave.model.seasons.Data>> =  dao.getAllSeasons()
    fun getAllStages() : LiveData<List<Series>> =  dao.getAllStages()
    fun getAllTeams() : LiveData<List<com.mdasrafulalam.cricwave.model.teams.Data>> =  dao.getAllTeams()
    fun getAllVenues() : LiveData<List<com.mdasrafulalam.cricwave.model.venues.Data>> =  dao.getAllVenues()
    fun getAllOfficials() : LiveData<List<com.mdasrafulalam.cricwave.model.officials.Data>> =  dao.getAllOfficials()
    fun getAllStandingsBySeason() : LiveData<List<AllStandings>> =  dao.getAllStandingsBySeason()

}