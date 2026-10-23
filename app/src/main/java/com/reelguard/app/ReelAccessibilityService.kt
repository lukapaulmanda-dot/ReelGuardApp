package com.reelguard.app

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast

/**
 * ReelAccessibilityService
 * Listens for scroll/swipe events on YouTube Shorts, Instagram Reels, and TikTok.
 */
class ReelAccessibilityService : AccessibilityService() {

    private val targetPackages = setOf(
        "com.google.android.youtube",
        "com.instagram.android",
        "com.zhiliaoapp.musically" // TikTok
    )

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return

        val packageName = event.packageName?.toString() ?: return

        if (targetPackages.contains(packageName)) {
            // Detect vertical scroll events indicating next short video watched
            if (event.eventType == AccessibilityEvent.TYPE_VIEW_SCROLLED) {
                Intent("com.reelguard.ACTION_REEL_SWIPED").apply {
                    putExtra("platform", packageName)
                    sendBroadcast(this)
                }
            }
        }
    }
