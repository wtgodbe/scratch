using System;
using System.Diagnostics;

namespace ConsoleApp4
{
    class Program
    {
        static void Main(string[] args)
        {
            ProcessStartInfo psi = new ProcessStartInfo();
            psi.UseShellExecute = true;
            psi.FileName = "pwsh";
            psi.Arguments = "-c 1+1;read-host";
            System.Diagnostics.Process.Start(psi);
        }
    }
}
