import 'package:flutter/material.dart';
import 'package:interaction/components/student_card.dart';
import 'package:interaction/models/student.dart';

class Course extends StatelessWidget {
  const Course({super.key});
  @override
  Widget build(BuildContext context) {
    final List<Student> studentList = students;
    var missCount = 0;
    for (var stu in studentList) {
      if (stu.email == null) {
        missCount += 1;
      }
    }

    return Padding(
      padding: EdgeInsets.all(12.0),
      child: Column(
        children: [
          Expanded(
            child: SizedBox(
              child: ListView(
                children: [for (var i in studentList) StudentCard(student: i)],
              ),
            ),
          ),
          SizedBox(
            child: Card(
              color: Colors.blue,
              elevation: 5,
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.all(Radius.circular(10)),
              ),
              child: Padding(
                padding: EdgeInsets.all(16.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Text(
                      'Tong so sinh vien: ${studentList.length}',
                      style: TextStyle(
                        fontSize: 16,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    Text(
                      'Thieu email: ${missCount}',
                      style: TextStyle(
                        fontSize: 16,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                  ],
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }
}
