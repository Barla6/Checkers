val ktor_version: String by project
val kotlin_version = "1.5.20"
val logback_version: String by project

plugins {
    id("checkers.kotlin-application-conventions")
}

application {
    mainClass.set("checkers.CheckersServer.ApplicationKt")
}

dependencies {
    implementation(project(":CheckersLib"))
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}