package com.mdasrafulalam.cricwave.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mdasrafulalam.cricwave.adapter.CompetitionsAdapter
import com.mdasrafulalam.cricwave.adapter.UpcomingMatchAdapter
import com.mdasrafulalam.cricwave.databinding.FragmentHomeBinding
import com.mdasrafulalam.cricwave.model.stages.Series
import com.mdasrafulalam.cricwave.utils.MyConstants
import com.mdasrafulalam.cricwave.viewmomdel.CricketViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {
    private val viewModel: CricketViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: UpcomingMatchAdapter
    private lateinit var favAdater: CompetitionsAdapter
    /*private var currentPosition = 0
    private val handler = MyConstants.handler*/
    private lateinit var homeUpcomingRecyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        homeUpcomingRecyclerView = binding.homeSummeryRV
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        return binding.root
    }

    private fun updateBookmark(favouriteSeries: Series) {
        viewModel.updateBookMark(favouriteSeries)
    }

    @SuppressLint("SuspiciousIndentation", "SimpleDateFormat", "NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        //news
        binding.homeNewsCV1.setOnClickListener {
            val url = "https://www.bbc.com/sport/cricket/64753742?at_medium=RSS&at_campaign=KARANGA"
            val action = HomeFragmentDirections.actionHomeFragmentToWebViewFragment(url)
            findNavController().navigate(action)
        }
        binding.homeNewsCV2.setOnClickListener {
            val url = "https://www.bbc.com/sport/cricket/64754899"
            val action = HomeFragmentDirections.actionHomeFragmentToWebViewFragment(url)
            findNavController().navigate(action)
        }
        binding.homeNewsCV3.setOnClickListener {
            val url = "https://www.bbc.com/sport/cricket/64694261"
            val action = HomeFragmentDirections.actionHomeFragmentToWebViewFragment(url)
            findNavController().navigate(action)
        }

        //upcoming
        adapter = UpcomingMatchAdapter()
        homeUpcomingRecyclerView.adapter = adapter
        homeUpcomingRecyclerView.layoutManager = layoutManager
        //favourite
        favAdater = CompetitionsAdapter(true, false, ::updateBookmark)
        binding.homeFavSeriesRV.adapter = favAdater
        binding.homeFavSeriesRV.layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration1 = DividerItemDecoration(
            binding.homeFavSeriesRV.context, LinearLayoutManager(requireContext()).orientation
        )
        binding.homeFavSeriesRV.addItemDecoration(dividerItemDecoration1)
        viewModel.getAllStages().observe(viewLifecycleOwner) {
            val bookmarkedlist = it.filter { it.isBookMarked }
            favAdater.submitList(bookmarkedlist)
        }

        lifecycleScope.launch {
//            if (viewModel.isFirstTime) {
//                viewModel.isFirstTime = false
//                viewModel.isFirstTime = false
//                binding.mainContentLL.visibility = View.GONE
//                binding.loadingIMG.visibility = View.VISIBLE
//                CoroutineScope(Dispatchers.Main).launch {
////                    delay(10000)
//                    binding.loadingIMG.visibility = View.GONE
//                    binding.mainContentLL.visibility = View.VISIBLE
//                }
//            }
            //ui update
            MyConstants.ALL_UpcomingFIXTURES.observe(viewLifecycleOwner) {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
                val list1 = it
                Log.d("upcomingfixtures", "upcomingfixtures: ${list1?.size}")
                val sortedList = list1?.sortedBy { dateFormat.parse(it.starting_at.toString()) }
                adapter.submitList(sortedList)
//            handler.postDelayed(runnable, 3000)
            }

            MyConstants.ALL_STAGES.observe(viewLifecycleOwner) {
                val bookmarkedlist = it.filter { i -> i.isBookMarked }
                favAdater.submitList(bookmarkedlist)
                favAdater.notifyDataSetChanged()
            }
        }



    }

    /*  private val runnable = object : Runnable {
          override fun run() {
              if (currentPosition == layoutManager.itemCount - 1) {
                  currentPosition = 0
                  homeUpcomingRecyclerView.scrollToPosition(currentPosition)
              } else {
                  currentPosition++
              }
              val itemWidth = homeUpcomingRecyclerView.width
              homeUpcomingRecyclerView.smoothScrollBy(
                  itemWidth,
                  0,
                  AccelerateDecelerateInterpolator()
              )
              handler.postDelayed(this, 3000) // Adjust the delay as per your preference
          }
      }*/

    /*  override fun onDestroyView() {
          super.onDestroyView()
      }
      override fun onResume() {
          super.onResume()
          handler.removeCallbacks(runnable)
          handler.postDelayed(runnable, 3000)
      }

      override fun onPause() {
          super.onPause()
          handler.removeCallbacks(runnable)
      }

      override fun onDestroy() {
          super.onDestroy()
          handler.removeCallbacks(runnable)
      }*/
}