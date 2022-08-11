package you.thiago.pomodoro.notification

import android.app.Notification as AppNotification
import android.content.Context
import androidx.core.app.NotificationManagerCompat
import java.lang.ref.WeakReference

class Notification (
    context: Context,
    private val appNotification: AppNotification
) {

    private val weakContext = WeakReference(context)

    companion object {
        private var notificationId = 0

        private fun getNotificationId(): Int {
            synchronized(this) {
                return ++notificationId
            }
        }
    }

    fun send() {
        weakContext.get()?.also { context ->
            with (NotificationManagerCompat.from(context)) {
                notify(getNotificationId(), appNotification)
            }
        }
    }
}