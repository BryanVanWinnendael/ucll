using Model.MineSweeper;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Data;

namespace View.Converters
{

    public class GameStatusConverter : IValueConverter
    {
        public object Won { get; set; }
        public object Lost { get; set; }
        public object InProgress { get; set; }

        public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
        {
            GameStatus status = (GameStatus)value;
            return status switch
            {
                GameStatus.Won => Won,
                GameStatus.Lost => Lost,
                GameStatus.InProgress => InProgress,
                _ => InProgress,
            };
        }

        public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
        {
            throw new NotImplementedException();
        }






    }


}
