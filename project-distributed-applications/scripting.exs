GameServer.Room.start(:room1)
GameServer.Room.start(:room2)

GameServer.Room.create_room(:room1, :blad)
GameServer.Room.create_room(:room2, :blaz)

GameServer.Room.join_room(:room1, :player1)
GameServer.Room.join_room(:room1, :player2)
GameServer.Room.join_room(:room2, :player11)
GameServer.Room.join_room(:room2, :player22)

GameServer.Room.list_room(:room1)
GameServer.Room.list_room(:room2)

GameServer.start_link(:server1)

GameServer.add_room(:server1,:room1)
GameServer.add_room(:server1,:room2)

GameServer.list_rooms(:server1)


### NEW
{_,pid} = GameServer.start_link(:bla)
 {_, rpid1} = GameServer.start_room(:room1)
 {_, rpid2} = GameServer.start_room(:room2)

 GameServer.Room.list_room(:room1)
 GameServer.Room.list_room(:room2)

 GameServer.crash(pid)

 GameServer.Room.crash(:room1)

 #:observer.start()

ServerManager.start_server(:server1)
ServerManager.start_server(:server2)

ServerManager.start_room(:room1)
ServerManager.start_room(:room2)
ServerManager.start_room(:room3)
ServerManager.start_room(:room4)

GameServer.Room.join_room(:room1, :player1)
GameServer.Room.join_room(:room1, :player2)

GameServer.Room.put_symbol(:room1, 1, :x)
GameServer.Room.put_symbol(:room1, 2, :o)
