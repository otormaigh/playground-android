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

