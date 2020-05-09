package omeronce.android.smartactivitymanager.managers

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import omeronce.android.smartactivitymanager.enums.AppState
import omeronce.android.smartactivitymanager.observers.AppStateObserver

interface IActivityManager {
    fun registerActivity(activity: Activity)
    fun getAppState(): AppState
    fun observe(appStateObserver: AppStateObserver)
    fun observe(lifecycleOwner: LifecycleOwner, appStateObserver: AppStateObserver)
    fun removeObserver(appStateObserver: AppStateObserver)
    fun clearObservers()
}