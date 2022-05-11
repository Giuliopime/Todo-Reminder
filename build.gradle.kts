plugins {
    id("application")

    kotlin("jvm") version "1.6.10"

    application

    // Shadow jars
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

version "1.0"
application.mainClass.set("me.todoReminder.bot.Bot")
group = "me.todoReminder.bot"

repositories {
    mavenCentral()
    jcenter()
    maven {
        name = "m2-dv8tion"
        url = uri("https://m2.dv8tion.net/releases")
    }
    maven("https://jitpack.io/")
}

dependencies {
    // JDA
    implementation("net.dv8tion:JDA:4.2.0_168") {
        exclude(module = "opus-java")
    }

    implementation("com.jagrosh:jda-utilities:3.0.4")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("io.github.cdimascio:java-dotenv:5.2.2")
    implementation("io.github.classgraph:classgraph:4.1.1")
    implementation("org.mongodb:mongodb-driver-sync:4.1.0")
    implementation("dev.morphia.morphia:morphia-core:2.0.1")
    implementation("redis.clients:jedis:3.3.0")
    implementation("joda-time:joda-time:2.10.6")
    implementation("com.google.code.gson:gson:2.8.6")
}

tasks {
    withType(JavaCompile::class) {
        options.encoding = "UTF-8"
    }

    shadowJar {
        archiveFileName.set("todo-reminder.jar")
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}
