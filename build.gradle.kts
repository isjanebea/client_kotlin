import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.5"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.31"
	kotlin("plugin.spring") version "1.5.31"
}

group = "pocClientesCreditas"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

	dependencies {
		implementation("org.springframework.boot:spring-boot-starter-web:2.3.4.RELEASE")
		implementation("org.springframework.boot:spring-boot-starter-webflux")
		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
		implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
		implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

		implementation("com.google.code.gson:gson:2.8.8")

		implementation("org.springframework.cloud:spring-cloud-starter-openfeign:3.0.0")

		implementation("com.squareup.okhttp3:okhttp")
		implementation("com.squareup.okhttp3:logging-interceptor")

		implementation("com.squareup.retrofit2:retrofit:2.9.0")
		implementation ("com.squareup.retrofit2:converter-gson:2.2.0")

		runtimeOnly("org.springframework.cloud:spring-cloud-dependencies:2020.0.4")

		testImplementation("org.springframework.boot:spring-boot-starter-test")
		testImplementation("io.projectreactor:reactor-test")
		testImplementation("com.squareup.okhttp3:mockwebserver")
		testImplementation("io.mockk:mockk:1.10.6")
	}


	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "11"
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
