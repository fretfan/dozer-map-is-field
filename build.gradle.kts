import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.41"
}

group = "dozer-map-is-field"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    // https://mvnrepository.com/artifact/com.github.dozermapper/dozer-core
    compile("com.github.dozermapper:dozer-core:6.5.0")
    // https://mvnrepository.com/artifact/ma.glasnost.orika/orika-core
    compile("ma.glasnost.orika:orika-core:1.5.4")
    // https://mvnrepository.com/artifact/net.sf.dozer/dozer
//    compile("net.sf.dozer:dozer:5.5.1")



}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}