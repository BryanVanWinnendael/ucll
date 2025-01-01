defmodule GameServer.Application do
  use Application

  def start(_type, _args) do
    topologies = [
      tic_tac_toe: [
        strategy: Cluster.Strategy.Gossip
      ]
    ]

    children = [
      {Cluster.Supervisor, [topologies, [name: Gameserver.ClusterSupervisor]]},
      {DynamicSupervisor, strategy: :one_for_one, name: Gameserver.DynamicGameSupervisor},
      {ServerManager, []}
    ]

    opts = [strategy: :one_for_one, name: Gameserver.Supervisor]
    Supervisor.start_link(children, opts)
  end
end
