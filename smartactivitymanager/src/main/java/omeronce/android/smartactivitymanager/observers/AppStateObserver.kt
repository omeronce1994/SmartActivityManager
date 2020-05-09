package omeronce.android.smartactivitymanager.observers

import omeronce.android.smartactivitymanager.enums.AppState

interface AppStateObserver {
    fun onChanged(appState: AppState)
}