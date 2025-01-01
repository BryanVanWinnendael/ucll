using Cells;
using Model.Data;
using Model.MineSweeper;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Diagnostics;
using System.Linq;
using System.Media;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Windows.Threading;
using ViewModel;
using ViewModel.Screens;

namespace View
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    /// 

    public partial class MainWindow : Window
    {
        MediaPlayer mp;

        public String MusicImage { get; set; }

        private MainViewModel mainViewModel;

        public MainWindow()
        {
            InitializeComponent();
            mainViewModel = new MainViewModel();


            try
            {
                mp = new MediaPlayer();
                mp.MediaOpened += Mp_MediaOpened;
                mp.MediaFailed += Mp_MediaFailed;
                mp.MediaEnded += Mp_MediaLoop;
                mp.Open(new Uri("./Sounds/music.wav", UriKind.Relative));
                mp.Volume = 1;

            }
            catch (Exception ex)
            {
                Console.WriteLine($"{ex.GetType()}: {ex.Message}\n{ex.StackTrace}");
            }

            this.DataContext = mainViewModel;
        }


        public void sound_mute(object sender, MouseEventArgs e)
        {
           
            if (mp.Volume == 1) 
            {
                ((Image)sender).Source = new BitmapImage(new Uri("./Images/musicOff.png", UriKind.Relative));
                mp.Volume = 0;
            }
            else
            {
                ((Image)sender).Source = new BitmapImage(new Uri("./Images/music.png", UriKind.Relative));
                mp.Volume = 1;
            }

        }

        private void Mp_MediaLoop(object sender, EventArgs e)
        {
            mp.Position = TimeSpan.Zero;
            mp.Play();

        }

        private void Mp_MediaFailed(object sender, ExceptionEventArgs e)
        {
            var ex = e.ErrorException;
            Console.WriteLine($"MEDIA FAILED: {ex.GetType()}: {ex.Message}\n{ex.StackTrace}");
        }

        private void Mp_MediaOpened(object sender, EventArgs e)
        {
            mp.Play();
            Console.WriteLine("Play called");
        }

    }
}
