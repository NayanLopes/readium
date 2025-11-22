// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:8.1.1' // ajuste conforme seu projeto
        classpath 'com.google.gms:google-services:4.4.4' // plugin Google Services
    }
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencies { 

classpath("com.android.tools.build:gradle:8.1.1")
        classpath("com.google.gms:google-services:4.4.4")
}
