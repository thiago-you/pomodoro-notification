package you.thiago.pomodoro.notification

import android.content.Context
import androidx.core.app.NotificationCompat
import you.thiago.pomodoro.R

class NotificationBuilder {

    private var title = ""
    private var content = ""

    companion object {
        const val CHANNEL_ID = "00000000001"
    }

    fun setTitle(title: String): NotificationBuilder {
        this.title = title
        return this
    }

    fun setContent(content: String): NotificationBuilder {
        this.content = content
        return this
    }

    fun build(context: Context): Notification {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        NotificationChannel().create(context)

        return Notification(context, builder.build())
    }
}