using Cells;
using Model.Data;
using Model.MineSweeper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ViewModel
{
    public class RowViewModel
    {
        public IEnumerable<SquareViewModel> Squares { get; }

        public RowViewModel(ICell<IGame> game, int rowIndex)
        {
            this.Squares = Row(rowIndex,game);
        }

        public IEnumerable<SquareViewModel> Row(int row, ICell<IGame> igame)
        {
            var squares = new List<SquareViewModel>();

            for (int i = 0; i < igame.Value.Board.Width; i++)
            {
                var position = new Vector2D(i, row);
                var square = new SquareViewModel(igame, position);

                squares.Add(square);
            }

            return squares;
        }

    }
}
