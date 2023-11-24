// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.4" apply false
    id("org.jetbrains.kotlin.android") version "1.9.20" apply false
    id("com.google.dagger.hilt.android") version "2.48.1" apply false
    id("io.realm.kotlin") version "1.10.0" apply false
    id("com.google.gms.google-services") version "4.3.15" apply false
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0" apply false
}