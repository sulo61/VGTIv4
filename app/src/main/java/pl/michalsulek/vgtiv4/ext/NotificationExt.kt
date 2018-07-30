package pl.michalsulek.vgtiv4.ext

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.text.format.DateFormat
import pl.michalsulek.vgtiv4.R
import java.util.*

/**
 * www.michalsulek.pl
 */
class NotificationUtils {

    companion object {
        const val TITLE: String = "VGTI"
        const val CONTENT_STARTED: String = "Started"
        const val CONTENT_KILLED: String = "Stopped"
        const val DATE_FORMAT: String = "yyyy-MM-dd  kk:mm:ss"
        const val NOTIFICATION_MIN_ID: Int = 66
    }

    enum class LaunchStatus {
        STARTED,
        KILLED
    }
}

fun NotificationUtils.Companion.showNotification(context: Context, launchStatus: NotificationUtils.LaunchStatus) {
    (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .notify(NOTIFICATION_MIN_ID, Notification.Builder(context)
                    .setAutoCancel(false)
                    .setOngoing(true)
                    .setContentTitle(NotificationUtils.TITLE)
                    .setColor(getNotificationColor(launchStatus))
                    .setContentText(getNotificationMessage(launchStatus))
                    .setSmallIcon(getNotificationIcon(launchStatus))
                    .build())

}

private fun getNotificationIcon(launchStatus: NotificationUtils.LaunchStatus) =
        when (launchStatus) {
            NotificationUtils.LaunchStatus.STARTED -> R.drawable.ic_check_green_500_24dp
            NotificationUtils.LaunchStatus.KILLED -> R.drawable.ic_close_red_500_24dp
        }


private fun getNotificationColor(launchStatus: NotificationUtils.LaunchStatus): Int {
    return when (launchStatus) {
        NotificationUtils.LaunchStatus.STARTED -> Color.GREEN
        NotificationUtils.LaunchStatus.KILLED -> Color.RED
    }
}

private fun NotificationUtils.Companion.getNotificationMessage(launchStatus: NotificationUtils.LaunchStatus) =
        when (launchStatus) {
            NotificationUtils.LaunchStatus.STARTED -> CONTENT_STARTED
            NotificationUtils.LaunchStatus.KILLED -> CONTENT_KILLED
        } + " " + DateFormat.format(DATE_FORMAT, Date().time)