using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Windows.Forms;
using static System.Windows.Forms.VisualStyles.VisualStyleElement.Window;

namespace PISLab3
{
    internal class Program
    {
        static string numericTemplate = @"[\+\-]?\d+[\.\,]?\d*";
        static string listDoubleTemplate = $@"({numericTemplate}\s*\;?\s*)+";
        static string textTemplate = $@"^(?:\([\w\s\,\.]+\))?\s*(?<name>\w+)\s*\=\s*(?<value>{numericTemplate}|{listDoubleTemplate})$";
        static string initial_directory = @"C:\Users\Ник\Desktop\Учеба в урфу\7 семестр\Проект. инф. систем";
        static OpenFileDialog ofd = new OpenFileDialog()
        {
            Filter = "Текстовые файлы (*.txt)|*.txt",
            DefaultExt = "txt",
            FilterIndex = 1,
            InitialDirectory = File.Exists(initial_directory) ? initial_directory : null
        };
        [STAThread]
        static void Main(string[] args)
        {
            Dictionary<string, object> inputData = new Dictionary<string, object>();
            if (ofd.ShowDialog() != DialogResult.OK)
            {
                return;
            }
            var rows = File.ReadAllLines(ofd.FileName);
            var mathces = rows.Select(row => Regex.Match(row, textTemplate)).Where(match => match.Groups.Count >= 3);
            inputData = mathces.ToDictionary(match => match.Groups["name"].Value,
                              match =>
                              {
                                  var val = match.Groups["value"].Value.Trim();
                                  if (Regex.IsMatch(val, $"^{numericTemplate}$"))
                                      return (object)Convert.ToDouble(match.Groups["value"].Value.Replace(',', '.'), CultureInfo.InvariantCulture);
                                  else if (Regex.IsMatch(val, $"^{listDoubleTemplate}$"))
                                      return val.Split(';').Select(x => Convert.ToDouble(x.Replace(',', '.'), CultureInfo.InvariantCulture)).ToList();
                                  else
                                      return null;
                              });
            var double_input_data = inputData.Where(x => x.Value.GetType().IsValueType).ToDictionary(x => x.Key, x => (double)x.Value);
            Console.WriteLine(Task1.Calculate(double_input_data));
            Console.WriteLine(Task2.Calculate(double_input_data));
            Console.WriteLine(Task3.Calculate(inputData));
            Console.ReadLine();
        }
    }
}
