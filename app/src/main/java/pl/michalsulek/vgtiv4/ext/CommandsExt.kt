package pl.michalsulek.vgtiv4.ext

import android.bluetooth.BluetoothAdapter
import eu.chainfire.libsuperuser.Shell

private const val YANOSIK_PACKAGE = "pl.neptis.yanosik.mobi.android"
private const val YANOSIK_LAUNCHER_ACTIVITY = "pl.neptis.yanosik.mobi.android.common.ui.activities.launcher.LauncherActivity"
private const val TORQUE_PACKAGE = "org.prowl.torque"
private const val TORQUE_LAUNCHER_ACTIVITY = "org.prowl.torque.landing.FrontPage"
private const val DELAY_BETWEEN_COMMANDS = 1000L
private const val GTI__PROCESS = "pl.michalsulek.vgtiv4"

fun changeAirplaneSetting(enabled: Boolean) {
    runCommand("settings put global airplane_mode_on " + if (enabled) "1" else "0")
    runCommand("am broadcast -a android.intent.action.AIRPLANE_MODE --ez state " + if (enabled) "true" else "false")
}

fun changeGpsSetting(enabled: Boolean) {
    runCommand(
            if (enabled) "settings put secure location_providers_allowed gps,wifi,network"
            else "settings put secure location_providers_allowed ' '")
}

fun changeBluetoothSetting(enabled: Boolean) {
    if (enabled) BluetoothAdapter.getDefaultAdapter().enable() else BluetoothAdapter.getDefaultAdapter().disable()
}

fun changeWifiSetting(enabled: Boolean) {
    runCommand("svc wifi " + if (enabled) "enable" else "disable")
}

fun launchYanosikActivity() {
    enableProcess(YANOSIK_PACKAGE)
    runCommand("am start -n $YANOSIK_PACKAGE/$YANOSIK_LAUNCHER_ACTIVITY")
}

fun launchTorqueActivity() {
    enableProcess(TORQUE_PACKAGE)
    runCommand("am start -n $TORQUE_PACKAGE/$TORQUE_LAUNCHER_ACTIVITY")
}

fun killYanosik() = killCommand(YANOSIK_PACKAGE)
fun killTorque() = killCommand(TORQUE_PACKAGE)
fun suicide() =restartCommand(GTI__PROCESS)

fun restartCommand(processName: String) {
    killCommand(processName)
    enableProcess(processName)
}

fun enableProcess(processName: String) {
    runCommand("pm enable $processName")
}

fun killCommand(processName: String) {
    runCommand("am force-stop $processName")
    runCommand("am kill $processName")
    runCommand("pm disable $processName")
}

fun setBrightnessOff() {
    runCommand("settings put system screen_brightness 0")
}

fun setBrightnessOn() {
    runCommand("settings put system screen_brightness 134")
}

fun setPowerSaveGovernor() {
    runCommand("echo 'powersave' > /sys/devices/system/cpu/cpu0/cpufreq/scaling_governor")
    runCommand("echo 'powersave' > /sys/devices/system/cpu/cpu1/cpufreq/scaling_governor")
}

fun setOnDemandGovernor() {
    runCommand("echo 'ondemand' > /sys/devices/system/cpu/cpu0/cpufreq/scaling_governor")
    runCommand("echo 'ondemand' > /sys/devices/system/cpu/cpu1/cpufreq/scaling_governor")
}

fun runCommand(command: String) {
    Shell.SU.run(command)
    Thread.sleep(DELAY_BETWEEN_COMMANDS)
}

fun rebootDevice() = runCommand("reboot")

fun isYanosikRunning() = Shell.SU.run("ps | grep $YANOSIK_PACKAGE").size > 0
fun isTorqueRunning() = Shell.SU.run("ps | grep $TORQUE_PACKAGE").size > 0
