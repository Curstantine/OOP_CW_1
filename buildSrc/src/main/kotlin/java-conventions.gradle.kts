plugins {
    java
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(23))
    }
}

dependencies {
    // add any dependencies that you want to apply to all your monorepo projects here
}