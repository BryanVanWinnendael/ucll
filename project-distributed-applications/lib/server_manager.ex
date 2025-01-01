defmodule ServerManager do
  use DynamicSupervisor
  alias GameServer
  @me __MODULE__

  ##########
  #  API   #
  ##########
  def start_link(_init),
    do: DynamicSupervisor.start_link(@me, %{}, name: @me)

  # GenServer.start_link(@me, :ok, name: {:via, :global, {Node.self(), @me}})
  def get_children, do: DynamicSupervisor.which_children(@me)

  ##########
  # SERVER #
  ##########

  def init(_init_arg) do
    DynamicSupervisor.init(strategy: :one_for_one)
  end

  def start_server(server_name) do
    DynamicSupervisor.start_child(@me, {GameServer, server_name})
  end

  def start_room(room_name) do
    {_, server_name} = get_smallest_server_per_node()

    GameServer.start_room(server_name, room_name)
  end

  def get_servers_in_cluster() do
    :global.registered_names() |> Enum.filter(fn {x, _} -> x == :gameservermodule end)
  end

  def get_games_per_server() do
    get_servers_in_cluster()
    |> Enum.map(fn {_, server} -> {server, GameServer.count_children(server)} end)
  end

  def get_smallest_server_per_node() do
    servers = get_servers_in_cluster()

    servers
    |> Enum.min_by(fn {_, pid_name} -> GameServer.count_children(pid_name) end)
  end
end
