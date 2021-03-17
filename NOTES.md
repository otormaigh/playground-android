### 10/03/2021
When building the `:library:networking` module using with `compileOptions.sourceCompatibility JavaVersion.VERSION_11` I get the following Gradle build error:
```
What went wrong:
Could not determine the dependencies of task ':app:lintVitalLiveRelease'.
> Could not resolve all dependencies for configuration ':app:liveReleaseRuntimeClasspath'.
   > java.util.ConcurrentModificationException (no error message)

```

Changing it to `compileOptions.sourceCompatibility JavaVersion.VERSION_1_8` allows the build to run successfully.

### 11/03/2021
Build warning being thrown when running `./gradlew test` task. This is caused by
> https://github.com/square/retrofit/issues/3341#issuecomment-602932069
> The reflection works around a bug in the JDK which was fixed in 14 but it's only used for default methods...

```
> Task :library:networking:testMockReleaseUnitTest
WARNING: An illegal reflective access operation has occurred
WARNING: Illegal reflective access by retrofit2.Platform (file:/Users/elliot/.gradle/caches/modules-2/files-2.1/com.squareup.retrofit2/retrofit/2.9.0/d8fdfbd5da952141a665a403348b74538efc05ff/retrofit-2.9.0.jar) to constructor java.lang.invoke.MethodHandles$Lookup(java.lang.Class,int)
WARNING: Please consider reporting this to the maintainers of retrofit2.Platform
WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
WARNING: All illegal access operations will be denied in a future release
```

Adding the following to the module gets rid of the warning
```
tasks.withType(Test) {
  configurations {
    jvmArgs += [
      '--add-opens=java.base/java.lang.invoke=ALL-UNNAMED'
    ]
  }
}
```

### 13/03/2021
Command line clean builds fail after adding SQLDelight dependency, Android Studio builds are fine when adding the SQLDelight plugin. Need to add a task dependency to generate the
SQLDelight class.2021
```
tasks.getByName("preBuild").dependsOn(":library:persistence:generateSqlDelightInterface")
```

----

Forgot to add hilt plugin to app module after adding the `@HiltAndroidApp` annotation to a newly created `Application` class.

The following build error was thrown:
```
> Task :app:kaptLiveReleaseKotlin FAILED
warning: File for type 'ie.otormaigh.playground.PlaygroundApplication_HiltComponents' created in the last round will not be subject to annotation processing./Users/elliot/Projects/pennylabs/playground-android/app/build/tmp/kapt3/stubs/liveRelease/ie/otormaigh/playground/PlaygroundApplication.java:7: error: [Hilt]
public final class PlaygroundApplication extends android.app.Application {
             ^
  Expected @HiltAndroidApp to have a value. Did you forget to apply the Gradle Plugin? (dagger.hilt.android.plugin)
  See https://dagger.dev/hilt/gradle-setup.html
  [Hilt] Processing did not complete. See error above for details.
```

Adding the following to the plugin block within the `app/build.gradle` file fixes the issue:
```
id 'dagger.hilt.android.plugin'
```

----

Theres a couple of Gradle tasks that can be run to both generate a Database Schema file and then validate any migrations that are present in the project. These can and should be
run locally, but on the occasions that this step is forgotten, we can run this on the CI to check this and fail the build as required.

Generate a Database schema file based off the latest schema version and the `.sq` table files present in the project. Optionally add an output directory for the database files
```
sqldelight {
  Database {
    schemaOutputDirectory = file("src/main/sqldelight/databases")
  }
}

./gradlew generateLiveReleaseDatabaseSchema
```

Validate any migration files that are present in the project. This can only be done if there are database schema files present to validate against.
```
sqldelight {
  Database {
    verifyMigrations = true
  }
}

./gradlew verifySqlDelightMigration
```

Add a step to the CI to first try and generate a database schema file, if there were any files changes caused by this task it means that we have probably skipped this step locally
and there may not be a migration present relative to these changes, fail the build, otherwise try and validate the migrations that are present.
```
- name: Validate Database schema changes
  run: |
    ./gradlew generateLiveReleaseDatabaseSchema --stacktrace --scan
    if [[ $(git diff --stat) != '' ]]; then
      echo "> Error: Database schema changed without adding a migration."
      exit 1
    else
      ./gradlew verifySqlDelightMigration --stacktrace --scan
    fi
```


###14/03/2021
When adding a new column to the `Camera` table, rather than adding the new column to the end of the list, I added it so it would match the `CameraResponse` object between the `id`
and `rover_id` field.

```
> Old schema
CREATE TABLE Camera (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  rover_id INTEGER NOT NULL,
  full_name TEXT NOT NULL
);

> New schema
CREATE TABLE Camera (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  name TEXT NOT NULL,
  rover_id INTEGER NOT NULL,
  full_name TEXT NOT NULL
);

> Migration command
ALTER TABLE Camera CREATE COLUMN name TEXT NOT NULL DEFAULT '';
```

But when running the `verifySqlDelightMigration` task with the basic `ALTER TABLE` command above, I get the following error:
```
> A failure occurred while executing com.squareup.sqldelight.gradle.VerifyMigrationTask$VerifyMigrationAction
   > Error migrating from 1.db, fresh database looks different from migration database:
     /tables[Camera]/columns[Camera.rover_id]/ordinalPosition - CHANGED
     /tables[Camera]/columns[Camera.full_name]/ordinalPosition - CHANGED
     /tables[Camera]/columns[Camera.name]/defaultValue - REMOVED
     /tables[Camera]/columns[Camera.name]/ordinalPosition - CHANGED
```

Unsure how to set the `ordinalPosition` or why `defaultValue` is causing an issue, I moved the column to the end of the list and re-ran the migration with the same command. This
resulted in the following error:
```
> Update schema
CREATE TABLE Camera (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  rover_id INTEGER NOT NULL,
  full_name TEXT NOT NULL,
  name TEXT NOT NULL
);

> A failure occurred while executing com.squareup.sqldelight.gradle.VerifyMigrationTask$VerifyMigrationAction
   > Error migrating from 1.db, fresh database looks different from migration database:
     /tables[Camera]/columns[Camera.name]/defaultValue - REMOVED

```

The only way I found to run a successful migration was to create a copy of the table, set the desired 'ordinal position' and then copy over the data from the 'old' table. I'm not
sure how to define the ordinal position through a migration otherwise, or even that the position was a requirement.

----

After adding the `feature:photos` Dynamic Feature Module to the project and running a build I get the following error:
```
A problem occurred configuring project ':app'.
> com.android.builder.errors.EvalIssueException: Resource shrinker for multi-apk applications can be enabled via experimental flag: 'android.experimental.enableNewResourceShrinker'.
```

Adding the following to the `gradle.properties` file fixes with issue:
```
android.experimental.enableNewResourceShrinker=true
```

----

Using the `postprocessing` block within the `:feature:photos` module makes the build complain about `minifyEnabled` being unsupported in dynamic feature modules.
```
A problem occurred configuring project ':feature:photos'.
> com.android.builder.errors.EvalIssueException: Dynamic feature modules cannot set minifyEnabled to true. minifyEnabled is set to true in build type 'release'.
  To enable minification for a dynamic feature module, set minifyEnabled to true in the base module.

```

But setting all the flags to false reports the following error:
```
Execution failed for task ':feature:photos:exportLiveReleaseConsumerProguardFiles'.
> Default file proguard-defaults.txt should not be specified in this module. It can be specified in the base module instead.

```

Looks like the postprocessing block is setting that default file by default, not sure how to unset it. Using the 'old' way and just defining the `proguardFiles` fixes the above
two errors'


----

Looks like using dynamic feature modules with Dagger.Hilt is a no-go for the moment, at least being able to use it within any extra effort.
https://developer.android.com/training/dependency-injection/hilt-multi-module#dfm
https://github.com/google/dagger/issues/1865
```
java.lang.RuntimeException: Unable to start activity ComponentInfo{ie.otormaigh.playground.debug/ie.otormaigh.playground.MainActivity}: java.lang.ClassCastException: ie.otormaigh.playground.DaggerPlaygroundApplication_HiltComponents_SingletonC$ActivityRetainedCImpl$ActivityCImpl$FragmentCImpl cannot be cast to ie.otormaigh.playground.feature.photos.PhotoListFragment_GeneratedInjector
```


----


Build error from Dagger. This error traces back to the `Retrofit` dependencies in the `:library:networking` module, it seems that Dagger needs to be able to 'see' that dependency
all the way up as far asa the module that contains the `@HiltAndroidApp` annotation.
```
Execution failed for task ':app:kaptLiveDebugKotlin'.
> A failure occurred while executing org.jetbrains.kotlin.gradle.internal.KaptExecution
   > java.lang.reflect.InvocationTargetException (no error message)
```

###15/03/2021

https://source.android.com/security/apksigning/v4
https://source.android.com/security/apksigning/v3

```
signingConfigs {
	debug {
		storeFile file('../signing/debug.keystore')

		enableV3Signing true
		enableV4Signing true
	}
}
```

----

Getting a warning when trying to decrypt files on Github Actions. Looks like my version of `openssl` doesn't not include deprecation notices for the "derivation key" that I'm using.
```
> Old command
openssl aes-256-cbc -a -md sha256 ...

> New command
openssl aes-256-cbc -md sha512 -salt -pbkdf2 -iter 10000 ...

*** WARNING : deprecated key derivation used.
Using -iter or -pbkdf2 would be better.
bad decrypt
```

### 16/03/2021
Dependabot is failing to fetch updates for this project. It seems to be falling back to an internal default repo URL (`https://repo.maven.apache.org:443/maven2`) if none can be
found in the project, I'm assuming it can't 'see' the repo block in the `settings.gradle` file.

I've traced it down to the new `dependencyResolutionManagement` blocking being used in the `settings.gradle` file. Reverting back to the 'old' way using the root/build.gradle file
fixes this.

```
settings.gradle
---------------

dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
  }
}


root/build.gradle
-----------------
allprojects {
  repositories {
    google()
    mavenCentral()
  }
}

```

I've created an issue on the Dependabot repo for this https://github.com/dependabot/dependabot-core/issues/3286


----


I was having some problems with Github secrets not being accessible by Pull Requests that were created by Dependabot. Github do this on purpose so that forks don't maliciously try
to expose any repo secrets. There is section in the Github settings that allow you you create secrets specific to apps, I did add one there but it still wasn't accessible by
Dependabot PRs, if I manually click 'Re-run job' then it would be accessible so I don't know if it it was me manually re-running the job that 'fixed' it or if it was a delayed
settings update. I'll know for sure once the next Dependabot PR comes through.


### 17/03/2021
The above Dependabot issue seems to be a 'feature'. There is a relevant bug [here](https://github.com/dependabot/dependabot-core/issues/3253). I think I may just remove the
ability to decrypt the release keystore on the Dependabot builds and only build the full release with real signing keys through the master branch. This is going to cause a
signing conflict on the diffuse output, but that's not too much of an issue. The setting I found that allows you to supply secrets to Dependabot appears to only allow access
within the `dependabot.yml` config file/step rather than within any 'external' step (see [here](https://docs.github.com/en/github/administering-a-repository/managing-encrypted-secrets-for-dependabot).