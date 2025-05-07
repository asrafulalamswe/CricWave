package com.mdasrafulalam.cricwave.adapter

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mdasrafulalam.cricwave.MainActivity
import com.mdasrafulalam.cricwave.R
import com.mdasrafulalam.cricwave.databinding.UpcomingMatchListItemBinding
import com.mdasrafulalam.cricwave.model.fixturesfilteringdateincludingruns.Data
import com.mdasrafulalam.cricwave.utils.MyConstants
import com.mdasrafulalam.cricwave.workers.WorkManagerUtils
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
// Define a constant for the notification channel ID
private const val NOTIFICATION_CHANNEL_ID = "UpcomingMatchNotifications"
class UpcomingMatchAdapter : androidx.recyclerview.widget.ListAdapter<Data, UpcomingMatchAdapter.UpcomingViewHolder>(RecentMatchesDiffCallback()){
    private var timerList: MutableList<CountDownTimer?> = mutableListOf()
    private lateinit var context: Context
    private var notificationId =0
    inner class UpcomingViewHolder(val binding: UpcomingMatchListItemBinding): RecyclerView.ViewHolder(binding.root){
        var timer: CountDownTimer? = null
        fun bind(recentMatch: Data) {
            /*val team1Standings = MyConstants.ALL_STANDINGSBYSESON.filter { it.season_id == recentMatch.season_id && it.team_id == recentMatch.localteam_id }
            val team2Standings = MyConstants.ALL_STANDINGSBYSESON.filter { it.season_id == recentMatch.season_id && it.team_id == recentMatch.visitorteam_id }

            val team1TotalWins = team1Standings.sumBy { it.won!! }
            val team1TotalMatchesPlayed = team1Standings.sumBy { it.played!! }
            val team1RecentWins = team1Standings.flatMap { it.recent_form!! }
                .count { it.equals("W", ignoreCase = true) }
            val team1RecentMatchesPlayed = 5
            val team1RecentFormWeight = 0.6
            val team1OverallFormWeight = 1 - team1RecentFormWeight

            val team2TotalWins = team2Standings.sumBy { it.won!! }
            val team2TotalMatchesPlayed = team2Standings.sumBy { it.played!! }
            val team2RecentWins = team2Standings.flatMap { it.recent_form!! }
                .count { it.equals("W", ignoreCase = true) }
            val team2RecentMatchesPlayed = 5
            val team2RecentFormWeight = 0.6
            val team2OverallFormWeight = 1 - team2RecentFormWeight

            val team1WinningPercentage = calculateWinningPercentage(
                team1TotalWins,
                team1TotalMatchesPlayed,
                team1RecentWins,
                team1RecentMatchesPlayed,
                team1OverallFormWeight
            )
            val team2WinningPercentage = calculateWinningPercentage(
                team2TotalWins,
                team2TotalMatchesPlayed,
                team2RecentWins,
                team2RecentMatchesPlayed,
                team2OverallFormWeight
            )

            Log.d("teamWinningPercentage", "team1WinningPercentage: $team1WinningPercentage, ${team1Standings.size}")
            Log.d("teamWinningPercentage", "team2WinningPercentage: $team2WinningPercentage")
            binding.winningPercentageBar.progress = team1WinningPercentage.toInt()
            binding.winningPercentageBar.secondaryProgress = (team1WinningPercentage + team2WinningPercentage).toInt()*/
            val randomPercentage = (30..70).random()
            val progress2 = 100 - randomPercentage
            binding.winningPercentageBar.progress = randomPercentage
            binding.winning1.text = "%d%%".format(randomPercentage)
            binding.winning2.text = "%d%%".format(progress2)

            val matchTime = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
                .parse(recentMatch.starting_at.toString())
            val remainingTime = matchTime!!.time - System.currentTimeMillis()

            if (remainingTime > 0) {
                timer?.cancel()
                timer = object : CountDownTimer(remainingTime, 1000) {
                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onTick(millisUntilFinished: Long) {
                        val remainingSeconds = millisUntilFinished / 1000
                        // Calculate the remaining time in days, hours, minutes and seconds
                        val remainingMinutes = remainingSeconds / 60
                        val remainingHours = remainingMinutes / 60
                        val remainingDays = remainingHours / 24
                        if (remainingSeconds <= 21600) {
                            val remainingMatchHours = (remainingSeconds / 3600).toInt()
                            val remainingMatchMinutes = ((remainingSeconds % 3600) / 60).toInt()
                            // Format the remaining time as "2D:4H:30M:20S"
                            val remainingTimeFormatted = String.format(
                                Locale.getDefault(),
                                "%02dHours:%02dMin",
                                remainingMatchHours,
                                remainingMatchMinutes,
                            )
                            // Update the UI with the remaining time
                            binding.detailsMatchiDateTimeInfo.text = remainingTimeFormatted
                        } else if (remainingDays.toInt() <= 1) {
                            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
                            val date = LocalDateTime.parse(recentMatch.starting_at, formatter)
                            val zoneId = ZoneId.systemDefault()
                            val bangladeshTime = date.atZone(ZoneId.of("UTC")).withZoneSameInstant(zoneId)
                            val formattedDate = bangladeshTime.format(DateTimeFormatter.ofPattern("hh:mm a"))
                            binding.detailsMatchiDateTimeInfo.text =
                                String.format("Tomorrow $formattedDate")
                        } else {
                            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
                            val date = LocalDateTime.parse(recentMatch.starting_at, formatter)
                            val zoneId = ZoneId.systemDefault()
                            val bangladeshTime = date.atZone(ZoneId.of("UTC")).withZoneSameInstant(zoneId)
                            val formattedDate = bangladeshTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a"))
                            binding.detailsMatchiDateTimeInfo.text = String.format(formattedDate)
                        }

                        if (remainingSeconds == 600L) {
                            // Send  notification when there are 15 minutes left
                            val tourname =
                                MyConstants.ALL_STAGES.value?.filter { it.id == recentMatch.stage_id }?.map { it.name }?.get(0)
                            sendNotification(recentMatch)
                            if (tourname != null) {
                                WorkManagerUtils().sendNotification(tourname,binding.root.context)
                            }
                            Log.d("onTickworker", "onTick: Worker called ${recentMatch.round}")
                        }
                    }

                    override fun onFinish() {
                        // Do something when the timer finishes
                        binding.detailsMatchiDateTimeInfo.text =
                            binding.root.context.getString(R.string.match_started)
                    }
                }.start()
                timerList.add(timer)
            } else {
//                binding.detailsMatchiDateTimeInfo.text = "Match started"
                val dateFormat =
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
                val cal = Calendar.getInstance()
                val currentDateTime = dateFormat.format(cal.time)
                val date = dateFormat.parse(currentDateTime)
                if (date != null) {
                    val list = MyConstants.ALL_UpcomingFIXTURES.value
                    MyConstants.ALL_UpcomingFIXTURES.value = list?.filter { i ->
                        i.status == "NS" && dateFormat.parse(i.starting_at!!)!!.after(date)
                    }
                }
            }

            binding.recent = recentMatch

        }

        fun calculateWinningPercentage(totalWins: Int, totalMatchesPlayed: Int, recentWins: Int, recentMatchesPlayed: Int, recentFormWeight: Double): Double {
            val overallWinningPercentage = if (totalMatchesPlayed > 0) totalWins.toDouble() / totalMatchesPlayed.toDouble() else 0.0
            val recentWinningPercentage = if (recentMatchesPlayed > 0) recentWins.toDouble() / recentMatchesPlayed.toDouble() else 0.0
            val weightedWinningPercentage = (1 - recentFormWeight) * overallWinningPercentage + recentFormWeight * recentWinningPercentage
            return weightedWinningPercentage * 100 // Convert to percentage
        }
    }
    class RecentMatchesDiffCallback : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingViewHolder {
        val binding =
            UpcomingMatchListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UpcomingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UpcomingViewHolder, position: Int) {
        val recentMatch: Data = getItem(position)
        holder.bind(recentMatch)
        context = holder.itemView.context
    }

    override fun onViewRecycled(holder: UpcomingViewHolder) {
        holder.timer?.cancel()
        super.onViewRecycled(holder)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        timerList.forEach { it?.cancel() }
        super.onDetachedFromRecyclerView(recyclerView)
    }
    @SuppressLint("UnspecifiedImmutableFlag")
    private fun sendNotification(item: Data) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        val tourname =
            MyConstants.ALL_STAGES.value?.filter { it.id == item.stage_id }?.map { it.name }?.get(0)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                tourname,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.notificaiton)
            .setContentTitle(context.getString(R.string.coming_next))
            .setContentText(tourname)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        notificationManager.notify(notificationId++, builder.build())
    }

}