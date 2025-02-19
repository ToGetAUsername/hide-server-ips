plugins {
	id("fabric-loom") version "1.10-SNAPSHOT"
}

version = project.property("mod_version") as String
group = project.property("maven_group") as String

base {
	archivesName.set(project.property("archives_base_name") as String)
}

val targetJavaVersion = 21
java {
	val javaVersion = JavaVersion.toVersion(targetJavaVersion)
	if (JavaVersion.current() < javaVersion) {
		toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
	}
	// Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
	// if it is present.
	// If you remove this line, sources will not be generated.
	withSourcesJar()
}

repositories {
	maven("https://maven.terraformersmc.com/releases/")
}

dependencies {
	// To change the versions see the gradle.properties file
	minecraft("com.mojang:minecraft:${project.property("minecraft_version")}")
	mappings("net.fabricmc:yarn:${project.property("yarn_mappings")}:v2")
	modImplementation("net.fabricmc:fabric-loader:${project.property("loader_version")}")
}

tasks {
	processResources {
		val props = mapOf("version" to project.version) + keyToPropertyMap(
			"minecraft_version",
			"loader_version"
		)
		inputs.properties(props)
		filteringCharset = "UTF-8"

		filesMatching("fabric.mod.json") {
			expand(props)
		}
	}

	withType<JavaCompile>().configureEach {
		// ensure that the encoding is set to UTF-8, no matter what the system default is
		// this fixes some edge cases with special characters not displaying correctly
		// see http://yodaconditions.net/blog/fix-for-java-file-encoding-problems-with-gradle.html
		// If Javadoc is generated, this must be specified in that task too.
		options.encoding = "UTF-8"
		if(targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible) {
			options.release.set(targetJavaVersion)
		}
	}

	jar {
		from("LICENSE") {
			rename { "${it}_${project.base.archivesName}"}
		}
	}
}

fun keyToPropertyMap(vararg keys: String): Map<String, Any?> = buildMap {
	for(key in keys) put(key, project.property(key))
}