package com.github.spacialcircumstances.gradle


import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue

class GradleCompatibilityTest {

    @ParameterizedTest
    @ValueSource(strings = ['4.10.3', '5.6.4', '6.2'])
    void 'Gradle compatibility test'(String gradleVersion) {
        String task = ':generateCucumberReports'
        String projectDir = 'src/functionalTest/resources'

        GradleRunner runner = GradleRunner.create()
                .withGradleVersion(gradleVersion)
                .withProjectDir(new File(projectDir))
                .withPluginClasspath()

        runner.withArguments(':clean').build()

        BuildResult result = runner
                .withArguments(task)
                .build()
        println(result.getOutput())

        assertEquals(task, result.tasks.get(0).path)
        assertEquals(SUCCESS, result.tasks.get(0).outcome)

        String reportFilePath = projectDir + '/output/generated-reports/cucumber-html-reports/overview-features.html'
        assertTrue(new File(reportFilePath).exists())
    }
}
