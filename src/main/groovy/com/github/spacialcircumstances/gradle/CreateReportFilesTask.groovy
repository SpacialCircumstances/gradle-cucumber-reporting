package com.github.spacialcircumstances.gradle

import net.masterthought.cucumber.ReportBuilder
import net.masterthought.cucumber.Reportable
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.tasks.TaskAction
import net.masterthought.cucumber.Configuration

class CreateReportFilesTask extends DefaultTask {
    File outputDir
    String buildName
    String projectName
    ConfigurableFileCollection reports = project.files()

    @TaskAction
    void createReportFiles() {
        if(!outputDir.exists()) {
            outputDir.mkdirs()
        }

        Configuration config = new Configuration(outputDir, projectName)
        config.setRunWithJenkins(false)
        config.setBuildNumber(buildName)

        //Check if reports exist
        List<String> existentFiles = new ArrayList<>()
        for(File file: reports) {
            if(file.exists() && file.isFile()) {
                existentFiles.add(file.path)
            }
        }

        ReportBuilder reportBuilder = new ReportBuilder(existentFiles, config)
        Reportable report = reportBuilder.generateReports()
        println "Failed tests:  ${report.failedSteps}"
    }
}
