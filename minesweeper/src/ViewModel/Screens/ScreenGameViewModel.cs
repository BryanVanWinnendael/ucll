using Cells;
using Model.MineSweeper;
using System.Windows.Input;
using System;
using System.Windows;
using System.Timers;
using Timer = System.Timers.Timer;
using System.Diagnostics;
using Model.Data;

namespace ViewModel.Screens
{
    public class ScreenGameViewModel : ScreenViewModel
    {
        public ICell<IGame> Game { get; set; }
        public GameBoardViewModel Board { get; set; }
        public ICell<bool> Flooding { get; }
        public ICell<int> Size { get; }
        public ICell<int> Probability { get; }
        public ICell<int> Time { get; set; }
        public ICell<TimeSpan> TimeSpanValue { get; set; }
        private static Timer timer;
        public ICell<bool> GameWon { get; set; }
        public ICell<bool> GameLos { get; set; }
        private bool closed { get; set; }



        public ScreenGameViewModel(ICell<ScreenViewModel> currentScreen, IGame game, ICell<int> size, ICell<int> probability, ICell<bool> flooding) : base(currentScreen)
        {
            Size = size;
            Probability = probability;
            Flooding = flooding;

            Game = Cell.Create(game);
            Board = new GameBoardViewModel(Game);
            

            GameWon = Cell.Create(false);
            GameLos = Cell.Create(false);


            //Timer
            Time = Cell.Create(0);
            TimeSpanValue = Cell.Create(TimeSpan.FromMilliseconds(0));
            timer = new System.Timers.Timer();
            timer.Interval = 1000;
            timer.Elapsed += OnTimedEvent;
            timer.AutoReset = true;
            timer.Enabled = true;

            closed = false;
            ClosePopUp = new ActionCommand(() => closeScreens());
            SwitchToScreenMenu = new ActionCommand(() => CurrentScreen.Value = new ScreenMenuViewModel(this.CurrentScreen));
            RestartGame = new ActionCommand(() => CurrentScreen.Value = CurrentScreen.Value = new ScreenGameViewModel(this.CurrentScreen, IGame.CreateRandom(Size.Value, (double)Probability.Value/100, Flooding.Value), Size, Probability, Flooding));
        }

        public void closeScreens()
        {
            GameLos.Value = false;
            GameWon.Value = false;
            closed = true;
        }

        public void OnTimedEvent(Object source, System.Timers.ElapsedEventArgs e)
        {
            if (Game.Value.Status != GameStatus.InProgress && !closed) {
                if (Game.Value.Status == GameStatus.Lost) GameLos.Value = true;
                else GameWon.Value = true;
                return; 
            }
            if(Game.Value.Status == GameStatus.InProgress)
            {
                Time.Value += 1;
                TimeSpanValue.Value = TimeSpan.FromSeconds(Time.Value);
            }
        }
      
        public ICommand SwitchToScreenMenu { get; }

        public ICommand RestartGame { get; }

        public ICommand CreateNewGameCommand { get; }

        public ICommand ClosePopUp { get; }
    }
}
