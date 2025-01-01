defmodule GameServer do
  use DynamicSupervisor
  alias GameServer.Room

  @me __MODULE__

  def start_link(init_arg) do
    DynamicSupervisor.start_link(@me, init_arg, name: via_tuple(init_arg))
  end

  def get_children(server), do: DynamicSupervisor.which_children(via_tuple(server))

  def count_children(server), do: DynamicSupervisor.count_children(via_tuple(server))

  def get_room_names(server) do
    children = get_children(server)

    children
    |> Enum.map(fn {_, pid, _, _} -> pid end)
    |> Enum.map(fn pid -> Room.list_room_pid(pid) end)
    |> Enum.map(fn {pid, _, _, _} -> pid end)
  end

  def init(_init_arg) do
    DynamicSupervisor.init(strategy: :one_for_one)
  end

  def start_room(server, room_name) do
    DynamicSupervisor.start_child(via_tuple(server), {Room, room_name})
  end

  def crash(pid) do
    Process.exit(pid, :kill)
  end

  def via_tuple(init_arg) do
    {:via, :global, {:gameservermodule, init_arg}}
  end
end
