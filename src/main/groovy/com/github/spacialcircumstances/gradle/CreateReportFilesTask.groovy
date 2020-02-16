package com.github.spacialcircumstances.gradle


import net.masterthought.cucumber.Configuration
import net.masterthought.cucumber.ReportBuilder
import net.masterthought.cucumber.Reportable
import net.masterthought.cucumber.json.support.Status
import net.masterthought.cucumber.presentation.PresentationMode
import org.gradle.api.DefaultTask
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskExecutionException

class CreateReportFilesTask extends DefaultTask {

    @Input
    String projectName

    CreateReportFilesTask() {
        doLast {
            try {
                ReportsPluginExtension pluginConfig = project.extensions.cucumberReports
                File outputDirectory = pluginConfig.outputDir
                String buildId = pluginConfig.buildId
                FileCollection reports = pluginConfig.reports
                Map<String, String> classifications = pluginConfig.classifications
                Boolean runWithJenkins = pluginConfig.runWithJenkins
                Boolean expandAllSteps = pluginConfig.expandAllSteps
                List<String> excludePatterns = pluginConfig.excludeTags
                String reportProjectName = (pluginConfig.projectNameOverride != null) ? pluginConfig.projectNameOverride : projectName
                File trends = pluginConfig.trends
                int trendsLimit = pluginConfig.trendsLimit

                if (!outputDirectory.exists()) {
                    outputDirectory.mkdirs()
                }

                //Check if reports exist
                List<String> existentFiles = new ArrayList<>()
                for (File file : reports) {
                    if (file.exists() && file.isFile()) {
                        existentFiles.add(file.path)
                    } else {
                        println "Reports file ${file.absolutePath} not found"
                    }
                }

                if (existentFiles.isEmpty()) {
                    println "No report files found, aborting..."
                    throw new RuntimeException("No test files found")
                }

                Configuration config = new Configuration(outputDirectory, reportProjectName)
                if (runWithJenkins) {
                    config.addPresentationModes(PresentationMode.RUN_WITH_JENKINS)
                }
                if (expandAllSteps) {
                    config.addPresentationModes(PresentationMode.EXPAND_ALL_STEPS)
                }
                config.setBuildNumber(buildId)

                //Add trends file
                if (trends != null) {
                    config.setTrends(trends, trendsLimit)
                }

                //Add custom classifications
                for (Map.Entry<String, String> c : classifications) {
                    config.addClassifications(c.key, c.value)
                }

                config.setNotFailingStatuses(pluginConfig.notFailingStatuses.collect({ Status.valueOf(it.toUpperCase()) }).toSet())

                config.setTagsToExcludeFromChart((String[]) excludePatterns.toArray())

                println "Generating reports..."
                ReportBuilder reportBuilder = new ReportBuilder(existentFiles, config)
                Reportable report = reportBuilder.generateReports()
                if (report != null) {
                    println "Generated report in ${outputDirectory.absolutePath}/cucumber-html-reports/"
                    println "Open file://${outputDirectory.absolutePath}/cucumber-html-reports/overview-features.html to get an overview about the test results."
                } else {
                    throw new RuntimeException("Failed to generate test reports. Open file://${outputDirectory.absolutePath}/cucumber-html-reports/overview-features.html to view the error page.")
                }
            } catch (Exception e) {
                throw new TaskExecutionException(this, e)
            }
        }
    }
}
