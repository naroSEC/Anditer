#include <fstream>
#include <sys/stat.h>
#include <vector>
#include <string>
#include <jni.h>

bool fileExists(const std::string& filePath) {
    std::ifstream file(filePath);
    return file.good();
}

bool checkRootPath(const std::string& path) {
    struct stat info;
    if (stat(path.c_str(), &info) != 0)
        return false;
    return (info.st_mode & S_IFDIR) != 0;
}

std::string findExecutable(const std::string& command) {
    std::string path;
    std::string cmd = "which " + command + " 2>/dev/null";

    FILE* pipe = popen(cmd.c_str(), "r");
    if (pipe != nullptr) {
        char buffer[128];
        if (fgets(buffer, sizeof(buffer), pipe) != nullptr) {
            path = buffer;
            // Remove trailing newline character
            if (!path.empty() && path.back() == '\n') {
                path.pop_back();
            }
        }
        pclose(pipe);
    }

    return path;
}

bool isRooted() {
    // Check for common rooted paths
    std::vector<std::string> rootedPaths = {
            "/sbin/su",
            "/system/bin/su",
            "/system/xbin/su",
            "/system/app/Superuser.apk",
            "/system/app/SuperSU.apk",
            "/system/app/BusyBox.apk",
            "/data/data/com.noshufou.android.su",
            "/data/data/eu.chainfire.supersu",
            "/data/data/com.koushikdutta.superuser"
    };

    for (const std::string& path : rootedPaths) {
        if (fileExists(path) || checkRootPath(path))
            return true;
    }

    // Check for installed root management apps
    std::vector<std::string> rootApps = {
            "com.noshufou.android.su",
            "eu.chainfire.supersu",
            "com.koushikdutta.superuser",
            "com.thirdparty.superuser",
            "com.topjohnwu.magisk",
            "com.playground.rooting"
    };

    for (const std::string& packageName : rootApps) {
        std::string cmd = "pm list packages " + packageName;
        if (system(cmd.c_str()) == 0)
            return true;
    }

    return false;
}

bool isCheckExecution() {
    std::string suPath = findExecutable("su");
    if (!suPath.empty()) {
        return true;
    }

    return false;
}

extern "C" {
    JNIEXPORT jboolean JNICALL
    Java_com_playground_anditer_NativeDetector_isCheckRooting(JNIEnv* env, jobject /* this */) {

        return isRooted() ? JNI_TRUE : JNI_FALSE;
    }

    JNIEXPORT jboolean JNICALL
    Java_com_playground_anditer_NativeDetector_isCheckSUExecution(JNIEnv* env, jobject /* this */) {

        return isCheckExecution() ? JNI_TRUE : JNI_FALSE;
    }
}