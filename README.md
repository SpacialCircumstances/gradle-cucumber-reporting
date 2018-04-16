# Gradle Cucumber Reporting
Generate pretty HTML files from cucumber report json files.
Uses [https://github.com/damianszczepanik/cucumber-reporting](https://github.com/damianszczepanik/cucumber-reporting) for generating the reports.

## Usage

The plugin will automatically run at the end of your `test` task. You can also run it manually with the `generateCucumberReports` task.

### Adding the dependency

(See [this](https://plugins.gradle.org/plugin/com.github.spacialcircumstances.gradle-cucumber-reporting) for the up-to-date version on the Gradle Plugin Portal)

All gradle versions:
```gradle
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.com.github.spacialcircumstances.gradle-cucumber-reporting:gradle-cucumber-reporting:0.0.9"
  }
}

apply plugin: "com.github.spacialcircumstances.gradle-cucumber-reporting"
```

New plugin mechanism (Gradle 2.1+):
```gradle
plugins {
  id "com.github.spacialcircumstances.gradle-cucumber-reporting" version "0.0.9"
}
```

### Configuration

Somewhere in your `build.gradle` add the following snippet:

```gradle
cucumberReports {
  outputDir = 'path/for/generated/html'
  buildName = '0'
  reports = files('path/to/cucumber-report.json', 'another/cucumber-json.json')
}
```

#### Optional configuration

`parallelTesting`: `true` or `false` determines if multiple tests were run in parallel

`classifications`: A map with <String, String> pairs that are added to the HTML report, for example os name etc.

### License

Just as [https://github.com/damianszczepanik/cucumber-reporting](cucumber-reporting), this plugin is available under the terms of the LGPL 2.1 license.