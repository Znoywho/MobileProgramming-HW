import 'package:flutter/material.dart';

import '../components/score_status_card.dart';
import '../components/score_status.dart';

class Level extends StatefulWidget {
  const Level({super.key});

  @override
  State<StatefulWidget> createState() => _Level();
}

class _Level extends State<Level> {
  TextEditingController grade = TextEditingController();

  @override
  Widget build(BuildContext context) {
    double? gradeScore;
    void statusColorUpdate() {}
    // TODO: implement build
    return Align(
      alignment: Alignment.topCenter,
      child: Column(
        children: [
          Padding(
            padding: const EdgeInsets.all(16),
            child: SizedBox(
              // width: 220,
              height: 100,
              child: TextFormField(
                style: TextStyle(fontSize: 40),
                controller: grade,
                textAlign: TextAlign.center,
                decoration: InputDecoration(
                  labelText: 'Diem',
                  border: OutlineInputBorder(),
                  enabledBorder: OutlineInputBorder(
                    borderSide: BorderSide(color: Colors.green),
                  ),
                ),
              ),
            ),
          ),

          ScoreStatusCard(score: ScoreStatus.gioi),
          SizedBox(
            height: 100,
            width: 100,
            child: Align(
              alignment: Alignment.center,
              child: ScoreStatusCard(score: ScoreStatus.gioi),
            ),
          ),
        ],
      ),
    );
  }
}
