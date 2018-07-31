package pl.michalsulek.vgtiv4.activities

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_vgti.*
import pl.michalsulek.vgtiv4.R
import pl.michalsulek.vgtiv4.ext.suicide

class VGTIActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vgti)

        suicideBtn.setOnClickListener { suicide() }

        Handler().postDelayed({ finish() }, SPLASH_VISIBLE_TIME)
    }

    companion object {
        private const val SPLASH_VISIBLE_TIME = 4000L
    }
}