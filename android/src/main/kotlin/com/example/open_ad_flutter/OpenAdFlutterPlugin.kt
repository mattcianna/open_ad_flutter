package com.example.open_ad_flutter

import android.app.Application
import android.content.Context
import androidx.annotation.NonNull
import com.google.android.gms.ads.MobileAds

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar

/** OpenAdFlutterPlugin */
class OpenAdFlutterPlugin: FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel

  private lateinit var context: Context
  private lateinit var appOpenManager: AppOpenManager

  companion object {
    private lateinit var application: Application
    var isPaused = false

    fun start(application: Application) {
      this.application = application
      MobileAds.initialize(application)
    }
  }

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    context = flutterPluginBinding.applicationContext
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "open_ad_flutter")
    channel.setMethodCallHandler(this)
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    when(call.method) {
      "configure" -> {
        val args = call.arguments as Map<*, *>
        appOpenManager = AppOpenManager(
                application = application,
                adUnitId = args["adUnitId"] as? String ?: "ca-app-pub-3940256099942544/5662855259"
        )
        appOpenManager.fetchAd()
      }
      "pause" -> {
        isPaused = true
      }
      "resume" -> {
        isPaused = false
      }
      else -> {
        result.notImplemented()
      }
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }
}
