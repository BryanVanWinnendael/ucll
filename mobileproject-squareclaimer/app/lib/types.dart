class User {
  String userName;
  String password;
  String email;

  User(this.userName, this.password, this.email);
}

class Team {
  int id;

  String name;
  String color;
  List<int> members = [];

  Team(this.id, this.name, this.color);
  List<int> getMembers() {
    return members;
  }
}

class Square {
  int x;
  int y;
  int team_id;
  int user_id;
  int timestamp;

  Square(this.x, this.y, this.team_id, this.user_id, this.timestamp);

}