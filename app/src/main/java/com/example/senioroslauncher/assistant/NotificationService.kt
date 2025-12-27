package com.example.senioroslauncher.assistant

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log

class NotificationService : NotificationListenerService() {

    companion object {
        private const val TAG = "NotificationService"
        var activeNotificationsList: Array<StatusBarNotification>? = null
            private set
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        updateActiveNotifications()
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)
        updateActiveNotifications()
    }

    private fun updateActiveNotifications() {
        activeNotificationsList = activeNotifications
        Log.d(TAG, "Active notifications: ${activeNotifications.size}")
    }

    override fun onListenerConnected() {
        super.onListenerConnected()
        updateActiveNotifications()
        Log.d(TAG, "Notification Listener Connected")
    }

    override fun onListenerDisconnected() {
        super.onListenerDisconnected()
        Log.d(TAG, "Notification Listener Disconnected")
    }
}