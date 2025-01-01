using Model.MineSweeper;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Data;
using System.Windows.Media;

namespace View.Converters
{

    public class SquareStatusConverter : IValueConverter
    {
        public object Covered { get; set; }
        public object Uncovered { get; set; }
        public object Flagged { get; set; }
        public object Mine { get; set; }
        public object LastMine { get; set; }


        public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
        {
            Square square = (Square) value;
            SquareStatus status = square.Status;

            switch (status)
            {
                case SquareStatus.Covered: return Covered;
                case SquareStatus.Uncovered: return Uncovered;
                case SquareStatus.Flagged: return Flagged;
                case SquareStatus.Mine:
                    if (square.LastUncovered) return LastMine;
                    else return Mine;
                default: return Covered;

            }
        }

        public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
        {
            throw new NotImplementedException();
        }


       

      

    }


   


}
