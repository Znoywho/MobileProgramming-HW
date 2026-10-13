import 'package:flutter/material.dart';

import 'score_status.dart';

class ScoreStatusCard extends StatelessWidget {
  const ScoreStatusCard({super.key, required this.score});
  final ScoreStatus score;

  @override
  Widget build(BuildContext context) {
    return Card(
      color: score.bgColor,
      child: Padding(
        padding: EdgeInsets.all(8.0),
        child: Text(score.label, style: TextStyle(color: score.textColor)),
      ),
    );
  }
}
