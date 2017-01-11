/* File Name:		COP2224_Cpp_Ch4_ProgEx15.cpp
 * Name:			James “Walker” Holland
 * Course:			COP 2224 at Valencia College
 * Instructor:		Prof. Jenny Hu
 * Description:		Prompt user for cellular service type (regular or premium) and
 *					number of minutes used. For premium service, request number
 *					of daytime and evening minutes separately. Calculate and output 
 *					the resulting bill.
 * Team Members:	None.
 * Notes:			2015/01/30	Project start
 */

#include <iostream>
#include <iomanip>

using namespace std;

// Constants
double const MIN_PREMIUM_DAY		= 0.10;
double const MIN_PREMIUM_NIGHT		= 0.05;
double const MIN_REGULAR			= 0.20;
double const MONTHLY_REGULAR		= 10.00;
double const MONTHLY_PREMIUM		= 25.00;
int const THRESHOLD_PREMIUM_DAY		= 75;
int const THRESHOLD_PREMIUM_NIGHT	= 100;
int const THRESHOLD_REGULAR			= 50;

/* Name:		Repeat()
 * Input:		none
 * Process:		Prompt user to enter Y/y/N/n to repeat statement generation.
 * Output:		char 'R' to end or char 'A' to repeat.
 */
char Repeat()
{
	char repeat;
	cout << "Would you like to generate another statement? (Y/N) ";
	cin >> repeat;
	if (repeat == 'n' || repeat == 'N')
	{
		repeat = 'R';
	} else {
		repeat = 'A';
	}
	cin.sync();
	cout << "\n\n" << endl;
	return repeat;
}

int main()
{
	// Declarations
	int minutesRegular, minutesPremiumDay, minutesPremiumNight;
	double total;
	char serviceType = 'A';

	// Welcome message
	cout << "Welcome to the Cellular Bill Generator\n\n" << endl;

	// Validate service type loop
	while (!(serviceType == 'r' || serviceType == 'R' || serviceType == 'p' || serviceType == 'P'))
	{
		// Reset between statements
		minutesRegular = 0, minutesPremiumDay = 0, minutesPremiumNight = 0;
		total = 0.0;

		// Input service type
		cout << "Please enter the service type: R - Regular\n"
			 << "                               P - Premium\n"
			 << "Service Type: ";
		cin >> serviceType;
		cin.sync();

		switch (serviceType)
		{
		case 'p': case 'P':
			cout << "\n\nPremium Service: " << endl;

			// Input Premium Day/Night Minutes
			cout << "\nPlease enter the number of daytime minutes used: ";
			cin >> minutesPremiumDay;
			cin.sync();

			cout << "\nPlease enter the number of nighttime minutes used: ";
			cin >> minutesPremiumNight;
			cin.sync();

			// Premium Calculations
			total += MONTHLY_PREMIUM;
			
			if (minutesPremiumDay > THRESHOLD_PREMIUM_DAY)
				total += ((minutesPremiumDay - THRESHOLD_PREMIUM_DAY) * MIN_PREMIUM_DAY);
			if (minutesPremiumNight > THRESHOLD_PREMIUM_NIGHT)
				total += ((minutesPremiumNight - THRESHOLD_PREMIUM_NIGHT) * MIN_PREMIUM_NIGHT);

			// Premium Output Bill
			cout << fixed << setprecision(2)
				 << "Customer Statement"
				 << "\n--------------------------------------------------------------"
				 << "\nMonthly Charge:            " << MONTHLY_PREMIUM
				 << "\nDay Minutes Used:          " << minutesPremiumDay;

			if (minutesPremiumDay > THRESHOLD_PREMIUM_DAY)
				cout << "\nDaytime Minute Charge:     " << (minutesPremiumDay - THRESHOLD_PREMIUM_DAY) * MIN_PREMIUM_DAY;

			cout << "\nNight Minutes Used:        " << minutesPremiumNight;
			if (minutesPremiumNight > THRESHOLD_PREMIUM_NIGHT)
				cout << "\nNighttime Minute Charge:   " << (minutesPremiumNight - THRESHOLD_PREMIUM_NIGHT) * MIN_PREMIUM_NIGHT;

			cout << "\nTotal Charges:             " << total << "\n\n" << endl;

			serviceType = Repeat();
			break; // end case premium
		case 'r': case 'R':
			cout << "\n\nRegular Service: \n" << endl;
			
			// Input Regular Minutes
			cout << "\nPlease enter the number of minutes used: ";
			cin >> minutesRegular;
			cin.sync();

			// Regular Calculations
			total += MONTHLY_REGULAR;
			
			if (minutesRegular > THRESHOLD_REGULAR)
				total += ((minutesRegular - THRESHOLD_REGULAR) * MIN_REGULAR);

			// Regular Output Bill
			cout << fixed << setprecision(2)
				 << "Customer Statement"
				 << "\n--------------------------------------------------------------"
				 << "\nMonthly Charge:            " << MONTHLY_REGULAR
				 << "\nDay Minutes Used:          " << minutesRegular;

			if (minutesRegular > THRESHOLD_REGULAR)
				cout << "\nMinute Charge:             " << (minutesRegular - THRESHOLD_REGULAR) * MIN_REGULAR;

			cout << "\nTotal Charges:             " << total << "\n\n" << endl;

			serviceType = Repeat();
			break; // end case regular
		default:
			cout << "Invalid service type. Please enter P or R to signify Premium or Regular.\n\n" << endl;
			break;
		} // end switch (serviceType)
		
		// Clear input in case of invalid entries
		cin.sync();
	} // end while loop validate service type

	system("pause");

	return 0;
}

