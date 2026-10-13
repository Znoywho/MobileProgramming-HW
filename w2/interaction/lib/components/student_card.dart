import 'package:flutter/material.dart';

import '../models/student.dart';

class StudentCard extends StatelessWidget {
  const StudentCard({super.key, required this.student});
  final Student student;

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    final textTheme = Theme.of(context).textTheme
        .apply(displayColor: Theme.of(context).colorScheme.onSurface);

    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Row(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Expanded(
              child: Column(
                mainAxisSize: MainAxisSize.min,
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    student.name,
                    maxLines: 2,
                    overflow: TextOverflow.ellipsis,
                    style: textTheme.titleLarge,
                  ),
                  Row(
                    children: [
                      Text('Email: ', style: textTheme.bodyLarge),
                      student.email == null
                          ? Text(
                              'Chưa cập nhật',
                              style: TextStyle(
                                color: Colors.red,
                                fontStyle: FontStyle.italic,
                              ),
                            )
                          : Text(
                              '${student.email}',
                              style: textTheme.bodyLarge,
                            ),
                    ],
                  ),
                  Row(
                    children: [
                      Text('Phone: ', style: textTheme.bodyLarge),
                      student.phone == null
                          ? Text(
                              'Chưa cập nhật',
                              style: TextStyle(
                                color: Colors.red,
                                fontStyle: FontStyle.italic,
                              ),
                            )
                          : Text(
                              '${student.phone}',
                              style: textTheme.bodyLarge,
                            ),
                    ],
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
