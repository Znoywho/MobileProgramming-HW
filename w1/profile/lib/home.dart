import 'package:flutter/material.dart';

import 'components/color_button.dart';
import 'components/theme_button.dart';
import 'constant.dart';

class Home extends StatefulWidget {
  const Home({
    super.key,
    required this.changeTheme,
    required this.changeColor,
    required this.colorSelected,
  });
  final void Function(bool useLightMode) changeTheme;
  final void Function(int value) changeColor;
  final ColorSelection colorSelected;
  @override
  State<Home> createState() => _HomeState();
}

class _HomeState extends State<Home> {
  // TODO: Track current tab
  int tab = 0;
  // TODO: Define tab bar destinations
  List<NavigationDestination> appBarDestinations = const [
    NavigationDestination(
      icon: Icon(Icons.person),
      label: 'Profile',
      selectedIcon: Icon(Icons.person),
    ),
    NavigationDestination(
      icon: Icon(Icons.mail_lock_rounded),
      label: 'Mail',
      selectedIcon: Icon(Icons.mail_lock_rounded),
    ),
    NavigationDestination(
      icon: Icon(Icons.work),
      label: 'work',
      selectedIcon: Icon(Icons.work),
    ),
  ];
  @override
  Widget build(BuildContext context) {
    // TODO: Define pages
    return Scaffold(
      appBar: AppBar(
        elevation: 4.0,
        backgroundColor: Theme.of(context).colorScheme.background,
        actions: [
          ThemeButton(changeThemeMode: widget.changeTheme),
          ColorButton(
            changeColor: widget.changeColor,
            colorSelected: widget.colorSelected,
          ),
        ],
      ),
      // TODO: Switch between pages
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Text(
          'You Hungry?! ',
          style: Theme.of(context).textTheme.displayLarge,
        ),
      ),
      // TODO: Add bottom navigation bar
      bottomNavigationBar: NavigationBar(
        // 2
        selectedIndex: tab,
        // 3
        onDestinationSelected: (index) {
          setState(() {
            tab = index;
          });
        },
        // 4
        destinations: appBarDestinations,
      ),
    );
  }
}
