
import 'package:flutter/material.dart';


enum ScoreStatus {
  gioi('Giỏi', Color(0xFF2E7D32), Color(0xFFE3F4E4)),
  kha('Khá', Color(0xFF1E4FBF), Color(0xFFE3ECFB)),
  trungBinh('Trung bình', Color(0xFFB7791F), Color(0xFFFDF0DA)),
  yeu('Yếu', Color(0xFFC62828), Color(0xFFFBE4E4)),
  khongHopLe('Không hợp lệ', Color(0xFF6B7280), Color(0xFFEDEEF2));
 
  final String label;
  final Color textColor;
  final Color bgColor;
  const ScoreStatus(this.label, this.textColor, this.bgColor);
}

ScoreStatus scoreLevel(double? grade) {
  if (grade == null||grade < 0 || grade > 10 ){
    return ScoreStatus.khongHopLe;
  }
  else if (grade < 10 && grade >= 8.5){
    return ScoreStatus.gioi;
  }
  else if (grade < 8.5 && grade >=6.5){
    return ScoreStatus.kha;
  }
  else if (grade < 6.5){
    return ScoreStatus.trungBinh;
  } else {
    return ScoreStatus.yeu;
  }
}





