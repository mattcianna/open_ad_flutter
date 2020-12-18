
import 'dart:async';

import 'package:flutter/services.dart';

class OpenAdFlutter {
  static const MethodChannel _channel =
      const MethodChannel('open_ad_flutter');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static setup(String adUnitId, {bool showImmediately = false}) async {
    print("Configuring Open Ad Flutter with adUnitId = $adUnitId.");
    final args = {
      "adUnitId": adUnitId,
      "showImmediately": showImmediately,
    };
    await _channel.invokeMethod("configure", args);
  }

  static pause() async {
    await _channel.invokeMethod("pause");
  }

  static resume() async {
    await _channel.invokeMethod("resume");
  }
}
