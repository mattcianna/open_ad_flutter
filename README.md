# OpenAd Flutter

A Plugin that allows to integrate the new App Open Ads (by Google AdMob) in your Flutter Projects.

More info about App Open Ads:

<https://developers.google.com/admob/android/app-open-ads>
<https://developers.google.com/admob/ios/app-open-ads>

## Getting Started

### Android

- Add the AdMob application identifier inside `AndroidManifest` as specified [here](https://developers.google.com/admob/android/quick-start#update_your_androidmanifestxml).
- Create a new Application class (that subclasses `FlutterApplication`) inside the Android project and specify it inside `AndroidManifest`.
- Add this piece of code inside the onCreate method in the Application class:

```kotlin
override fun onCreate() {
    super.onCreate()
    OpenAdFlutterPlugin.start(this)
}
```

- If Flutter complains about `<queries>` at build time, change the gradle version in `android/build.gradle` to `3.3.3`

### iOS

- Add the AdMob application identifier inside `Info.plist` as specified [here](https://developers.google.com/admob/ios/quick-start).

### Dart

- Inside `main.dart`, after `runApp(...)`, add this piece of code:

```dart
await OpenAdFlutter.setup(
  "ca-app-pub-3940256099942544/5662855259", // Replace with your own id,
);
```

### Optional

On iOS, you can optionally specify `showImmediately: true` inside the setup, if you want to show the open ad also at app startup. On Android it won't work.

If you want to stop presenting open ads for any reasons (for example if the user has purchased the premium version of your app), you can do so by adding this code:

```dart
await OpenAdFlutter.pause();
```

To restart presenting open ads:

```dart
await OpenAdFlutter.pause();
```

## Thanksgiving

The Android part of this library is based on [this repo](https://github.com/ItzNotABug/AppOpenAdManager). Thank you for creating a manager for this ad when Google didn't do it.

## TODO

- Make `showImmediately: true` work on Android
