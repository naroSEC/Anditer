#include <unistd.h>
#include <sys/types.h>
#include <sys/ptrace.h>
#include <fstream>
#include <dirent.h>
#include <jni.h>
#include <sstream>

bool isDebuggerAttached() {
    if (ptrace(PTRACE_TRACEME, 0, 1, 0) == -1) {
        return true;
    }
    return false;
}

bool isDebuggerTracerPid() {
    std::ifstream statusFile("/proc/self/status");
    std::string line;
    while (std::getline(statusFile, line)) {
        if (line.substr(0, 10) == "TracerPid:") {
            std::istringstream iss(line.substr(11));
            int tracerPid;
            if (iss >> tracerPid) {
                if (tracerPid != 0) {
                    return true;
                }
            }
            break;
        }
    }
    return false;
}

bool isDebuggable() {
    std::string getpropCmd = "getprop ro.debuggable";
    std::string debuggableValue;
    FILE* pipe = popen(getpropCmd.c_str(), "r");
    if (pipe != nullptr) {
        char buffer[128];
        if (fgets(buffer, sizeof(buffer), pipe) != nullptr) {
            debuggableValue = buffer;

            if (!debuggableValue.empty() && debuggableValue.back() == '\n') {
                debuggableValue.pop_back();
            }
        }
        pclose(pipe);
    }

    return (debuggableValue == "1");
}

bool isIDADebuggerAttached() {
    DIR* procDir = opendir("/proc");
    if (procDir == nullptr) {
        return false;  // /proc 디렉터리 열기 실패
    }

    dirent* entry;
    while ((entry = readdir(procDir)) != nullptr) {
        std::string procName = entry->d_name;
        if (procName != "." && procName != "..") {
            std::string procPath = "/proc/" + procName;
            std::ifstream cmdlineFile(procPath + "/cmdline");
            std::string cmdline;
            std::getline(cmdlineFile, cmdline);
            if (cmdline.find("ida") != std::string::npos) {
                closedir(procDir);
                return true;
            }
            if (cmdline.find("jeb") != std::string::npos) {
                closedir(procDir);
                return true;
            }
        }
    }

    closedir(procDir);
    return false;
}

extern "C" {
    JNIEXPORT jboolean JNICALL
    Java_com_playground_anditer_NativeDetector_isCheckPtrace(JNIEnv* env, jobject /* this */) {

        return isDebuggerAttached() ? JNI_TRUE : JNI_FALSE;
    }

    JNIEXPORT jboolean JNICALL
    Java_com_playground_anditer_NativeDetector_isCheckTracerPID(JNIEnv* env, jobject /* this */) {

        return isDebuggerTracerPid() ? JNI_TRUE : JNI_FALSE;
    }

    JNIEXPORT jboolean JNICALL
    Java_com_playground_anditer_NativeDetector_isCheckDebuggable(JNIEnv* env, jobject /* this */) {

        return isDebuggable() ? JNI_TRUE : JNI_FALSE;
    }
}