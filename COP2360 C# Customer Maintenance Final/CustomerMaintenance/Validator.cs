/* Name:			Validator.cs
 * Student:			James "Walker" Holland
 * Course:			COP2360 C# Programming at Valencia College
 * Instructor:		Professor Jeho Park
 * Team Members:	None
 * Notes:			
 */

using System;
using System.Windows.Forms;

namespace CustomerMaintenance
{
    public static class Validator
	{

		private static string title = "Entry Error";

		public static string Title
		{
			get { return title; }
			set { title = value; }
		}

		public static bool IsDecimal(TextBox textBox)
		{
			try
			{
				Convert.ToDecimal(textBox.Text);
				return true;
			}
			catch (FormatException)
			{
				MessageBox.Show(textBox.Tag + " must be a decimal number.", Title);
				textBox.Focus();
				return false;
			}
		} // end IsDecimal()

		public static bool IsInt32(TextBox textBox)
		{
			try
			{
				Convert.ToInt32(textBox.Text);
				return true;
			}
			catch (FormatException)
			{
				MessageBox.Show(textBox.Tag + " must be an integer.", Title);
				textBox.Focus();
				return false;
			}
		} // end IsInt32()

		public static bool IsPresent(TextBox textBox)
		{
			if (textBox.Text == "")
			{
				MessageBox.Show(textBox.Tag + " is a required field.", Title);
				textBox.Focus();
				return false;
			}
			return true;
		} // end IsPresent()

		public static bool IsValidEmail(TextBox textBox)
		{
			if (textBox.Text.IndexOf("@") == -1 ||
				 textBox.Text.IndexOf(".") == -1)
			{
				MessageBox.Show(textBox.Tag + " must be a valid email address.",
					Title);
				textBox.Focus();
				return false;
			}
			else
			{
				return true;
			}
		} // end IsValidEmail()

		public static bool IsWithinRange(TextBox textBox, decimal min, decimal max)
		{
			decimal number = Convert.ToDecimal(textBox.Text);
			if (number < min || number > max)
			{
				MessageBox.Show(textBox.Tag + " must be between " + min
					+ " and " + max + ".", Title);
				textBox.Focus();
				return false;
			}
			return true;
		} // end IsWithinRange()
    }
}
