package com.github.spacialcircumstances.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.testing.Test

class ReportsPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.extensions.create('cucumberReports', ReportsPluginExtension)
        Task reportTask = project.task('generateCucumberReports', type: CreateReportFilesTask) {
            description = "Creates cucumber html reports"
            group = "Cucumber reports"
            projectName = project.displayName
        }

        reportTask.onlyIf { ! project.hasProperty('skipReports') }

        project.tasks.withType(Test) { Test test ->
            test.finalizedBy(reportTask)
        }
    }
}
