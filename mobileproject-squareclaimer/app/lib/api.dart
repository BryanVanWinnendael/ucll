import 'dart:convert';
import 'package:http/http.dart' as http;

import 'types.dart';

String address = "localhost:8080";
//OK = ok with session
//ok = ok without session added

///Claim a square using the square's x and y coordinates, and the user's id (which is saved in the session) - OK
Future<Map> claimSquare(x, y) async {
  Session s = Session();
  return await s.post(address, "/api/square/claim/${x.toString()}/${y.toString()}");
}

///Get squares that are 'radius' squares away from the provided x and y coordinates - OK
Future<List<Square>> getSquares(x, y, radius) async{
  Session s = Session();
  List<Square> squares = [];
  Map<String, dynamic> response = await s.get(address, "/api/square/radius/${x.toString()}/${y.toString()}/${radius.toString()}");
  for (var square in response["squares"]) {
    Square s = Square(square['x'], square['y'], square['team_id'], square['user_id'], square['timestamp']);
    squares.add(s);
  }
  return squares;
}

//User data
///Get all users - OK
Future<List<User>> getAllUsers() async{
  List<User> users = [];
  Session s = Session();
  List response = await s.get(address, "/api/user/all");
  for (var user in response) {
    User u = User(user['email'], user['userName'], user['password']);
    users.add(u);
  }
  return users;
}

///Get a user object using the user id - OK
Future<User> getUser() async {
  Session s = Session();
  Map userdata = await s.get(address, "/api/user/get");
  User user = User(userdata["email"], userdata["userName"], userdata["password"]);
  return user;
}

///Add a user using a user object (with userName, email and password set). The password is not hashed yet! - OK
void addUser(User user) async{
  final data = {
    "userName" : user.userName,
    "email" : user.email,
    "password" : user.password,
  };
  Session s = Session();
  s.post_with_data(address, "/api/user/add", data);
}

///Log a user in with their username and password, and return a key - OK
Future<String> passwordLogin(username ,password) async{
  final data = {
    "username" : username,
    "password" : password,
  };
  Session s = Session();
  return (await s.post_with_data(address, "/api/user/login/credentials", data))["key"];
}

///Log a user in using a token (created when logging in with password), and returns the token on succesful login - OK
Future<String> tokenLogin(key) async{
  final data = {
    "key" : key,
  };
  Session s = Session();
  return (await s.post_with_data(address, "/api/user/login/key", data))["key"];
}

///Log a user out by removing their session - OK
Future<String> logout() async {
  Session s = Session();
  return (await s.get(address, "/api/user/logout"))["message"];
}

void removeUser() async{
  Session s = Session();
  await s.post(address, "/api/user/remove");
}

//Team data
///Get all teams - OK
Future<List<Team>> getAllTeams() async{
  Session s = Session();
  List<Team> teams = [];
  List data = await s.get(address, "/api/team/all");
  for (var team in data) {
    Team t = Team(team['id'], team['name'], team['color']);
    teams.add(t);
  }
  return teams;
}

///Get a team using the team's id - OK
Future<Team> getTeam(id) async{
  Session s = Session();
  Map valuemap = await s.get(address, "/api/team/get/1");
  Team team = Team(valuemap['id'] , valuemap['name'], valuemap['color']);
  return team;
}

///Create a new team using a team object (with nameName and teamColor set) - OK
void addTeam(teamName, teamColor) async{
  final data = {
    "name" : teamName,
    "color" : teamColor,
  };
  Session s = Session();
  s.post_with_data(address, "/api/team/add", data);
}

///Join a team using the userid and the teamid - OK
void joinTeam(teamid) async{
  Session s = Session();
  await s.post(address, "/api/team/join/${teamid.toString()}");
}


class Session {
  ///Singleton logic -> a new instance gets created if one doesn't exist, or the existing instance gets returned when one exists
  ///Just use (new) Session() to get the singleton instance
  static final Session _session = Session._internal();
  factory Session(){
    return _session;
  }
  Session._internal();

  ///A map with all headers to include in each request. We add the session id to this map once it's obtained
  Map<String, String> headers = {'content-type': 'application/json', 'Accept': 'application/json'};

  dynamic get(String address, String url) async {
    Uri uri = Uri.http(address, url);
    http.Response response = await http.get(uri, headers: headers);
    updateCookie(response);
    return json.decode(response.body);
  }

  dynamic post(String address, String url) async {
    Uri uri = Uri.http(address, url);
    http.Response response = await http.post(uri, headers: headers);
    updateCookie(response);
    return json.decode(response.body);
  }

  dynamic post_with_data(String address, String url, dynamic data) async {
    Uri uri = Uri.http(address, url);
    http.Response response = await http.post(uri, body: jsonEncode(data), headers: headers);
    updateCookie(response);
    return json.decode(response.body);
  }

  void updateCookie(http.Response response) {
    String? rawCookie = response.headers['set-cookie'];
    if (rawCookie != null) {
      int index = rawCookie.indexOf(';');
      headers['cookie'] =
      (index == -1) ? rawCookie : rawCookie.substring(0, index);
    }
  }
}