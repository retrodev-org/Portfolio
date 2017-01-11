import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import javax.swing.JOptionPane;

/* File Name:		ExceptionHandling.java
 * Name:			James Walker Holland
 * Course:			COP 2800 at Valencia College
 * Instructor:		Mahendra Gossai
 * Description:		Prompts user to choose whether to enter new clients into toll
 * 					system "database." If yes, enter number of clients and proceed 
 * 					through name, address, and initial balance.
 * 					If no, read file to determine current status of clients and 
 * 					run simulation of random drivers using tolls 5 times.
 * 					Prompt if balance is below $20 and ask if balance should be 
 * 					renewed by default amount. (Default amount is $20 or a negative 
 * 					balance plus an additional $20.)
 * Team Members:	None.
 * Notes:			2014/03/26	Project start
 * 					2014/03/28	Finished adding methods
 * 								Update to include low balance threshold
 * 
 * The clientList.txt file should be placed in C:\\COP2800\\A09, 
 * or you can change the path in the declaration of public static variables.
 */
public class ExceptionHandling
{
	public static final int NAME = 0;
	public static final int STREET = 1;
	public static final int CITY = 2;
	public static final int STATE = 3;
	public static final int ZIP_CODE = 4;
	public static final int FIELDS = 5;
	public static final int BALANCE = 5;
	public static final double INITIAL_AMOUNT = 30.00;
	public static final double TOLL_COST = 5.00;
	public static final double LOW_BALANCE_THRESHOLD = 20.00;
	public static final String PATH = "C:\\COP2800\\A09\\clientList.txt";
	
	public static void main(String[] args) throws FileNotFoundException
	{
		int dialogButton = JOptionPane.YES_NO_OPTION;
		dialogButton = JOptionPane.showConfirmDialog (null, 
			"Would you like to add clients to the list?",
			"Welcome to the SunPass Toll Tracker", 
			dialogButton);
		if (dialogButton != 1)
		{
			AddClients();
		}
		
		int numberOfClientsTotal = FindNumberOfLines();
		System.out.println("There are " + numberOfClientsTotal + " clients registered.");
		
		String[][] clientInfo = new String [numberOfClientsTotal][FIELDS];
		double[] clientBalance = new double [numberOfClientsTotal];
		
		// Read from file
		ReadFromFile(clientInfo, clientBalance, numberOfClientsTotal);
		
		// Simulate cars at random using tolls
		for (int i = 0; i < FIELDS; i++) // Used FIELDS just out of convenience
		{
			DriveOnTollRoads(clientBalance, clientInfo);
		}
		
		// Write new client information
		/*
		 * Not the best way to handle this, but I don't know yet how to
		 * replace text at the end of a specific line. On a small scale, 
		 * this works.
		 */
		OverwriteClientInfo(clientInfo, clientBalance, numberOfClientsTotal);
	}
	
	public static void AddClients()
	{
		// Ask number of clients to set size for 2D client array
		int numberOfNewClients = Integer.parseInt(JOptionPane.showInputDialog(
				"How many clients would you like to add?"
				+ "\nTo skip adding clients, enter 0"));
		
		String[][] clientInfo = new String[numberOfNewClients][FIELDS];
		double[] clientBalance = new double[numberOfNewClients];
		/*
		 * clientInfo[ID][0] = Name
		 * clientInfo[ID][1] = Street Address
		 * clientInfo[ID][2] = City
		 * clientInfo[ID][3] = State
		 * clientInfo[ID][4] = Zip Code
		 */

		if (numberOfNewClients > 0)
		{
			// Gather client info
			GatherClientInfo(clientInfo, numberOfNewClients, clientBalance);
					
			// Write client into to file
			try {
				WriteClientInfo(clientInfo, clientBalance, numberOfNewClients);
			} catch(FileNotFoundException notFound) {
				System.out.println("\n\nError: Unable to find or create client list.\nPlease verify directory and filename.\n\n");
			}
		}
	}
	
	public static void DriveOnTollRoads(double clientBalance[], String clientInfo[][])
	{
		int driverNumber = (int)(Math.random() * FindNumberOfLines());
		int tollsUsed = (int)(1 + Math.random() * 10);
		
		System.out.println((clientInfo[driverNumber][NAME]) + " used " + tollsUsed + " tolls.");
		
		clientBalance[driverNumber] -= tollsUsed * TOLL_COST;
		
		
		if (clientBalance[driverNumber] < LOW_BALANCE_THRESHOLD)
		{
			double rechargeAmount;
			
			if (clientBalance[driverNumber] < 0.0)
			{
				rechargeAmount = (clientBalance[driverNumber] * -1) + 20.00;
			} else {
				rechargeAmount = 20.00;
			}
			
			clientBalance[driverNumber] += Double.parseDouble(JOptionPane.showInputDialog(
					"Balance for " + clientInfo[driverNumber][NAME] + 
					" has reached $" + clientBalance[driverNumber] + 
					". \nWould you like to recharge the account?\n" + 
					"(Enter 0 for No.)", rechargeAmount));
			System.out.println("Balance for " + clientInfo[driverNumber][NAME] + 
					" is $" + clientBalance[driverNumber]);
		}
			
	}
		
	public static int FindNumberOfLines()
	{
		int lineNumber = 0;

		try {
			File clientList = new File (PATH);
			FileReader clients = new FileReader(clientList);
			LineNumberReader lnr = new LineNumberReader(clients);
			
			while (lnr.readLine() != null)
			{
				lineNumber++;
			}
			
			lnr.close();
		} catch(FileNotFoundException notFound) {
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lineNumber;
	}
	
	public static void GatherClientInfo(String clientInfo[][], int numberOfClients, double clientBalance[])
	{
		for (int i = 0; i < numberOfClients; i++)
		{
			clientInfo[i][NAME] = JOptionPane.showInputDialog("Please enter name for client " 
					+ (i + 1) + ":");
			clientInfo[i][STREET] = JOptionPane.showInputDialog("Thank you.\nPlease enter street address for "
					+ clientInfo[i][NAME] + ":\n(ex: 123 E 4th St.)");
			clientInfo[i][CITY] = JOptionPane.showInputDialog("Please enter city for " 
					+ clientInfo[i][NAME] + ":");
			clientInfo[i][STATE] = JOptionPane.showInputDialog("Please enter state for " 
					+ clientInfo[i][NAME] + ".");
			clientInfo[i][ZIP_CODE] = JOptionPane.showInputDialog("Please enter zip code for " 
					+ clientInfo[i][NAME] + ":");
			clientBalance[i] = Double.parseDouble(JOptionPane.showInputDialog("Beginning balance for " 
					+ clientInfo[i][NAME] + ":", INITIAL_AMOUNT));
		}
	}
	
	public static void ReadFromFile(String clientInfo[][], double clientBalance[], int numberOfLines)
	{
		try {
			File clientList = new File ("C:\\COP2800\\A09\\clientList.txt");
			FileReader clients = new FileReader(clientList);
			BufferedReader buffer = new BufferedReader(clients);
			
			for (int i = 0; i < numberOfLines; i++)
			{
				String stringBuffer = buffer.readLine();
				String[] stringTemp  = stringBuffer.split("\t");
				
				for (int j = 0; j < FIELDS; j++)
				{
					clientInfo[i][j] = stringTemp[j];
				}
				
				clientBalance[i] = Double.parseDouble(stringTemp[BALANCE]);
			}
			
			buffer.close();
		} catch(FileNotFoundException notFound) {
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void WriteClientInfo(String clientInfo[][], double clientBalance[], int numberOfClients) throws FileNotFoundException
	{
		File clientList = new File (PATH);
		PrintWriter pw = new PrintWriter(new FileOutputStream(clientList, true /*append == true */));

		for (int i = 0; i < numberOfClients; i++)
		{
			pw.printf(clientInfo[i][NAME]
					+ "\t" + clientInfo[i][STREET]
					+ "\t" + clientInfo[i][CITY]
					+ "\t" + clientInfo[i][STATE]
					+ "\t" + clientInfo[i][ZIP_CODE]
					+ "\t" + clientBalance[i] + "\r\n");
		}
		pw.close();
	}
	
	public static void OverwriteClientInfo(String clientInfo[][], double clientBalance[], int numberOfClients) throws FileNotFoundException
	{
		File clientList = new File (PATH);
		PrintWriter pw = new PrintWriter(new FileOutputStream(clientList, false /*append == false */));

		for (int i = 0; i < numberOfClients; i++)
		{
			pw.printf(clientInfo[i][NAME]
					+ "\t" + clientInfo[i][STREET]
					+ "\t" + clientInfo[i][CITY]
					+ "\t" + clientInfo[i][STATE]
					+ "\t" + clientInfo[i][ZIP_CODE]
					+ "\t" + clientBalance[i] + "\r\n");
		}
		pw.close();
	}
}
