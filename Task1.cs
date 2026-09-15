using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Linq;

namespace PISLab3
{
    internal static class Task1
    {
        public static string Calculate(Dictionary<string, double> idata)
        {
            double V_star = CalculateV(idata);
            double B = CalculateB(idata);
            StringBuilder sb = new StringBuilder();
            sb.Append($"Задание 1\n");
            sb.Append($"Количество входных параметров: {(int)CalculateCountParam(idata)}\n");
            sb.Append($"Потенциальный объем программы, байт: {Math.Round(V_star)}\n");
            sb.Append($"Потенциальное число ошибок в программе:  {Math.Round(B)}\n");
            return sb.ToString();
        }
        public static double CalculateV(Dictionary<string, double> idata)
        {
            double n2_star = CalculateCountParam(idata);
            return (n2_star + 2) * MathFunctions.Log2(n2_star + 2); // $"Потенциальный объем программы, байт: {(n2_star + 2) * MathFunctions.Log2(n2_star + 2)}" ;
        }
        public static double CalculateB(Dictionary<string, double> idata)
        {
            double V = CalculateV(idata);
            double lym = idata["lym"];
            return V * V / (3000 * lym);
        }
        public static double CalculateCountParam(Dictionary<string, double> idata)
        {
            double count_target = idata["count_target"];
            double count_calc_proces = idata["count_calc_proces"];
            double count_target_param = idata["count_target_param"];
            double count_calc_param = idata["count_calc_param"];
            double n1 = count_target * count_calc_proces * count_target_param;
            double n2 = count_target * count_calc_param;
            return n1 + n2;
        }
    }
}
