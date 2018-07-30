package pl.michalsulek.vgtiv4

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import pl.michalsulek.vgtiv4.services.CheckService

class App : Application() {

    private var alarmIntent: PendingIntent? = null

    override fun onCreate() {
        super.onCreate()
        if (alarmIntent == null) alarmIntent = createAlarmIntent()
        cancelPreviousAlarms()
        addNewAlarm()
    }

    private fun createAlarmIntent(): PendingIntent? {
        return PendingIntent.getService(
                applicationContext,
                REQUEST_CODE,
                Intent(applicationContext, CheckService::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun cancelPreviousAlarms() {
        alarmIntent?.let {
            (getSystemService(Context.ALARM_SERVICE) as AlarmManager).cancel(it)
        }

    }

    private fun addNewAlarm() {
        alarmIntent?.let {
            (getSystemService(Context.ALARM_SERVICE) as AlarmManager).setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis(),
                    CHECK_INTERVAL_IN_MILLIS,
                    it
            )
        }
    }

    companion object {
        var rebootOnSleep = false
        private const val CHECK_INTERVAL_IN_MILLIS = 60000L // it's always forced up to 60 sec, read documentation about setRepeating
        private const val REQUEST_CODE = 777
    }
}
