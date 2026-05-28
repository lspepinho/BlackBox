#ifndef FREEREFLECTION_ART_H
#define FREEREFLECTION_ART_H

#include <jni.h>
#include <string>

struct JavaVMExt {
    void *functions;
    void *runtime;
};

struct ExperimentalFlags {
    uint32_t value;
};

enum class EnforcementPolicy {
    kNoChecks = 0,
    kJustWarn = 1,
    kDarkGreyAndBlackList = 2,
    kBlacklistOnly = 3,
    kMax = 3,
};

struct PartialRuntime {
    int32_t target_sdk_version_;
    bool implicit_null_checks_;
    bool implicit_so_checks_;
    bool implicit_suspend_checks_;
    bool no_sig_chain_;
    bool force_native_bridge_;
    bool is_native_bridge_loaded_;
    bool is_native_debuggable_;
    bool async_exceptions_thrown_;
    bool is_java_debuggable_;
    uint32_t zygote_max_failed_boots_;
    ExperimentalFlags experimental_flags_;
    std::string fingerprint_;
    void *oat_file_manager_;
    bool is_low_memory_mode_;
    bool madvise_random_access_;
    bool safe_mode_;
    EnforcementPolicy hidden_api_policy_;
};

struct PartialRuntimeR {
    uint32_t target_sdk_version_;
    void *disabled_compat_changes_[3];
    bool implicit_null_checks_;
    bool implicit_so_checks_;
    bool implicit_suspend_checks_;
    bool no_sig_chain_;
    bool force_native_bridge_;
    bool is_native_bridge_loaded_;
    bool is_native_debuggable_;
    bool async_exceptions_thrown_;
    bool non_standard_exits_enabled_;
    bool is_java_debuggable_;
    bool is_profileable_from_shell_;
    uint32_t zygote_max_failed_boots_;
    ExperimentalFlags experimental_flags_;
    std::string fingerprint_;
    void *oat_file_manager_;
    bool is_low_memory_mode_;
    bool madvise_random_access_;
    bool safe_mode_;
    EnforcementPolicy hidden_api_policy_;
};

int unseal(JNIEnv *env, jint targetSdkVersion);

#endif //FREEREFLECTION_ART_H
