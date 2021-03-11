### 10/03/2021
When building the `:feature:networking` module using with `compileOptions.sourceCompatibility JavaVersion.VERSION_11` I get the following Gradle build error:
```
What went wrong:
Could not determine the dependencies of task ':app:lintVitalLiveRelease'.
> Could not resolve all dependencies for configuration ':app:liveReleaseRuntimeClasspath'.
   > java.util.ConcurrentModificationException (no error message)

```

Changing it to `compileOptions.sourceCompatibility JavaVersion.VERSION_1_8` allows the build to run successfully.