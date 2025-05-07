package com.mdasrafulalam.cricwave.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mdasrafulalam.cricwave.R
import com.mdasrafulalam.cricwave.adapter.PlayersAdapter
import com.mdasrafulalam.cricwave.databinding.FragmentMoreBinding
import com.mdasrafulalam.cricwave.model.players.Data
import com.mdasrafulalam.cricwave.utils.MyConstants
import com.mdasrafulalam.cricwave.viewmomdel.CricketViewModel
import kotlinx.coroutines.launch

class MoreFragment : Fragment() {

    private lateinit var binding: FragmentMoreBinding
    private lateinit var adapter: PlayersAdapter
    private val viewModel:CricketViewModel by activityViewModels()
    private var list:List<Data> = listOf()
    private var isFromMoreFragment:Boolean = false
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMoreBinding.inflate(layoutInflater,container,false)
        bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = PlayersAdapter(true)
        binding.moreFragmentPlayersRV.adapter = adapter
        binding.moreFragmentPlayersRV.layoutManager = LinearLayoutManager(requireContext())
        viewModel.getAllPlayers().observe(viewLifecycleOwner){
            list = it.take(30)
            adapter.submitList(list)
            MyConstants.ALL_PLAYERS.value = it
        }

        binding.moreSearchBox.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchAction(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {
                searchAction(s.toString())
            }

        })
        binding.playerVoiceSearchIV.setOnClickListener {
            displaySpeechRecognizerForDesc()
        }
        binding.statisticsMoreCV.setOnClickListener {
            try {
                findNavController().navigate(R.id.action_moreFragment_to_statisticsFragment)
                binding.statisticsMoreCV.visibility = View.GONE
            }catch (_:Exception){

            }

        }
        binding.leaguesMoreCV.setOnClickListener {
            try {
                findNavController().navigate(R.id.action_moreFragment_to_t20LeaguesFragment)
                binding.statisticsMoreCV.visibility = View.GONE
            }catch (_:Exception){

            }

        }

    }

    private fun displaySpeechRecognizerForDesc() {
        //Starts an activity that will prompt the user for speech and send it through a speech recognizer.
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL, //Informs the recognizer which speech model to prefer when performing ACTION_RECOGNIZE_SPEECH.
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM //Use a language model based on free-form speech recognition.
            )
        }
        // This starts the activity and populates the intent with the speech text.
        startActivityForResult(intent, 0)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            val spokenText =
                data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).let { results ->
                    results?.get(0) ?: return
                }
            Log.d("voice", spokenText)
            //setting voice text into input field
            searchAction(spokenText)
        }
        super.onActivityResult(requestCode, resultCode, data)

    }

    fun searchAction(query: String) {
        viewModel.getAllPlayers().observe(viewLifecycleOwner){
            val collectionSearch: List<Data> = it.filter {
                it.fullname!!.uppercase().contains(query.uppercase())
            }.toList()
            Log.d("searchedList", "searchedList: ${collectionSearch.size}")
            adapter.submitList(null)
            adapter.submitList(collectionSearch)
        }

    }

    override fun onPause() {
        super.onPause()
        isFromMoreFragment = false
    }
}