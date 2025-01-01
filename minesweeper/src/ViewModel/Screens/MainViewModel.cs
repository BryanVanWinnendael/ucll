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
    public class MainViewModel
    {

        public MainViewModel()
        {
            // Create empty cell
            CurrentScreen = Cell.Create<ScreenViewModel>(null);

            // Create screen A
            var firstScreen = new ScreenMenuViewModel(CurrentScreen);

            // Put first screen in CurrentScreen cell
            CurrentScreen.Value = firstScreen;
        }
        public ICell<ScreenViewModel> CurrentScreen { get; }

    }

    public abstract class ScreenViewModel
    {
        /// <summary>
        /// Constructor.
        /// </summary>
        /// <param name="currentScreen">
        /// Cell containing the current screen
        /// </param>
        protected ScreenViewModel(ICell<ScreenViewModel> currentScreen)
        {
            this.CurrentScreen = currentScreen;
        }

        /// <summary>
        /// Cell containing the current screen.
        /// Overwrite the contents of this cell to switch screens.
        /// </summary>
        protected ICell<ScreenViewModel> CurrentScreen { get; }

    }

    public class ActionCommand : ICommand
    {
        private readonly Action action;

        public ActionCommand(Action action)
        {
            this.action = action;
        }

        public event EventHandler CanExecuteChanged { add { } remove { } }

        public bool CanExecute(object parameter)
        {
            return true;
        }

        public void Execute(object parameter)
        {
            this.action();
        }
    }
}
