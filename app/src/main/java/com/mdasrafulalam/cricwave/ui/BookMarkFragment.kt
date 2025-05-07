package com.mdasrafulalam.cricwave.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mdasrafulalam.cricwave.adapter.CompetitionsAdapter
import com.mdasrafulalam.cricwave.databinding.FragmentBookmarkBinding
import com.mdasrafulalam.cricwave.model.stages.Series
import com.mdasrafulalam.cricwave.utils.MyConstants
import com.mdasrafulalam.cricwave.viewmomdel.CricketViewModel
import kotlinx.coroutines.launch

class BookMarkFragment : Fragment() {
    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CompetitionsAdapter
    private val viewModel: CricketViewModel by activityViewModels()
    private  var list = emptyList<Series>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBookmarkBinding.inflate(layoutInflater,container, false)
        return binding.root
    }

    private fun updateBookmark(favouriteSeries: Series) {
        viewModel.updateBookMark(favouriteSeries)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = CompetitionsAdapter(false,false,::updateBookmark)
        binding.bookmarkSeriesRV.adapter = adapter
        binding.bookmarkSeriesRV.layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration1 = DividerItemDecoration(
            binding.bookmarkSeriesRV.context,
            LinearLayoutManager(requireContext()).orientation
        )
        binding.bookmarkSeriesRV.addItemDecoration(dividerItemDecoration1)

        viewModel.getAllStages().observe(viewLifecycleOwner){
            val bookmarkedlist = it.filter { it.isBookMarked }
            adapter.submitList(bookmarkedlist)
            list = it
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}