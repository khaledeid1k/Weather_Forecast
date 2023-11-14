package com.kh.mo.weatherforecast.ui.alert

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.kh.mo.weatherforecast.MainActivity
import com.kh.mo.weatherforecast.R
import com.kh.mo.weatherforecast.local.LocalDataImp
import com.kh.mo.weatherforecast.model.Weather
import com.kh.mo.weatherforecast.model.ui.LocationData
import com.kh.mo.weatherforecast.remot.ApiSate
import com.kh.mo.weatherforecast.remot.RemoteDataImp
import com.kh.mo.weatherforecast.repo.RepoIm
import com.kh.mo.weatherforecast.utils.Constants.CHANNEL_ID
import com.kh.mo.weatherforecast.utils.Constants.NOTIFICATION_DATA
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmReceiver : BroadcastReceiver() {
    private lateinit var repoIm: RepoIm
    override fun onReceive(context: Context, intent: Intent?) {
        intiRepo(context)


        val locationData = intent?.getParcelableExtra<LocationData>(NOTIFICATION_DATA)!!
        getCurrentUpdatedWeatherState(locationData,context)
    }

    private fun intiRepo(context: Context) {
        repoIm = RepoIm.getRepoImInstance(
                LocalDataImp.getLocalDataImpInstance(context),
        RemoteDataImp.getRemoteDataImpInstance(context))

    }

    fun getCurrentUpdatedWeatherState(locationData: LocationData,context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            repoIm.getCurrentUpdatedWeatherState(locationData.lat, locationData.lon)
                .collect {
                    when (it) {
                        is ApiSate.Failure -> {

                        }
                        is ApiSate.Loading -> {

                        }
                        is ApiSate.Success -> {
                            createNotification(context,locationData)
                        }
                    }


                }
        }
    }


    private fun createNotification(context: Context,locationData:LocationData): NotificationCompat.Builder {

        val notificationManager =
            context.getSystemService(NotificationManager::class.java) as NotificationManager

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_IMMUTABLE,
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = context.getString(R.string.channelName)
            val channelImportance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel =
                NotificationChannel(CHANNEL_ID, channelName, channelImportance)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(locationData.nameOfCity + " , "+ locationData.nameOfCountry)
            .setContentText("Every Thing is good")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
        notificationManager.notify(1, builder.build())
        return builder
    }


}
