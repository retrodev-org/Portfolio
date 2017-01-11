using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace COP2360_week4forms
{
    public partial class Exceptions : Form
    {
        double firstNumber = 0, secondNumber = 0, result = 0;

        public Exceptions()
        {
            InitializeComponent();
        }

        private bool ReadUserInput()
        {
            if (txtFirstNumber.Text.Length > 0 && txtSecondNumber.Text.Length > 0)
            {
                try
                {
                    firstNumber = Convert.ToDouble(txtFirstNumber.Text);
                    secondNumber = Convert.ToDouble(txtSecondNumber.Text);
                    return true;
                }
                catch
                {
                    MessageBox.Show("Please confirm that your dividend and divisor are numbers.");
                    return false;
                }   
            }
            else
            {
                MessageBox.Show("Please enter a dividend and divisor.");
                return false;
            }
        }

        private void DisplayResult()
        {
            lblResult.Text = result.ToString();
        }

        private void btnAdd_Click(object sender, EventArgs e)
        {
            bool  valid = ReadUserInput();
            try
            {
                result = firstNumber + secondNumber;
                DisplayResult();
            }
            catch
            {
                MessageBox.Show("Please confirm that your dividend and divisor are numbers.");
            }
        }

        private void btnSubtract_Click(object sender, EventArgs e)
        {
            bool valid = ReadUserInput();
            if (valid)
            {
                result = firstNumber - secondNumber;
                DisplayResult();
            }
        }

        private void btnMultiply_Click(object sender, EventArgs e)
        {
            bool valid = ReadUserInput();
            if (valid)
            {
                result = firstNumber * secondNumber;
                DisplayResult();
            }
        }

        private void btnDivide_Click(object sender, EventArgs e)
        {
            bool valid = ReadUserInput();
            if (valid)
            {
                if (secondNumber > 0)
                {
                    result = firstNumber / secondNumber;
                    DisplayResult();
                }
                else
                {
                    MessageBox.Show("Cannot divide by zero.");
                }
            }
        }

        private void btnModulus_Click(object sender, EventArgs e)
        {
            bool valid = ReadUserInput();
            if (valid)
            {
                if (secondNumber > 0)
                {
                    try
                    {
                        CheckMyException(); // throws custom exception if divisor > 999
                        result = firstNumber % secondNumber;
                        DisplayResult();
                    }
                    catch (MyExceptions ex) // catch custom exception
                    {
                        MessageBox.Show(ex.Message);
                    }
                    catch
                    {
                        MessageBox.Show("Please check your input.");
                    }
                }
                else
                {
                    MessageBox.Show("Cannot divide by zero.");
                }
            }            
        }

        private void btnExit_Click(object sender, EventArgs e)
        {
            MessageBox.Show("Thanks for using this product. Have a great day.");
            this.Close();
        }

        private void CheckMyException()
        {
            // Throw custom exception based on if statement
            if (txtSecondNumber.Text.Length > 3)
            {
                throw new MyExceptions("Divisor should be less than 1,000.");
            }


        }
    } // end class Form1 : Form

    class MyExceptions : Exception
    {
        public MyExceptions(string errorMessage): base(errorMessage)
        {
            
        }
    }
}
