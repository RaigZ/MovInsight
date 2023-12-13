<p align="center">
<!-- <img src="https://raw.githubusercontent.com/RaigZ/MovInsight/main/app/src/main/res/drawable/logo.png" height = 230 width = 220> -->
<img src="https://raw.githubusercontent.com/RaigZ/MovInsight/main/app/src/main/res/drawable/logo.png" height = 230 width = 220>
</p>

---
**MovInsight** is a Kotlin-based Android movie app that helps users to explore and manage a list of movies.

## Screenshots
<img src="https://raw.githubusercontent.com/RaigZ/MovInsight/docs/screenshots/home.png" height = 277 width = 135>
<img src="https://raw.githubusercontent.com/RaigZ/MovInsight/docs/screenshots/search-results.png" height = 277 width = 135>
<img src="https://raw.githubusercontent.com/RaigZ/MovInsight/docs/screenshots/search-showcase1.png" height = 277 width = 135>
<img src="https://raw.githubusercontent.com/RaigZ/MovInsight/docs/screenshots/search-showcase2.png" height = 277 width = 135>
<img src="https://raw.githubusercontent.com/RaigZ/MovInsight/docs/screenshots/add-watchlist-item.png" height = 277 width = 135>
<img src="https://raw.githubusercontent.com/RaigZ/MovInsight/docs/screenshots/watchlist-showcase.png" height = 277 width = 135>
<img src="https://raw.githubusercontent.com/RaigZ/MovInsight/docs/screenshots/remove-watchlist-item.png" height = 277 width = 135>
<img src="https://raw.githubusercontent.com/RaigZ/MovInsight/docs/screenshots/profile.png" height = 277 width = 135>

## Features
* Login and signup to distinguish between users and their watchlist items
* Browse for movies/shows with the help of a search field
* Add or remove movies of a user watchlist
* View contents of a movie along with its associated specific details
* Profile to manage an account password
* Allows users to upload picture icon from their photo album
* Bottom navigation bar to make it easier to navigate through the app

## Tech Stack
* Kotlin
* XML
* Room Database
* Firebase Authentication
* Firebase Storage
* Firebase Firestore

## Tools
* Third party libraries: [Picasso](https://square.github.io/picasso/#download), [Mockito](https://github.com/mockito/mockito-kotlin) 

## Local Setup
1. The app may run without the API keys. However, it defeats the purpose of an app that showcases movies, so you may need to generate both API keys from:<br> 
[rapidapi](https://rapidapi.com/DataCrawler/api/imdb188/)<br>
[omdbapi](https://www.omdbapi.com/)

2. Assuming you already generated the keys, place them in `app/build.gradle.kts`, in the app scope build.gradle file. <br>Where it provides `<Replace with api key>`, replace it with the subscribed associated key.
```
    buildTypes {
        release {
            buildConfigField("String", "RAPID_API_KEY", "\"<Replace with api key>\"")
            buildConfigField("String", "OMDB_API_KEY", "\"<Replace with api key>\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            buildConfigField("String", "RAPID_API_KEY", "\"<Replace with api key>\"")
            buildConfigField("String", "OMDB_API_KEY", "\"<Replace with api key>\"")
        }
    }
```

3. Then sync the project files with gradle, build and run the application and you are good to go!

## Demo
Youtube: https://youtu.be/hUsj_LZvH1M<br>
Google Drive: https://drive.google.com/file/d/1uT6iKDuyAK3t3zZK7AYcDkQK8uUcc9jO/view?usp=sharing
