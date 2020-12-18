import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:open_ad_flutter/open_ad_flutter.dart';

void main() async {
  runApp(MyApp());
  await OpenAdFlutter.setup(
    "ca-app-pub-3940256099942544/5662855259",
    showImmediately: true,
  );
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Text('ciao'),
        ),
      ),
    );
  }
}
