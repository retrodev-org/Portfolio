/* File:		Holland_COP2224_Ch9_ProgEx4.cpp
 * Student:		James Walker Holland II
 * Course:		COP2224 at Valencia College
 * Instructor:	Prof. Jenny Hu
 * Team:		None
 * Description: Program automates local restaurant's breakfast billing by displaying a
 *				menu of available items and allowing the customer to select the item(s)
 *				desired from the menu.
 * Notes:		2015/03/19  Notes begin.
 */

#include <iostream>
#include <iomanip>
#include <string>

using namespace std;

const int MENU_LENGTH = 8;
const int MAX_ITEMS_PER_CHECK = 20;
const int MAX_ORDERS = 10;
const double TAX_RATE = 0.07;

struct menuItemType
{
	string menuItem;
	double menuPrice;
};

struct order
{
	int itemList[MAX_ITEMS_PER_CHECK];
	int itemCount;
	double total;
};

void getData(menuItemType menu[]);
void showMenu(menuItemType menu[]);
void printCheck(order thisOrder, menuItemType menu[]);
bool repeatProcess();
void takeOrder(menuItemType menu[], order thisOrder);

int main()
{
	bool repeat = true;
	while (repeat)
	{
		menuItemType menu[MENU_LENGTH];
		order thisOrder;
	
		// Set zero item count
		thisOrder.itemCount = 0;
		thisOrder.total = 0.0;

		// Display welcome message
		cout << "Welcome to Local Restaurant #1\n"
			 << endl;

		// Load menu into memory
		getData(menu);

		// Display menu for customer
		showMenu(menu);

		// Take order
		takeOrder(menu, thisOrder);

		repeat = repeatProcess();
	}

	return 0;
}

void getData(menuItemType menu[])
{
	menu[0].menuItem = "Plain Egg";
	menu[0].menuPrice = 1.45;
	menu[1].menuItem = "Bacon and Egg";
	menu[1].menuPrice = 2.45;
	menu[2].menuItem = "Muffin";
	menu[2].menuPrice = 0.99;
	menu[3].menuItem = "French Toast";
	menu[3].menuPrice = 1.99;
	menu[4].menuItem = "Fruit Basket";
	menu[4].menuPrice = 2.49;
	menu[5].menuItem = "Cereal";
	menu[5].menuPrice = 0.69;
	menu[6].menuItem = "Coffee";
	menu[6].menuPrice = 0.50;
	menu[7].menuItem = "Tea";
	menu[7].menuPrice = 0.75;
}

void printCheck(order thisOrder, menuItemType menu[])
{
	cout << "\n\nYour check: \n";
	for (int i = 0; i < thisOrder.itemCount; i++)
	{
		cout << setw(2) << i + 1 << ":  "
			<< menu[thisOrder.itemList[i]].menuItem << endl;
	}

	cout << "\nSubtotal:    $" << fixed << setprecision(2)
		 << setw(6) << thisOrder.total
		 << "\nTax:         $" << setw(6) 
		 << thisOrder.total * TAX_RATE
		 << "\nGrand Total: $" << setw(6) 
		 << thisOrder.total + (thisOrder.total * TAX_RATE)
		 << "\n\nThank you for your business.\n\n" << endl;
}

bool repeatProcess()
{
	char input;
	cout << "\n\nWould you like to repeat this process? "
		 << "(y/n) ";
	cin >> input;

	switch(input)
	{
	case 'y':
	case 'Y':
		return true;
	default:
		return false;
	}
}

void showMenu(menuItemType menu[])
{
	cout << "Menu: \n";
	for (int i = 0; i < MENU_LENGTH; i++)
	{
		cout << setw(2) << i + 1 << ": "
			 << menu[i].menuItem;
		if (menu[i].menuItem.length() < 12)
			cout << "\t";
		if (menu[i].menuItem.length() < 6)
			cout << "\t";
		cout << "\t" << fixed << setprecision(2)
			 << "$" << menu[i].menuPrice << endl;
	}
}

void takeOrder(menuItemType menu[], order thisOrder)
{
	string buffer = "";
	int input = 0;

	while (input != 99 && thisOrder.itemCount <= MAX_ITEMS_PER_CHECK)
	{
		if (thisOrder.itemCount < MAX_ITEMS_PER_CHECK)
		{
			cout << "Enter your desired item number below, "
				 << "or enter 99 to complete this order.\n"
				 << "Your entry: ";
			cin >> buffer;

			try 
			{
				input = stoi(buffer);

				if (input <= 0 || (input > MENU_LENGTH && input != 99))
				{
					cout << "\nError: Please enter a number between "
						 << "1 and " << MENU_LENGTH << " or enter 99"
						 << "to exit and complete this order.\n" << endl;
					showMenu(menu);
				}
				else if (input > 0 && input <= MENU_LENGTH)
				{			
					// Valid items
					thisOrder.itemList[thisOrder.itemCount] = input - 1;
					thisOrder.itemCount += 1;
					thisOrder.total += menu[input - 1].menuPrice;

					cout << "\n\n\nAdded: " << menu[input - 1].menuItem
						 << "\n\nWould you like to add something else?" << endl;
					showMenu(menu);
				}
			}
			catch(exception e)
			{
				cout << "\n\n\nInvalid input. Please enter a number "
					 << "between 1 and " << MENU_LENGTH << ".\n"
					 << endl;
				showMenu(menu);
			}
		}
		else // Too many items
		{
			cout << "\n\nOur automated ordering system can "
				 << "only accept orders up to " << MAX_ITEMS_PER_CHECK
				 << " items.\n\nYour server would be happy to help you "
				 << "with additional items. We apologize for "
				 << "any inconvenience." << endl;
			input = 99;
		}
	}

	printCheck(thisOrder, menu);
}