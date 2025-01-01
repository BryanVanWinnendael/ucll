# TicTacToe

**TODO: Add description**

## Installation

If [available in Hex](https://hex.pm/docs/publish), the package can be installed
by adding `tic_tac_toe` to your list of dependencies in `mix.exs`:

```elixir
def deps do
  [
    {:tic_tac_toe, "~> 0.1.0"}
  ]
end
```

Documentation can be generated with [ExDoc](https://github.com/elixir-lang/ex_doc)
and published on [HexDocs](https://hexdocs.pm). Once published, the docs can
be found at <https://hexdocs.pm/tic_tac_toe>.



## Commands

### Start the app

```bash
iex --sname *enter node name here* -S mix
```

### Start a server

```elixir
ServerManager.start_server(:*server name*)
```

### Make a room
  
```elixir
ServerManager.start_room(:*room name*)
```

### Join a room

```elixir
GameServer.Room.join_room(:*room name*, :*player name*)
```

### Make a move

```elixir
GameServer.Room.put_symbol(:*room name*, *position*, :*symbol*)
```
note: symbol must either be :o or :x
## Util commands

### Check the amount of rooms for each server

```elixir
ServerManager.get_games_per_server()
```

### Get info about a room

```elixir
GameServer.Room.list_room(:*room name*)
```

### Get all rooms on a server
```elixir
GameServer.get_room_names(:*server name*)
```

## Load balancing
This app will automatically assign rooms to different servers on different nodes based on how populated the servers in each node are
In order to see the load balancing in action you must do the following

- start the app
- create a server
- create 2+ rooms
- check the amount of rooms for each server
- start another instance of the app in another terminal
- create a server there
- create a room in the original terminal
- Check the amount of rooms for each server again

Example:
first terminal:
```elixir
iex --sname node1 -S mix
ServerManager.start_server(:server1)
Servermanager.start_room(:room11)
Servermanager.start_room(:room12)
Servermanager.start_room(:room13)
ServerManager.get_games_per_server()
```

second terminal:
```elixir
iex --sname node2 -S mix
ServerManager.start_server(:server2)
Servermanager.start_room(:room21)
```

first terminal:
```elixir
ServerManager.get_games_per_server()
```
