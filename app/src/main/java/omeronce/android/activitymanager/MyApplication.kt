package omeronce.android.activitymanager

import android.app.Application
import android.util.Log
import omeronce.android.smartactivitymanager.SmartActivityManager
import omeronce.android.smartactivitymanager.enums.AppState
import omeronce.android.smartactivitymanager.observers.AppStateObserver
import omeronce.android.smartactivitymanager.observers.WeakRefAppStateObserver

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        SmartActivityManager.init(this)
    }
}