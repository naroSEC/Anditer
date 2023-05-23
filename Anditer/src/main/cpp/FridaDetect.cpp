#include <fstream>
#include <unistd.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <vector>
#include <string>
#include <netinet/in.h>
#include <sys/socket.h>
#include <jni.h>
#include <android/log.h>
#include <dirent.h>

bool isFridaFile() {
    const char* dirPath = "/data/local/tmp";
    std::string targetString = "frida";
    std::transform(targetString.begin(), targetString.end(), targetString.begin(), [](unsigned char c) {
        return std::tolower(c);
    });

    DIR* dir = opendir(dirPath);
    if (dir == nullptr) {

        return false;
    }

    dirent* entry;
    while ((entry = readdir(dir)) != nullptr) {
        if (strcmp(entry->d_name, ".") == 0 || strcmp(entry->d_name, "..") == 0) {

            continue;
        }

        std::string fileName = entry->d_name;
        std::transform(fileName.begin(), fileName.end(), fileName.begin(), [](unsigned char c) {
            return std::tolower(c);
        });

        if (fileName.find(targetString) != std::string::npos) {
            closedir(dir);

            return true;
        }
    }

    closedir(dir);
    return false;
}

bool isFridaProc() {
    std::string cmd = "ps -A | grep frida";
    FILE* pipe = popen(cmd.c_str(), "r");
    if (pipe != nullptr) {
        char buffer[128];
        while (fgets(buffer, sizeof(buffer), pipe) != nullptr) {
            std::string processInfo(buffer);
            if (processInfo.find("frida") != std::string::npos) {
                pclose(pipe);
                return true;
            }
        }
        pclose(pipe);
    }

    return false;
}

bool isFridaPort() {
    int fridaPort = 27042;
    int sock = socket(AF_INET, SOCK_STREAM, 0);
    if (sock != -1) {
        struct sockaddr_in addr;
        addr.sin_family = AF_INET;
        addr.sin_port = htons(fridaPort);
        addr.sin_addr.s_addr = htonl(INADDR_LOOPBACK);
        if (connect(sock, (struct sockaddr*)&addr, sizeof(addr)) != -1) {
            close(sock);
            return true;
        }
        close(sock);
    }

    return false;
}

extern "C" {
    JNIEXPORT jboolean JNICALL
    Java_com_playground_anditer_NativeDetector_isCheckFridaFile(JNIEnv* env, jobject /* this */) {

        return isFridaFile() ? JNI_TRUE : JNI_FALSE;
    }

    JNIEXPORT jboolean JNICALL
    Java_com_playground_anditer_NativeDetector_isCheckFridaPs(JNIEnv* env, jobject /* this */) {

        return isFridaProc() ? JNI_TRUE : JNI_FALSE;
    }

    JNIEXPORT jboolean JNICALL
    Java_com_playground_anditer_NativeDetector_isCheckFridaPort(JNIEnv* env, jobject /* this */) {

        return isFridaPort() ? JNI_TRUE : JNI_FALSE;
    }
}