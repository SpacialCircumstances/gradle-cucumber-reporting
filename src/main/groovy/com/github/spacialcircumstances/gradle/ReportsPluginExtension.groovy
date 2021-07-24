package com.github.spacialcircumstances.gradle

import net.masterthought.cucumber.reducers.ReducingMethod
import org.gradle.api.file.FileCollection

class ReportsPluginExtension {
    String projectNameOverride
    File outputDir
    String directorySuffix
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
    Set<ReducingMethod> reducingMethods = new HashSet<>()

    def reducingMethod(ReducingMethod reducingMethod) {
        reducingMethods.add(reducingMethod)
    }

    def classification(String name, String value) {
        classifications.put(name, value)
    }

    def excludeTagPattern(String regex) {
        excludeTags.add(regex)
    }
}
