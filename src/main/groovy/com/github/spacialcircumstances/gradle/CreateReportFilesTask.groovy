package com.github.spacialcircumstances.gradle

import net.masterthought.cucumber.ReportBuilder
import net.masterthought.cucumber.Reportable
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.tasks.TaskAction
import net.masterthought.cucumber.Configuration
import org.gradle.api.tasks.TaskExecutionException

class CreateReportFilesTask extends DefaultTask {
    String projectName
    CreateReportFilesTask() {
        doLast {
            try {
                String outputDir = project.extensions.cucumberReports.outputDir
                String buildName = project.extensions.cucumberReports.buildName
                Boolean parallelTesting = project.extensions.cucumberReports.parallelTesting
                ConfigurableFileCollection reports = project.extensions.cucumberReports.reports
                Map<String, String> classifications = project.extensions.cucumberReports.classifications

                File outputDirectory = new File(outputDir)
                if (!outputDirectory.exists()) {
                    outputDirectory.mkdirs()
                }

                //Check if reports exist
                List<String> existentFiles = new ArrayList<>()
                for (File file : reports) {
                    if (file.exists() && file.isFile()) {
                        existentFiles.add(file.path)
                    } else {
                        println "Reports file ${file.path} not found"
                    }
                }

                if(existentFiles.isEmpty()) {
                    println "No report files found, aborting..."
                    throw new RuntimeException("No test files found")
                }

                Configuration config = new Configuration(outputDirectory, projectName)
                config.setRunWithJenkins(false)
                config.setBuildNumber(buildName)
                config.setParallelTesting(parallelTesting)
                //Add custom classifications
                for(Map.Entry<String, String> c: classifications) {
                    config.addClassifications(c.key, c.value)
                }

                println "Generating reports..."
                ReportBuilder reportBuilder = new ReportBuilder(existentFiles, config)
                Reportable report = reportBuilder.generateReports()
                if (report != null) {
                    println "Generated report in ${outputDirectory.path}"
                } else {
                    throw new RuntimeException("Failed to generate test reports")
                }
            } catch (Exception e) {
                throw new TaskExecutionException(this, e)
            }
        }
    }
}
