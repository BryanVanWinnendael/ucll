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
    public class GameViewModel
    {
        private readonly ICell<IGame> game;

        public GameViewModel(IGame Game)
        {
            game = Cell.Create(Game);
            if(Board == null)
            {
                Board = new GameBoardViewModel(game);
            }
        }   
   
        public GameBoardViewModel Board { get; }

    }
} 
