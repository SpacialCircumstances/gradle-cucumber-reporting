package com.github.spacialcircumstances.gradle

import net.masterthought.cucumber.ReportBuilder
import net.masterthought.cucumber.Reportable
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.tasks.TaskAction
import net.masterthought.cucumber.Configuration

class CreateReportFilesTask extends DefaultTask {
    String projectName

    @TaskAction
    void createReportFiles() {
        String outputDir = project.extensions.cucumberReports.outputDir
        String buildName = project.extensions.cucumberReports.buildName
        Boolean parallelTesting = project.extensions.cucumberReports.parallelTesting
        ConfigurableFileCollection reports = project.extensions.cucumberReports.reports
        File outputDirectory = new File(outputDir)
        if(!outputDirectory.exists()) {
            outputDirectory.mkdirs()
        }

        Configuration config = new Configuration(outputDirectory, projectName)
        config.setRunWithJenkins(false)
        config.setBuildNumber(buildName)
        config.setParallelTesting(parallelTesting)

        //Check if reports exist
        List<String> existentFiles = new ArrayList<>()
        for(File file: reports) {
            if(file.exists() && file.isFile()) {
                existentFiles.add(file.path)
            } else {
                println "Reports file ${file.path} not found"
            }
        }

        ReportBuilder reportBuilder = new ReportBuilder(existentFiles, config)
        Reportable report = reportBuilder.generateReports()
        if(report != null) {
            println "Failed tests:  ${report.failedSteps}"
        }
    }
}
