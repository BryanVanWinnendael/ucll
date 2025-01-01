defmodule GameServer.Room do
  use GenServer
  alias GameServer.Board

  @me __MODULE__
  defstruct name: nil, player1: nil, player2: nil, board: %Board{}, turn: nil

  def start_link(room_name) do
    GenServer.start_link(@me, room_name, name: via_tuple(room_name))
  end

  def list_room(room_name) do
    GenServer.call(via_tuple(room_name), {:get_state})
  end

  def list_room_pid(pid) do
    GenServer.call(pid, :send_info)
  end

  def join_room(room_name, name) do
    GenServer.call(via_tuple(room_name), {:join_room, name})
  end

  def crash(pid) do
    Process.exit(pid, :kill)
  end

  def put_symbol(room_name, position, symbol) do
    GenServer.call(via_tuple(room_name), {:put_symbol, position, symbol})
  end

  def restart(room_name) do
    GenServer.call(via_tuple(room_name), {:restart})
  end

  ##########
  # SERVER #
  ##########

  @impl true
  @spec init(any) :: {:ok, %GameServer.Room{name: any, player1: nil, player2: nil}}
  def init(room_name), do: {:ok, %@me{name: room_name}}

  def handle_call({:join_room, name}, _from, %{player1: nil} = state) do
    {:reply, "#{name} joined #{state.name}", %{state | player1: name}}
  end

  def handle_call({:join_room, name}, _from, %{player2: nil} = state) do
    turn = Enum.random([:x, :o])
    {:reply, "#{name} joined #{state.name}", %{state | player2: name, turn: turn}}
  end

  def handle_call({:join_room, _player}, _from, state) do
    {:reply, {:retry, "Room is full"}, state}
  end

  def handle_call({:restart}, _from, state) do
    {:replay, "restarting room", %{state | board: %Board{}, turn: nil}}
  end

  def handle_call({:put_symbol, position, symbol}, _from, %{turn: symbol} = state) do
    case Board.put_symbol(state.board, position, symbol) do
      {:error, error_message} ->
        {:reply, {:retry, error_message}, state}

      board ->
        turn = if symbol == :x, do: :o, else: :x
        new_state = %{state | board: board, turn: turn}

        cond do
          winner = Board.winner(board) ->
            turn = Enum.random([:x, :o])
            winner_state = %{new_state | board: %Board{}, turn: turn}
            positions = new_state.board.positions

            board =
              {"      #{Enum.at(positions, 0)}      |      #{Enum.at(positions, 1)}      |      #{Enum.at(positions, 2)}      ",
               "      #{Enum.at(positions, 3)}      |      #{Enum.at(positions, 4)}      |      #{Enum.at(positions, 5)}      ",
               "      #{Enum.at(positions, 6)}      |      #{Enum.at(positions, 7)}      |      #{Enum.at(positions, 8)}      ",
               "Game is finished, #{winner} won                  ",
               "You can start a new game, it's now #{turn} turn"}

            {:reply, {:retry, board}, winner_state}

          true ->
            positions = new_state.board.positions

            board =
              {"      #{Enum.at(positions, 0)}      |      #{Enum.at(positions, 1)}      |      #{Enum.at(positions, 2)}      ",
               "      #{Enum.at(positions, 3)}      |      #{Enum.at(positions, 4)}      |      #{Enum.at(positions, 5)}      ",
               "      #{Enum.at(positions, 6)}      |      #{Enum.at(positions, 7)}      |      #{Enum.at(positions, 8)}      ",
               "its now #{turn} turn                           "}

            {:reply, board, new_state}
        end
    end
  end

  def handle_call({:put_symbol, _position, _symbol}, _from, state) do
    {:reply, {:retry, "Not your turn"}, state}
  end

  def handle_call({:get_board}, _, state) do
    {:reply, {:ok, state.board}, state}
  end

  @impl true
  def handle_call(:send_info, _caller, state) do
    {:reply, {state.name, state.player1, state.player2, state.board}, state}
  end

  @impl true
  def handle_call({:get_state}, _, %@me{} = state) do
    {:reply, {:ok, state}, state}
  end

  defp via_tuple(room_name) do
    {:via, :global, {:name, room_name}}
  end
end
