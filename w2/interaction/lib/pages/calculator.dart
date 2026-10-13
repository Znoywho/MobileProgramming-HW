import 'package:flutter/material.dart';

class Calculator extends StatefulWidget {
  const Calculator({super.key});
  @override
  State<Calculator> createState() => _Calculator();
}

class _Calculator extends State<Calculator> {
  String? symbol;
  double? result;
  TextEditingController text1 = TextEditingController();
  TextEditingController text2 = TextEditingController();

  void updateResult() {
    double? num1 = double.parse(text1.text);
    double? num2 = double.parse(text2.text);

    setState(() {
      if (symbol == '+') {
        result = num1 + num2;
      } else if (symbol == '-') {
        result = num1 - num2;
      } else if (symbol == '*') {
        result = num1 * num2;
      } else {
        result = num1 / num2;
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Padding(
      padding: EdgeInsets.all(16.0),
      child: Column(
        children: [
          Center(
            child: Text(
              'Thuc Hanh 1',
              style: TextStyle(fontSize: 25.0, fontWeight: FontWeight.bold),
            ),
          ),
          TextField(
            controller: text1,
            decoration: InputDecoration(
              border: OutlineInputBorder(
                borderRadius: BorderRadius.all(Radius.circular(30)),
              ),
              hintText: 'Number 1',
            ),
          ),

          Padding(
            padding: EdgeInsets.all(16.0),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                FilledButton(
                  onPressed: () => {
                    symbol = '+',
                    print(Symbol),
                    updateResult(),
                  },
                  child: Text('+'),
                ),
                FilledButton(
                  onPressed: () {
                    symbol = '-';
                    print(symbol);
                    updateResult();
                  },
                  child: Text('-'),
                ),
                FilledButton(
                  onPressed: () {
                    symbol = '*';
                    print(symbol);
                    updateResult();
                  },
                  child: Text('*'),
                ),
                FilledButton(
                  onPressed: () {
                    symbol = '/';
                    print(symbol);
                    updateResult();
                  },
                  child: Text('/'),
                ),
              ],
            ),
          ),

          TextField(
            controller: text2,
            decoration: InputDecoration(
              border: OutlineInputBorder(
                borderRadius: BorderRadius.all(Radius.circular(30)),
              ),
              hintText: 'Number 2',
            ),
          ),
          SizedBox(height: 20),
          Row(
            children: [
              Text(
                'Kết quả: ',
                style: TextStyle(
                  color: Colors.redAccent,
                  fontSize: 20,
                  fontWeight: FontWeight.bold,
                ),
              ),
              Text(
                '${result}',
                style: TextStyle(
                  fontSize: 20,
                  fontWeight: FontWeight.bold,
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }
}
