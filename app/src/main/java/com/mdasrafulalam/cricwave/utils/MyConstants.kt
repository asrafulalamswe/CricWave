package com.mdasrafulalam.cricwave.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.os.Handler
import androidx.lifecycle.MutableLiveData
import com.mdasrafulalam.cricwave.BuildConfig
import com.mdasrafulalam.cricwave.R
import com.mdasrafulalam.cricwave.model.fixturesfilteringdateincludingruns.Run
import com.mdasrafulalam.cricwave.model.stages.Series
import com.mdasrafulalam.cricwave.model.standings.AllStandings
import com.mdasrafulalam.cricwave.model.teams.Data
import org.aviran.cookiebar2.CookieBar

class MyConstants {
    companion object {
        val handler = Handler()
        // Name of Notification Channel for verbose notifications of background work
        @JvmField
        val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence =
            "News Notifications"
        const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
            "Shows notifications whenever work starts"
        @JvmField
        val NOTIFICATION_TITLE: CharSequence = "Coming Next!"
        const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
        const val NOTIFICATION_ID = 1

        //no network
        fun noNetwork(activity:Activity){
            CookieBar.build(activity)
                .setTitle("No Active Internet!")
                .setIcon(R.drawable.alert)
                .setBackgroundColor(R.color.swipe_color_4)
                .setCookiePosition(CookieBar.TOP)
                .setDuration(5000) // 3 seconds
                .show()
        }
        //API KEY
        const val API_KEY = BuildConfig.API_KEY
//        const val API_KEY = "dQpsY5Q8GUBBrHhCE9Dfrc9wU79kAmV6xyj0308tC8zSaBkS0hq9Kuk0kjLy"
//        const val PRE_API_KEY = BuildConfig.PRE_API_KEY
//        const val PRE_API_KEY = "qEx13JbWRD8rtNU6dGUIpQIx6aHxawElzDxMs4EkGNpOMTrvqyjqZSHQdZIK"
         var ALL_TEAMS = MutableLiveData<List<Data>>()
        var ALL_STANDINGSBYSESON = MutableLiveData<List<AllStandings>>()
         var ALL_VENUES = MutableLiveData<List<com.mdasrafulalam.cricwave.model.venues.Data>>()
         var ALL_OFFICIALS = MutableLiveData<List<com.mdasrafulalam.cricwave.model.officials.Data>>()
         var ALL_PLAYERS = MutableLiveData<List<com.mdasrafulalam.cricwave.model.players.Data>>()
         var ALL_COUNTRIES=MutableLiveData<List<com.mdasrafulalam.cricwave.model.countries.Data>>()
        var ALL_FIXTURES = MutableLiveData<List<com.mdasrafulalam.cricwave.model.fixturesfilteringdateincludingruns.Data>>()

        var ALL_UpcomingFIXTURES= MutableLiveData<List<com.mdasrafulalam.cricwave.model.fixturesfilteringdateincludingruns.Data>>()
         var ALL_PLAYERSWITHTEAM = MutableLiveData<List<com.mdasrafulalam.cricwave.model.playerswithteams.Data>>()
        var seasonIdList:List<Int> = listOf()
        var ALL_STAGES = MutableLiveData<List<Series>>()
        var ALL_SEASONS = MutableLiveData<List<com.mdasrafulalam.cricwave.model.seasons.Data>>()
        var ALL_RUNS = MutableLiveData<List<Run>>()
        var ALL_SCORES =MutableLiveData<List<com.mdasrafulalam.cricwave.model.scores.Data>>()
        var CURRENT_PLAYER_ID = 0
        //API END POINTS
        const val API_EP = "api_token"
        const val CONTINENT_EP = "continents"
        const val COUNTRIES_EP = "countries"
        const val LEAGUE_EP = "leagues"
        const val SEASONS_EP = "seasons"
        const val FIXTURES_EP = "fixtures"
        const val LIVESCORES_EP = "livescores"
        const val TEAMS_EP = "teams"
        const val PLAYERS_EP = "players"
        const val OFFICIALS_EP = "officials"
        const val VENUES_EP = "venues"
        const val STAGES_EP = "stages"
        const val TEAM_RANKING_EP = "team-rankings"
        const val STD_BY_SEASON_EP = "standings/season/{SEASON_ID}"
        const val SCORES_EP = "scores"
        const val PLAYERSBYIDTEAMS_EP = "players/{PLAYER_ID}"

        var IS_FROM_VIEWPAGER = false
        val matchTypeArray = arrayOf(
            "Live",
            "Upcoming",
            "Recent",
        )
        val playerPositionArray = arrayOf(
            "",
            "Batsman",
            "Bowler",
            "Wicketkeeper",
            "Allrounder",
        )
        val playerStatsTypeArray = arrayOf(
            "T20",
            "ODI",
            "TEST",
        )
        val seriesTabArray = arrayOf(
            "Teams",
            "Series",
        )
        val popularT20TabArray = arrayOf(
            "T20 Int.",
            "BBL",
            "CSA T20",
        )
        val rankTypeTabArray = arrayOf(
            "T20",
            "ODI",
            "TEST"
        )
        @SuppressLint("MissingPermission")
        fun verifyAvailableNetwork(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }
}