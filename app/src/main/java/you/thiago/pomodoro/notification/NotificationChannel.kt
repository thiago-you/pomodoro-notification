package you.thiago.pomodoro.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

class NotificationChannel {

    companion object {
        const val CHANNEL_ID = "00000000001"
        const val CHANNEL_NAME = "Pomodoro Notification"
        const val CHANNEL_DESC = "Pomodoro timer feature notification"
    }

    fun create(context: Context) {
        (context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager)?.also {
            it.createNotificationChannel(getChannel())
        }
    }

    private fun getChannel(): NotificationChannel {
        val importance = NotificationManager.IMPORTANCE_DEFAULT

        return NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
            description = CHANNEL_DESC
        }
    }
}