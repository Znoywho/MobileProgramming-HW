import 'package:flutter/material.dart';
import 'constants.dart';
import 'home.dart';


void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  MyApp({super.key});


  @override
  State<MyApp> createState() => _MyApp();

  // This widget is the root of your application.
}

class _MyApp extends State<MyApp> {
  ThemeMode themeMode = ThemeMode.dark;
  ColorSelection colorSelected = ColorSelection.blue;


  void changeThemeMode(bool isBright) {
    setState(() {
          themeMode = isBright ? ThemeMode.light: ThemeMode.dark;
        });
  }
  
  void changeColor(int value) {
    setState(() {
          colorSelected = ColorSelection.values[value];
        });
  }  @override
  Widget build(BuildContext context) {
    const appTitle = 'Interaction';


    //3
    return MaterialApp(
        title: appTitle,
        //debugShowCheckedModeBanner: false, // Uncomment to remove Debug banner

        // TODO: Add theme
        themeMode: themeMode,
        theme: ThemeData(
          colorSchemeSeed: colorSelected.color,
          useMaterial3: true,
          brightness: Brightness.light,
        ),
        darkTheme: ThemeData(
          colorSchemeSeed: colorSelected.color,
          useMaterial3: true,
          brightness: Brightness.dark,
        ),
        // 4
        home: Home(
            isLightMode: changeThemeMode,
            colorChange: changeColor,
            colorSelected: colorSelected));
  }
}





