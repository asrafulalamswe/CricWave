package com.mdasrafulalam.cricwave.ui

import android.annotation.SuppressLint
import android.net.http.HttpException
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresExtension
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mdasrafulalam.cricwave.adapter.LiveMatchAdapter
import com.mdasrafulalam.cricwave.adapter.UpcomingMatchAdapter
import com.mdasrafulalam.cricwave.databinding.FragmentLiveMatchesBinding
import com.mdasrafulalam.cricwave.model.livescores.Data
import com.mdasrafulalam.cricwave.utils.MyConstants
import com.mdasrafulalam.cricwave.viewmomdel.CricketViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat


class LiveMatchesFragment : Fragment() {
    private lateinit var binding: FragmentLiveMatchesBinding
    private lateinit var upcomingAdapter: UpcomingMatchAdapter
    private lateinit var liveAdapter: LiveMatchAdapter
    private var list:List<Data> = listOf()
    private var liveScoreList:List<Data> = listOf()
    private val viewModel:CricketViewModel by activityViewModels()
    private var list1:List<com.mdasrafulalam.cricwave.model.fixturesfilteringdateincludingruns.Data> = listOf()
    private val updateLiveScoreHandler = MyConstants.handler
    private val updateLiveScoreRunnable = object : Runnable {
        override fun run() {
            lifecycleScope.launch {
                // Call the API or update the data source for live scores here
                try {
                    viewModel.getLiveScore()?.let { newList ->
                        if (newList.isNotEmpty()) {
                            liveScoreList = newList
                            liveAdapter.submitList(liveScoreList)
                        }
                    }
                }catch (e:Exception){
                    Log.d("liveerror", "liveerror: ${e.message}")
                }
            }
            updateLiveScoreHandler.postDelayed(this, 5000) // Update live scores every 10 seconds
        }
    }

    override fun onPause() {
        super.onPause()

        try {
            updateLiveScoreHandler.removeCallbacks(updateLiveScoreRunnable)
            updateLiveScoreHandler.post(updateLiveScoreRunnable)
        }catch (e:Exception){
            Log.d("liveerror", "liveerror: ${e.message}")
        }
    }
    override fun onResume() {
        super.onResume()
        try {
            updateLiveScoreHandler.removeCallbacks(updateLiveScoreRunnable)
        }catch (e:Exception){
            Log.d("liveerror", "liveerror: ${e.message}")
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        try {
            updateLiveScoreHandler.removeCallbacks(updateLiveScoreRunnable)
        }catch (e:Exception){
            Log.d("liveerror", "liveerror: ${e.message}")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLiveMatchesBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    @Deprecated("Deprecated in Java")
    @SuppressLint("SimpleDateFormat")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        try {
            if (MyConstants.verifyAvailableNetwork(requireActivity())) {
                lifecycleScope.launch {
                    try {
                        // Call your ViewModel's getLiveScore function to get the live score data
                        val response = try {
                            viewModel.getLiveScore()
                        } catch (e: Exception) {
                            Log.d("liveerror", "Error calling getLiveScore: ${e.message}")
                            null
                        }

                        // Check if the response is null or if it's an empty list
                        if (response != null && response.isNotEmpty()) {
                            // If we have live scores, update the adapter with the data
                            liveScoreList = response
                            liveAdapter = LiveMatchAdapter()
                            binding.liveMatchesRV.adapter = liveAdapter
                            binding.liveMatchesRV.layoutManager = LinearLayoutManager(requireContext())
                            liveAdapter.submitList(liveScoreList)

                            binding.liveStatus.visibility = View.GONE
                            binding.liveMatchesRV.visibility = View.VISIBLE

                            try {
                                // Start the runnable to refresh live scores periodically
                                updateLiveScoreHandler.post(updateLiveScoreRunnable)
                            } catch (e: Exception) {
                                Log.d("liveerror", "liveerror: ${e.message}")
                            }
                        } else {
                            // If no live scores, show the upcoming matches section
                            binding.liveStatus.visibility = View.VISIBLE
                            binding.liveMatchesRV.visibility = View.GONE
                            upcomingAdapter = UpcomingMatchAdapter()
                            binding.livematchUpcomingRV.adapter = upcomingAdapter
                            binding.livematchUpcomingRV.layoutManager = LinearLayoutManager(requireContext())

                            // Observe the upcoming fixtures
                            if (view != null) {
                                MyConstants.ALL_UpcomingFIXTURES.observe(viewLifecycleOwner) {
                                    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
                                    list1 = it
                                    Log.d("upcomingfixtures", "upcomingfixtures: ${list1.size}")
                                    val sortedList = list1.sortedBy { dateFormat.parse(it.starting_at.toString()) }
                                    upcomingAdapter.submitList(sortedList)
                                }
                            }
                        }
                    } catch (e: HttpException) {
                        // Handle specific HTTP exceptions (e.g., 429 for too many requests)
                        Log.d("liveerror", "HTTP Error: ${e.toString()} - ${e.message}")
                        if (e.toString() == "429") {
                            // Implement retry logic after a delay (rate limit exceeded)
                            Log.d("liveerror", "Too Many Requests (HTTP 429). Retrying...")
                            retryAfterDelay()
                        }
                    } catch (e: Exception) {
                        // Handle generic exceptions (network issues, unknown errors, etc.)
                        Log.d("liveerror", "Error: ${e.message}")
                    }
                }
            } else {
                // Handle no network available
                MyConstants.noNetwork(requireActivity())
            }
        } catch (e: Exception) {
            Log.d("liveerror", "Error: ${e.message}")
        }
    }

    // Function to retry fetching live scores after a delay (rate limit backoff)
    private fun retryAfterDelay() {
        val delayMillis = 5000L // Retry after 5 seconds (or any backoff strategy)
        updateLiveScoreHandler.postDelayed(updateLiveScoreRunnable, delayMillis)
    }


}