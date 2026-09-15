using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PISLab3
{
    internal class Task2
    {
        public static string Calculate(Dictionary<string, double> idata)
        {
            double k = Math.Round( CalculateK(idata));
            double i = Math.Round(CalculateI(idata));
            double N = Math.Round(CalculateN(idata));
            double V = Math.Round(CalculateV(idata));
            double P = Math.Round(CalculateP(idata));
            double Tk = Math.Round(CalculateTk(idata));
            double B = Math.Round(CalculateB(idata));
            double tn = Math.Round(Calculate_tn(idata));
            StringBuilder sb = new StringBuilder();
            sb.Append($"Задание 2\n");
            sb.Append($"Количество входных параметров: {(int)Task1.CalculateCountParam(idata)}\n");
            sb.Append($"Число модулей программного средства:: {k}\n");
            sb.Append($"Число уровней: {i}\n");
            sb.Append($"Длина программы: {N}\n");
            sb.Append($"Объем ПС, байт: {V}\n");
            sb.Append($"Кол-во команд ассемблера: {P}\n");
            sb.Append($"Календарное время программирования (для 5х программистов при 8ми часовом рабочем дне), дни: {Tk}\n");
            sb.Append($"Потенциальное количество ошибок (до отладки): {B}\n");
            sb.Append($"Начальная надежность ПО (время наработки на отказ), часы: {tn}\n");
            return sb.ToString();
        }
        public static double CalculateK(Dictionary<string, double> idata)
        {
            double i = CalculateI(idata);
            double k = 0;
            double n2_star = Task1.CalculateCountParam(idata);
            for (int j = 0; j < (int)i; j++)
            {
                k += n2_star / Math.Pow(8, j + 1);
                k = Math.Round(k);
            }
            return k;
        }
        public static double CalculateI(Dictionary<string, double> idata)
        {
            double n2_star = Task1.CalculateCountParam(idata);
            double k = Math.Round(n2_star / 8);
            if (k > 80)
            {
                double i = MathFunctions.Log2(n2_star) / 3 + 1;
                return Math.Round(i);
            }
            else
                return 1;

        }
        public static double CalculateN(Dictionary<string, double> idata)
        {
            double k = CalculateK(idata);
            return 220 * k + k * MathFunctions.Log2(k);
        }
        public static double CalculateV(Dictionary<string, double> idata)
        {
            double k = CalculateK(idata);
            return 220 * k * MathFunctions.Log2(48);
        }
        public static double CalculateP(Dictionary<string, double> idata)
        {
            double N = CalculateN(idata);
            return 3 * N / 8;
        }
        public static double CalculateTk(Dictionary<string, double> idata)
        {
            double N = CalculateN(idata);
            // m - 5 (Количество участников команды)
            // mu = 20 (число отладенных программ)
            return 3 * N / (8 * 20 * 5); // расчет в днях
        }
        public static double CalculateB(Dictionary<string, double> idata)
        {
            double V = CalculateV(idata);
            return V / 3000;
        }
        public static double Calculate_tn(Dictionary<string, double> idata)
        {
            double Tk = CalculateTk(idata);
            double B = CalculateB(idata); 
            return (Tk * 8) / (2 * Math.Log(B)); // расчет в часах
        }
    }
}
