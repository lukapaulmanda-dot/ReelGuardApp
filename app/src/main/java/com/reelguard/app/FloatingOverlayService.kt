package com.reelguard.app

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView

/**
 * FloatingOverlayService
 * Renders the floating counter widget on top of all apps (SYSTEM_ALERT_WINDOW).
 */
class FloatingOverlayService : Service() {

    private lateinit var windowManager: WindowManager
    private lateinit var overlayView: View
    private lateinit var txtCounter: TextView
    private var reelCount = 0

    private val reelReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            reelCount++
            txtCounter.text = "$reelCount Reels"
        }
    }

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        
        // Inflate floating widget layout
        overlayView = LayoutInflater.from(this).inflate(R.layout.layout_floating_widget, null)
        txtCounter = overlayView.findViewById(R.id.txtOverlayReels)

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.END
            x = 30
            y = 150
        }

        windowManager.addView(overlayView, params)
        registerReceiver(reelReceiver, IntentFilter("com.reelguard.ACTION_REEL_SWIPED"))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(reelReceiver)
        if (::overlayView.isInitialized) windowManager.removeView(overlayView)
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
