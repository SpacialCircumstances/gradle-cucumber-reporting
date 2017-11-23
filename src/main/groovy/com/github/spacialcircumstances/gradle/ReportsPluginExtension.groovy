package com.github.spacialcircumstances.gradle

import org.gradle.api.Project
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.FileCollection

class ReportsPluginExtension {
    String outputDir
    String buildName
    ConfigurableFileCollection reports
    Boolean parallelTesting = false
}
