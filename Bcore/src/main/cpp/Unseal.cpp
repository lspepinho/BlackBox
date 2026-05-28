#include <jni.h>
#include "FreeReflection/art.h"

extern "C"
JNIEXPORT jint JNICALL
Java_top_niunaijun_blackbox_reflection_Reflection_unsealNative(JNIEnv *env, jclass type, jint targetSdkVersion) {
    return unseal(env, targetSdkVersion);
}
