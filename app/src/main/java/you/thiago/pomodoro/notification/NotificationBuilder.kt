package you.thiago.pomodoro.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import you.thiago.pomodoro.R
import java.lang.ref.WeakReference

class NotificationBuilder (context: Context) {

    companion object {
        const val CHANNEL_ID = "00000000001"

        private var notificationId = 0

        private fun getNotificationId(): Int {
            synchronized(this) {
                return ++notificationId
            }
        }
    }

    private val weakContext = WeakReference(context)

    fun send() {
        weakContext.get()?.also { context ->
            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Pomodoro")
                .setContentText("Test")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            createNotificationChannel()

            with (NotificationManagerCompat.from(context)) {
                // notificationId is a unique int for each notification that you must define
                notify(getNotificationId(), builder.build())
            }
        }
    }

    private fun createNotificationChannel() {
        val name = "notification_channel"
        val descriptionText = "notification_desc"
        val importance = NotificationManager.IMPORTANCE_DEFAULT

        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }

        // Register the channel with the system
        weakContext.get()?.also { context ->
            (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).also {
                it.createNotificationChannel(channel)
            }
        }
    }
}