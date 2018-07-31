package pl.michalsulek.vgtiv4.activities

import android.app.Activity
import android.os.Bundle
import pl.michalsulek.vgtiv4.ext.launchYanosikActivity

class YanosikActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchYanosikActivity()
        finish()
    }
}