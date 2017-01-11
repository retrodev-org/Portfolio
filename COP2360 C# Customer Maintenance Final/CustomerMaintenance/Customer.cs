/* Name:			Customer.cs
 * Student:			James "Walker" Holland
 * Course:			COP2360 C# Programming at Valencia College
 * Instructor:		Professor Jeho Park
 * Team Members:	None
 * Notes:			
 */

using System;

namespace CustomerMaintenance
{
    public class Customer
	{
		private string firstName;
		private string lastName;
		private string email;

		public Customer()
		{
		}

		public Customer(string firstName, string lastName, string email)
		{
			this.FirstName = firstName;
			this.LastName = lastName;
			this.Email = email;
		}

		public string Email
		{
			get { return email; }
			set
			{ 
				if (value.Length > 30)
				{
					throw new ArgumentException(
						"Maximum length of the email address is 30 characters.");
				}
				email = value;
			}
		}

		public string FirstName
		{
			get { return firstName; }
			set
			{
                if (value.Length > 30)
                {
                    throw new ArgumentException(
                        "Maximum length of the first name is 30 characters.");
                }
                firstName = value;
			}
		}

		public string LastName
		{
			get { return lastName; }
			set
			{
                if (value.Length > 30)
                {
                    throw new ArgumentException(
                        "Maximum length of the last name is 30 characters.");
                }
                lastName = value;
			}
		}

		public string GetDisplayText()
		{
			return firstName + " " + lastName + ", " + email;
		}
	}
}
