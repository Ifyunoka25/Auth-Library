# Auth Feature

`features/auth` contains the reusable Android auth UI and view model layer. It depends on:

- `designsystem` for Compose UI components and theme primitives.
- Host-provided auth contracts from `university.miva.auth.api`.

## Host App Requirements

Register these dependencies in the host DI graph before showing auth screens:

```kotlin
single { AuthFeatureConfig(casBaseUrl = BuildConfig.CAS_BASE_URL) }
single<AuthWebLauncher> { YourAuthWebLauncher() }
single<AuthNavigationHandler> { YourAuthNavigationHandler() }
single<AuthAnalytics> { NoOpAuthAnalytics } // or a project-specific analytics implementation
single<AuthTokenStore> { YourAuthTokenStore() }
single<AuthSessionStore> { YourAuthSessionStore() }
single<AuthTokenRefresher> { YourAuthTokenRefresher() }
single<AuthTicketAuthenticator> { YourAuthTicketAuthenticator() }
single<AuthDashboardLauncher> { YourAuthDashboardLauncher() }
```

`AuthWebLauncher` opens the CAS/web auth flow.
`AuthNavigationHandler` owns app-specific destinations for guests and authenticated users.
`AuthAnalytics` keeps tracking optional and outside the reusable feature.
The data and web contracts keep token storage, session storage, ticket authentication, and dashboard routing owned by the host app.

## Publishing

The auth and design system AARs publish to GitHub Packages under `Ifyunoka25/Auth-Library`.

Set credentials outside the repo:

```bash
export GITHUB_ACTOR=your-github-username
export GITHUB_TOKEN=your-github-token-with-write-packages
```

Then publish:

```bash
./gradlew :designsystem:publishReleasePublicationToGithubPackagesRepository \
  :auth:publishReleasePublicationToGithubPackagesRepository
```

Consumers should add the GitHub Packages repository and depend on:

```kotlin
implementation("university.miva:designsystem:1.0.0-SNAPSHOT")
implementation("university.miva:auth:1.0.0-SNAPSHOT")
```
