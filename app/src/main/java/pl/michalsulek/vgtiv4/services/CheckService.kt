package pl.michalsulek.vgtiv4.services

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import pl.michalsulek.vgtiv4.App
import pl.michalsulek.vgtiv4.ext.*

class CheckService : IntentService(CheckService::class.java.simpleName) {
    override fun onHandleIntent(p0: Intent?) {
        if (isCharging(applicationContext)) wakeUp()
        else sleep()
        stopSelf()
    }

    private fun sleep() {
        NotificationUtils.showNotification(applicationContext, NotificationUtils.LaunchStatus.KILLED)

        killYanosik()
        killTorque()

        if (isGpsOn(applicationContext)) changeGpsSetting(false)
        if (isBluetoothOn(applicationContext)) changeBluetoothSetting(false)
        if (isWifiOn(applicationContext)) changeWifiSetting(false)
        if (!isAirplaneModeOn(applicationContext)) changeAirplaneSetting(true)

        setBrightnessOff()
        setPowerSaveGovernor()

        if (App.rebootOnSleep) {
            App.rebootOnSleep = false
            rebootDevice()
        }
    }

    private fun wakeUp() {
        App.rebootOnSleep = true

        NotificationUtils.showNotification(applicationContext, NotificationUtils.LaunchStatus.STARTED)

        setOnDemandGovernor()
        setBrightnessOn()

        if (isAirplaneModeOn(applicationContext)) changeAirplaneSetting(false)
        if (!isGpsOn(applicationContext)) changeGpsSetting(true)
        if (!isBluetoothOn(applicationContext)) changeBluetoothSetting(true)
        if (!isYanosikRunning()) launchYanosikActivity()
    }

    private fun isCharging(context: Context) = getBatteryStatus(context).let {
        (it == BatteryManager.BATTERY_PLUGGED_AC) || (it == BatteryManager.BATTERY_PLUGGED_USB)
    }


    private fun getBatteryStatus(context: Context) = context
            .registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            .getIntExtra(BatteryManager.EXTRA_PLUGGED, BatteryManager.BATTERY_STATUS_UNKNOWN)

}