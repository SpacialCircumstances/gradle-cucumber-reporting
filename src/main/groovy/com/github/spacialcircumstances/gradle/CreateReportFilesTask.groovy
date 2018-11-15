package com.github.spacialcircumstances.gradle

import com.google.common.collect.Lists
import net.masterthought.cucumber.ReportBuilder
import net.masterthought.cucumber.Reportable
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import net.masterthought.cucumber.Configuration
import org.gradle.api.tasks.TaskExecutionException

class CreateReportFilesTask extends DefaultTask {
    String projectName
    CreateReportFilesTask() {
        doLast {
            try {
                ReportsPluginExtension pluginConfig = project.extensions.cucumberReports
                File outputDirectory = pluginConfig.outputDir
                String buildId = pluginConfig.buildId
                ConfigurableFileCollection reports = pluginConfig.reports
                Map<String, String> classifications = pluginConfig.classifications
                Boolean runWithJenkins = pluginConfig.runWithJenkins
                List<String> excludePatterns = pluginConfig.excludeTags
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

                Configuration config = new Configuration(outputDirectory, projectName)
                config.setRunWithJenkins(runWithJenkins)
                config.setBuildNumber(buildId)
                //Add custom classifications
                for(Map.Entry<String, String> c: classifications) {
                    config.addClassifications(c.key, c.value)
                }

                config.setTagsToExcludeFromChart((String[])excludePatterns.toArray())

                println "Generating reports..."
                ReportBuilder reportBuilder = new ReportBuilder(existentFiles, config)
                Reportable report = reportBuilder.generateReports()
                if (report != null) {
                    println "Generated report in ${outputDirectory.absolutePath}/cucumber-html-reports/"
                    println "Open ${outputDirectory.absolutePath}/cucumber-html-reports/overview-features.html to get an overview about the test results."
                } else {
                    throw new RuntimeException("Failed to generate test reports. Open ${outputDirectory.absolutePath}/cucumber-html-reports/overview-features.html to view the error page.")
                }
            } catch (Exception e) {
                throw new TaskExecutionException(this, e)
            }
        }
    }
}
