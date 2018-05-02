package com.github.spacialcircumstances.gradle

import org.gradle.api.Project
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.FileCollection

class ReportsPluginExtension {
    File outputDir
    String buildName
    ConfigurableFileCollection reports
    Boolean parallelTesting = false
    Boolean runWithJenkins = false
    Map<String, String> classifications = new HashMap<>()
}
