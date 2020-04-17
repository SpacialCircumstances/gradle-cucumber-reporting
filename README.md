# Gradle Cucumber Reporting
Generate pretty HTML files from cucumber report json files.
Uses [https://github.com/damianszczepanik/cucumber-reporting](https://github.com/damianszczepanik/cucumber-reporting) for generating the reports.

[This plugin on the Gradle Plugin Portal](https://plugins.gradle.org/plugin/com.github.spacialcircumstances.gradle-cucumber-reporting)

## Usage

The plugin will automatically run at the end of your `test` task. You can also run it manually with the `generateCucumberReports` task.

### Adding the plugin to your build.gradle

```gradle
plugins {
  id "com.github.spacialcircumstances.gradle-cucumber-reporting" version "0.1.20"
}
```

### Configuration

Somewhere in your `build.gradle` add the following snippet:

```gradle
cucumberReports {
  outputDir = file('path/for/generated/html')
  buildId = '0'
  reports = files('path/to/cucumber-report.json', 'another/cucumber-json.json')
}
```

#### Optional configuration

- `classifications`: A map with `<String, String>` pairs that are added to the HTML report, for example os name etc.
    Use the method `classification` to add a single classification. Setting this property directly will overwrite old classifications.
- `testTasksFinalizedByReport`: `true` or `false` determines if the `generateCucumberReports` task finalizes the test tasks. `true` by default.
    Should be set to false if you have other tests that do not create cucumber reports and you want to avoid the failing task.
- `excludeTags`: A `List<String>` of regexes that will filter out tags so they are not present in the generated report.
- `runWithJenkins`: Set this to `true` if you are running on a Jenkins. `false` by default.
- `expandAllSteps`: Set this to `true` to make all scenarios expanded in the generated report.
- `projectNameOverride`: A `String`. By default, the reports will use the name of your gradle project as the project name. If this property is not null, it will be used instead.
- `trends`: File containing a trend store.
- `trendsLimit`: Limit the number of past trends used in report generation.
- `notFailingStatuses`: (`Set<String>`) Step statuses that should not be marked as failed in the report generation
- `directorySuffix`: `String`. Sets a suffix for directories.

##### Example

A working example (with pre-existing reports) can be found in the example directory.

```gradle
cucumberReports {
  //...
  //Pass a map
  classifications = [Browser: "Firefox"]
  //or call the classification method
  classification "Browser" "Firefox"
  
  //Pass a list to exclude
  excludeTags = [".*"]
  //or call the excludeTag method
  excludeTag ".*"
}
```


#### Properties

- `skipReports`: Pass this via `-PskipReports` when invoking gradle.

### License

Just as [https://github.com/damianszczepanik/cucumber-reporting](cucumber-reporting), this plugin is available under the terms of the LGPL 2.1 license.
