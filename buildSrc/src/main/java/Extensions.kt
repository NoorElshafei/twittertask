import org.gradle.api.tasks.TaskContainer

fun createApkName(baseName: String, versionName: String): String {
    val formatter = java.time.format.DateTimeFormatter.ofPattern("dd_MMM_yyyy_HHmm")
    val currentDateTime = java.time.LocalDateTime.now().format(formatter)
    return "${baseName}_${versionName}_$currentDateTime.apk"
}

fun TaskContainer.applyLinitingtasks() {
    getByPath("preBuild").dependsOn("ktlintCheck", "ktlintFormat", "detekt")
}