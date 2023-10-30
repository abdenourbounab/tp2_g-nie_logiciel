import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
  java
  jacoco
  checkstyle
  pmd
}

repositories {
  mavenCentral()
}

dependencies {
  testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
  testImplementation("com.approvaltests:approvaltests:19.0.0")
}

tasks.test {
  useJUnitPlatform()
  jvmArgs("--enable-preview")
  testLogging {
    showStandardStreams = true
    exceptionFormat = TestExceptionFormat.FULL // SHORT
    events("passed", "skipped", "failed")
  }
}

tasks.withType<JavaCompile>() {
  options.encoding = "UTF-8"
  options.compilerArgs.add("-Xlint:all")
  options.compilerArgs.add("-Xlint:-serial")
  options.compilerArgs.add("--enable-preview")
}

tasks.withType<Checkstyle>().configureEach {
  reports {
    xml.required.set(false)
    html.required.set(true)
    html.stylesheet = resources.text.fromFile("config/checkstyle/checkstyle.xsl")
  }
}

pmd {
  isIgnoreFailures = true
  isConsoleOutput = true
  toolVersion = "6.55.0"
  rulesMinimumPriority.set(5)
  threads.set(4)
  ruleSets = listOf(
    "category/java/bestpractices.xml",
    "category/java/codestyle.xml",
    "category/java/design.xml",
    "category/java/documentation.xml",
    "category/java/errorprone.xml",
    "category/java/multithreading.xml",
    "category/java/performance.xml",
    "category/java/security.xml",
  )
}
