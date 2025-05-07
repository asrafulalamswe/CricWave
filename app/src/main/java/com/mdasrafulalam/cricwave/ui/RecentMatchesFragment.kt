package com.mdasrafulalam.cricwave.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.aminography.primecalendar.civil.CivilCalendar
import com.aminography.primedatepicker.picker.PrimeDatePicker
import com.aminography.primedatepicker.picker.callback.SingleDayPickCallback
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mdasrafulalam.cricwave.R
import com.mdasrafulalam.cricwave.adapter.RecentMatchAdapter
import com.mdasrafulalam.cricwave.databinding.FragmentRecentMatchesBinding
import com.mdasrafulalam.cricwave.model.fixturesfilteringdateincludingruns.Data
import com.mdasrafulalam.cricwave.utils.MyConstants
import com.mdasrafulalam.cricwave.viewmomdel.CricketViewModel
import org.aviran.cookiebar2.CookieBar
import java.text.SimpleDateFormat
import java.util.*

class RecentMatchesFragment : Fragment() {
    private lateinit var binding: FragmentRecentMatchesBinding
    var isFromViewPager:Boolean = false
    private var selectedDate = ""
    private var list:List<Data> = listOf()
    private val viewModel: CricketViewModel by activityViewModels()
    private lateinit var sortedList:List<Data>
    private lateinit var adapter: RecentMatchAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRecentMatchesBinding.inflate(layoutInflater, container, false)
        swipeRefreshLayout = binding.recentMatchesSRL
        bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        isFromViewPager = MyConstants.IS_FROM_VIEWPAGER
        adapter = RecentMatchAdapter(isFromViewPager, false)
        binding.recentMatchesRV.adapter = adapter
        binding.recentMatchesRV.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getAllFixtures().observe(viewLifecycleOwner){
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
            list = MyConstants.ALL_FIXTURES.value?.filter {fixture-> fixture.status == "Finished" }!!
            Log.d("finishedfixtures", "finishedfixtures: ${list.size}")
            sortedList = list.sortedByDescending  { dateFormat.parse(it.starting_at.toString()) }
            adapter.submitList(sortedList.take(30))
        }

        //date picker
        binding.recentDatePickerTV.setOnClickListener {
            val callback = SingleDayPickCallback { day ->
                val date = day.getTime()
                val datetext = day.longDateString
                selectedDate = convertDateFormat(date.toString())
                binding.recentDatePickerTV.text = String.format("Selected Date : $datetext")
                val filteredList = list.filter { it.starting_at?.substring(0,10) ==  selectedDate.substring(0,10)}
                adapter.submitList(filteredList)
                Log.d("selectedDate", "selected date: ${filteredList.size}")
            }
            val today = CivilCalendar()
            val datePicker = PrimeDatePicker.bottomSheetWith(today)
                .pickSingleDay(callback)
                .initiallyPickedSingleDay(today)
                .build()
            datePicker.show(childFragmentManager, "SOME_TAG")
        }
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            binding.recentDatePickerTV.text = resources.getString(R.string.select_date)
            adapter.submitList(sortedList.take(30))
            if (MyConstants.verifyAvailableNetwork(requireContext())){
                CookieBar.build(requireActivity()).setMessage("Fixtures Updated!").setDuration(3000)
                    .setBackgroundColor(R.color.color_tab_text).setIcon(R.drawable.success)
                    .setMessageColor(R.color.white)
                    .setAnimationIn(android.R.anim.slide_in_left, android.R.anim.slide_in_left)
                    .setAnimationOut(android.R.anim.slide_out_right, android.R.anim.slide_out_right)
                    .show()
            }else{
                MyConstants.noNetwork(requireActivity())
            }

        }
    }
    @SuppressLint("SimpleDateFormat")
    private fun convertDateFormat(inputDate: String): String{
        val inputFormat = "EEE MMM dd HH:mm:ss z yyyy"
        val simpleDateFormat = SimpleDateFormat(inputFormat, Locale.ENGLISH)
        val date = simpleDateFormat.parse(inputDate)
        val outputFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"
        val outputDateFormat = SimpleDateFormat(outputFormat)
        outputDateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val outputDate = date?.let { outputDateFormat.format(it) }
        return outputDate!!
    }

}