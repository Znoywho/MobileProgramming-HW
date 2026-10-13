class Student {
  Student(this.name, this.email, this.phone);

  String name;
  String? email;
  String? phone;
}

List<Student> students = [
  Student('Nguyen Van An', 'an@sv.ut.edu', '090 222 888'),
  Student('Nguyen Van Binh', null, '090 000 888'),
  Student('Nguyen Van Chi', 'chi@sv.ut.edu', null),
  Student('Nguyen Van Dung', null, null),
  Student('Nguyen Van Em', 'em@sv.ut.edu', '090 122 888'),
];
