package com.mdasrafulalam.cricwave.retrofit

import com.mdasrafulalam.cricwave.model.countries.AllCountries
import com.mdasrafulalam.cricwave.model.fixturesbyidincludeballs.FixtureByIdIncludeBalls
import com.mdasrafulalam.cricwave.model.fixturesfilteringdateincludingruns.FixturesFilterDateIncludeRuns
import com.mdasrafulalam.cricwave.model.fixtureswithbattingscore.FixturesWithBattingScore
import com.mdasrafulalam.cricwave.model.fixtureswithbowlingscore.FixturesWithBowling
import com.mdasrafulalam.cricwave.model.leagues.Leagues
import com.mdasrafulalam.cricwave.model.livescores.LiveScore
import com.mdasrafulalam.cricwave.model.officials.Officials
import com.mdasrafulalam.cricwave.model.playerinfowithcareerbyid.PlayerInfoWithCareerById
import com.mdasrafulalam.cricwave.model.players.Players
import com.mdasrafulalam.cricwave.model.playerswithteams.PlayersWithTeams
import com.mdasrafulalam.cricwave.model.scores.Scores
import com.mdasrafulalam.cricwave.model.seasons.Seasons
import com.mdasrafulalam.cricwave.model.stages.Stages
import com.mdasrafulalam.cricwave.model.standings.Standings
import com.mdasrafulalam.cricwave.model.teamrankings.TeamRankings
import com.mdasrafulalam.cricwave.model.teams.Teams
import com.mdasrafulalam.cricwave.model.teamsquadwithteamandseasonid.TeamSqdWithTeamAndSeasonId
import com.mdasrafulalam.cricwave.model.venues.Venues
import com.mdasrafulalam.cricwave.utils.MyConstants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://cricket.sportmonks.com/api/v2.0/"

val client = OkHttpClient.Builder()
    .connectTimeout(2, TimeUnit.MINUTES)
    .readTimeout(2, TimeUnit.MINUTES)
    .writeTimeout(2, TimeUnit.MINUTES)
    .build()

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(client)
    .build()

interface CricketApiService {
    //get live scores
    @GET(MyConstants.LIVESCORES_EP)
    suspend fun getLiveSores(
        @Query("include") include: String,
        @Query(MyConstants.API_EP) apiKey: String) : LiveScore
    //get all fixtures
    @GET(MyConstants.FIXTURES_EP)
    suspend fun getFixtures(@Query("filter[starts_between]") filteredDate: String,
                            @Query("include") include: String,
                            @Query(MyConstants.API_EP) apiKey: String,
                            @Query("page") page: Int,) : FixturesFilterDateIncludeRuns

    //get all fixtures filtering date with runs
    @GET(MyConstants.FIXTURES_EP)
    suspend fun getFixturesFilteringDateIncludeRuns(@Query("filter[starts_between]") filteredDate: String,
                                                    @Query("include") include: String,
                                                    @Query(MyConstants.API_EP) apiKey: String) : FixturesFilterDateIncludeRuns


    //get all country
    @GET(MyConstants.COUNTRIES_EP)
    suspend fun getAllCountries(@Query(
        MyConstants.API_EP) apiKey: String) : AllCountries

    //get all leagues
    @GET(MyConstants.LEAGUE_EP)
    suspend fun getAllLeagues(@Query(
        MyConstants.API_EP) apiKey: String) : Leagues

    //get all players
    @GET(MyConstants.PLAYERS_EP)
    suspend fun getAllPlayers(
        @Query("include") include: String,
        @Query("fields[players]") fields: String,
                              @Query(MyConstants.API_EP) apiKey: String) : Players

    //get all scores
    @GET(MyConstants.SCORES_EP)
    suspend fun getAllScores(@Query(
        MyConstants.API_EP) apiKey: String) : Scores

    //get all seasons
    @GET(MyConstants.SEASONS_EP)
    suspend fun getAllSeasons(@Query(
        MyConstants.API_EP) apiKey: String) : Seasons

    //get all stages
    @GET(MyConstants.STAGES_EP)
    suspend fun getAllStages(
        @Query("sort") sort: String,
        @Query(MyConstants.API_EP) apiKey: String) : Stages

    //get all standings
    @GET(MyConstants.STD_BY_SEASON_EP)
    suspend fun getAllStandingBySeason(
        @Path("SEASON_ID") season_id: Int,
        @Query(MyConstants.API_EP) apiKey: String) : Standings

    //get all teams
    @GET(MyConstants.TEAMS_EP)
    suspend fun getAllTeams(@Query(
        MyConstants.API_EP) apiKey: String) : Teams

    //get all venues
    @GET(MyConstants.VENUES_EP)
    suspend fun getAllVenues(@Query(
        MyConstants.API_EP) apiKey: String) : Venues

    //get all getAllOfficials
    @GET(MyConstants.OFFICIALS_EP)
    suspend fun getAllOfficials(@Query(
        MyConstants.API_EP) apiKey: String) : Officials

    //get fixtures with balls
    @GET("fixtures/{FIXTURE_ID}")
    suspend fun getFixturesByIdIncludingBallsRuns(
        @Path("FIXTURE_ID") fixtureId: Int,
        @Query("include") include: String,
        @Query(MyConstants.API_EP) apiKey: String
    ) : FixtureByIdIncludeBalls

    //get fixtures with batting score
    @GET("fixtures/{FIXTURE_ID}")
    suspend fun getFixturesByIdIncludingBattingScore(
        @Path("FIXTURE_ID") fixtureId: Int,
        @Query("include") include: String,
        @Query(MyConstants.API_EP) apiKey: String
    ) : FixturesWithBattingScore

    //get fixtures with bowling score
    @GET("fixtures/{FIXTURE_ID}")
    suspend fun getFixturesByIdIncludingBowlingScore(
        @Path("FIXTURE_ID") fixtureId: Int,
        @Query("include") include: String,
        @Query(MyConstants.API_EP) apiKey: String
    ) : FixturesWithBowling

    //get team squad with team and season id
    @GET("teams/{TEAM_ID}/squad/{SEASON_ID}")
    suspend fun getSquadWithTeamAndSeasonId(
        @Path("TEAM_ID") teamId: Int,
        @Path("SEASON_ID") seasonId: Int,
        @Query(MyConstants.API_EP) apiKey: String
    ) : TeamSqdWithTeamAndSeasonId

    //get player career by playerId
    @GET(MyConstants.PLAYERSBYIDTEAMS_EP)
    suspend fun getPlayerByIdIncludingCareer(
        @Path("PLAYER_ID") playerId: Int,
        @Query("include") include: String,
        @Query(MyConstants.API_EP) apiKey: String
    ) : PlayerInfoWithCareerById

    //get all players Including teams
    @GET(MyConstants.PLAYERS_EP)
    suspend fun getPlayersIncludingTeams(
        @Query("include") include: String,
        @Query(MyConstants.API_EP) apiKey: String
    ) : PlayersWithTeams

    //getPlayersByIdIncludingTeams
    @GET(MyConstants.PLAYERSBYIDTEAMS_EP)
    suspend fun getPlayersByIdIncludingTeams(
        @Path("PLAYER_ID") playerId: Int,
        @Query("include") include: String,
        @Query(MyConstants.API_EP) apiKey: String
    ) : PlayersWithTeams

    //get ranking using gender and type filter
    @GET(MyConstants.TEAM_RANKING_EP)
    suspend fun getRankingByGenderAndTournamentType(
        @Query("filter[type]") tournamentType: String,
        @Query("filter[gender]") gender: String,
        @Query(MyConstants.API_EP) apiKey: String
    ) : TeamRankings
}

object CricketApi {
    val retrofitService: CricketApiService by lazy { retrofit.create(CricketApiService::class.java) }
}