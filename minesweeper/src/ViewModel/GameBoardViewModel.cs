using Cells;
using Model.Data;
using Model.MineSweeper;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace ViewModel
{
    public class GameBoardViewModel
    {
        private readonly ICell<IGameBoard> board;

        public ICell<GameStatus> gameStatus { get; set; }

        public GameBoardViewModel(ICell<IGame> igame)
        {
            board = igame.Derive(g => g.Board);
            Rows = CreateRows(igame);
            
            gameStatus = Cell.Create(igame.Value.Status);
        }

        public IEnumerable<RowViewModel> Rows { get;}

        public IEnumerable<RowViewModel> CreateRows(ICell<IGame> igame)
        {
                for (int i = 0; i < board.Value.Height; i++)
                {
                    yield return new RowViewModel(igame,i);
                }
        }

    }

    public class UncoverSquareCommand : ICommand
    {
        public event EventHandler? CanExecuteChanged;

        public UncoverSquareCommand(ICell<IGame> igame, Vector2D position)
        {
            this.Game = igame;
            this.Position = position;
        }

        public ICell<IGame> Game { get; set; }

        public Vector2D Position { get; set; }

        public bool CanExecute(object? parameter)
        {
            return Game.Value.IsSquareCovered(Position) && Game.Value.Status == GameStatus.InProgress;
        }

        public void ucoverMines()
        {
            for (int i = 0; i < Game.Value.Board.Height; i++)
            {
                for (int j = 0; j < Game.Value.Board.Width; j++)
                {
                    var position = new Vector2D(i, j);
                    if (Game.Value.Board[position].ContainsMine && !position.Equals(Position))
                    {
                        Game.Value.Board[position].Uncover();
                    }
                }
            }
        }

        public void Execute(object? parameter)
        {

            if (Game.Value.Board[Position].ContainsMine)
            {
                ucoverMines();
            }
            Game.Value = Game.Value.UncoverSquare(Position);
        }
    }

    public class FlagSquareCommand : ICommand
    {
        public event EventHandler? CanExecuteChanged;

        public FlagSquareCommand(ICell<IGame> igame, Vector2D position)
        {
            this.Game = igame;
            this.Position = position;
        }

        public ICell<IGame> Game { get; set; }

        public Vector2D Position { get; set; }

        public bool CanExecute(object? parameter)
        {
            return Game.Value.IsSquareCovered(Position) && Game.Value.Status == GameStatus.InProgress;
        }

        public void Execute(object? parameter)
        {
          
          Game.Value = Game.Value.ToggleFlag(Position);
        }
    }

}
