import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

group = "org.aslansoft.caffesist"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    
    implementation(compose.desktop.currentOs)
    implementation("org.jetbrains.androidx.navigation:navigation-compose:2.8.0-alpha02")
    implementation("com.google.firebase:firebase-admin:8.0.1")
    implementation("org.slf4j:slf4j-simple:1.7.32")
    implementation ("com.google.cloud:google-cloud-firestore:3.0.0") // Firestore

    implementation ("io.grpc:grpc-core:1.54.1")
    implementation ("io.grpc:grpc-netty-shaded:1.54.1") // gRPC Netty
    implementation ("io.grpc:grpc-protobuf:1.54.1") // gRPC Protobuf
    implementation ("io.grpc:grpc-stub:1.54.1") // gRPC Stub
    implementation ("io.grpc:grpc-auth:1.54.1") // gRPC Auth

}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            includeAllModules = true
            appResourcesRootDir.set(project.layout.projectDirectory.dir("resources"))
            macOS{
                iconFile.set(project.file("src/main/resources/caffesist.icns"))
            }
            windows{
                iconFile.set(project.file("src/main/resources/caffesist_logo.ico"))
            }
            linux{
                iconFile.set(project.file("src/main/resources/caffesist.png"))
            }
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb,TargetFormat.Exe,TargetFormat.AppImage)
            packageName = "Caffesist"
            packageVersion = "1.0.1"
            vendor = "Aslan SOFTWARE STUDIO"
        }
    }
}
