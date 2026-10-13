import 'package:flutter/material.dart';

class ThemeButton extends StatelessWidget {
  const ThemeButton({Key? key, required this.changeThemeMode})
    : super(key: key);

  final Function(bool isBright) changeThemeMode;

  @override
  Widget build(BuildContext context) {
    final bool isBright = Theme.of(context).brightness == Brightness.light;
    return IconButton(
      icon: isBright
          ? Icon(Icons.dark_mode_rounded)
          : Icon(Icons.light_mode_rounded),
      onPressed: () => changeThemeMode(!isBright),
    );
  }
}
