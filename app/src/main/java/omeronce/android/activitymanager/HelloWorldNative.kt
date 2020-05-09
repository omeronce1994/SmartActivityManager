package omeronce.android.activitymanager

import androidx.lifecycle.MutableLiveData

class HelloWorldNative {
    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }

    external fun helloWorldNative(): String
    external fun observeNative(mutableLiveData: MutableLiveData<String>): Unit
}