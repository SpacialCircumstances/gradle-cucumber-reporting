package com.github.spacialcircumstances.gradle

import org.gradle.api.file.ConfigurableFileCollection

class ReportsPluginExtension {
    File outputDir
    String buildNumber
    ConfigurableFileCollection reports
    Boolean runWithJenkins = false
    Boolean testTasksFinalizedByReport = true
    Map<String, String> classifications = new HashMap<>()
}
