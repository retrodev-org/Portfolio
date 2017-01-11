/* Name:			Program.cs
 * Student:			James "Walker" Holland
 * Course:			COP2360 C# Programming at Valencia College
 * Instructor:		Professor Jeho Park
 * Team Members:	None
 * Notes:			
 */

using System;
using System.Collections.Generic;
using System.Linq;
using System.Windows.Forms;

namespace CustomerMaintenance
{
    static class Program
    {
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new frmCustomers());
        }
    }
}