using Cells;
using Model.MineSweeper;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace ViewModel.Screens
{
    public class ScreenMenuViewModel : ScreenViewModel
    {
        public ICell<bool> Flooding { get;}
        public ICell<int> Size { get; }
        public ICell<int> Probability { get; }
        public ICell<bool> ErrorSize { get; set; }
        public ICell<bool> ErrorProbability { get; set; }

        public int MinimumBoardSize { get; }
        public int MaximumBoardSize { get; }


        public ScreenMenuViewModel(ICell<ScreenViewModel> currentScreen) : base(currentScreen)
        {
            Size = Cell.Create(5);
            Probability = Cell.Create(10);
            Flooding = Cell.Create(true);

            ErrorSize = Cell.Create(false);
            ErrorProbability = Cell.Create(false);

            MinimumBoardSize = IGame.MinimumBoardSize;
            MaximumBoardSize = IGame.MaximumBoardSize;

            SwitchToScreenGame = new ActionCommand(() => CurrentScreen.Value = createScreen());

            SwitchDifficultyEasy = new ActionCommand(() => createGame(5,10,true));
            SwitchDifficultyNormal = new ActionCommand(() => createGame(10, 20, true));
            SwitchDifficultyHard = new ActionCommand(() => createGame(15, 22, true));

        }

        public ICommand SwitchToScreenGame { get; }
        public ICommand SwitchDifficultyEasy { get; }
        public ICommand SwitchDifficultyNormal { get; }
        public ICommand SwitchDifficultyHard { get; }

        public void createGame(int size, int probability, bool flooding)
        {
            Size.Value = size;
            Probability.Value = probability;
            Flooding.Value = flooding;

            CurrentScreen.Value = createScreen();
        }


        public ScreenViewModel createScreen()
        {
            if (Size.Value < MinimumBoardSize || Size.Value > MaximumBoardSize) ErrorSize.Value = true;
            else ErrorSize.Value = false;

            if (Probability.Value <= 0 || Probability.Value > 100) ErrorProbability.Value = true;
            else ErrorProbability.Value = false;

            if (ErrorProbability.Value || ErrorSize.Value) return this.CurrentScreen.Value;

            return new ScreenGameViewModel(this.CurrentScreen, IGame.CreateRandom(Size.Value, (double)Probability.Value / 100, Flooding.Value), this.Size, this.Probability, this.Flooding);
        }


    }
}
  