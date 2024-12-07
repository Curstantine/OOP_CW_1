plugins {
	id("java-conventions")
}

group = "moe.curstantine"
version = "0.0.1-SNAPSHOT"

dependencies {
	implementation(project(":shared"))
	implementation("com.google.code.gson:gson:2.11.0")
	testImplementation(platform("org.junit:junit-bom:5.11.3"))
	testImplementation("org.junit.jupiter:junit-jupiter")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
