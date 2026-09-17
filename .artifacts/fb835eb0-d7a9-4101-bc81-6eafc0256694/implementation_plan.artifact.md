# Implementation Plan - Personal Task Manager (Firebase + Room + MVVM)

This plan outlines the steps to implement the "Gestor Personal de Tareas" workshop in the current project, following the architecture and requirements described in the provided PDFs and reference project.

## User Review Required

> [!IMPORTANT]
> The package name will be changed to `com.sena.crud` as requested. This will affect the `applicationId` and `namespace` in `build.gradle.kts`.
> I will also copy the `google-services.json` file from your Downloads folder to the project's `app/` directory.

## Proposed Changes

### Configuration & Dependencies

#### [MODIFY] [libs.versions.toml](file:///C:/Users/USUARIO/AndroidStudioProjects/MyApplication6/gradle/libs.versions.toml)
- Add versions and libraries for:
    - Firebase BoM, Auth, and Firestore.
    - Room (Runtime, Compiler, KTX).
    - Hilt (Dagger Hilt, Android, Compiler).
    - Lifecycle (ViewModel Compose).
    - KSP (for Room and Hilt).

#### [MODIFY] [build.gradle.kts (:app)](file:///C:/Users/USUARIO/AndroidStudioProjects/MyApplication6/app/build.gradle.kts)
- Change `namespace` and `applicationId` to `com.sena.crud`.
- Add plugins: `com.google.gms.google-services`, `com.google.dagger.hilt.android`, `com.google.devtools.ksp`.
- Add necessary dependencies for Room, Firebase, Hilt, and Lifecycle.

#### [MODIFY] [build.gradle.kts (Project)](file:///C:/Users/USUARIO/AndroidStudioProjects/MyApplication6/build.gradle.kts)
- Add classpath for Google Services and Hilt plugins.

### Data Layer

#### [NEW] [TaskDraftEntity.kt](file:///C:/Users/USUARIO/AndroidStudioProjects/MyApplication6/app/src/main/java/com/sena/crud/data/local/entity/TaskDraftEntity.kt)
- Room entity for local task drafts.

#### [NEW] [TaskDraftDao.kt](file:///C:/Users/USUARIO/AndroidStudioProjects/MyApplication6/app/src/main/java/com/sena/crud/data/local/dao/TaskDraftDao.kt)
- DAO for local drafts.

#### [NEW] [AppDatabase.kt](file:///C:/Users/USUARIO/AndroidStudioProjects/MyApplication6/app/src/main/java/com/sena/crud/data/local/database/AppDatabase.kt)
- Room database definition.

#### [NEW] [TaskDto.kt](file:///C:/Users/USUARIO/AndroidStudioProjects/MyApplication6/app/src/main/java/com/sena/crud/data/remote/dto/TaskDto.kt)
- DTO for Firebase Firestore.

#### [NEW] [AuthRemoteDataSource.kt](file:///C:/Users/USUARIO/AndroidStudioProjects/MyApplication6/app/src/main/java/com/sena/crud/data/remote/datasource/AuthRemoteDataSource.kt) & [TaskRemoteDataSource.kt](file:///C:/Users/USUARIO/AndroidStudioProjects/MyApplication6/app/src/main/java/com/sena/crud/data/remote/datasource/TaskRemoteDataSource.kt)
- Data sources for Firebase.

#### [NEW] [Repository Implementations](file:///C:/Users/USUARIO/AndroidStudioProjects/MyApplication6/app/src/main/java/com/sena/crud/data/repository/)
- `AuthRepositoryImpl`, `TaskRepositoryImpl`, `DraftRepositoryImpl`.

### Domain Layer

#### [NEW] [Models](file:///C:/Users/USUARIO/AndroidStudioProjects/MyApplication6/app/src/main/java/com/sena/crud/domain/model/)
- `Task`, `TaskDraft`, `OperationState`.

#### [NEW] [Repository Interfaces](file:///C:/Users/USUARIO/AndroidStudioProjects/MyApplication6/app/src/main/java/com/sena/crud/domain/repository/)
- `AuthRepository`, `TaskRepository`, `DraftRepository`.

#### [NEW] [Use Cases](file:///C:/Users/USUARIO/AndroidStudioProjects/MyApplication6/app/src/main/java/com/sena/crud/domain/usecase/)
- `AuthUseCases`, `TaskUseCases`, `DraftUseCases`.

### UI Layer

#### [NEW] [ViewModels](file:///C:/Users/USUARIO/AndroidStudioProjects/MyApplication6/app/src/main/java/com/sena/crud/ui/viewmodel/)
- `AuthViewModel`, `TaskViewModel`, `DraftViewModel`.

#### [NEW] [Screens](file:///C:/Users/USUARIO/AndroidStudioProjects/MyApplication6/app/src/main/java/com/sena/crud/ui/screen/)
- `AuthScreens`, `TaskScreens`, `AppScreen` (Navigation).

#### [MODIFY] [MainActivity.kt](file:///C:/Users/USUARIO/AndroidStudioProjects/MyApplication6/app/src/main/java/com/sena/crud/MainActivity.kt)
- Set up Hilt and initial Composable.

#### [NEW] [MyApp.kt](file:///C:/Users/USUARIO/AndroidStudioProjects/MyApplication6/app/src/main/java/com/sena/crud/MyApp.kt)
- Hilt Application class.

### Miscellaneous

#### [NEW] [google-services.json](file:///C:/Users/USUARIO/AndroidStudioProjects/MyApplication6/app/google-services.json)
- Copy from Downloads.

## Verification Plan

### Automated Tests
- I will run `./gradlew assembleDebug` to ensure everything compiles correctly.

### Manual Verification
- The user should:
    1. Verify the `google-services.json` matches their Firebase project.
    2. Run the app on a device/emulator.
    3. Test Sign up / Login.
    4. Test CRUD operations and local drafts sync.
