package com.mdasrafulalam.cricwave.ui


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
import com.mdasrafulalam.cricwave.databinding.FragmentGlobalTestRankBinding
import com.mdasrafulalam.cricwave.utils.MyConstants
import com.mdasrafulalam.cricwave.viewmomdel.CricketViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GlobalTestRankFragment : Fragment() {
    private lateinit var binding: FragmentGlobalTestRankBinding
    private lateinit var adapter: RankingAdapter
    private val viewModel: CricketViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentGlobalTestRankBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (MyConstants.verifyAvailableNetwork(requireContext())){
            adapter = RankingAdapter()
            binding.globalTestRankRV.adapter = adapter
            binding.globalTestRankRV.layoutManager = LinearLayoutManager(requireContext())
            val dividerItemDecoration= DividerItemDecoration(
                binding.globalTestRankRV.context,
                LinearLayoutManager(requireContext()).orientation
            )
            binding.globalTestRankRV.addItemDecoration(dividerItemDecoration)
            binding.testMenRankingTV.setBackgroundColor(
                ContextCompat.getColor(requireContext(),
                    R.color.color_tab_text
                ))
            binding.testloadingTV.visibility = View.VISIBLE
            //check if men list is available or not
            if (viewModel.menTESTList.isEmpty()){
                getMenList()
            }else{
                showMenList()
            }

            binding.testMenRankingTV.setOnClickListener {
                binding.testMenRankingTV.setBackgroundColor(
                    ContextCompat.getColor(requireContext(),
                        R.color.color_tab_text
                    ))
                showMenList()
            }
        }else{
            MyConstants.noNetwork(requireActivity())
        }
    }
    override fun onPause() {
        super.onPause()
        if (MyConstants.verifyAvailableNetwork(requireContext())){
            if (viewModel.menTESTList.isNotEmpty()){
                binding.testloadingTV.visibility = View.GONE
                adapter.submitList(viewModel.menTESTList[0].team)
            }else{
                getMenList()
            }
        }
    }

    private fun showMenList() {
        if (viewModel.menTESTList.isNotEmpty()){
            binding.testloadingTV.visibility = View.GONE
            adapter.submitList(viewModel.menTESTList[0].team)
        }
    }

    private fun getMenList(){
        lifecycleScope.launch {
            try {
                viewModel.menTESTList = viewModel.getRankingByGenderAndTournamentType("TEST","men")
                delay(3000)
                showMenList()
            }catch (e:Exception){
                Log.d("getMenList", "getMenList: Exception ${e.message} ")
            }
        }
    }
}