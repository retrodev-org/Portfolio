/* File Name:		Lottery.java
 * Name:			James Walker Holland
 * Course:			COP 2800 at Valencia College
 * Instructor:		Mahendra Gossai
 * Description:		Create 6 Powerball tickets. Generate random numbers for tickets
 * 					one through three. Use predetermined values for tickets four through six.
 * 					For chosen ticket, determine applicable state and federal taxes, display 
 * 					lump sum payment amount or 30 year payout amount per user choice.
 * Team Members:	None.
 * Notes:			2014/04/19	Project start
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Lottery{
	final static int powerballNumber = 9;
	final static int[] winningNumbers = new int[]{12, 13, 6, 8, 25};
	final static double jackpotAmount = 131000000.00;
	final static double lumpPayoutAmount = 81400000.00;
	final static double federalTaxRate = 0.25;
	static final String PATH = "C:\\COP2800\\lotteryNumbers.txt";

	/*
	 * Name:	main()
	 * Input:	none
	 * Process:	Create 2d array of lottery numbers. Generate random numbers for tickets
	 * 			one through three. Use predetermined values for tickets four through six.
	 * 			Sort all but powerball number to display smallest to largest. Store numbers 
	 * 			in file. Gather user input for which ticket to use and calculate winnings.
	 * 			Gather user input for lump sum or 30 year payout option. Gather user input 
	 * 			for state to determine tax rate. Display total winnings, federal and state 
	 * 			taxes paid, and net	amount for chosen option.
	 * Output:	none
	 */
	public static void main(String[] args){
		int[][] lotteryNumbers = new int[][]{
			{0, 0, 0, 0, 0, 0}, // first 3 will be randomized
			{0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0},
			{12, 13, 6, 8, 44, 9}, // #5 wrong
			{12, 13, 6, 8, 25, 35}, // Powerball wrong
			{12, 13, 6, 8, 25, 9} // all correct
		};
		int powerball = 0, winners = 0, menuChoice, lumpOrPayout, ticketNumber;
		double stateTaxRate, amountWon = 0;
	
		// Generate three random tickets
		for (int i = 0; i < 3; i++){
			GenerateRandomTicket(lotteryNumbers[i]);
		}
		
		// Selectively sort arrays
		for (int i = 0; i < 6; i++){
			// sort 5 numbers, beginning at index 0 (omit powerball)
			Arrays.sort(lotteryNumbers[i], 0, 5);	
		}
		
		// Write tickets to file
		try {
			WriteToFile(lotteryNumbers);
		} catch(FileNotFoundException notFound) {
			System.out.println("\n\nError: Unable to find or create the file.\nPlease verify directory and filename.\n\n");
		}
		
		// Display all tickets
		System.out.println("\nHere are your ticket numbers:");
		for (int i = 0; i < 6; i++){
			System.out.printf("%2d: ", i + 1);
			DisplayTicket(lotteryNumbers[i]);
			CheckNumbers(lotteryNumbers[i]);
			CheckPowerball(lotteryNumbers[i]);
			System.out.println();
		}
		System.out.println("\nWhich ticket would you like to check?");
		System.out.printf("Please enter 1 - 6: ");
		menuChoice = 0;
		while (menuChoice < 1 || menuChoice > 6){
			try {
				menuChoice = GatherOption(1,6);
			} catch (NumberFormatException e) {
				menuChoice = 0;
			}
		}
		ticketNumber = menuChoice - 1;
		
		System.out.println("\n\nHere are your ticket numbers:");
		DisplayTicket(lotteryNumbers[ticketNumber]);
		winners = CheckNumbers(lotteryNumbers[ticketNumber]);
		powerball = CheckPowerball(lotteryNumbers[ticketNumber]);
		amountWon = DetermineAmountWon(winners, powerball);
		
		if (winners < 3 && powerball == 0)
			System.out.println("\n\nBetter luck next time! You need to match at least three numbers or the Powerball to win a prize.");
		else {
			System.out.printf("\n\nCongratulations! You have won $%,.2f.", amountWon);
			
			if (amountWon == jackpotAmount){
				System.out.println("\nWould you like a lump sum or a 30 year annuity?");
				System.out.println("1. Lump sum");
				System.out.println("2. 30 year payout");
				System.out.printf("Please enter your choice: ");
				menuChoice = 0;
				while (menuChoice == 0){
					try {
						menuChoice = GatherOption(1,2);
					} catch (NumberFormatException e){
						menuChoice = 0;
					}
				}
				lumpOrPayout = menuChoice;
			} else if (amountWon > 200){
				System.out.println("\nAll non-jackpot prizes are set payouts.");
				lumpOrPayout = 2;
			} else { // under $200 can be cashed in store
				System.out.println("\nYour ticket can be redeemed at any authorized Powerball dealer.");
				lumpOrPayout = 1;
			}
			
			
			DisplayStateMenu();
			menuChoice = 0;
			while (menuChoice == 0){
				try {
					menuChoice = GatherOption(1,52);
				} catch (NumberFormatException e){
					menuChoice = 0;
				}
			}
			stateTaxRate = SetStateTaxRate(menuChoice);
			
			// display winnings
			if (lumpOrPayout == 1){
				amountWon = lumpPayoutAmount;
				PrintLumpSum(stateTaxRate, amountWon);
			} else
				Print30YearPayout(stateTaxRate, amountWon);
		} // end if/else no prize
		
	}
	
	/*
	 * Name:	CheckNumbers()
	 * Input:	numbers[]: array of numbers on a single lottery ticket
	 * Process:	Cycle through each number on ticket and check against each of the 
	 * 			winning lottery numbers, omitting the Powerball number. Display 
	 * 			message with total of winning numbers.
	 * Output:	int winningNumbers: number of winning tickets
	 */
	public static int CheckNumbers(int[] numbers){
		int correct = 0;
		for (int i = 0; i < 5; i++){ // check each number on ticket
			for (int j = 0; j < 5; j++){ // check against all winning numbers
				if (numbers[i] == winningNumbers[j]){
					correct++;
				}
			}
		}
		// print count of winning numbers
		if (correct > 0)
			System.out.printf(" Correct: %d", correct);
		return correct;
	}
	
	/*
	 * Name:	CheckPowerball()
	 * Input:	int numbers[]: array of numbers on a single lottery ticket
	 * Process:	Check Powerball number (index 5 of array) and display message if
	 * 			Powerball matches.
	 * Output:	int powerballMatch: 1 if match, 0 if not
	 */
	public static int CheckPowerball(int[] numbers){
		int powerballWinner = 0;
		if (numbers[5] == powerballNumber){
			powerballWinner++;
			System.out.printf(" Powerball match!");
		}
		return powerballWinner;
	}
	
	/*
	 * Name:	DetermineAmountWon()
	 * Input:	none
	 * Process:	Display menu of states by abbreviation
	 * Output:	none
	 */
	public static double DetermineAmountWon(int winners, int powerball){
		double amountWon = 0.0;
		if (winners > 0 || powerball > 0){
			if (winners == 5){
				if (powerball == 1)
					amountWon = jackpotAmount;
				else
					amountWon = 1000000.0;
			} else if (winners == 4){
				if (powerball == 1)
					amountWon = 10000.0;
				else
					amountWon = 100.0;
			} else if (winners == 3){
				if (powerball == 1)
					amountWon = 100.0;
				else
					amountWon = 7.0;
			} else if (powerball == 1){ // under 3 winning #s, only powerball gets prize
				if (winners == 2)
					amountWon = 7.0;
				else
					amountWon = 4.0;
			}
		} // end if no winning numbers
		return amountWon;
	}
	
	/*
	 * Name:	DisplayStateMenu()
	 * Input:	none
	 * Process:	Display menu of states by abbreviation
	 * Output:	none
	 */
	public static void DisplayStateMenu(){
		System.out.println("In which state did you purchase this ticket?");
		System.out.println();
		System.out.printf("1.  AL \t\t");
		System.out.printf("21. ME \t\t");
		System.out.printf("41. SD \t\t");
		System.out.println();
		
		System.out.printf("2.  AK \t\t");
		System.out.printf("22. MI \t\t");
		System.out.printf("42. TN \t\t");
		System.out.println();
		
		System.out.printf("3.  AR \t\t");
		System.out.printf("23. MN \t\t");
		System.out.printf("43. TX \t\t");
		System.out.println();
		
		System.out.printf("4.  AZ \t\t");
		System.out.printf("24. MO \t\t");
		System.out.printf("44. UT \t\t");
		System.out.println();
				
		System.out.printf("5.  CA \t\t");
		System.out.printf("25. MS \t\t");
		System.out.printf("45. VA \t\t");
		System.out.println();
		
		System.out.printf("6.  CO \t\t");
		System.out.printf("26. MT \t\t");
		System.out.printf("46. VT \t\t");
		System.out.println();
		
		System.out.printf("7.  CT \t\t");
		System.out.printf("27. NC \t\t");
		System.out.printf("47. WA \t\t");
		System.out.println();
		
		System.out.printf("8.  DE \t\t");
		System.out.printf("28. ND \t\t");
		System.out.printf("48. WI \t\t");
		System.out.println();
		
		System.out.printf("9.  FL \t\t");
		System.out.printf("29. NE \t\t");
		System.out.printf("49. WV \t\t");
		System.out.println();
		
		System.out.printf("10. GA \t\t");
		System.out.printf("30. NH \t\t");
		System.out.printf("50. WY \t\t");
		System.out.println();
		
		System.out.printf("11. HI \t\t");
		System.out.printf("31. NJ \t\t");
		System.out.printf("51. Washington, D.C.");
		System.out.println();
		
		System.out.printf("12. IA \t\t");
		System.out.printf("32. NM \t\t");
		System.out.printf("52. US Virgin Islands");
		System.out.println();
		
		System.out.printf("13. ID \t\t");
		System.out.printf("33. NV \t\t");
		System.out.println();
		
		System.out.printf("14. IL \t\t");
		System.out.printf("34. NY \t\t");
		System.out.println();
		
		System.out.printf("15. IN \t\t");
		System.out.printf("35. OH \t\t");
		System.out.println();
		
		System.out.printf("16. KS \t\t");
		System.out.printf("36. OK \t\t");
		System.out.println();
		
		System.out.printf("17. KY \t\t");
		System.out.printf("37. OR \t\t");
		System.out.println();
		
		System.out.printf("18. LA \t\t");
		System.out.printf("38. PA \t\t");
		System.out.println();
		
		System.out.printf("19. MA \t\t");
		System.out.printf("39. RI \t\t");
		System.out.println();
		
		System.out.printf("20. MD \t\t");
		System.out.printf("40. SC \t\t");
		System.out.println();
		System.out.printf("Please enter your choice: ");
	}
	
	
	/*
	 * Name:	DisplayTicket()
	 * Input:	int numbers[]: array of numbers on a single lottery ticket
	 * Process:	Display numbers on ticket with a space in between. Display 
	 * 			Powerball number off to the side separately.
	 * Output:	none
	 */
	public static void DisplayTicket(int[] numbers){
		for (int col = 0; col < numbers.length; col++){
			if (col == 5)
				System.out.printf(" Powerball: ");
			System.out.printf("%2d ", numbers[col]);
		}
	}

	/*
	 * Name:	GatherOption()
	 * Input:	int validMin, int validMax: set range of appropriate values
	 * Process:	Gather user input as string. Parse for integer value.
	 * 			Throw InvalidActivityException if not acceptable value.
	 * 			Displays invalid input error message.
	 * Output:	int menuChoice
	 */
	public static int GatherOption(int validMin, int validMax){
		int menuChoice = 0;
		String inputLine;
		@SuppressWarnings("resource")
		Scanner myInput = new Scanner(System.in);
		inputLine = myInput.nextLine();
		try {
			menuChoice = Integer.parseInt(inputLine);
			if (menuChoice < validMin || menuChoice > validMax){
				System.out.println("Please enter a number between " + validMin + " and " + validMax + ".");
				System.out.printf("Please enter your choice: ");
				return 0;
			}
					
		} catch (NumberFormatException e) {
			System.out.println("Please enter a number between " + validMin + " and " + validMax + ".");
			System.out.printf("Please enter your choice: ");
		}
		return menuChoice;
	}
	
	/*
	 * Name:	GenerateRandomTicket()
	 * Input:	int numbers[]: array to store numbers for single lottery ticket
	 * Process:	Generate six random numbers. After the first number, verify that
	 * 			each successive number generated does not match an existing number
	 * 			on this ticket.
	 * Output:	none
	 */
	public static void GenerateRandomTicket(int[] numbers){
		for (int i = 0; i < numbers.length; i++){
			numbers[i] = 1 + (int)(Math.random() * ((59 - 1) + 1));
			
			// numbers 1 through five
			if (i > 0 && i < numbers.length - 1){
				// check each number. cannot duplicate any existing numbers
				for (int j = 0; j < i; j++){
					// re-randomize current number
					while (numbers[j] == numbers[i]){
						numbers[i] = 1 + (int)(Math.random() * ((59 - 1) + 1));
					}
				} // end check each number
			} // end 1 through 5
			
			// powerball
			if (i == numbers.length - 1){
				while (numbers[i] >= 35){
					numbers[i] = 1 + (int)(Math.random() * ((35 - 1) + 1));
				}
			}
		} // end randomize each number
	}
	
	/*
	 * Name:	PrintLumpSum()
	 * Input:	double lumpRate: state-based rate of taxation for lump sum payout
	 * 			double totalWinnings
	 * Process:	Calculate and display federal tax, state tax, gross and net prize
	 * 			for lump sum payout.
	 * Output:	none
	 */
	public static void PrintLumpSum(double stateTaxRate, double prizeAmount){
		double netPrize = 0, federalTaxes, stateTaxes;
		federalTaxes = prizeAmount * federalTaxRate;
		stateTaxes = prizeAmount * stateTaxRate;
		netPrize = prizeAmount - (federalTaxes + stateTaxes);

		System.out.printf("Gross Prize:     $%,14.2f\n", prizeAmount);
		System.out.printf("Federal Tax:     $%,14.2f\n", federalTaxes);
		System.out.printf("State Tax:       $%,14.2f\n", stateTaxes);
		System.out.printf("Net Prize:       $%,14.2f\n", netPrize);
	}
	
	/*
	 * Name:	Print30YearPayout()
	 * Input:	double payoutRate:	state-based rate of taxation 
	 * 			for 30 year payout
	 * 			double totalWinnings
	 * Process:	Calculate and display federal tax, state tax, gross and net prize
	 * 			for 30 year payout.
	 * Output:	none
	 */
	public static void Print30YearPayout(double stateTaxRate, double prizeAmount){
		double netPrize, federalTaxes, stateTaxes;
		double grossPayment = prizeAmount / 30;
		federalTaxes = grossPayment * federalTaxRate;
		stateTaxes = grossPayment * stateTaxRate;
		netPrize = grossPayment - (federalTaxes + stateTaxes);
		
		System.out.printf("Gross Prize:       $%,14.2f\n", prizeAmount);
		System.out.printf("Avg Annual Pmt:    $%,14.2f\n", grossPayment);
		System.out.printf("Federal Tax / Pmt: $%,14.2f\n", federalTaxes);
		System.out.printf("State Tax / Pmt:   $%,14.2f\n", stateTaxes);
		System.out.printf("Net Annual Pmt:    $%,14.2f\n", netPrize);
		System.out.println();
		System.out.printf("After 30 Payments: $%,14.2f\n", netPrize * 30);
		
	}
	
	/*
	 * Name:	SetStateTaxRate()
	 * Input:	int state: 1 through 52 
	 * Process:	Gather user input to determine state of residence and set
	 * 			state tax rate.
	 * Output:	double stateTaxRate
	 */
	public static double SetStateTaxRate(int state){
		double taxRate = 0.0;
		if (state == 8 || state == 9 || state == 30 || state == 38 || state == 41 || state == 42 || state == 43 || state == 47 || state == 52)
			taxRate = 0.0;
		else if (state == 15)
			taxRate = 0.034;
		else if (state == 6 || state == 24 || state == 36 || state == 45)
			taxRate = 0.04;
		else if (state == 22)
			 taxRate = 0.0435;
		else if (state == 4 || state == 20){ // AZ and MD have special rules
			System.out.printf("\nAre you a resident of ");
			if (state == 4)
				System.out.println("Arizona?");
			else
				System.out.println("Maryland?");
			System.out.println("1. Yes\n2. No");
			System.out.printf("Please enter your choice: ");
			int menuChoice = 0;
			while (menuChoice == 0){
				try {
					menuChoice = GatherOption(1,2);
				} catch (NumberFormatException e){
					menuChoice = 0;
				}
			}
			if (menuChoice == 1)
				if (state == 4)
					taxRate = 0.05;
				else
					taxRate = 0.085;
			else
				if (state == 4)
					taxRate = 0.06;
				else
					taxRate = 0.0675;
		} // end special rules for AZ and MD
		else if (state == 12 || state == 14 || state == 16 || state == 18 || state == 19 || state == 21 || state == 29)
			taxRate = 0.05;
		else if (state == 28)
			taxRate = 0.054;
		else if (state == 10 || state == 17 || state == 32 || state == 35 || state == 46)
			taxRate = 0.06;
		else if (state == 49)
			taxRate = 0.065;
		else if (state == 7)
			taxRate = 0.067;
		else if (state == 26)
			taxRate = 0.069;
		else if (state == 3 || state == 27 || state == 39 || state == 40)
			taxRate = 0.07;
		else if (state == 23)
			taxRate = 0.0725;
		else if (state == 48)
			taxRate = 0.075;
		else if (state == 13)
			taxRate = 0.078;
		else if (state == 37)
			taxRate = 0.08;
		else if (state == 51)
			taxRate = 0.085;
		else if (state == 34)
			taxRate = 0.0897;
		else if (state == 31)
			taxRate = 0.108;
		else
			System.out.println("Your tax rate is unknown. Please check with your state's revenue board.");
		return taxRate;
	}
	
	/*
	 * Name:	WriteToFile()
	 * Input:	int numbers[][]: 2d array of lottery tickets
	 * Process:	Write to text file as specified in "path" variable 
	 * Output:	none
	 */
	public static void WriteToFile(int numbers[][]) throws FileNotFoundException
	{
		File lotteryNumbers = new File (PATH);
		PrintWriter pw = new PrintWriter(new FileOutputStream(lotteryNumbers, false /*append == false */));

		for (int row = 0; row < numbers.length; row++)
		{
			pw.printf("Ticket %d: \t", row + 1);
			pw.printf(numbers[row][0]
					+ "\t" + numbers[row][1]
					+ "\t" + numbers[row][2]
					+ "\t" + numbers[row][3]
					+ "\t" + numbers[row][4]
					+ "\t Powerball: " + numbers[row][5] + "\r\n");
		}
		pw.close();
	}
	
}