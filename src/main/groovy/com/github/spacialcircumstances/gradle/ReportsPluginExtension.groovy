package com.github.spacialcircumstances.gradle

import org.gradle.api.file.FileCollection

class ReportsPluginExtension {
    String projectNameOverride
    File outputDir
    String buildId
    FileCollection reports
    Boolean runWithJenkins = false
    Boolean expandAllSteps = false
    Boolean testTasksFinalizedByReport = true
    Map<String, String> classifications = new HashMap<>()
    List<String> excludeTags = new ArrayList<>()
    File trends
    Set<String> notFailingStatuses
    int trendsLimit = 0

    def classification(String name, String value) {
        classifications.put(name, value)
    }

    def excludeTagPattern(String regex) {
        excludeTags.add(regex)
    }
}
