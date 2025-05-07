package com.mdasrafulalam.cricwave.workers

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mdasrafulalam.cricwave.viewmomdel.CricketViewModel


class SyncWorker(context: Context, params: WorkerParameters) : Worker(context, params),
    ViewModelStoreOwner {
    private lateinit var viewModel: CricketViewModel
    private val appViewModelStore: ViewModelStore by lazy { ViewModelStore() }

    @SuppressLint("WrongThread")
    override fun doWork(): Result {
        return try {
            viewModel =  ViewModelProvider(this)[CricketViewModel::class.java]
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                viewModel.fetchAndInsertAllDataFromApi()
            }
            Log.d(TAG, "doWork: updated")
            makeStatusNotification(
                "There may be some updated fixtures! Please check.",
                applicationContext
            )
            return Result.success()
        } catch (throwable: Throwable) {
            Log.e(TAG, "Error fetching news")
            throwable.printStackTrace()
            Result.failure()
        }

    }

    override val viewModelStore: ViewModelStore
        get() = appViewModelStore
}