package com.mdasrafulalam.cricwave.workers

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import androidx.work.Data
import androidx.work.WorkerParameters
import com.mdasrafulalam.cricwave.MainActivity
import com.mdasrafulalam.cricwave.utils.MyConstants
import java.util.concurrent.TimeUnit

const val TAG: String = "WorkerUtils"

@SuppressLint("MissingPermission", "UnspecifiedImmutableFlag")
fun makeStatusNotification(message: String, context: Context) {
    // Make a channel if necessary
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = MyConstants.VERBOSE_NOTIFICATION_CHANNEL_NAME
        val description = MyConstants.VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(MyConstants.CHANNEL_ID, name, importance)
        channel.description = description
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        notificationManager?.createNotificationChannel(channel)
    }
    val intent = Intent(context, MainActivity:: class.java).apply{
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
    val builder = NotificationCompat.Builder(context, MyConstants.CHANNEL_ID)
        .setSmallIcon(com.mdasrafulalam.cricwave.R.drawable.notificaiton)
        .setContentTitle(MyConstants.NOTIFICATION_TITLE)
        .setContentText(message)
        .setColor(context.resources.getColor(com.mdasrafulalam.cricwave.R.color.swipe_color_1))
        .setContentIntent(pendingIntent)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setVibrate(LongArray(0))
    NotificationManagerCompat.from(context).notify(MyConstants.NOTIFICATION_ID, builder.build())
}

class WorkManagerUtils {
    fun syncData(context: Context) {
        val workManager = WorkManager.getInstance(context)
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val periodicWorkRequest =
            PeriodicWorkRequest.Builder(SyncWorker::class.java, 2, TimeUnit.DAYS).setConstraints(constraints).addTag("Sync_Data").build()
        workManager.enqueueUniquePeriodicWork(
            "Sync_Data",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )
    }
    fun sendNotification(message:String, context: Context){
        val workManager = WorkManager.getInstance(context)
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        // Define input data
        val inputData = Data.Builder()
            .putString("message", message)
            .build()
        val sendNotificationWork =
            OneTimeWorkRequest.Builder(SendNotificationWorker::class.java).setConstraints(constraints).addTag("Sync_Data").setInputData(inputData).build()
        workManager.enqueue(sendNotificationWork)
    }
}
