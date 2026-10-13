import 'package:flutter/material.dart';
import 'package:interaction/constants.dart';
import 'package:interaction/pages/calculator.dart';
import 'package:interaction/pages/course.dart';
import 'package:interaction/pages/level.dart';

import 'components/theme_button.dart';

class Home extends StatefulWidget {
  Home({
    super.key,
    required this.isLightMode,
    required this.colorChange,
    required this.colorSelected,
  });

  final void Function(bool isBright) isLightMode;
  final void Function(int value) colorChange;
  final ColorSelection colorSelected;

  @override
  State<StatefulWidget> createState() => _Home();
}

class _Home extends State<Home> {
  int tab = 0;
  List<NavigationDestination> appBarDestinations = const [
    NavigationDestination(
      icon: Icon(Icons.class_outlined),
      label: 'Course',
      selectedIcon: Icon(Icons.class_),
    ),
    NavigationDestination(
      icon: Icon(Icons.calculate_outlined),
      label: 'Calculator',
      selectedIcon: Icon(Icons.calculate),
    ),
    NavigationDestination(
      icon: Icon(Icons.person_2_outlined),
      label: 'Account',
      selectedIcon: Icon(Icons.person),
    ),
        NavigationDestination(
      icon: Icon(Icons.person_2_outlined),
      label: 'Account',
      selectedIcon: Icon(Icons.person),
    ),

  ];

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    final pages = [Course(), Calculator() ,Level()];
    return Scaffold(
      appBar: AppBar(
        elevation: 4.0,
        actions: [ThemeButton(changeThemeMode: widget.isLightMode)],
      ),
      body: IndexedStack(index: tab, children: pages),
      bottomNavigationBar: NavigationBar(
        selectedIndex: tab,
        onDestinationSelected: (index) {
          setState(() {
            tab = index;
          });
        },
        destinations: appBarDestinations,
      ),
    );
  }
}
