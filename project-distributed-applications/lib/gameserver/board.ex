defmodule GameServer.Board do
  @me __MODULE__

  @symbols [:x, :o]
  defstruct positions: ["_", "_", "_", "_", "_", "_", "_", "_", "_"]

  def put_symbol(board, position, symbol)
      when symbol in @symbols and position >= 1 and position <= 9 do
    case Enum.at(board.positions, position - 1) do
      "_" ->
        new_positions = List.replace_at(board.positions, position - 1, symbol)
        %@me{board | positions: new_positions}

      _ ->
        {:error, "Position already taken"}
    end
  end

  def put_symbol(_board, _position, _symbol) do
    error_message =
      "Wrong symbol and/or wrong position. Check if you are passing :x or :o as" <>
        " symbol and a position between 1 and 9."

    {:error, error_message}
  end

  def winner(%@me{positions: positions}) do
    do_winner(positions)
  end

  defp do_winner([
         s,
         s,
         s,
         _,
         _,
         _,
         _,
         _,
         _
       ])
       when s in @symbols,
       do: s

  defp do_winner([
         _,
         _,
         _,
         s,
         s,
         s,
         _,
         _,
         _
       ])
       when s in @symbols,
       do: s

  defp do_winner([
         _,
         _,
         _,
         _,
         _,
         _,
         s,
         s,
         s
       ])
       when s in @symbols,
       do: s

  defp do_winner([
         s,
         _,
         _,
         s,
         _,
         _,
         s,
         _,
         _
       ])
       when s in @symbols,
       do: s

  defp do_winner([
         _,
         s,
         _,
         _,
         s,
         _,
         _,
         s,
         _
       ])
       when s in @symbols,
       do: s

  defp do_winner([
         _,
         _,
         s,
         _,
         _,
         s,
         _,
         _,
         s
       ])
       when s in @symbols,
       do: s

  defp do_winner([
         s,
         _,
         _,
         _,
         s,
         _,
         _,
         _,
         s
       ])
       when s in @symbols,
       do: s

  defp do_winner([
         _,
         _,
         s,
         _,
         s,
         _,
         s,
         _,
         _
       ])
       when s in @symbols,
       do: s

  defp do_winner(_), do: nil
end
