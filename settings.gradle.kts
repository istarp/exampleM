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

val rickAndMortyModule = ":modules:rickandmortymodule"
include(rickAndMortyModule)
project(rickAndMortyModule).projectDir = File(settingsDir, "./modules/rickandmortymodule")

val coreModule = ":modules:coremodule"
include(coreModule)
project(coreModule).projectDir = File(settingsDir, "./modules/coremodule")
