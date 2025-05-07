package com.mdasrafulalam.cricwave.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mdasrafulalam.cricwave.model.fixturesfilteringdateincludingruns.Data
import com.mdasrafulalam.cricwave.utils.MyConstants

class SendNotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params){
    override fun doWork(): Result {
        val inputData = inputData
        val value = inputData.getString("message")
        return try {
            Log.d(TAG, "doWork: updated")
            if (value != null) {
                makeStatusNotification(
                    value,
                    applicationContext
                )
            }
            return Result.success()
        } catch (throwable: Throwable) {
            Log.e(TAG, "Error fetching data")
            throwable.printStackTrace()
            Result.failure()
        }
    }

}