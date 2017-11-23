package com.github.spacialcircumstances.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.tasks.TaskAction

class CreateReportFilesTask extends DefaultTask {
    File outputDir
    String buildName
    String projectName
    ConfigurableFileCollection reports = project.files()

    @TaskAction
    void createReportFiles() {
        println "Creating report file in ${outputDir.toString()} for build $buildName in $projectName"
    }
}
