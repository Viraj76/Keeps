name: AndroidBuild

on:
  pull_request :
    branches : [master]
  push :
    branches : [ master ]


jobs:
  build:
    runs-on: windows-latest
    steps:

      - name : Checkout
        uses: actions/checkout@v4.1.3

      - name : Setup Java JDK
        uses: actions/setup-java@v4.2.1
        with:
          java-version: '17'
          distribution: 'adopt'

      - name: Make gradlew executable
        run: chmod +x ./gradlew

      - name: Setup Gradle Cache
        uses: actions/cache@v4.0.2
        with:
          gradle-home-cache-cleanup: true
          path: |
            ~/.gradle/caches
            ~/.gradle/wrapper
          key: ${{ runner.os }}-${{ hashFiles('**/*.gradle*') }}-${{ hashFiles('**/gradle/wrapper/gradle-wrapper.properties') }}-${{ hashFiles('**/buildSrc/**/*.kt') }}


      - name: Bump Version
        uses: chkfung/android-version-actions@v1.2.3
        with:
          gradlePath: app/build.gradle.kts
          versionCode: ${{ github.run_number }}
          versionName: 1.0.0



      - name: Build Release AAB
        run: ./gradlew bundleRelease

      - name: Sign AAb2
        id: sign
        uses: jonfazzaro/sign-android-release@v1.0.1
        with:
            releaseDirectory: app/release
            signingKeyBase64: ${{ secrets.KEYSTORE_FILE }}
            alias: ${{ secrets.SIGNING_KEY_ALIAS }}
            keyStorePassword: ${{ secrets.KEYSTORE_PASSWORD }}
            keyPassword: ${{ secrets.SIGNING_KEY_PASSWORD }}


      - name: Upload app bundle as artifact
        uses: actions/upload-artifact@v4.3.3
        with:
            name: app-bundle
            path: app/build/outputs/bundle/release/app-release.aab

      - run: echo Build status report=${{ job.status }}




  deploy:
      name: Job_2 - Deploy aab to playstore
      needs: build
      runs-on: ubuntu-latest

      steps:
        - name: Download app bundle from artifact
          uses: actions/download-artifact@v4.1.6
          with:
              name: app-bundle

        - name: Publish to Play Store2
          uses: r0adkll/upload-google-play@v1
          with:
              serviceAccountJsonPlainText: ${{ secrets.SERVICE_JSON }}
              packageName: com.geeta.bhagwatgita
              releaseFiles: ${{ github.workspace }}/app-release.aab
              track: production
