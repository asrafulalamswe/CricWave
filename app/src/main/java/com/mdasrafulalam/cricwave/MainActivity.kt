package com.mdasrafulalam.cricwave

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.mdasrafulalam.cricwave.databinding.ActivityMainBinding
import com.mdasrafulalam.cricwave.network.NetworkChangeReceiver
import com.mdasrafulalam.cricwave.utils.MyConstants
import com.mdasrafulalam.cricwave.viewmomdel.CricketViewModel
import kotlinx.coroutines.launch
import org.aviran.cookiebar2.CookieBar
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var networkChangeReceiver: NetworkChangeReceiver
    var isListening = false
    private lateinit var viewModel: CricketViewModel
    private lateinit var speechRecognizer: SpeechRecognizer
    private fun isDarkTheme(): Boolean {
        return resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }



    private fun startListening() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

        speechRecognizer.startListening(intent)
        // Check if the switch is still on and if yes, call startListening() again
        if (isListening) {
            Handler().postDelayed({ startListening() }, 1000)
        }
    }

    private fun stopListening() {
        speechRecognizer.stopListening()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val toolbar: Toolbar = findViewById(R.id.mToolbar)
        setSupportActionBar(toolbar)
        viewModel = ViewModelProvider(this)[CricketViewModel::class.java]

        val switchCompat = binding.navigationView.menu.findItem(R.id.nav_item_switch).actionView as SwitchCompat
        val thumbDrawable = ContextCompat.getDrawable(this, R.drawable.switch_selector)
        switchCompat.thumbDrawable = thumbDrawable
        //dark theme
        if (isDarkTheme()){
            switchCompat.isChecked = true
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        }else{
            switchCompat.isChecked = false
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        switchCompat.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (!isDarkTheme()){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    switchCompat.isChecked = true
                }
            } else {
                if (isDarkTheme()){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    switchCompat.isChecked = false
                }
            }
        }


        val voiceSwitch = binding.navigationView.menu.findItem(R.id.nav_item_voice_switch).actionView as SwitchCompat
        if (SpeechRecognizer.isRecognitionAvailable(this)){
            //voice navigation
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
            val recognitionListener = object : RecognitionListener {
                override fun onReadyForSpeech(params: Bundle?) {
                    // Called when the recognizer is ready to receive speech input.
                    Log.d("speechrecognizerintent", "speechrecognizer: onReadyForSpeech")
                }

                override fun onBeginningOfSpeech() {
                    // Called when the user starts speaking.
                    Log.d("speechrecognizerintent", "speechrecognizer: onBeginningOfSpeech")
                }

                override fun onRmsChanged(rmsdB: Float) {
                    // Called when the volume of the user's speech changes.
                    Log.d("speechrecognizerintent", "speechrecognizer: onRmsChanged")
                }

                /**
                 * More sound has been received. The purpose of this function is to allow giving feedback to the
                 * user regarding the captured audio. There is no guarantee that this method will be called.
                 *
                 * @param buffer a buffer containing a sequence of big-endian 16-bit integers representing a
                 * single channel audio stream. The sample rate is implementation dependent.
                 */
                override fun onBufferReceived(buffer: ByteArray?) {
                    buffer?.let {
                        Log.d("speechrecognizerintent", "speechrecognizer: onBufferReceived, size=${it.size}")
                    }
                }

                override fun onEndOfSpeech() {
                    // Called when the user stops speaking.
                    Log.d("speechrecognizerintent", "speechrecognizer: onEndOfSpeech")
                }

                override fun onError(error: Int) {
                    // Called when there is an error in the recognizer.
                    Log.d("speechrecognizerintent", "speechrecognizer: onError $error")
                }

                override fun onResults(results: Bundle?) {
                    // Called when the recognizer returns recognition results.
                    val voiceCommands = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    Log.d("speechrecognizerintent", "speechrecognizer: $voiceCommands")
                    // Handle the recognized voice commands here.
                    if (voiceCommands != null) {
                        if (voiceCommands.contains("matches")){
                            navController.navigate(R.id.matchesFragment)
                        }else if (voiceCommands.contains("series")){
                            navController.navigate(R.id.seriesFragment)
                        }else if (voiceCommands.contains("more")){
                            navController.navigate(R.id.moreFragment)
                        }
                        else if (voiceCommands.contains("home") || voiceCommands.contains("back")){
                            navController.navigate(R.id.homeFragment)
                        }
                        else if (voiceCommands.contains("ranking")){
                            navController.navigate(R.id.rankingFragment)
                        }else if (voiceCommands.contains("statistics")){
                            navController.navigate(R.id.statisticsFragment)
                        }else if (voiceCommands.contains("popular t20")){
                            navController.navigate(R.id.t20LeaguesFragment)
                        }
                    }else{
                        CookieBar.build(this@MainActivity)
                            .setTitle("Sorry! cannot understand what've you said!")
                            .setTitleColor(R.color.white)
                            .setMessage("Internet Permission Required!")
                            .setBackgroundColor(R.color.swipe_color_3)
                            .setSwipeToDismiss(true)
                            .setDuration(3000) // 3 seconds
                            .show()
                    }
                }

                override fun onPartialResults(partialResults: Bundle?) {
                    // Called when the recognizer returns partial recognition results.
                }

                override fun onEvent(eventType: Int, params: Bundle?) {
                    // Called when there is a change in the recognizer's state.
                }
            }
            speechRecognizer.setRecognitionListener(recognitionListener)

            voiceSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    isListening = true
                    // Start listening for voice commands.
                    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                    speechRecognizer.startListening(intent)
                    // Check if the switch is still on and if yes, call startListening() again
                    if (isListening) {
                        Handler().postDelayed({ startListening() }, 1000)
                    }

                    Log.d("speechrecognizerintent", "speechrecognizer: triggered")
                } else {
                    // Stop listening for voice commands.
                    speechRecognizer.stopListening()
                    isListening = false
                    Log.d("speechrecognizerintent", "speechrecognizer: stopped")
                }
            }
        }else{
            Log.d("speechrecognizerintent", "speech recognition not available on this device")
        }


        drawerLayout = findViewById(R.id.drawerLayout)
        val navView:NavigationView = findViewById(R.id.navigationView)
        val bottomNavView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavView.itemIconTintList = null
        navView.itemIconTintList = null

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.matchesFragment,R.id.seriesFragment,
            R.id.rankingFragment, R.id.moreFragment, R.id.liveMatchesFragment, R.id.recentMatchesFragment, R.id.upcomingMatchesFragment,
            R.id.bookMarkFragment), drawerLayout)

        bottomNavView.setupWithNavController(navController)
        navView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, nd: NavDestination, _ ->
            if (nd.id == R.id.matchDetailsFragment || nd.id == R.id.statisticsFragment||  nd.id == R.id.t20LeaguesFragment || nd.id == R.id.tournamentFixturesFragment || nd.id==R.id.playerDetailsFragment || nd.id==R.id.teamDetailsFragment) {
                bottomNavView.visibility = View.GONE
            } else {
                bottomNavView.visibility = View.VISIBLE
            }
        }
        bottomNavView.setOnNavigationItemReselectedListener { item->
            when(item.itemId){
                R.id.homeFragment-> navController.navigate(R.id.homeFragment)
                R.id.matchesFragment-> navController.navigate(R.id.matchesFragment)
                R.id.seriesFragment-> navController.navigate(R.id.seriesFragment)
                R.id.rankingFragment-> navController.navigate(R.id.rankingFragment)
                R.id.moreFragment-> navController.navigate(R.id.moreFragment)
            }
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        }

        viewModel.getAllStages().observe(this) {
            MyConstants.ALL_STAGES.value = it
        }
        viewModel.getAllFixtures().observe(this) {
//            Log.d("dataoffixtures", "Fixtures: ${it.size}")
            val dateFormat =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
            val cal = Calendar.getInstance()
            val currentDateTime = dateFormat.format(cal.time)
            val date = dateFormat.parse(currentDateTime)
            MyConstants.ALL_FIXTURES.value = it
            if (date != null) {
                MyConstants.ALL_UpcomingFIXTURES.value = it.filter { i ->
                    i.status == "NS" && dateFormat.parse(i.starting_at!!)!!.after(date)
                }
            }

        }
        viewModel.getAllCountries().observe(this) {
            Log.d("countries", "countries: $it")
            MyConstants.ALL_COUNTRIES.value = it
        }

        viewModel.getAllTeams().observe(this) {
            MyConstants.ALL_TEAMS.value = it
        }
        viewModel.getAllRuns().observe(this) {
            MyConstants.ALL_RUNS.value = it
        }
        viewModel.getAllScores().observe(this) {
            MyConstants.ALL_SCORES.value = it
        }
        viewModel.getAllVenues().observe(this) {
            MyConstants.ALL_VENUES.value = it
        }
        viewModel.getAllOfficials().observe(this) {
            MyConstants.ALL_OFFICIALS.value = it
        }
        viewModel.getAllSeasons().observe(this){
            MyConstants.ALL_SEASONS.value = it
        }
        viewModel.getAllScores().observe(this) {
            MyConstants.ALL_SCORES.value = it
        }
        viewModel.getAllPlayersWithTeam().observe(this) {
            MyConstants.ALL_PLAYERSWITHTEAM.value = it
        }
        viewModel.getAllSeasons().observe(this) {
            MyConstants.seasonIdList = it.distinctBy { it.id }.map { it.id }.sorted()
            lifecycleScope.launch {
                for (i in MyConstants.seasonIdList) {
                    viewModel.getStandings(i)
                }
                viewModel.getAllStandingsBySeason().observe(this@MainActivity) {
                    MyConstants.ALL_STANDINGSBYSESON.value = it
                }
                Log.d(
                    "teamWinningPercentage",
                    "team1WinningPercentage: ${MyConstants.seasonIdList.size} ${MyConstants.ALL_STANDINGSBYSESON.value?.size} "
                )
            }

        }
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        navController.navigate(R.id.homeFragment)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
            checkPermissions()
            viewModel.checkAndLoadDataIfNeeded()
            // Register network check
            networkChangeReceiver = NetworkChangeReceiver(viewModel)
            val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            registerReceiver(networkChangeReceiver, filter)

    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(networkChangeReceiver)
    }

    private fun checkPermissions() {
        val permissionsToRequest = mutableListOf<String>()

        // RECORD_AUDIO
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(Manifest.permission.RECORD_AUDIO)
        }

        // POST_NOTIFICATIONS (Only Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(Manifest.permission.POST_NOTIFICATIONS)
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsToRequest.toTypedArray(), 100)
        }
    }

}