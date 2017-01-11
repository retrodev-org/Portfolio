/* Name:			CustomerList.cs
 * Student:			James "Walker" Holland
 * Course:			COP2360 C# Programming at Valencia College
 * Instructor:		Professor Jeho Park
 * Team Members:	None
 * Notes:			
 */

using System;
using System.Collections.Generic;

namespace CustomerMaintenance
{
	public class CustomerList
	{
		private List<Customer> customerList;

        public delegate void ChangeHandler(CustomerList customers);
        public event ChangeHandler Changed;

		public CustomerList()
		{
            customerList = new List<Customer>();
		}

		public int GetCount
		{
			get
			{
				return customerList.Count;
			}
		}

		public Customer this[int i]
		{
			get
			{
				return customerList[i];
			}
			set
			{
				customerList[i] = value;
				Changed(this);
			}
		}

		public void Add(Customer customer)
		{
			customerList.Add(customer);
			Changed(this);
		}

		public void Fill()
		{
			customerList = CustomerDB.GetCustomers();
		}

		public void Remove(Customer customer)
		{
			customerList.Remove(customer);
			Changed(this);
		}

		public void Save()
		{
			CustomerDB.SaveCustomers(customerList);
		}

		public static CustomerList operator + (CustomerList c1, Customer c)
		{
			c1.Add(c);
			return c1;
		}

		public static CustomerList operator - (CustomerList c1, Customer c)
		{
			c1.Remove(c);
			return c1;
		}
	} // end class CustomerList
}
