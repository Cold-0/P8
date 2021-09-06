package com.cold0.realestatemanager.notifications

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat

object NotificationHelper {
	@SuppressLint("UnspecifiedImmutableFlag")
	fun sendSimpleNotification(context: Context, icon: Int? = null, title: String? = null, message: String? = null, intent: Intent, reqCode: Int = 9195919) {
		val pendingIntent = PendingIntent.getActivity(context, reqCode, intent, PendingIntent.FLAG_ONE_SHOT)
		val channelId = "REAL_ESTATE_MANAGER"
		val notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(context, channelId)
			.setSmallIcon(icon ?: android.R.drawable.ic_input_add)
			.setContentTitle(title)
			.setContentText(message)
			.setAutoCancel(true)
			.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
			.setContentIntent(pendingIntent)
		val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			val name: CharSequence = "Real Estate Manager"
			val importance = NotificationManager.IMPORTANCE_HIGH
			val mChannel = NotificationChannel(channelId, name, importance)
			notificationManager.createNotificationChannel(mChannel)
		}
		notificationManager.notify(reqCode, notificationBuilder.build())
	}
}