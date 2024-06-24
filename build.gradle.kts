plugins {
    val kotlinVersion = "1.7.21"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("net.mamoe.mirai-console") version "2.13.2"
}

group = "top.rumble"
version = "1.0.0"

repositories {
    if (System.getenv("CI")?.toBoolean() != true) {
        maven("https://maven.aliyun.com/repository/public") // 阿里云国内代理仓库
    }
    mavenCentral()
}

dependencies {
    implementation("com.alibaba:fastjson:1.2.83")
}

tasks.create("CopyToLib", Copy::class) {
    into("${buildDir}/output/libs")
    from(configurations.runtimeClasspath)
}
