plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.8.0"
    id("org.jetbrains.kotlin.jvm") version "1.7.10"
}

group = "com.xdl"
version = "2.1.2"

repositories {
    mavenLocal()
    mavenCentral()
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
//    version.set("2021.2")
    version.set("2021.3.3")
//    type.set("IU") // Target IDE Platform
    type.set("IC") // Target IDE Platform
    plugins.set(listOf("com.intellij.java"))
}
dependencies {
    compileOnly ("org.projectlombok:lombok:1.18.24")
    annotationProcessor ("org.projectlombok:lombok:1.18.24")
    testCompileOnly ("org.projectlombok:lombok:1.18.24")
    testAnnotationProcessor ("org.projectlombok:lombok:1.18.24")
    implementation("org.freemarker:freemarker:2.3.31")
    implementation("org.projectlombok","lombok","1.18.24")
    implementation("org.yaml","snakeyaml","1.30")
    implementation("cn.hutool","hutool-all","5.8.16")
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
        options.encoding = "UTF-8"
    }

    patchPluginXml {
        sinceBuild.set("213")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}



