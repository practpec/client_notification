package com.example.client_notification.core.FCM
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.example.client_notification.MainActivity
import com.example.client_notification.R

class FCMService : FirebaseMessagingService() {

    companion object {
        private const val TAG = "FCMService"
        private var token: String? = null

        fun getToken(): String? {
            return token
        }

        fun setToken(newToken: String) {
            token = newToken
        }
    }

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
        Log.d(TAG, "Refreshed token: $newToken")

        token = newToken

        saveTokenToPrefs(newToken)

        if (isUserLoggedIn()) {
            sendTokenToServer(newToken)
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(TAG, "From: ${remoteMessage.from}")

        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
            handleDataMessage(remoteMessage.data)
        }

        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            sendNotification(it.title ?: "Notificación", it.body ?: "")
        }
    }

    private fun saveTokenToPrefs(token: String) {
        val sharedPrefs = applicationContext.getSharedPreferences("FCMPrefs", Context.MODE_PRIVATE)
        with(sharedPrefs.edit()) {
            putString("fcm_token", token)
            apply()
        }
    }


    private fun sendTokenToServer(token: String) {
        Log.d(TAG, "Enviando token al servidor: $token")
    }

    private fun isUserLoggedIn(): Boolean {
        val sharedPrefs = applicationContext.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        return sharedPrefs.contains("auth_token")
    }

    private fun handleDataMessage(data: Map<String, String>) {
        val type = data["type"] ?: return

        when (type) {
            "new_order" -> {
                val orderId = data["order_id"] ?: return
            }
            "order_assigned" -> {
                val orderId = data["order_id"] ?: return
                val courierName = data["courier_name"] ?: "Un repartidor"
            }
            "order_completed" -> {
                val orderId = data["order_id"] ?: return
            }
        }
    }

    // Método para mostrar una notificación
    private fun sendNotification(title: String, messageBody: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT
        )
        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_stat_name)
            .setContentTitle(title)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Para Android Oreo y superior, es necesario crear un canal de notificación
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Canal de notificaciones de la aplicación",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }
}