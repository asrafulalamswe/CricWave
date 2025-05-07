package com.mdasrafulalam.cricwave.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mdasrafulalam.cricwave.R
import com.mdasrafulalam.cricwave.adapter.CompetitionsAdapter
import com.mdasrafulalam.cricwave.databinding.FragmentCompetitionsBinding
import com.mdasrafulalam.cricwave.model.stages.Series
import com.mdasrafulalam.cricwave.utils.MyConstants
import com.mdasrafulalam.cricwave.viewmomdel.CricketViewModel


class CompetitionsFragment : Fragment() {

    private lateinit var adapter: CompetitionsAdapter
    private val viewModel:CricketViewModel by activityViewModels()
    private lateinit var binding: FragmentCompetitionsBinding
    private  var list = emptyList<Series>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCompetitionsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    private fun updateBookmark(favouriteSeries: Series) {
        viewModel.updateBookMark(favouriteSeries)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        adapter = CompetitionsAdapter(false,true,::updateBookmark)
        binding.comtetitionsRV.adapter = adapter
        binding.comtetitionsRV.layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration1 = DividerItemDecoration(
            binding.comtetitionsRV.context,
            LinearLayoutManager(requireContext()).orientation
        )
        binding.comtetitionsRV.addItemDecoration(dividerItemDecoration1)
        viewModel.getAllStages().observe(viewLifecycleOwner){
            list = it
            adapter.submitList(list.reversed())
        }


        binding.competitionsSRL.setOnRefreshListener {
            binding.competitionsSRL.isRefreshing = false
        }
    }
    fun searchAction(query: String) {
        val collectionSearch: List<Series> = list.filter {
            it.name?.uppercase()?.contains(query.uppercase()) == true
        }.toList()
        adapter.submitList(collectionSearch.reversed())
    }

    @SuppressLint("InflateParams")
    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.search_action)
        val searchView = searchItem?.actionView as? SearchView
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchAction(query.toString())
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                searchAction(newText.toString())
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }
}