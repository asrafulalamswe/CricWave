package com.mdasrafulalam.cricwave.viewmomdel

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mdasrafulalam.cricwave.database.CricketDatabase
import com.mdasrafulalam.cricwave.model.fixturesbyidincludeballs.Ball
import com.mdasrafulalam.cricwave.model.fixturesfilteringdateincludingruns.Data
import com.mdasrafulalam.cricwave.model.fixtureswithbattingscore.Batting
import com.mdasrafulalam.cricwave.model.fixtureswithbowlingscore.Bowling
import com.mdasrafulalam.cricwave.model.playerinfowithcareerbyid.Career
import com.mdasrafulalam.cricwave.model.stages.Series
import com.mdasrafulalam.cricwave.model.standings.AllStandings
import com.mdasrafulalam.cricwave.model.teamsquadwithteamandseasonid.Squad
import com.mdasrafulalam.cricwave.repository.CricketRepository
import com.mdasrafulalam.cricwave.retrofit.CricketApi
import com.mdasrafulalam.cricwave.utils.MyConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

enum class CricketApiStatus { ERROR, DONE }
class CricketViewModel(application: Application): AndroidViewModel(application) {
//     var menODIList:List<com.mdasrafulalam.cricwave.model.teamrankings.Data> = listOf()
//     var womenODIList:List<com.mdasrafulalam.cricwave.model.teamrankings.Data> = listOf()
//     var menT20List:List<com.mdasrafulalam.cricwave.model.teamrankings.Data> = listOf()
//     var womenT20List:List<com.mdasrafulalam.cricwave.model.teamrankings.Data> = listOf()
//     var menTESTList:List<com.mdasrafulalam.cricwave.model.teamrankings.Data> = listOf()
//    var isFirstTime = true
//    internal var repository: CricketRepository
//    internal val _status = MutableLiveData<CricketApiStatus>()
//    internal val _allFixturesList = MutableLiveData<List<Data>>()
//    internal val _allCountriesList = MutableLiveData<List<com.mdasrafulalam.cricwave.model.countries.Data>>()
//    internal val _allLeaguesList = MutableLiveData<List<com.mdasrafulalam.cricwave.model.leagues.Data>>()
//    internal val _allPlayersList = MutableLiveData<List<com.mdasrafulalam.cricwave.model.players.Data>>()
//    internal val _allPlayersListWithTeam = MutableLiveData<List<com.mdasrafulalam.cricwave.model.playerswithteams.Data>>()
//    internal val _allScoreList = MutableLiveData<List<com.mdasrafulalam.cricwave.model.scores.Data>>()
//    internal val _allSeasonsList = MutableLiveData<List<com.mdasrafulalam.cricwave.model.seasons.Data>>()
//    internal val _allTeamsList = MutableLiveData<List<com.mdasrafulalam.cricwave.model.teams.Data>>()
//    internal val _allVenuesList = MutableLiveData<List<com.mdasrafulalam.cricwave.model.venues.Data>>()
//    internal val _allOfficialsList = MutableLiveData<List<com.mdasrafulalam.cricwave.model.officials.Data>>()
//    internal val _allStagesList = MutableLiveData<List<com.mdasrafulalam.cricwave.model.stages.Data>>()
//    internal val _allStandingsBySeasonList = MutableLiveData<List<com.mdasrafulalam.cricwave.model.standings.Data>>()
//    init {
//        val dao = CricketDatabase.getDB(application).getCricketDao()
//        repository = CricketRepository(dao)
//    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    fun loadAllData(){
//        viewModelScope.launch {
//            try{
//                getFixtures()
//                getCountries()
//                getLeagues()
//                getScores()
//                getSeasons()
//                getStages()
//                getTeams()
//                getVenus()
//                getOfficials()
//                getPlayersWithTeams()
//                getPlayers()
//                Log.d("loadAllData", "loadAllData success: Successful!")
//            }catch (e:Exception){
//                Log.d("loadAllData", "loadAllData exception: ${e.message}")
//            }
//        }
//    }
//
//
    // getPlayersByIdIncludingTeams
//    suspend fun getPlayersByIdIncludingTeams(playerId:Int):List<com.mdasrafulalam.cricwave.model.playerswithteams.Data>{
//       return CricketApi.retrofitService.getPlayersByIdIncludingTeams(playerId,"runs",MyConstants.API_KEY).data
//    }
//    //get livescore
//    suspend fun getLiveScore(): List<com.mdasrafulalam.cricwave.model.livescores.Data>? {
//        return CricketApi.retrofitService.getLiveSores("runs,batting,bowling", MyConstants.API_KEY ).data
//    }
//    //get player career by id
//    suspend fun getPlayerByIdIncludingCareer(playerId:Int):List<Career>{
//        return CricketApi.retrofitService.getPlayerByIdIncludingCareer(playerId, "career", MyConstants.API_KEY).data.career!!
//    }
//



//    //get batting score
//    suspend fun getFixturesByIdIncludingBattingScore(fixtureId: Int): List<Batting>{
//        return  CricketApi.retrofitService.getFixturesByIdIncludingBattingScore(fixtureId,"batting",MyConstants.API_KEY
//        ).data.batting!!
//    }
//    //get bowling score
//    suspend fun getFixturesByIdIncludingBowlingScore(fixtureId: Int):List<Bowling>{
//        return CricketApi.retrofitService.getFixturesByIdIncludingBowlingScore(
//            fixtureId, "bowling", MyConstants.API_KEY
//        ).data.bowling!!
//    }
//    //get fixture including runs
//    suspend fun getFixturesByIdIncludingBalls(fixtureId: Int):List<Ball>{
//        return CricketApi.retrofitService.getFixturesByIdIncludingBallsRuns(fixtureId,"balls",MyConstants.API_KEY
//        ).data?.balls!!
//    }
//
//    suspend fun getFixturesFilteringDateIncludeRuns(filteredDate:String): List<Data>{
//        return CricketApi.retrofitService.getFixturesFilteringDateIncludeRuns(filteredDate,"runs", MyConstants.API_KEY).data!!
//    }
//    //getSquadWithTeamAndSeasonId
//    suspend fun getSquadWithTeamAndSeasonId(teamId: Int?, seasonId: Int?): List<Squad> {
//        return CricketApi.retrofitService.getSquadWithTeamAndSeasonId(
//            teamId!!,
//            seasonId!!,
//            MyConstants.API_KEY
//        ).data.squad
//    }
//
//    //add favourite series into db
//    fun updateBookMark(stages: Series) {
//        viewModelScope.launch {
//            repository.updateFavouriteSeries(stages)
//        }
//    }
//
//    //add fixtures into db
//    private fun addFixturesIntoDB(fixtures: Data) {
//        viewModelScope.launch {
//            repository.addFixtures(fixtures)
//        }
//    }
//
//    //add runs into db
//    private fun addRunsIntoDB(runs: com.mdasrafulalam.cricwave.model.fixturesfilteringdateincludingruns.Run) {
//        viewModelScope.launch {
//            repository.addRuns(runs)
//        }
//    }
//
//
//
//    //add addPlayersWithTeam into db
//    private fun addPlayersWithTeamIntoDB(player: com.mdasrafulalam.cricwave.model.playerswithteams.Data) {
//        viewModelScope.launch {
//            repository.addPlayersWithTeam(player)
//        }
//    }
//
//
//    private fun addCountryIntoDB(country:com.mdasrafulalam.cricwave.model.countries.Data) {
//        viewModelScope.launch {
//            repository.addCountries(country)
//        }
//    }
//    private fun addLeagueIntoDB(leagues:com.mdasrafulalam.cricwave.model.leagues.Data) {
//        viewModelScope.launch {
//            repository.addLeagues(leagues)
//        }
//    }
//    private fun addPlayerIntoDB(player:com.mdasrafulalam.cricwave.model.players.Data) {
//        viewModelScope.launch {
//            repository.addPlayers(player)
//        }
//    }
//    private fun addScoreIntoDB(score:com.mdasrafulalam.cricwave.model.scores.Data) {
//        viewModelScope.launch {
//            repository.addScores(score)
//        }
//    }
//    private fun addSeasonIntoDB(season:com.mdasrafulalam.cricwave.model.seasons.Data) {
//        viewModelScope.launch {
//            repository.addSeasons(season)
//        }
//    }
//    private fun addStageIntoDB(stages:List<com.mdasrafulalam.cricwave.model.stages.Data>) {
//        Log.d("stagesseries", "stages: ${stages.size}")
//        viewModelScope.launch {
//            for (i in stages){
//                val code = i.code
//                val id = i.id
//                val leagueId = i.league_id
//                val name = i.name
//                val resource = i.resource
//                val seasonId = i.season_id
//                val standings = i.standings
//                val type = i.type
//                val updatedAt = i.updated_at
//                val stage = Series(
//                    code = code, id = id, league_id = leagueId, name = name, resource = resource, season_id = seasonId, standings = standings, type = type, updated_at = updatedAt)
//                repository.addStages(stage)
//            }
//
//        }
//    }
//    private fun addStandingIntoDB(standing: MutableLiveData<List<com.mdasrafulalam.cricwave.model.standings.Data>>) {
//        viewModelScope.launch {
//            for(i in standing.value!!){
//                val draw = i.draw
//                val league_id = i.league_id
//                val lost = i.lost
//                val netto_run_rate = i.netto_run_rate
//                val noresult = i.noresult
//                val overs_against = i.overs_against
//                val overs_for = i.overs_for
//                val played = i.played
//                val points = i.points
//                val position = i.position
//                val recent_form = i.recent_form
//                val resource = i.resource
//                val runs_against = i.runs_against
//                val runs_for = i.runs_for
//                val season_id = i.season_id
//                val stage_id = i.stage_id
//                val team_id = i.team_id
//                val updated_at = i.updated_at
//                val won = i.won
//                val newStanding = AllStandings(
//                    draw = draw,
//                    league_id = league_id,
//                    lost = lost,
//                    netto_run_rate = netto_run_rate,
//                    noresult = noresult,
//                    overs_against = overs_against,
//                    overs_for = overs_for,
//                    played = played,
//                    points = points,
//                    position = position,
//                    recent_form = recent_form,
//                    resource = resource,
//                    runs_against = runs_against,
//                    runs_for =  runs_for,
//                    season_id = season_id,
//                    stage_id = stage_id,
//                    team_id = team_id,
//                    updated_at = updated_at,
//                    won = won
//                )
//                try {
//                    repository.addStandinsBySeason(newStanding)
//                } catch (e: Exception) {
//                    // handle the exception here
//                }
//            }
//        }
//    }
//    private fun addTeamsIntoDB(stage:com.mdasrafulalam.cricwave.model.teams.Data) {
//        viewModelScope.launch {
//            repository.addTeams(stage)
//        }
//    }
//    private fun addVenusIntoDB(venu:com.mdasrafulalam.cricwave.model.venues.Data) {
//        viewModelScope.launch {
//            repository.addVenues(venu)
//        }
//    }
//    private fun addOfficialsIntoDB(official:com.mdasrafulalam.cricwave.model.officials.Data) {
//        viewModelScope.launch {
//            repository.addOfficials(official)
//        }
//    }
//
//    private var currentPage = 1
//    private val allFixturesList = CopyOnWriteArrayList<Data>()
//    //fetch from api
//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun getFixtures(){
//        viewModelScope.launch {
//            try { var fixtures: FixturesFilterDateIncludeRuns
//                val currentDate = LocalDate.now()
//                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
//                val formattedCurrentDate = currentDate.format(formatter)
//                val startdate = currentDate.minusMonths(1)
//                val formattedStartDate = startdate.format(formatter)
//                val endtdate = currentDate.plusMonths(1)
//                val formattedEndDate = endtdate.format(formatter)
//                var list: List<Data> = emptyList()
//                do {
//                    fixtures = CricketApi.retrofitService.getFixtures("$formattedStartDate,$formattedEndDate", "runs", MyConstants.API_KEY,currentPage)
//                    if (fixtures.data?.isEmpty()== true){
//                        Log.d("getFixtures", "getFixtures: no data available")
//                    }
//                    _allFixturesList.value = fixtures.data!!
//                    currentPage++
//                    allFixturesList.addAll(fixtures.data ?: emptyList())
//                    _allFixturesList.value = allFixturesList
//                    _status.value = CricketApiStatus.DONE
//                    Log.d("getFixturesinotdbdate", "Fixtures: ${list.size}, time: $formattedStartDate, $formattedEndDate")
//                    Log.d("getFixturesinotdb", "list: ${_allFixturesList.value!!.size}")
//                    list = _allFixturesList.value!!
//                    if (list.isNotEmpty()){
//                        viewModelScope.launch (Dispatchers.IO){
//                            synchronized(allFixturesList) {
//                            for (i in list){
//                                Log.d("getFixturesinotdb", "getFixtures: addinto db : $i ")
//                                addFixturesIntoDB(i)
//                                if (i.runs != null){
//                                    val run =i.runs
//                                    if (run != null) {
//                                        for (j in run){
//                                            addRunsIntoDB(j)
//                                        }
//                                    }
//                                }
//                            }
//                            }
//                        }
//                    }
//                }while (fixtures.meta?.current_page != fixtures.meta?.total)
//                MyConstants.ALL_FIXTURES = _allFixturesList
//                val fixturesList = MyConstants.ALL_FIXTURES.value
//                MyConstants.ALL_UpcomingFIXTURES.value = fixturesList?.filter { it.status == "NS" }
//
//
//
//            } catch (e: Exception) {
//                _status.value = CricketApiStatus.ERROR
//                Log.d("fixtures", "Fixtures: $e")
//                _allFixturesList.value = listOf()
//            }
//        }
//    }
//    //fetch from api
//    private fun getPlayersWithTeams(){
//        viewModelScope.launch {
//            try {
//                _allPlayersListWithTeam.value = CricketApi.retrofitService.getPlayersIncludingTeams("teams", MyConstants.API_KEY).data
//                val list: List<com.mdasrafulalam.cricwave.model.playerswithteams.Data> =
//                    _allPlayersListWithTeam.value!!
//                _status.value = CricketApiStatus.DONE
//                Log.d("playerswithteamdata", "playerswithteam: ${list.size}")
//                Log.d("playerswithteamdata", "list: ${_allPlayersListWithTeam.value!!.size}")
//                if (list.isNotEmpty()){
//                    viewModelScope.launch (Dispatchers.IO){
//                        for (i in list){
//                            addPlayersWithTeamIntoDB(i)
//                        }
//                    }
//                }
//            } catch (e: Exception) {
//                _status.value = CricketApiStatus.ERROR
//                Log.d("playerswithteamdata", "Fixtures: $e")
//                _allPlayersListWithTeam.value = listOf()
//            }
//        }
//    }
//    private fun getCountries(){
//        viewModelScope.launch {
//            try {
//                _allCountriesList.value = CricketApi.retrofitService.getAllCountries(
//                    MyConstants.API_KEY
//                ).data
//                val list: List<com.mdasrafulalam.cricwave.model.countries.Data> = _allCountriesList.value!!
//                _status.value = CricketApiStatus.DONE
//                Log.d("countries", "countries: ${list.size}")
//                Log.d("countries", "list: ${_allCountriesList.value!!.size}")
//                if (list.isNotEmpty()){
//                    viewModelScope.launch (Dispatchers.IO){
//                        for (i in list){
//                            addCountryIntoDB(i)
//                        }
//                    }
//                }
//            } catch (e: Exception) {
//                _status.value = CricketApiStatus.ERROR
//                Log.d("countries", "countries: $e")
//                _allCountriesList.value = listOf()
//            }
//        }
//    }
//    private fun getLeagues(){
//        viewModelScope.launch {
//            try {
//                _allLeaguesList.value = CricketApi.retrofitService.getAllLeagues(
//                    MyConstants.API_KEY
//                ).data
//                val list: List<com.mdasrafulalam.cricwave.model.leagues.Data> = _allLeaguesList.value!!
//                _status.value = CricketApiStatus.DONE
//                Log.d("leagues", "leagues: ${list.size}")
//                Log.d("leagues", "list: ${_allLeaguesList.value!!.size}")
//                if (list.isNotEmpty()){
//                    viewModelScope.launch (Dispatchers.IO){
//                        for (i in list){
//                            addLeagueIntoDB(i)
//                        }
//                    }
//                }
//            } catch (e: Exception) {
//                _status.value = CricketApiStatus.ERROR
//                Log.d("leagues", "leagues: $e")
//                _allLeaguesList.value = listOf()
//            }
//        }
//    }
//    private fun getPlayers(){
//        viewModelScope.launch {
//            try {
//                _allPlayersList.value = CricketApi.retrofitService.getAllPlayers("career", "id,fullname,country_id,image_path",MyConstants.API_KEY).data
//                val list: List<com.mdasrafulalam.cricwave.model.players.Data> = _allPlayersList.value!!
//                _status.value = CricketApiStatus.DONE
//                Log.d("players", "players: ${list.size}")
//                Log.d("players", "list: ${_allPlayersList.value!!.size}")
//                if (list.isNotEmpty()){
//                    viewModelScope.launch (Dispatchers.IO){
//                        for (i in list){
//                            addPlayerIntoDB(i)
//                        }
//                    }
//                }
//            } catch (e: Exception) {
//                _status.value = CricketApiStatus.ERROR
//                Log.d("players", "players: $e")
//                _allPlayersList.value = listOf()
//            }
//        }
//    }
//    private fun getScores(){
//        viewModelScope.launch {
//            try {
//                _allScoreList.value = CricketApi.retrofitService.getAllScores(
//                    MyConstants.API_KEY
//                ).data
//                val list: List<com.mdasrafulalam.cricwave.model.scores.Data> = _allScoreList.value!!
//                _status.value = CricketApiStatus.DONE
//                Log.d("scores", "scores: ${list.size}")
//                Log.d("scores", "list: ${_allScoreList.value!!.size}")
//                if (list.isNotEmpty()){
//                    viewModelScope.launch (Dispatchers.IO){
//                        for (i in list){
//                            addScoreIntoDB(i)
//                        }
//                    }
//                }
//            } catch (e: Exception) {
//                _status.value = CricketApiStatus.ERROR
//                Log.d("scores", "scores: $e")
//                _allScoreList.value = listOf()
//            }
//        }
//    }
//    private fun getSeasons(){
//        viewModelScope.launch {
//            try {
//                _allSeasonsList.value = CricketApi.retrofitService.getAllSeasons(
//                    MyConstants.API_KEY
//                ).data
//                val list: List<com.mdasrafulalam.cricwave.model.seasons.Data> = _allSeasonsList.value!!
//                _status.value = CricketApiStatus.DONE
//                Log.d("seasons", "seasons: ${list.size}")
//                Log.d("seasons", "seasons: ${_allSeasonsList.value!!.size}")
//                if (list.isNotEmpty()){
//                    viewModelScope.launch (Dispatchers.IO){
//                        for (i in list){
//                            addSeasonIntoDB(i)
//                        }
//                    }
//                }
//            } catch (e: Exception) {
//                _status.value = CricketApiStatus.ERROR
//                Log.d("seasons", "seasons: $e")
//                _allSeasonsList.value = listOf()
//            }
//        }
//    }
//    private fun getStages(){
//        viewModelScope.launch {
//            try {
//                _allStagesList.value = CricketApi.retrofitService.getAllStages(
//                    "-updated_at",MyConstants.API_KEY
//                ).data
//                val list: List<com.mdasrafulalam.cricwave.model.stages.Data> = _allStagesList.value!!
//                _status.value = CricketApiStatus.DONE
//                Log.d("stagesseries", "stages: ${list.size}")
//                Log.d("stagesseries", "stages: ${_allStagesList.value!!.size}")
//                if (list.isNotEmpty()){
//                    viewModelScope.launch (Dispatchers.IO){
//                        addStageIntoDB(list)
//                    }
//                }
//            } catch (e: Exception) {
//                _status.value = CricketApiStatus.ERROR
//                Log.d("stagesseries", "stages: $e")
//                _allStagesList.value = listOf()
//            }
//        }
//    }
//    private fun getTeams(){
//        viewModelScope.launch {
//            try {
//                _allTeamsList.value = CricketApi.retrofitService.getAllTeams(
//                    MyConstants.API_KEY
//                ).data
//                val list: List<com.mdasrafulalam.cricwave.model.teams.Data> = _allTeamsList.value!!
//                _status.value = CricketApiStatus.DONE
//                Log.d("teams", "teams: ${list.size}")
//                Log.d("teams", "teams: ${_allTeamsList.value!!.size}")
//                if (list.isNotEmpty()){
//                    viewModelScope.launch (Dispatchers.IO){
//                        for (i in list){
//                            addTeamsIntoDB(i)
//                        }
//                    }
//                }
//            } catch (e: Exception) {
//                _status.value = CricketApiStatus.ERROR
//                Log.d("stages", "stages: $e")
//                _allStagesList.value = listOf()
//            }
//        }
//    }
//    private fun getVenus(){
//        viewModelScope.launch {
//            try {
//                _allVenuesList.value = CricketApi.retrofitService.getAllVenues(
//                    MyConstants.API_KEY
//                ).data
//                val list: List<com.mdasrafulalam.cricwave.model.venues.Data> = _allVenuesList.value!!
//                _status.value = CricketApiStatus.DONE
//                Log.d("venues", "venues: ${list.size}")
//                Log.d("venues", "venues: ${_allVenuesList.value!!.size}")
//                if (list.isNotEmpty()){
//                    viewModelScope.launch (Dispatchers.IO){
//                        for (i in list){
//                            addVenusIntoDB(i)
//                        }
//                    }
//                }
//            } catch (e: Exception) {
//                _status.value = CricketApiStatus.ERROR
//                Log.d("venues", "venuesError: $e")
//                _allVenuesList.value = listOf()
//            }
//        }
//    }
//    private fun getOfficials(){
//        viewModelScope.launch {
//            try {
//                _allOfficialsList.value = CricketApi.retrofitService.getAllOfficials(MyConstants.API_KEY).data
//                val list: List<com.mdasrafulalam.cricwave.model.officials.Data> = _allOfficialsList.value!!
//                _status.value = CricketApiStatus.DONE
//                Log.d("officials", "officials: ${list.size}")
//                Log.d("officials", "officials: ${_allOfficialsList.value!!.size}")
//                if (list.isNotEmpty()){
//                    viewModelScope.launch (Dispatchers.IO){
//                        for (i in list){
//                            addOfficialsIntoDB(i)
//                        }
//                    }
//                }
//            } catch (e: Exception) {
//                _status.value = CricketApiStatus.ERROR
//                Log.d("officials", "venuesError: $e")
//                _allOfficialsList.value = listOf()
//            }
//        }
//    }
//    private fun getStandings(seasonId:Int){
//        viewModelScope.launch {
//            try {
//                _allStandingsBySeasonList.value = CricketApi.retrofitService.getAllStandingBySeason(seasonId,
//                    MyConstants.API_KEY
//                ).data
//                val list: List<com.mdasrafulalam.cricwave.model.standings.Data> =
//                    _allStandingsBySeasonList.value!!
//                _status.value = CricketApiStatus.DONE
//                Log.d("standing", "stages: ${list.size}")
//                Log.d("stages", "stages: ${_allStandingsBySeasonList.value!!.size}")
//                if (list.isNotEmpty()){
//                    viewModelScope.launch (Dispatchers.IO){
//                        addStandingIntoDB(_allStandingsBySeasonList)
//                    }
//                }
//            } catch (e: Exception) {
//                _status.value = CricketApiStatus.ERROR
//                Log.d("stages", "stages: $e")
//                _allStandingsBySeasonList.value = listOf()
//            }
//        }
//    }
//
//    fun getAllStandingsWithSeasnIdandsaveIntoDB(seasonId: Int){
//        getStandings(seasonId)
//    }
//    //fetch from database
//
//    fun getAllFixtures(): LiveData<List<Data>> = repository.getAllFixtures()
//    fun getAllRuns(): LiveData<List<com.mdasrafulalam.cricwave.model.fixturesfilteringdateincludingruns.Run>> = repository.getAllRuns()
//    fun getAllPlayersWithTeam(): LiveData<List<com.mdasrafulalam.cricwave.model.playerswithteams.Data>> = repository.getAllPlayersWithTeam()
//    fun getAllCountries(): LiveData<List<com.mdasrafulalam.cricwave.model.countries.Data>> = repository.getAllCountries()
//    fun getAllPlayers(): LiveData<List<com.mdasrafulalam.cricwave.model.players.Data>> = repository.getAllPlayers()
//    fun getAllScores(): LiveData<List<com.mdasrafulalam.cricwave.model.scores.Data>> = repository.getAllScores()
//    fun getAllSeasons(): LiveData<List<com.mdasrafulalam.cricwave.model.seasons.Data>> = repository.getAllSeasons()
//    fun getAllStages(): LiveData<List<Series>> = repository.getAllStages()
//    fun getAllTeams(): LiveData<List<com.mdasrafulalam.cricwave.model.teams.Data>> = repository.getAllTeams()
//    fun getAllVenues(): LiveData<List<com.mdasrafulalam.cricwave.model.venues.Data>> = repository.getAllVenues()
//    fun getAllOfficials(): LiveData<List<com.mdasrafulalam.cricwave.model.officials.Data>> = repository.getAllOfficials()
//    fun getAllStandingsBySeason(): LiveData<List<AllStandings>> = repository.getAllStandingsBySeason()


    private val _status = MutableLiveData<CricketApiStatus>()
    private val repository : CricketRepository
    val status: LiveData<CricketApiStatus> get() = _status

    private val _allFixturesList = MutableLiveData<List<Data>>()
    val allFixturesList: LiveData<List<Data>> get() = _allFixturesList

    var menODIList:List<com.mdasrafulalam.cricwave.model.teamrankings.Data> = listOf()
     var womenODIList:List<com.mdasrafulalam.cricwave.model.teamrankings.Data> = listOf()
     var menT20List:List<com.mdasrafulalam.cricwave.model.teamrankings.Data> = listOf()
     var womenT20List:List<com.mdasrafulalam.cricwave.model.teamrankings.Data> = listOf()
     var menTESTList:List<com.mdasrafulalam.cricwave.model.teamrankings.Data> = listOf()

    private val _allPlayersListWithTeam = MutableLiveData<List<com.mdasrafulalam.cricwave.model.playerswithteams.Data>>()
    private val _allCountriesList = MutableLiveData<List<com.mdasrafulalam.cricwave.model.countries.Data>>()
    private val _allPlayersList = MutableLiveData<List<com.mdasrafulalam.cricwave.model.players.Data>>()
    private val _allScoreList = MutableLiveData<List<com.mdasrafulalam.cricwave.model.scores.Data>>()
    private val _allStagesList = MutableLiveData<List<com.mdasrafulalam.cricwave.model.stages.Data>>()
    private val _allSeasonsList = MutableLiveData<List<com.mdasrafulalam.cricwave.model.seasons.Data>>()
    private val _allTeamsList = MutableLiveData<List<com.mdasrafulalam.cricwave.model.teams.Data>>()
    private val _allVenuesList = MutableLiveData<List<com.mdasrafulalam.cricwave.model.venues.Data>>()
    private val _allOfficialsList = MutableLiveData<List<com.mdasrafulalam.cricwave.model.officials.Data>>()
    private val _allLeaguesList = MutableLiveData<List<com.mdasrafulalam.cricwave.model.leagues.Data>>()
    private val _allStandingsBySeasonList = MutableLiveData<List<com.mdasrafulalam.cricwave.model.standings.Data>>()

    init {
        val dao = CricketDatabase.getDB(application).getCricketDao()
        repository = CricketRepository(dao)
    }

    //add favourite series into db
    fun updateBookMark(stages: Series) {
        viewModelScope.launch {
            repository.updateFavouriteSeries(stages)
        }

    }

    // getPlayersByIdIncludingTeams
    suspend fun getPlayersByIdIncludingTeams(playerId:Int):List<com.mdasrafulalam.cricwave.model.playerswithteams.Data>{
       return CricketApi.retrofitService.getPlayersByIdIncludingTeams(playerId,"runs",MyConstants.API_KEY).data
    }
    //get livescore
    suspend fun getLiveScore(): List<com.mdasrafulalam.cricwave.model.livescores.Data>? {
        return CricketApi.retrofitService.getLiveSores("runs,batting,bowling", MyConstants.API_KEY ).data
    }
    //get player career by id
    suspend fun getPlayerByIdIncludingCareer(playerId:Int):List<Career>{
        return CricketApi.retrofitService.getPlayerByIdIncludingCareer(playerId, "career", MyConstants.API_KEY).data.career!!
    }




    //get batting score
    suspend fun getFixturesByIdIncludingBattingScore(fixtureId: Int): List<Batting>{
        return  CricketApi.retrofitService.getFixturesByIdIncludingBattingScore(fixtureId,"batting",MyConstants.API_KEY
        ).data.batting!!
    }
    //get bowling score
    suspend fun getFixturesByIdIncludingBowlingScore(fixtureId: Int):List<Bowling>{
        return CricketApi.retrofitService.getFixturesByIdIncludingBowlingScore(
            fixtureId, "bowling", MyConstants.API_KEY
        ).data.bowling!!
    }
    //get fixture including runs
    suspend fun getFixturesByIdIncludingBalls(fixtureId: Int):List<Ball>{
        return CricketApi.retrofitService.getFixturesByIdIncludingBallsRuns(fixtureId,"balls",MyConstants.API_KEY
        ).data?.balls!!
    }

    suspend fun getFixturesFilteringDateIncludeRuns(filteredDate:String): List<Data>{
        return CricketApi.retrofitService.getFixturesFilteringDateIncludeRuns(filteredDate,"runs", MyConstants.API_KEY).data!!
    }
    //getSquadWithTeamAndSeasonId
    suspend fun getSquadWithTeamAndSeasonId(teamId: Int?, seasonId: Int?): List<Squad> {
        return CricketApi.retrofitService.getSquadWithTeamAndSeasonId(
            teamId!!,
            seasonId!!,
            MyConstants.API_KEY
        ).data.squad
    }

    //get ranking using gender and type filter
    suspend fun getRankingByGenderAndTournamentType(tournamentType:String,gender:String): List<com.mdasrafulalam.cricwave.model.teamrankings.Data>{
        return CricketApi.retrofitService.getRankingByGenderAndTournamentType(tournamentType,gender,MyConstants.API_KEY).data
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getFixtures() {
        viewModelScope.launch {
            try {
                val currentDate = LocalDate.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val startDate = currentDate.minusMonths(1).format(formatter)
                val endDate = currentDate.plusMonths(1).format(formatter)

                val allFixtures = mutableListOf<Data>()
                val allRuns = mutableListOf<com.mdasrafulalam.cricwave.model.fixturesfilteringdateincludingruns.Run>()
                var page = 1

                while (true) {
                    val response = CricketApi.retrofitService.getFixtures(
                        "$startDate,$endDate", "runs", MyConstants.API_KEY, page
                    )

                    val data = response.data ?: emptyList()
                    if (data.isEmpty()) break

                    allFixtures.addAll(data)
                    data.forEach { fixture -> fixture.runs?.let { allRuns.addAll(it) } }

                    if (response.meta?.current_page == response.meta?.total) break
                    page++
                }

                _allFixturesList.value = allFixtures
                MyConstants.ALL_FIXTURES.value = allFixtures
                MyConstants.ALL_UpcomingFIXTURES.value = allFixtures.filter { it.status == "NS" }
                _status.value = CricketApiStatus.DONE

                viewModelScope.launch(Dispatchers.IO) {
                    repository.insertAllFixtures(allFixtures)
                    repository.insertAllRuns(allRuns)
                }

            } catch (e: Exception) {
                _status.value = CricketApiStatus.ERROR
                _allFixturesList.value = listOf()
                Log.e("getFixtures", "Error fetching fixtures: $e")
            }
        }
    }

    private inline fun <T> fetchAndSave(
        crossinline fetch: suspend () -> List<T>,
        crossinline save: suspend (List<T>) -> Unit,
        liveData: MutableLiveData<List<T>>,
        tag: String
    ) {
        viewModelScope.launch {
            try {
                val data = fetch()
                liveData.value = data
                _status.value = CricketApiStatus.DONE
                Log.d(tag, "$tag size: ${data.size}")
                if (data.isNotEmpty()) {
                    withContext(Dispatchers.IO) {
                        save(data)
                    }
                }
            } catch (e: Exception) {
                _status.value = CricketApiStatus.ERROR
                liveData.value = listOf()
                Log.e(tag, "$tag error: $e")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkAndLoadDataIfNeeded() {
        viewModelScope.launch(Dispatchers.IO) {
            val fixtures = repository.getAllFixtures().value
            val runs = repository.getAllRuns().value
            val playersWithTeam = repository.getAllPlayersWithTeam().value
            val countries = repository.getAllCountries().value
//            val leagues = repository.getAllLeagues().value
            val players = repository.getAllPlayers().value
            val scores = repository.getAllScores().value
            val seasons = repository.getAllSeasons().value
            val stages = repository.getAllStages().value
            val teams = repository.getAllTeams().value
            val venues = repository.getAllVenues().value
            val officials = repository.getAllOfficials().value
            val standings = repository.getAllStandingsBySeason().value

            if (
                fixtures.isNullOrEmpty() ||
                runs.isNullOrEmpty() ||
                playersWithTeam.isNullOrEmpty() ||
                countries.isNullOrEmpty() ||
//                leagues.isNullOrEmpty() ||
                players.isNullOrEmpty() ||
                scores.isNullOrEmpty() ||
                seasons.isNullOrEmpty() ||
                stages.isNullOrEmpty() ||
                teams.isNullOrEmpty() ||
                venues.isNullOrEmpty() ||
                officials.isNullOrEmpty() ||
                standings.isNullOrEmpty()
            ) {
                fetchAndInsertAllDataFromApi() // your function to load data from API
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchAndInsertAllDataFromApi() {
        getPlayersWithTeams()
        getCountries()
        getLeagues()
        getPlayers()
        getScores()
        getSeasons()
        getStages()
        getTeams()
        getVenues()
        getOfficials()
        getFixtures()
    }

    private fun getPlayersWithTeams() = fetchAndSave(
        fetch = { CricketApi.retrofitService.getPlayersIncludingTeams("teams", MyConstants.API_KEY).data ?: emptyList() },
        save = { repository.insertAllPlayersWithTeams(it) },
        liveData = _allPlayersListWithTeam,
        tag = "PlayersWithTeams"
    )

    private fun getCountries() = fetchAndSave(
        fetch = { CricketApi.retrofitService.getAllCountries(MyConstants.API_KEY).data ?: emptyList() },
        save = { repository.insertAllCountries(it) },
        liveData = _allCountriesList,
        tag = "Countries"
    )

    private fun getLeagues() = fetchAndSave(
        fetch = { CricketApi.retrofitService.getAllLeagues(MyConstants.API_KEY).data ?: emptyList() },
        save = { repository.insertAllLeagues(it) },
        liveData = _allLeaguesList,
        tag = "Leagues"
    )

    fun getPlayers() = fetchAndSave(
        fetch = {
            CricketApi.retrofitService.getAllPlayers(
                "career", "id,fullname,country_id,image_path", MyConstants.API_KEY
            ).data ?: emptyList()
        },
        save = { repository.insertAllPlayers(it) },
        liveData = _allPlayersList,
        tag = "Players"
    )

    fun getScores() = fetchAndSave(
        fetch = { CricketApi.retrofitService.getAllScores(MyConstants.API_KEY).data ?: emptyList() },
        save = { repository.insertAllScores(it) },
        liveData = _allScoreList,
        tag = "Scores"
    )

    fun getSeasons() = fetchAndSave(
        fetch = { CricketApi.retrofitService.getAllSeasons(MyConstants.API_KEY).data ?: emptyList() },
        save = { repository.insertAllSeasons(it) },
        liveData = _allSeasonsList,
        tag = "Seasons"
    )

    private fun getStages() = fetchAndSave(
        fetch = { CricketApi.retrofitService.getAllStages("-updated_at", MyConstants.API_KEY).data ?: emptyList() },
        save = { stageDataList ->
            val stages = stageDataList.map { stage ->
                Series(
                    isBookMarked = false,
                    code = stage.code,
                    id = stage.id,
                    league_id = stage.league_id,
                    name = stage.name,
                    resource = stage.resource,
                    season_id = stage.season_id,
                    standings = stage.standings,
                    type = stage.type,
                    updated_at = stage.updated_at
                )
            }
            repository.insertAllStages(stages)
        },
        liveData = _allStagesList,
        tag = "Stages"
    )

    fun getTeams() = fetchAndSave(
        fetch = { CricketApi.retrofitService.getAllTeams(MyConstants.API_KEY).data ?: emptyList() },
        save = { repository.insertAllTeams(it) },
        liveData = _allTeamsList,
        tag = "Teams"
    )

    fun getVenues() = fetchAndSave(
        fetch = { CricketApi.retrofitService.getAllVenues(MyConstants.API_KEY).data ?: emptyList() },
        save = { repository.insertAllVenues(it) },
        liveData = _allVenuesList,
        tag = "Venues"
    )

    fun getOfficials() = fetchAndSave(
        fetch = { CricketApi.retrofitService.getAllOfficials(MyConstants.API_KEY).data ?: emptyList() },
        save = { repository.insertAllOfficials(it) },
        liveData = _allOfficialsList,
        tag = "Officials"
    )

    fun getStandings(seasonId: Int) = fetchAndSave(
        fetch = { CricketApi.retrofitService.getAllStandingBySeason(seasonId, MyConstants.API_KEY).data ?: emptyList() },
        save = { dataList ->
            val allStandings = dataList.map { item ->
                AllStandings(
                    draw = item.draw,
                    league_id = item.league_id,
                    lost = item.lost,
                    netto_run_rate = item.netto_run_rate,
                    noresult = item.noresult,
                    overs_against = item.overs_against,
                    overs_for = item.overs_for,
                    played = item.played,
                    points = item.points,
                    position = item.position,
                    recent_form = item.recent_form,
                    resource = item.resource,
                    runs_against = item.runs_against,
                    runs_for = item.runs_for,
                    season_id = item.season_id,
                    stage_id = item.stage_id,
                    team_id = item.team_id,
                    updated_at = item.updated_at,
                    won = item.won
                )
            }
            repository.insertAllStandings(allStandings)
        },
        liveData = _allStandingsBySeasonList,
        tag = "Standings"
    )

    fun getAllStandingsWithSeasonIdAndSaveIntoDB(seasonId: Int) = getStandings(seasonId)

    // Fetch from local DB
    fun getAllFixtures(): LiveData<List<Data>> = repository.getAllFixtures()
    fun getAllRuns(): LiveData<List<com.mdasrafulalam.cricwave.model.fixturesfilteringdateincludingruns.Run>> = repository.getAllRuns()
    fun getAllPlayersWithTeam(): LiveData<List<com.mdasrafulalam.cricwave.model.playerswithteams.Data>> = repository.getAllPlayersWithTeam()
    fun getAllCountries(): LiveData<List<com.mdasrafulalam.cricwave.model.countries.Data>> = repository.getAllCountries()
    fun getAllPlayers(): LiveData<List<com.mdasrafulalam.cricwave.model.players.Data>> = repository.getAllPlayers()
    fun getAllScores(): LiveData<List<com.mdasrafulalam.cricwave.model.scores.Data>> = repository.getAllScores()
    fun getAllSeasons(): LiveData<List<com.mdasrafulalam.cricwave.model.seasons.Data>> = repository.getAllSeasons()
    fun getAllStages(): LiveData<List<Series>> = repository.getAllStages()
    fun getAllTeams(): LiveData<List<com.mdasrafulalam.cricwave.model.teams.Data>> = repository.getAllTeams()
    fun getAllVenues(): LiveData<List<com.mdasrafulalam.cricwave.model.venues.Data>> = repository.getAllVenues()
    fun getAllOfficials(): LiveData<List<com.mdasrafulalam.cricwave.model.officials.Data>> = repository.getAllOfficials()
    fun getAllStandingsBySeason(): LiveData<List<AllStandings>> = repository.getAllStandingsBySeason()


}