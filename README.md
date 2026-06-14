# Miva Auth Library

Standalone Android library repo for the reusable Miva auth flow and design system.

## Modules

- `:designsystem` publishes `university.miva:designsystem`
- `:auth` publishes `university.miva:auth`

The auth module owns the reusable auth UI, auth view models, in-app auth webview, and public host contracts. App-specific storage, token refresh, ticket authentication, navigation, dashboard launching, and analytics are supplied by the host app through the `university.miva.auth.api` contracts.

## Build

```bash
./gradlew :designsystem:compileDebugKotlin :auth:compileDebugKotlin
```

## Publish To GitHub Packages

Set credentials outside the repo:

```bash
export GITHUB_ACTOR=Ifyunoka25
export GITHUB_TOKEN=<token-with-write-packages>
```

Publish both AARs:

```bash
./gradlew :designsystem:publishReleasePublicationToGithubPackagesRepository \
  :auth:publishReleasePublicationToGithubPackagesRepository
```

## Consume

Add GitHub Packages to the consuming app:

```kotlin
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/Ifyunoka25/Auth-Library")
        credentials {
            username = providers.gradleProperty("gpr.user")
                .orElse(providers.environmentVariable("GITHUB_ACTOR"))
                .orNull
            password = providers.gradleProperty("gpr.key")
                .orElse(providers.environmentVariable("GITHUB_TOKEN"))
                .orNull
        }
    }
}
```

Then depend on:

```kotlin
implementation("university.miva:designsystem:1.0.0-SNAPSHOT")
implementation("university.miva:auth:1.0.0-SNAPSHOT")
```
