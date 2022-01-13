# community_android

# Introduction
This is a Listing android app with one primary feature - to list the data.

# Project Configuration
Language: Kotlin
Minimum SDK: 21

# Project Structure
app
Activity the listing results page
ViewModel with function to fetch remote results data from Repository

# Tech Stack
Android
MVVM
Koin for dependency injection
Retrofit for networking
Moshi for json parsing
View binding for UI layer.
Coroutines for async tasks.
Paging 3
Spek 2 for unit testing
glide for image download

# The way to test the Spek Unit test 
AS 4.2 Spek plugin automatically selects Intellij compatible spek plugin as the latest and recommended.

We need to install the AS version of Spek Plugin.
A list of versions can also be found here, https://plugins.jetbrains.com/plugin/10915-spek-framework/versions for the specific version of AS.

Choose install from disk option:

![plot](https://github.com/thusya/community_android/blob/master/screenshot/spek.png)

# Screen shot 
![plot](https://github.com/thusya/community_android/blob/master/screenshot/device-2022-01-13-121105.png)
![plot](https://github.com/thusya/community_android/blob/master/screenshot/play.gif)

