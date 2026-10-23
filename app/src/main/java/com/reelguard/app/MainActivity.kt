package com.reelguard.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

/**
 * MainActivity
 * Setup screen for system permissions (Draw over apps & Accessibility Service).
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnOverlayPerm).setOnClickListener {
            if (!Settings.canDrawOverlays(this)) {
                startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")))
            }
        }

        findViewById<Button>(R.id.btnAccessibilityPerm).setOnClickListener {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }

        findViewById<Button>(R.id.btnStartService).setOnClickListener {
            startService(Intent(this, FloatingOverlayService::class.java))
        }
    }
}
