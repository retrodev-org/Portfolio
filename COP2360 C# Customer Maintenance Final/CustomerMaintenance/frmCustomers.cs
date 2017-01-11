/* Name:			frmCustomers.cs
 * Student:			James "Walker" Holland
 * Course:			COP2360 C# Programming at Valencia College
 * Instructor:		Professor Jeho Park
 * Team Members:	None
 * Notes:			
 */

using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace CustomerMaintenance
{
    public partial class frmCustomers : Form
    {
        public frmCustomers()
        {
            InitializeComponent();
        }

        private CustomerList customerList = new CustomerList();

        private void frmCustomers_Load(object sender, EventArgs e)
        {
            customerList.Changed += new CustomerList.ChangeHandler(HandleChanges);
            customerList.Fill();
            FillCustomerListBox();
        }

        private void FillCustomerListBox()
        {
            lstCustomers.Items.Clear();
            for (int i = 0; i < customerList.GetCount; i++)
            {
                Customer c = customerList[i];
                lstCustomers.Items.Add(c.GetDisplayText());
            }
        }

        private void btnAdd_Click(object sender, EventArgs e)
        {
            frmAddCustomer addCustomerForm = new frmAddCustomer();
            Customer customer = addCustomerForm.GetNewCustomer();
            if (customer != null)
            {
                customerList += customer;
            }
        }

        private void btnDelete_Click(object sender, EventArgs e)
        {
            int i = lstCustomers.SelectedIndex;
            if (i != -1)
            {
                Customer thisCustomer = (Customer)customerList[i];
                string message = "Are you sure you want to delete "
                    + thisCustomer.FirstName + " " + thisCustomer.LastName + "?";
                DialogResult button = MessageBox.Show(message, "Confirm Deletion",
                    MessageBoxButtons.YesNo);
                if (button == DialogResult.Yes)
                {
                    customerList -= thisCustomer;
                }
            }
        }

        private void HandleChanges(CustomerList list)
        {
            customerList.Save();
            FillCustomerListBox();
        }

        private void btnExit_Click(object sender, EventArgs e)
        {
            this.Close();
        }
    }
}