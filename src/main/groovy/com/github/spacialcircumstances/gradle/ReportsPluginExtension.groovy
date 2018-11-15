package com.github.spacialcircumstances.gradle

import org.gradle.api.file.ConfigurableFileCollection

class ReportsPluginExtension {
    File outputDir
    String buildId
    ConfigurableFileCollection reports
    Boolean runWithJenkins = false
    Boolean testTasksFinalizedByReport = true
    Map<String, String> classifications = new HashMap<>()
    List<String> excludeTags = new ArrayList<>()

    def classification(String name, String value) {
        classifications.put(name, value)
    }

    def excludeTagPattern(String regex) {
        excludeTags.add(regex)
    }
}
