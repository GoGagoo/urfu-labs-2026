using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PISLab3
{
    internal static class Task3
    {
        public static string Calculate(Dictionary<string, object> idata)
        {

            StringBuilder sb = new StringBuilder();
            sb.Append($"Задание 3\n");
            for (int i = 0; i < 3; i++)
            {
                sb.Append($"Вариант расчета {i + 1}: {GetDescriptionMod(i)}\n");
                sb.Append($"Текущий рейтинг: {Math.Round(CalcR(idata, i))}\n");
                sb.Append($"Ожидаемое число ошибок: {Math.Round(CalcB(idata, i))}\n");

            }
            //sb.Append($"Вариант расчет: {(int)CalculateCountParam(idata)}\n");
            //sb.Append($"Потенциальный объем программы, байт: {V_star}\n");
            //sb.Append($"Потенциальное число ошибок в программе: {B}\n");
            CalcR(idata, 0);
            return sb.ToString();
        }
        public static double CalcR(Dictionary<string, object> idata, int mod)
        {
            List<double> R = new List<double>();
            R.Add((double)idata["R0"]);
            List<double> V = idata["V"] as List<double>;
            List<double> B = idata["B"] as List<double>;

            for (int i = 0; i < V.Count; i++)
            {
                double left = 0;
                for (int j = 0; j < i + 1; j++)
                {
                    left += B[j] / CalcC(idata, R[j], mod);
                }
                var R_next = R[i] * (1 + 0.001 *
                    (V.Take(i + 1).Sum() - left));

                R.Add(R_next);
            }
            return R.Last();
        }
        public static double CalcC(Dictionary<string, object> idata, double R, int mod)
        {
            double lym = (double)idata["lym"];
            switch (mod)
            {
                case 0:
                    return 1 / (R + lym);
                case 1:
                    return 1 / (R * lym);
                case 2:
                    return 1 / R + 1 / lym;
                default:
                    throw new NotImplementedException();
            }
        }
        public static double CalcB(Dictionary<string, object> idata, int mod)
        {
            var R = CalcR(idata, mod);
            var V1 = (double)idata["V1"];
            return CalcC(idata, R, mod) * V1;
        }
        private static string GetDescriptionMod(int mod)
        {
            switch (mod)
            {
                case 0:
                    return "1 / (R + lym)";
                case 1:
                    return "1 / (R * lym)";
                case 2:
                    return "1 / R + 1 / lym";
                default:
                    throw new NotImplementedException();
            }
        }

    }
}
