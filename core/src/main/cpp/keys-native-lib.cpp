
#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring

Java_com_islam_task_core_ElMenusKeys_geApiUrl(JNIEnv *env, jobject object) {
    return env->NewStringUTF("https://www.themealdb.com/api/json/v1/");
}

extern "C" JNIEXPORT jstring

Java_com_islam_task_core_ElMenusKeys_geApiKey(JNIEnv *env, jobject object) {
    return env->NewStringUTF("1");
}