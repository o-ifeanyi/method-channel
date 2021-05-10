import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
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
  int _counter = 0;

  final channel = MethodChannel('notification');

  void _startService() async {
    try {
      await channel.invokeMethod('create');
    } catch (error) {
      print(error);
    }
  }

  void _stopService() async {
    try {
      await channel.invokeMethod('destroy');
    } catch (error) {
      print(error);
    }
  }

  @override
  void initState() {
    super.initState();
    channel.setMethodCallHandler((call) {
      if (call.method == 'increase') {
        setState(() {
          _counter++;
        });
      } else if (call.method == 'decrease') {
        setState(() {
          _counter--;
        });
      }
      return;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Spacer(),
            ElevatedButton(
              onPressed: _startService,
              child: Text('Start Notification'),
            ),
            ElevatedButton(
              onPressed: _stopService,
              child: Text('Stop Notification'),
            ),
            Spacer(),
            Text(
              'Current count is:',
              style: Theme.of(context).textTheme.headline4,
            ),
            Text(
              '$_counter',
              style: Theme.of(context).textTheme.headline2,
            ),
            Spacer(),
          ],
        ),
      ),
    );
  }
}
