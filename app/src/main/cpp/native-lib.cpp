#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_omeronce_android_activitymanager_HelloWorldNative_helloWorldNative(JNIEnv *env, jobject thiz) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT void JNICALL
Java_omeronce_android_activitymanager_HelloWorldNative_observeNative(JNIEnv *env, jobject thiz, jobject livedata) {
    jclass ldClass = env->GetObjectClass(livedata);
    jmethodID mId = env->GetMethodID(ldClass, "postValue", "(Ljava/lang/Object;)V");
    std::string hello = "Hello from C++";
    jstring js = env->NewStringUTF(hello.c_str());
    env->CallVoidMethod(livedata, mId, js);
}

