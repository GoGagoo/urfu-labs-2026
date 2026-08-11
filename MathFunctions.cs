using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PISLab3
{
    internal static class MathFunctions
    {
        public static double Log2(double n)
        {
            return Math.Log10(n) / Math.Log10(2);
        }
    }
}
