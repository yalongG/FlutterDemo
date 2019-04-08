import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'dart:async';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.red,
      ),
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  MethodChannel methodChannel = MethodChannel('increment');

  @override
  void initState() {
    methodChannel.setMethodCallHandler(_handlePlatformIncrement);
    super.initState();
  }

  Future<dynamic> _handlePlatformIncrement(MethodCall call) async {
    if (call.method == 'flutterMethod1') {
      return flutterMethod1();
    } else if (call.method == 'flutterMethod2') {
      return flutterMethod2();
    }
    return '';
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.yellow,
      body: Center(
        child: Column(
          children: <Widget>[
            IconButton(
                icon: Icon(Icons.add),
                onPressed: () async {
                  String message =
                      await methodChannel.invokeMethod('androidMethod1');
                  print(message);
                  print('flutter 按钮1===========>');
                }),
            IconButton(
                icon: Icon(Icons.delete),
                onPressed: () async {
                  String message =
                      await methodChannel.invokeMethod('androidMethod2');
                  print(message);
                  print('flutter 按钮2===========>');
                }),
          ],
        ),
      ),
    );
  }

  String flutterMethod1() {
    return "这是来自Flutter的方法一";
  }

  String flutterMethod2() {
    return "这是来自Flutter的方法二";
  }
}
