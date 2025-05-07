package com.mdasrafulalam.cricwave.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mdasrafulalam.cricwave.R
import com.mdasrafulalam.cricwave.adapter.BattingScoreAdapter
import com.mdasrafulalam.cricwave.adapter.BowlingScoreAdapter
import com.mdasrafulalam.cricwave.adapter.CommentryListAdapter
import com.mdasrafulalam.cricwave.adapter.TeamSquadAdpater
import com.mdasrafulalam.cricwave.databinding.FragmentMatchDetailsBinding
import com.mdasrafulalam.cricwave.model.fixturesbyidincludeballs.Ball
import com.mdasrafulalam.cricwave.model.fixturesfilteringdateincludingruns.Data
import com.mdasrafulalam.cricwave.model.fixtureswithbattingscore.Batting
import com.mdasrafulalam.cricwave.model.fixtureswithbowlingscore.Bowling
import com.mdasrafulalam.cricwave.model.teamsquadwithteamandseasonid.Squad
import com.mdasrafulalam.cricwave.utils.MyConstants
import com.mdasrafulalam.cricwave.viewmomdel.CricketViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.aviran.cookiebar2.CookieBar


class MatchDetailsFragment : Fragment() {
    private lateinit var binding: FragmentMatchDetailsBinding
    private val viewModel:CricketViewModel by activityViewModels()
    private lateinit var commentryAdapter:CommentryListAdapter
    private lateinit var team1BattingAdapter: BattingScoreAdapter
    private lateinit var team2BattingAdapter: BattingScoreAdapter
    private lateinit var team1BowlingAdapter: BowlingScoreAdapter
    private lateinit var team2BowlingAdapter: BowlingScoreAdapter
    private lateinit var runs:List<com.mdasrafulalam.cricwave.model.fixturesfilteringdateincludingruns.Run>
    private var ballsList:List<Ball> = listOf()
    private lateinit var team1SquadList:List<Squad>
    private lateinit var team2SquadList:List<Squad>
    private lateinit var battingscores:List<Batting>
    private lateinit var bowlingscores:List<Bowling>
    private lateinit var team1Battingscores:List<Batting>
    private lateinit var team1Bowlingscores:List<Bowling>
    private lateinit var team2Battingscores:List<Batting>
    private lateinit var team2Bowlingscores:List<Bowling>
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var recentMatchInfo:Data
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMatchDetailsBinding.inflate(layoutInflater,container,false)
        bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getAllPlayers().observe(viewLifecycleOwner){
            MyConstants.ALL_PLAYERS.value = it
        }
        val lastDest = arguments?.getString("lastDestination")
        recentMatchInfo = arguments?.getSerializable("matchInfo") as Data
        binding.recent = recentMatchInfo
        lifecycleScope.launch {
            viewModel.getAllOfficials().observe(viewLifecycleOwner){
                val firstump = it.filter { i->i.id==recentMatchInfo.first_umpire_id }
                val secondUmp = it.filter { i->i.id==recentMatchInfo.second_umpire_id }
                val tvUmp = it.filter { i->i.id==recentMatchInfo.tv_umpire_id }
                if (firstump.isNotEmpty()){
                    binding.matchdetails1stUmpInfo.text = firstump[0].fullname
                }
                if (secondUmp.isNotEmpty()){
                    binding.matchdetails2ndUmpInfo.text = secondUmp[0].fullname
                }
                if (tvUmp.isNotEmpty()){
                    binding.matchdetailsTvUmpInfo.text = tvUmp[0].fullname
                }
                Log.d("mtchdetailsteamWinningPercentage", "firstump: ${firstump}, ${MyConstants.ALL_OFFICIALS.value?.size} ${recentMatchInfo.first_umpire_id}")
            }
        }
//

        if (recentMatchInfo.status=="Finished"){
            //get runs for both teams
            getBothTeamRuns()
            //batting adapater team1
            getBattingAdapterForTeam1()
            //bowling adapter team1
            getBattingAdapterForTeam1()
            //battting adapter for team 2
            getBattingAdapterForTeam2()
            //bowling adapter for team1
            getBowlingAdapterForTeam1()
            //bowling adapter for team2
            getBowlingAdapterForTeam2()
            getBatttingAndBowlingSoreOfBothTeams()
        }
        binding.team1NameAndOverTV.setBackgroundColor(resources.getColor(R.color.color_tab_text))

        binding.team1NameAndOverTV.setOnClickListener {
            binding.matchdetailsTeam1InfoLL.visibility = View.VISIBLE
            binding.matchdetailsTeam2InfoLL.visibility = View.GONE
            binding.commentryLL.visibility = View.GONE
            binding.matchdetailsTeamSquadLL.visibility = View.GONE
            binding.team2NameAndOverTV.setBackgroundColor(Color.TRANSPARENT)
            binding.squad.setBackgroundColor(Color.TRANSPARENT)
            binding.commentry.setBackgroundColor(Color.TRANSPARENT)
            binding.team1NameAndOverTV.setBackgroundColor(resources.getColor(R.color.color_tab_text))
        }
        binding.team2NameAndOverTV.setOnClickListener {
            binding.matchdetailsTeam1InfoLL.visibility = View.GONE
            binding.matchdetailsTeam2InfoLL.visibility = View.VISIBLE
            binding.commentryLL.visibility = View.GONE
            binding.matchdetailsTeamSquadLL.visibility = View.GONE
            binding.team1NameAndOverTV.setBackgroundColor(Color.TRANSPARENT)
            binding.squad.setBackgroundColor(Color.TRANSPARENT)
            binding.commentry.setBackgroundColor(Color.TRANSPARENT)
            binding.team2NameAndOverTV.setBackgroundColor(resources.getColor(R.color.color_tab_text))
        }
        binding.commentry.setOnClickListener {
            binding.matchdetailsTeam1InfoLL.visibility = View.GONE
            binding.matchdetailsTeam2InfoLL.visibility = View.GONE
            binding.commentryLL.visibility = View.VISIBLE
            binding.matchdetailsTeamSquadLL.visibility = View.GONE
            binding.team1NameAndOverTV.setBackgroundColor(Color.TRANSPARENT)
            binding.team2NameAndOverTV.setBackgroundColor(Color.TRANSPARENT)
            binding.squad.setBackgroundColor(Color.TRANSPARENT)
            binding.commentry.setBackgroundColor(resources.getColor(R.color.color_tab_text))
        }
        binding.squad.setOnClickListener {
            binding.matchdetailsTeam1InfoLL.visibility = View.GONE
            binding.matchdetailsTeam2InfoLL.visibility = View.GONE
            binding.commentryLL.visibility = View.GONE
            binding.matchdetailsTeamSquadLL.visibility = View.VISIBLE
            binding.team1NameAndOverTV.setBackgroundColor(Color.TRANSPARENT)
            binding.team2NameAndOverTV.setBackgroundColor(Color.TRANSPARENT)
            binding.commentry.setBackgroundColor(Color.TRANSPARENT)
            binding.squad.setBackgroundColor(resources.getColor(R.color.color_tab_text))
        }

        //commentry
        commentryAdapter = CommentryListAdapter()
        binding.matchDetailsCommentryRV.adapter = commentryAdapter
        binding.matchDetailsCommentryRV.layoutManager = LinearLayoutManager(requireContext())
        lifecycleScope.launch {
            if (MyConstants.verifyAvailableNetwork(requireContext())){
                ballsList = recentMatchInfo.id?.let { viewModel.getFixturesByIdIncludingBalls(it) }!!
                commentryAdapter.submitList(ballsList)
            }else{
                MyConstants.noNetwork(requireActivity())
            }
        }
        //get team squad score for both team
        if (MyConstants.verifyAvailableNetwork(requireContext())){
            try {
                lifecycleScope.launch {
                    team1SquadList = viewModel.getSquadWithTeamAndSeasonId(recentMatchInfo.localteam_id, recentMatchInfo.season_id)
                    val adapter1  = TeamSquadAdpater()
                    binding.team1SquadRV.adapter = adapter1
                    binding.team1SquadRV.layoutManager = LinearLayoutManager(requireContext())
                    adapter1.submitList(team1SquadList)
                    team2SquadList = viewModel.getSquadWithTeamAndSeasonId(recentMatchInfo.visitorteam_id, recentMatchInfo.season_id)
                    val adapter2  = TeamSquadAdpater()
                    binding.team2SquadRV.adapter = adapter2
                    binding.team2SquadRV.layoutManager = LinearLayoutManager(requireContext())
                    adapter2.submitList(team2SquadList)
                }
            }catch (e:Exception){
                CookieBar.build(requireActivity())
                    .setTitle(getString(R.string.network_conncetion))
                    .setMessage("$e")
                    .setDuration(3000)
                    .setAnimationIn(android.R.anim.slide_in_left, android.R.anim.slide_in_left)
                    .setAnimationOut(android.R.anim.slide_out_right, android.R.anim.slide_out_right)
                    .show()
            }

        }else{
            MyConstants.noNetwork(requireActivity())
        }

        binding.backbFAB.setOnClickListener {
            if (lastDest == "MatchViewPager"){
                findNavController().navigate(R.id.action_matchDetailsFragment_to_matchesFragment)
            }else if (lastDest == "PopularT20ViewPager"){
                findNavController().navigate(R.id.action_matchDetailsFragment_to_t20LeaguesFragment)
            }else if (lastDest == "TeamFixture" || lastDest == "TournamentFixtureSeries"){
                val action = MatchDetailsFragmentDirections.actionMatchDetailsFragmentToTournamentFixturesFragment(recentMatchInfo.stage_id!!,"Series")
                findNavController().navigate(action)
            }else if (lastDest == "TournamentFixtureHome"){
                val action = MatchDetailsFragmentDirections.actionMatchDetailsFragmentToTournamentFixturesFragment(recentMatchInfo.stage_id!!,"Home")
                findNavController().navigate(action)
            } else{
                findNavController().navigate(R.id.action_matchDetailsFragment_to_recentMatchesFragment)
            }
        }

        binding.scrollviewTop.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (scrollY > oldScrollY) {
                binding.backbFAB.animate().translationY(bottomNavigationView.height.toFloat())
                binding.backbFAB.hide()
            } else if (scrollY < oldScrollY) {
                binding.backbFAB.animate().translationY(0f)
                binding.backbFAB.show()
            }
        }
    }

    private fun getBatttingAndBowlingSoreOfBothTeams() {
        //get batting score for both teams
        if (MyConstants.verifyAvailableNetwork(requireContext())){
            try {
                //get batting scores for both team players
                lifecycleScope.launch {
                    battingscores = recentMatchInfo.id?.let {
                        viewModel.getFixturesByIdIncludingBattingScore(
                            it
                        )
                    }!!
                    team1Battingscores = battingscores.filter { it.team_id == recentMatchInfo.localteam_id }
                    team1BattingAdapter.submitList(team1Battingscores)
                    delay(3000)
                    team2Battingscores = battingscores.filter { it.team_id == recentMatchInfo.visitorteam_id }
                    team2BattingAdapter.submitList(team2Battingscores)
                    Log.d("batting", "team1Batting: ${team1Battingscores.size}")
                    Log.d("batting", "team1Batting: ${team2Battingscores.size}")
                }
            }catch (e:Exception){
                Log.d("batting", "exception: ${e.message}")
            }
        }else{
            MyConstants.noNetwork(requireActivity())
        }
        //get bowling score for both teams
        if (MyConstants.verifyAvailableNetwork(requireContext())){
            try {
                //get bowling scores for both team players
                lifecycleScope.launch {
                    bowlingscores = recentMatchInfo.id?.let {
                        viewModel.getFixturesByIdIncludingBowlingScore(
                            it
                        )
                    }!!
                    team1Bowlingscores = bowlingscores.filter { it.team_id == recentMatchInfo.localteam_id }
                    team1BowlingAdapter.submitList(team1Bowlingscores)
                    delay(3000)
                    team2Bowlingscores = bowlingscores.filter { it.team_id == recentMatchInfo.visitorteam_id }
                    team2BowlingAdapter.submitList(team2Bowlingscores)
                    Log.d("batting", "team1Batting: ${team1Bowlingscores.size}")
                    Log.d("batting", "team1Batting: ${team2Bowlingscores.size}")
                }
            }catch (e:Exception){
                Log.d("batting", "exception: ${e.message}")
            }

        }else{
            MyConstants.noNetwork(requireActivity())
        }
    }

    private fun getBothTeamRuns(){
        if (MyConstants.verifyAvailableNetwork(requireContext())){
            try {
                lifecycleScope.launch {
                    MyConstants.ALL_RUNS.observe(viewLifecycleOwner){
                        runs = it.filter { it.fixture_id == recentMatchInfo.id }
                        Log.d("getBothTeamRuns", "getBothTeamRuns: ${runs[0].score} ${runs[1].score}")
                        if (runs.isNotEmpty()){
                            binding.detailsTeam2ScoreTV.text = String.format( runs[1].score.toString()+"/"+ runs[1].wickets.toString()+"  ("+ runs[1].overs.toString()+" overs)")
                            binding.detailsTeam1ScoreTV.text = String.format( runs[0].score.toString()+"/"+ runs[0].wickets.toString()+"  ("+ runs[0].overs.toString()+" overs)")
                            binding.team2TotalTV.text = String.format("Total Score:    "+ runs[1].score.toString()+"/"+ runs[1].wickets.toString()+"  ("+ runs[1].overs.toString()+" overs)")
                            binding.team1TotalTV.text = String.format("Total Score:    "+runs[0].score.toString()+"/"+ runs[0].wickets.toString()+"  ("+ runs[0].overs.toString()+" overs)")
                        }
                    }

                }
            }catch (e:Exception){
                Log.d("batting", "exception: ${e.message}")
            }

        }else{
            MyConstants.noNetwork(requireActivity())
        }
    }
    private fun getBattingAdapterForTeam1(){
        //batting adapter for team1
        team1BattingAdapter = BattingScoreAdapter()
        binding.detailsTeam1PlayersScoreRV.adapter = team1BattingAdapter
        val dividerItemDecoration1 = DividerItemDecoration(
            binding.detailsTeam1PlayersScoreRV.context,
            LinearLayoutManager(requireContext()).orientation
        )
        binding.detailsTeam1PlayersScoreRV.addItemDecoration(dividerItemDecoration1)
        binding.detailsTeam1PlayersScoreRV.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun getBowlingAdapterForTeam1(){
        //bowling adapter for team1
        team1BowlingAdapter = BowlingScoreAdapter()
        binding.detailsTeam1BowlingScoreRV.adapter = team1BowlingAdapter
        val dividerItemDecorationteam1Bowling = DividerItemDecoration(
            binding.detailsTeam1BowlingScoreRV.context,
            LinearLayoutManager(requireContext()).orientation
        )
        binding.detailsTeam1BowlingScoreRV.addItemDecoration(dividerItemDecorationteam1Bowling)
        binding.detailsTeam1BowlingScoreRV.layoutManager = LinearLayoutManager(requireContext())
    }
    private fun getBattingAdapterForTeam2(){
        //batting adapter for team2
        team2BattingAdapter = BattingScoreAdapter()
        binding.detailsTeam2PlayersScoreRV.adapter = team2BattingAdapter
        val dividerItemDecoration2 = DividerItemDecoration(
            binding.detailsTeam2PlayersScoreRV.context,
            LinearLayoutManager(requireContext()).orientation
        )
        binding.detailsTeam2PlayersScoreRV.addItemDecoration(dividerItemDecoration2)
        binding.detailsTeam2PlayersScoreRV.layoutManager = LinearLayoutManager(requireContext())
    }
    private fun getBowlingAdapterForTeam2(){
        team2BowlingAdapter = BowlingScoreAdapter()
        binding.detailsTeam2BowlingScoreRV.adapter = team2BowlingAdapter
        val dividerItemDecorationteam2Bowling = DividerItemDecoration(
            binding.detailsTeam2BowlingScoreRV.context,
            LinearLayoutManager(requireContext()).orientation
        )
        binding.detailsTeam2BowlingScoreRV.addItemDecoration(dividerItemDecorationteam2Bowling)
        binding.detailsTeam2BowlingScoreRV.layoutManager = LinearLayoutManager(requireContext())
    }
}