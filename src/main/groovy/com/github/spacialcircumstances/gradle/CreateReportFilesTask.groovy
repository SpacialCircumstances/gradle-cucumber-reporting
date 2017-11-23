package com.github.spacialcircumstances.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.tasks.TaskAction

class CreateReportFilesTask extends DefaultTask {
    final File outputDirectory
    final String buildName
    final String projectName
    final ConfigurableFileCollection reportFiles = project.files()

    @TaskAction
    void createReportFiles() {
        println "Creating report file in ${outputDirectory.toString()} for build $buildName in $projectName"
    }
}
