package omeronce.android.smartactivitymanager.ktextensions

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import omeronce.android.smartactivitymanager.enums.AppState
import omeronce.android.smartactivitymanager.observers.AppStateObserver

internal fun Application.onActivityCreated(lambda: (Activity) -> Unit) {
   this.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
       override fun onActivityPaused(activity: Activity) {}

       override fun onActivityStarted(activity: Activity) {}

       override fun onActivityDestroyed(activity: Activity) {}

       override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

       override fun onActivityStopped(activity: Activity) {}

       override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
           Log.i("onActivityCreated: ", "callback called")
           lambda.invoke(activity)
       }

       override fun onActivityResumed(activity: Activity) {}
   })
}

fun Any.AppStateObserver(lmbd: (AppState) -> Unit) = object : AppStateObserver {
    override fun onChanged(appState: AppState) {
        lmbd.invoke(appState)
    }
}