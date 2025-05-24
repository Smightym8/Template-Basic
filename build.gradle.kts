/*
 *  Copyright (c) 2024 Bayerische Motoren Werke Aktiengesellschaft (BMW AG)
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Apache License, Version 2.0 which is available at
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Contributors:
 *       Bayerische Motoren Werke Aktiengesellschaft (BMW AG) - initial API and implementation
 *
 */


plugins {
    id("application")
    alias(libs.plugins.edc.build)
    alias(libs.plugins.shadow)
    alias(libs.plugins.docker)
}

val edcVersion: String by project
val annotationProcessorVersion: String by project
val metaModelVersion: String by project

dependencies {
    // example dependencies, uncomment if needed
    implementation("org.eclipse.edc:boot:$edcVersion")
    implementation("org.eclipse.edc:connector-core:$edcVersion")
    implementation("org.eclipse.edc:http:$edcVersion")
}

apply(plugin = "org.eclipse.edc.edc-build")

repositories {
    mavenCentral()
}

configure<org.eclipse.edc.plugins.autodoc.AutodocExtension> {
    processorVersion.set(annotationProcessorVersion)
    outputDirectory.set(layout.buildDirectory.asFile)
}

configure<org.eclipse.edc.plugins.edcbuild.extensions.BuildExtension> {
    versions {
        metaModel.set(metaModelVersion)
    }
}

application {
    mainClass.set("org.eclipse.edc.boot.system.runtime.BaseRuntime")
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    exclude("**/pom.properties", "**/pom.xml")
    mergeServiceFiles()
    archiveFileName.set("${project.name}.jar")
}

tasks.register("dockerize", com.bmuschko.gradle.docker.tasks.image.DockerBuildImage::class) {
    val dockerContextDir = projectDir
   
    dockerFile.set(file("src/main/docker/Dockerfile"))
    images.add("${project.name}:${project.version}")
    images.add("${project.name}:latest")
   
    if (System.getProperty("platform") != null) {
        platform.set(System.getProperty("platform"))
    }

    inputDir.set(dockerContextDir)
    dependsOn(tasks.named("shadowJar"))
    doNotTrackState("Do not track state so that this task works on Windows")
}

edcBuild {
    publish.set(false)
}
