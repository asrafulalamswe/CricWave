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
import com.mdasrafulalam.cricwave.databinding.FragmentGlobalOdiRankBinding
import com.mdasrafulalam.cricwave.utils.MyConstants
import com.mdasrafulalam.cricwave.viewmomdel.CricketViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GlobalOdiRankFragment : Fragment() {
    private lateinit var binding: FragmentGlobalOdiRankBinding
    private lateinit var adapter: RankingAdapter
    private val viewModel: CricketViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentGlobalOdiRankBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       if (MyConstants.verifyAvailableNetwork(requireContext())){
           adapter = RankingAdapter()
           binding.globalOdiRankRV.adapter = adapter
           binding.globalOdiRankRV.layoutManager = LinearLayoutManager(requireContext())
           val dividerItemDecoration= DividerItemDecoration(
               binding.globalOdiRankRV.context,
               LinearLayoutManager(requireContext()).orientation
           )
           binding.globalOdiRankRV.addItemDecoration(dividerItemDecoration)
           binding.odiMenRankingTV.setBackgroundColor(
               ContextCompat.getColor(requireContext(),
                   R.color.color_tab_text
               ))
           binding.odiloadingTV.visibility = View.VISIBLE
           //check if men list is available or not
           if (viewModel.menODIList.isEmpty()){
               getMenList()
           }else{
               showMenList()
           }
           //check if women list is available or not
           if (viewModel.womenODIList.isEmpty()){
               getWomenList()
           }
           binding.odiMenRankingTV.setOnClickListener {
               binding.odiMenRankingTV.setBackgroundColor(
                   ContextCompat.getColor(requireContext(),
                       R.color.color_tab_text
                   ))
               binding.odiWomenRankingTV.setBackgroundColor(Color.TRANSPARENT)
               showMenList()
           }

           binding.odiWomenRankingTV.setOnClickListener {
               binding.odiWomenRankingTV.setBackgroundColor(
                   ContextCompat.getColor(requireContext(),
                       R.color.color_tab_text
                   ))
               binding.odiMenRankingTV.setBackgroundColor(Color.TRANSPARENT)
               showWomenList()
           }
       }else{
           MyConstants.noNetwork(requireActivity())
       }
    }
    override fun onPause() {
        super.onPause()
        if (MyConstants.verifyAvailableNetwork(requireContext())){
            if (viewModel.menODIList.isNotEmpty()){
                binding.odiloadingTV.visibility = View.GONE
                adapter.submitList(viewModel.menODIList[0].team)
            }else{
                getMenList()
            }
        }
    }

    private fun showMenList() {
        if (viewModel.menODIList.isNotEmpty()){
            binding.odiloadingTV.visibility = View.GONE
            adapter.submitList(viewModel.menODIList[0].team)
        }
    }
    private fun showWomenList() {
        if (viewModel.womenODIList.isNotEmpty()){
            binding.odiloadingTV.visibility = View.GONE
            adapter.submitList(viewModel.womenODIList[0].team)
        }
    }

    private fun getMenList(){
        lifecycleScope.launch {
           try {
               viewModel.menODIList = viewModel.getRankingByGenderAndTournamentType("ODI","men")
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
                viewModel.womenODIList = viewModel.getRankingByGenderAndTournamentType("ODI","women")
            }catch (e:Exception){
                Log.d("getMenList", "getMenList: Exception ${e.message} ")
            }
        }
    }
}