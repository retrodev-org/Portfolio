/* File Name:		RandomNumbers.java
 * Name:			James Walker Holland
 * Course:			COP 2800 at Valencia College
 * Instructor:		Mahendra Gossai
 * Description:		
 * Team Members:	None.
 * Notes:			2014/01/23	Project start
 */

import java.util.Random;

public class RandomNumbers {
	public static void main(String[] args){
		Random generator = new Random();
		
		for(int i = 0; i < 10; i++)
		{
			System.out.print( generator.nextInt(10) + " ");
		};
	}
}