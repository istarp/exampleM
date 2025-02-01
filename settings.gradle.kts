dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("sharedLibs") {
            from(files("./gradle/shared-libs.versions.toml"))
        }
    }
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
    }
}

include(":app")

val nbaModule = ":modules:nbamodule"
include(nbaModule)
project(nbaModule).projectDir = File(settingsDir, "./modules/nbamodule")

val coreModule = ":modules:coremodule"
include(coreModule)
project(coreModule).projectDir = File(settingsDir, "./modules/coremodule")
