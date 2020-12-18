package com.example.open_ad_flutter_example

import com.example.open_ad_flutter.OpenAdFlutterPlugin
import io.flutter.app.FlutterApplication

class MyApp: FlutterApplication() {
    override fun onCreate() {
        super.onCreate()
        OpenAdFlutterPlugin.start(this)
    }
}