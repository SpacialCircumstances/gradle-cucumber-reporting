package com.github.spacialcircumstances.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class ReportsPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        def extension = project.extensions.create('cucumberReports', ReportsPluginExtension, project)
        project.task('createCucumberReports', type: CreateReportFilesTask) {
            description = "Creates cucumber html reports"
            projectName = project.displayName
        }
    }
}
