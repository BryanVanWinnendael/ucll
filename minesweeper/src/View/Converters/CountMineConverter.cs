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

    public class CountMineConverter : IValueConverter
    {
        public object blue { get; set; }
        public object green { get; set; }
        public object red { get; set; }
        public object darkBlue { get; set; }
        public object darkRed { get; set; }
        public object cyan { get; set; }
        public object black { get; set; }
        public object gray { get; set; }


        public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
        {
            int count = (int)value;
            return count switch
            {
                1 => blue,
                2 => green,
                3 => red,
                4 => darkBlue,
                5 => darkRed,
                6 => cyan,
                7 => black,
                8 => gray,
                _ => blue,
            };
        }

        public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
        {
            throw new NotImplementedException();
        }






    }


}
