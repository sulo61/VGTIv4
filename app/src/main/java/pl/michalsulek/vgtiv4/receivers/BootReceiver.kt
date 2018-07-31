package pl.michalsulek.vgtiv4.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import pl.michalsulek.vgtiv4.activities.VGTIActivity

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            it.startActivity(Intent(it, VGTIActivity::class.java)
                    .apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) })
        }
    }
}