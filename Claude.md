# MoeMemosAndroid 项目笔记

## Android 安装技巧

### minSdk 限制绕过
- `gradle installDebug` 会检查 minSdk 并拒绝安装到低版本设备
- `adb install` 直接推送 APK，绕过 minSdk 校验，可以强制安装到低版本设备
- 步骤：先 `./gradlew :app:assembleDebug` 构建，再 `adb install -r app/build/outputs/apk/debug/app-debug.apk`
- 注意：虽然能安装，但运行时可能有兼容性问题
