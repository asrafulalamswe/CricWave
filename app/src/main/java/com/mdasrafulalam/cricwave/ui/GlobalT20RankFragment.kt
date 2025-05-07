package com.mdasrafulalam.cricwave.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mdasrafulalam.cricwave.R
import com.mdasrafulalam.cricwave.adapter.RankingAdapter
import com.mdasrafulalam.cricwave.databinding.FragmentGlobalT20RankBinding
import com.mdasrafulalam.cricwave.utils.MyConstants
import com.mdasrafulalam.cricwave.viewmomdel.CricketViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.aviran.cookiebar2.CookieBar

class GlobalT20RankFragment : Fragment() {

    private lateinit var binding: FragmentGlobalT20RankBinding
    private lateinit var adapter: RankingAdapter
    private val viewModel:CricketViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentGlobalT20RankBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (MyConstants.verifyAvailableNetwork(requireContext())){
            adapter = RankingAdapter()
            binding.globalT20RankRV.adapter = adapter
            binding.globalT20RankRV.layoutManager = LinearLayoutManager(requireContext())
            val dividerItemDecoration= DividerItemDecoration(
                binding.globalT20RankRV.context,
                LinearLayoutManager(requireContext()).orientation
            )
            binding.globalT20RankRV.addItemDecoration(dividerItemDecoration)
            binding.t20MenRankingTV.setBackgroundColor(ContextCompat.getColor(requireContext(),
                R.color.color_tab_text
            ))
            binding.t20loadingTV.visibility = View.VISIBLE
            //check if men list is available or not
            if (viewModel.menT20List.isEmpty()){
                getMenList()
            }else{
                showMenList()
            }

            //check if women list is available or not
            if (viewModel.womenT20List.isEmpty()){
                getWomenList()
            }
            binding.t20MenRankingTV.setOnClickListener {
                binding.t20MenRankingTV.setBackgroundColor(ContextCompat.getColor(requireContext(),
                    R.color.color_tab_text
                ))
                binding.t20WomenRankingTV.setBackgroundColor(Color.TRANSPARENT)
                showMenList()
            }

            binding.t20WomenRankingTV.setOnClickListener {
                binding.t20WomenRankingTV.setBackgroundColor(ContextCompat.getColor(requireContext(),
                    R.color.color_tab_text
                ))
                binding.t20MenRankingTV.setBackgroundColor(Color.TRANSPARENT)
                showWomenList()
            }
        }else{
            CookieBar.build(requireActivity())
                .setTitle("No Active Internet!")
                .setIcon(R.drawable.alert)
                .setBackgroundColor(R.color.swipe_color_4)
                .setCookiePosition(CookieBar.TOP)
                .setDuration(5000) // 3 seconds
                .show()
        }
    }
    override fun onPause() {
        super.onPause()
        if (MyConstants.verifyAvailableNetwork(requireContext())){
            if (viewModel.menT20List.isNotEmpty()){
                binding.t20loadingTV.visibility = View.GONE
                adapter.submitList(viewModel.menT20List[0].team)
            }else{
                getMenList()
            }
        }
    }

    private fun showMenList() {
        if (viewModel.menT20List.isNotEmpty()){
            binding.t20loadingTV.visibility = View.GONE
            adapter.submitList(viewModel.menT20List[0].team)
        }
    }
    private fun showWomenList() {
        if (viewModel.womenT20List.isNotEmpty()){
            binding.t20loadingTV.visibility = View.GONE
            adapter.submitList(viewModel.womenT20List[0].team)
        }
    }

    private fun getMenList(){
        lifecycleScope.launch {
           try {
               viewModel.menT20List = viewModel.getRankingByGenderAndTournamentType("T20I","men")
               delay(3000)
               showMenList()
           }catch (e:Exception){
               Log.d("getMenList", "getMenList: Exception ${e.message} ")
           }
        }
    }
    private fun getWomenList(){
        lifecycleScope.launch {
            try {
                viewModel.womenT20List = viewModel.getRankingByGenderAndTournamentType("T20I","women")
            }catch (e:Exception){
                Log.d("getMenList", "getMenList: Exception ${e.message} ")
            }
        }
    }

}