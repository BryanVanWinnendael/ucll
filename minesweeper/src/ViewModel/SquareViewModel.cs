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
    public class SquareViewModel
    {

        public SquareViewModel(ICell<IGame> game, Vector2D position)
          {
            this.Square = game.Derive(g => g.Board[position]);
            this.Uncover = new UncoverSquareCommand(game, position);
            this.Flag = new FlagSquareCommand(game, position);
            this.Status = Square.Derive(g => g.Status);
            this.LastUncovered = Square.Derive(g => g.LastUncovered);
            this.ContainesMine = Square.Derive(g => g.ContainsMine);
        }
   
        public ICell<Square> Square { get; }

        public ICell<bool> LastUncovered { get; }
        public ICell<bool> ContainesMine { get; }


        public ICell<SquareStatus> Status { get; }

        public ICommand Uncover { get; }

        public ICommand Flag { get; }

    }
}

   